package com.tubebiascalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.*;

import java.util.List;

public class ActivityEx extends Activity {
    Spinner findSpinnerById(int id) {
        return (Spinner)findViewById(id);
    }
    EditText findEditTextById(int id) {
        return (EditText)findViewById(id);
    }
    TextView findTextViewById(int id) {
        return (TextView)findViewById(id);
    }
    Button findButtonById(int id) {
        return (Button)findViewById(id);
    }
    SeekBar findSeekBarById(int id) {
        return (SeekBar)findViewById(id);
    }

    void setTextViewText(int id, CharSequence text){
        TextView textView = findTextViewById(id);
        textView.setText(text);
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

    protected void setSpinnerDropDownItems(Context context, int spinnerId, List<String> dropDownItems, int selection) {
        Spinner spinner = findSpinnerById(spinnerId);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_dropdown_item, dropDownItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(selection);
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

    protected void showSimpleAlertDialog(Context context, CharSequence title, CharSequence message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}
