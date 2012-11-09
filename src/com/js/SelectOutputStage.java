package com.js;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

public class SelectOutputStage extends ActivityEx implements RadioGroup.OnCheckedChangeListener {
    final Context context = this;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.output_stage);

        configureOutputStageSelector();
        showDisclaimer();
    }

    private void configureOutputStageSelector() {
        RadioGroup outputStageSelection = (RadioGroup)findViewById(R.id.outputStageSelection);
        outputStageSelection.setOnCheckedChangeListener(this);
    }

    private void showDisclaimer() {
        CharSequence title = "Disclaimer/SAFETY WARNING!";
        CharSequence message = "Tube amplifiers contain dangerous high voltages that can KILL YOU! If you don't " +
                "have experience with the proper safety precautions close this application and " +
                "consult a professional technician. By clicking OK you agree not to hold the " +
                "creator of this application liable for any injury.";

        showSimpleAlertDialog(context, title, message);
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