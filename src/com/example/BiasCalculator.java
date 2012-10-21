package com.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.TooManyListenersException;

public class BiasCalculator extends Activity {
    final Context context = this;
    int whichTube = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button selectTubeButton = (Button)findViewById(R.id.selectTubeButton);
        selectTubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTube();
            }
        });
    }

    private void selectTube() {
        AlertDialog.Builder tubeSelector = new AlertDialog.Builder(context);
        tubeSelector.setTitle(R.string.selectTube);
        tubeSelector.setItems(R.array.tubes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                whichTube = i;
                Toast.makeText(context, Integer.toString(whichTube), Toast.LENGTH_SHORT).show();
            }
        });
        tubeSelector.setCancelable(false);
        tubeSelector.create();
        tubeSelector.show();
    }


}
