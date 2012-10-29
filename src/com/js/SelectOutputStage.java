package com.js;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

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
        if (i == R.id.pushPull)
            StartPushPullActivity();
        else if (i == R.id.singleEnded)
            Toast.makeText(context, "Single Ended", Toast.LENGTH_SHORT).show();
    }

    private void StartPushPullActivity() {
        Intent intent = new Intent(context, PushPullBiasCalculator.class);
        startActivity(intent);
    }
}