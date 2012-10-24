package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiasCalculator extends Activity implements Button.OnClickListener {
    final Context context = this;

    private Map<String, Float> _tubeMap;

    public BiasCalculator() {
        _tubeMap = new HashMap<String, Float>();
        _tubeMap.put("6L6GC", 30.0f);
        _tubeMap.put("EL84", 12.0f);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ConfigureTubeSelector();
        ConfigureCalculateButton();
    }

    private void ConfigureCalculateButton() {
        Button calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(this);
    }

    private int getNumberOfTubes() {
        Spinner numberOfTubesSelector = (Spinner)findViewById(R.id.numberOfTubesSelector);
        return Integer.parseInt((String)numberOfTubesSelector.getSelectedItem());
    }

    private Float getSelectedTubePowerDissipation(){
        Spinner tubeSelector = (Spinner)findViewById(R.id.tubeSelector);
        String tube = (String)tubeSelector.getSelectedItem();
        return _tubeMap.get(tube);
    }

    private Float getEditBoxFloat(int id){
        EditText editText = (EditText)findViewById(id);
        return Float.parseFloat(editText.getText().toString());
    }

    private void ConfigureTubeSelector() {
        Spinner tubeSelector = (Spinner)findViewById(R.id.tubeSelector);
        List<String> tubes = new ArrayList<String>(_tubeMap.keySet());
        ArrayAdapter<String> tubeAdapter = new ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_dropdown_item, tubes);
        tubeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tubeSelector.setAdapter(tubeAdapter);
    }

    @Override
    public void onClick(View view) {
        Float maxPowerDissipation = getSelectedTubePowerDissipation();
        int numberOfTubes = getNumberOfTubes();
        Float plateVoltage = getEditBoxFloat(R.id.plateVoltage);
        Float screenResistor = getEditBoxFloat(R.id.screenResistor);
        Float screenDrop = getEditBoxFloat(R.id.screenDrop);
        Float cathodeResistor = getEditBoxFloat(R.id.cathodeResistor);
        Float cathodeDrop = getEditBoxFloat(R.id.cathodeDrop);

        //TODO: Validate data and display Toast for invalid then return

        Float cathodeCurrent = cathodeDrop / cathodeResistor;
        Float screenCurrent = screenDrop / screenResistor;
        Float plateCurrent = cathodeCurrent - screenCurrent;
        Float plateDrop = plateVoltage - cathodeDrop;
        Float idlePlateDissipation = plateDrop*plateCurrent/numberOfTubes;
        Float percentageMaxDissipation =  100.0f*idlePlateDissipation/maxPowerDissipation;

        Intent intent = new Intent(context, Results.class);
        intent.putExtra(ExtraDataKeys.IDLE_PLATE_DISSIPATION, idlePlateDissipation);
        intent.putExtra(ExtraDataKeys.PERCENTAGE_MAX_PLATE_DISSIPATION, percentageMaxDissipation);
        startActivity(intent);
    }
}
