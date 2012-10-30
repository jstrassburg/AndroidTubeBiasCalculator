package com.js;

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

public abstract class BiasCalculator extends ActivityEx implements Button.OnClickListener {
    final Context context = this;
    protected Map<String, Float> _tubeMap;
    private int _layoutResourceId;

    public BiasCalculator(int layoutResourceId) {
        _tubeMap = new HashMap<String, Float>();
        _tubeMap.put("6L6GC", 30.0f);
        _tubeMap.put("EL84", 12.0f);

        _layoutResourceId = layoutResourceId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(_layoutResourceId);

        ConfigureTubeSelector();
        ConfigureCalculateButton();
    }

    protected void ConfigureCalculateButton() {
        Button calculateButton = findButtonById(R.id.calculateButton);
        calculateButton.setOnClickListener(this);
    }

    protected int getNumberOfTubes() {
        Spinner numberOfTubesSelector = findSpinnerById(R.id.numberOfTubesSelector);
        return Integer.parseInt((String) numberOfTubesSelector.getSelectedItem());
    }

    protected Float getSelectedTubePowerDissipation(){
        Spinner tubeSelector = findSpinnerById(R.id.tubeSelector);
        String tube = (String)tubeSelector.getSelectedItem();
        return _tubeMap.get(tube);
    }

    protected Float getEditBoxFloat(int id){
        EditText editText = (EditText)findViewById(id);
        String value = editText.getText().toString();

        return Float.parseFloat(value);
    }

    protected void ConfigureTubeSelector() {
        Spinner tubeSelector = findSpinnerById(R.id.tubeSelector);
        List<String> tubes = new ArrayList<String>(_tubeMap.keySet());
        ArrayAdapter<String> tubeAdapter = new ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_dropdown_item, tubes);
        tubeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tubeSelector.setAdapter(tubeAdapter);
    }

    @Override
    public void onClick(View view) {
        if (!fieldsAreValid()) return;

        Float maxPowerDissipation = getSelectedTubePowerDissipation();
        int numberOfTubes = getNumberOfTubes();

        calculateResultsAndStartResultsActivity(maxPowerDissipation, numberOfTubes);
    }

    protected abstract void calculateResultsAndStartResultsActivity(Float maxPowerDissipation, int numberOfTubes);

    protected boolean fieldsAreValid() {
        if (!validateEditTextNotEmpty(R.id.plateVoltage, "Plate voltage is required")) return false;
        if (!validateEditTextNotEmpty(R.id.screenResistor, "Screen resistor is required")) return false;
        if (!validateEditTextNotEmpty(R.id.screenDrop, "Screen resistor voltage drop is required")) return false;
        if (!validateEditTextNotEmpty(R.id.cathodeResistor, "Cathode resistor is required")) return false;
        if (!validateEditTextNotEmpty(R.id.cathodeDrop, "Cathode resistor voltage drop is required")) return false;

        if (!validateEditTextFloatNotZero(R.id.screenResistor, "Screen resistor must be > 0")) return false;
        if (!validateEditTextFloatNotZero(R.id.cathodeResistor, "Cathode resistor must be > 0")) return false;
        return true;
    }

    protected void startResultsActivity(Float idlePlateDissipation, Float percentageMaxDissipation) {
        Intent intent = new Intent(context, Results.class);
        intent.putExtra(ExtraDataKeys.IDLE_PLATE_DISSIPATION, idlePlateDissipation);
        intent.putExtra(ExtraDataKeys.PERCENTAGE_MAX_PLATE_DISSIPATION, percentageMaxDissipation);
        startActivity(intent);
    }
}
