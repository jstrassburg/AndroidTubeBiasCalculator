package com.js;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

public class SelectOutputStage extends Activity implements RadioGroup.OnCheckedChangeListener {
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
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle("Disclaimer/SAFETY WARNING!")
                    .setCancelable(false)
                    .setMessage("Tube amplifiers contain dangerous high voltages that can KILL YOU! If you don't " +
                                "have experience with the proper safety precautions close this application and " +
                                "consult a professional technician. By clicking OK you agree not to hold the " +
                                "creator of this application liable for any injury.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    }).show();
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