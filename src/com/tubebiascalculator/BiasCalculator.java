package com.tubebiascalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        _tubeMap.put("6V6", 14.0f);
        _tubeMap.put("EL84", 12.0f);
        _tubeMap.put("EL34", 25.0f);

        _layoutResourceId = layoutResourceId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(_layoutResourceId);

        configureTubeSelector();
        configureNumberOfTubesSelector();
        configureCalculateButton();
    }

    protected void configureCalculateButton() {
        setButtonOnClickListener(R.id.calculateButton, this);
    }

    protected int getNumberOfTubes() {
        return getSpinnerSelectedItemInteger(R.id.numberOfTubesSelector);
    }

    protected Float getSelectedTubePowerDissipation() {
        return _tubeMap.get(getSpinnerSelectedItem(R.id.tubeSelector));
    }

    protected void configureTubeSelector() {
        setSpinnerDropDownItems(
            context, R.id.tubeSelector, new ArrayList<String>(_tubeMap.keySet()), 0);
    }

    protected void configureNumberOfTubesSelector() {
        List<String> numberOfTubes = new ArrayList<String>();
        Integer outputStageConfigurationId = getOutputStageConfigurationId();

        if (outputStageConfigurationId == R.id.singleEnded) {
            numberOfTubes.add("1");
            numberOfTubes.add("2");
        }
        else if (outputStageConfigurationId == R.id.pushPull) {
            numberOfTubes.add("1");
            numberOfTubes.add("2");
            numberOfTubes.add("4");
            numberOfTubes.add("6");
        }

        int selection = outputStageConfigurationId == R.id.pushPull ? 1 : 0;
        setSpinnerDropDownItems(
            context, R.id.numberOfTubesSelector, numberOfTubes, selection);
    }

    private Integer getOutputStageConfigurationId() {
        return getIntent().getIntExtra(ExtraDataKeys.OUTPUT_STAGE_CONFIGURATION, R.id.singleEnded);
    }

    @Override
    public void onClick(View view) {
        if (!fieldsAreValid()) return;

        Float maxPowerDissipation = getSelectedTubePowerDissipation();
        calculateResultsAndStartResultsActivity(maxPowerDissipation, getNumberOfTubes());
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

        Integer outputStageConfigurationId = getOutputStageConfigurationId();
        Integer lowRangePercentage = outputStageConfigurationId == R.id.pushPull ? 50 : 70;
        Integer highRangePercentage = outputStageConfigurationId == R.id.pushPull ? 70 : 90;

        intent.putExtra(ExtraDataKeys.RESULTS_LOW_RANGE_PERCENTAGE, lowRangePercentage);
        intent.putExtra(ExtraDataKeys.RESULTS_HIGH_RANGE_PERCENTAGE, highRangePercentage);
        startActivity(intent);
    }
}
