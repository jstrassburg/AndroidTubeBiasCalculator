package com.js;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

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

    protected String getEditBoxText(int id) {
        EditText editText = findEditTextById(id);
        return editText.getText().toString();
    }
    protected Float getEditBoxFloat(int id){
        return Float.parseFloat(getEditBoxText(id));
    }

    protected String getSpinnerSelectedItem(int id) {
        Spinner spinner = findSpinnerById(id);
        return spinner.getSelectedItem().toString();
    }
    protected Integer getSpinnerSelectedItemInteger(int id) {
        return Integer.parseInt(getSpinnerSelectedItem(id));
    }

    protected void setSpinnerDropDownItems(Context context, int spinnerId, List<String> dropDownItems) {
        Spinner spinner = findSpinnerById(spinnerId);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_dropdown_item, dropDownItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    protected void setButtonOnClickListener(int buttonId, View.OnClickListener onClickListener) {
        Button button = findButtonById(buttonId);
        button.setOnClickListener(onClickListener);
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
