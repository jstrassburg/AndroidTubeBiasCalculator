package com.js;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ActivityEx extends Activity {
    Spinner findSpinnerById(int id) {
        return (Spinner)findViewById(id);
    }
    EditText findEditTextById(int id) {
        return (EditText)findViewById(id);
    }
    Button findButtonById(int id) {
        return (Button)findViewById(id);
    }

    protected Boolean validateEditTextNotEmpty(int id, CharSequence message){
        EditText editText = findEditTextById(id);
        String value = editText.getText().toString();
        if (value.trim().length() == 0){
            editText.setError(message);
            return false;
        }
        editText.setError(null);
        return true;
    }

    protected Boolean validateEditTextFloatNotZero(int id, CharSequence message){
        EditText editText = findEditTextById(id);
        String value = editText.getText().toString();
        Float floatValue = Float.parseFloat(value);
        if (floatValue == 0){
            editText.setError(message);
            return false;
        }
        editText.setError(null);
        return true;
    }
}
