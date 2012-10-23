package com.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.*;

public class BiasCalculator extends Activity {
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
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float maxPowerDissipation = getSelectedTubePowerDissipation();
                int numberOfTubes = getNumberOfTubes();
            }
        });
    }

    private int getNumberOfTubes() {
        Spinner numberOfTubesSelector = (Spinner)findViewById(R.id.numberOfTubesSelector);
        return Integer.getInteger((String)numberOfTubesSelector.getSelectedItem());
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
}
