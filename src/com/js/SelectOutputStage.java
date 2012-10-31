package com.js;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

public class SelectOutputStage extends Activity implements RadioGroup.OnCheckedChangeListener {
    final Context context = this;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.output_stage);

        ConfigureOutputStageSelector();
    }

    private void ConfigureOutputStageSelector() {
        RadioGroup outputStageSelection = (RadioGroup)findViewById(R.id.outputStageSelection);
        outputStageSelection.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
         startCathodeCurrentBiasCalculatorActivity(i);
    }

    private void startCathodeCurrentBiasCalculatorActivity(int id) {
        Intent intent = new Intent(context, CathodeCurrentBiasCalculator.class);
        intent.putExtra(ExtraDataKeys.OUTPUT_STAGE_CONFIGURATION, id);
        startActivity(intent);
    }
}