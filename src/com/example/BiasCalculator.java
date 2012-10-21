package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.TooManyListenersException;

public class BiasCalculator extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button pickTube = (Button)findViewById(R.id.pickTube);
        pickTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup tubeGroup = (RadioGroup)findViewById(R.id.radioTube);
                int selectedId = tubeGroup.getCheckedRadioButtonId();
                RadioButton tubeSelection = (RadioButton)findViewById(selectedId);
                Toast.makeText(BiasCalculator.this, tubeSelection.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
