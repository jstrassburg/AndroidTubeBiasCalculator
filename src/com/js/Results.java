package com.js;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Results extends Activity implements SeekBar.OnSeekBarChangeListener {
    private float _idlePlateDissipation = 0.0f;
    private float _percentageMaxPlateDissipation = 0.0f;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        GetIntentExtraData();
        SetupBiasIndicatorSeekBar();
        SetTextViews();
    }

    private void SetTextViews() {
        DecimalFormat decimalFormat = new DecimalFormat("######.##");
        TextView idlePlateTextView = (TextView)findViewById(R.id.idlePlateDissipation);
        idlePlateTextView.setText(
                "Idle plate dissipation: " + decimalFormat.format(_idlePlateDissipation) + " Watts");
        TextView percentageMaxTextView = (TextView)findViewById(R.id.percentageMaxPlateDissipation);
        percentageMaxTextView.setText(
                "Percentage max plate dissipation: " + decimalFormat.format(_percentageMaxPlateDissipation) + "%");
    }

    private void GetIntentExtraData() {
        Intent intent = getIntent();
        _idlePlateDissipation = intent.getFloatExtra(
                ExtraDataKeys.IDLE_PLATE_DISSIPATION, 0.0f);
        _percentageMaxPlateDissipation = intent.getFloatExtra(
                ExtraDataKeys.PERCENTAGE_MAX_PLATE_DISSIPATION, 0.0f);
    }

    private void SetupBiasIndicatorSeekBar() {
        SeekBar seekBar = (SeekBar)findViewById(R.id.biasIndicator);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setProgress((int)_percentageMaxPlateDissipation);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int progress = seekBar.getProgress();
        if (progress < 50)
            seekBar.setProgressDrawable(new ColorDrawable(Color.BLUE));
        else if (progress <= 70)
            seekBar.setProgressDrawable(new ColorDrawable(Color.YELLOW));
        else
            seekBar.setProgressDrawable(new ColorDrawable(Color.RED));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}