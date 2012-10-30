package com.js;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
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
        setButtonOnClickListener(R.id.calculateButton, this);
    }

    protected int getNumberOfTubes() {
        return getSpinnerSelectedItemInteger(R.id.numberOfTubesSelector);
    }

    protected Float getSelectedTubePowerDissipation() {
        return _tubeMap.get(getSpinnerSelectedItem(R.id.tubeSelector));
    }

    protected void ConfigureTubeSelector() {
        setSpinnerDropDownItems(
            context, R.id.tubeSelector, new ArrayList<String>(_tubeMap.keySet()));
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
        startActivity(intent);
    }
}
