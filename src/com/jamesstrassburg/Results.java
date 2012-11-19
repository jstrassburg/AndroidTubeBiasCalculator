package com.jamesstrassburg;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Results extends ActivityEx implements SeekBar.OnSeekBarChangeListener {
    private float _idlePlateDissipation = 0.0f;
    private float _percentageMaxPlateDissipation = 0.0f;
    private int _lowRangePercentage = 0;
    private int _highRangePercentage = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        getIntentExtraData();
        setupBiasIndicatorSeekBar();
        setTextViews();
    }

    private void setTextViews() {
        DecimalFormat decimalFormat = new DecimalFormat("######.##");
        TextView idlePlateTextView = findTextViewById(R.id.idlePlateDissipation);
        idlePlateTextView.setText(
                "Idle plate dissipation: " + decimalFormat.format(_idlePlateDissipation) + " Watts");
        TextView percentageMaxTextView = findTextViewById(R.id.percentageMaxPlateDissipation);
        percentageMaxTextView.setText(
                "Percentage max plate dissipation: " + decimalFormat.format(_percentageMaxPlateDissipation) + "%");
    }

    private void getIntentExtraData() {
        Intent intent = getIntent();
        _idlePlateDissipation = intent.getFloatExtra(
                ExtraDataKeys.IDLE_PLATE_DISSIPATION, 0.0f);
        _percentageMaxPlateDissipation = intent.getFloatExtra(
                ExtraDataKeys.PERCENTAGE_MAX_PLATE_DISSIPATION, 0.0f);
        _lowRangePercentage = intent.getIntExtra(
                ExtraDataKeys.RESULTS_LOW_RANGE_PERCENTAGE, 0);
        _highRangePercentage = intent.getIntExtra(
                ExtraDataKeys.RESULTS_HIGH_RANGE_PERCENTAGE, 0);
    }

    private void setupBiasIndicatorSeekBar() {
        SeekBar seekBar = findSeekBarById(R.id.biasIndicator);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setProgress((int)_percentageMaxPlateDissipation);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int progress = seekBar.getProgress();
        if (progress < _lowRangePercentage) {
            seekBar.setProgressDrawable(new ColorDrawable(Color.BLUE));
            setTextViewText(R.id.biasDescription, "Your output stage is biased too cold.");
        }
        else if (progress <= _highRangePercentage){
            seekBar.setProgressDrawable(new ColorDrawable(Color.YELLOW));
            setTextViewText(R.id.biasDescription, "Your output stage is biased safely.");
        }
        else {
            seekBar.setProgressDrawable(new ColorDrawable(Color.RED));
            setTextViewText(R.id.biasDescription, "Your output stage is biased too hot.");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}