package com.example.m2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import static android.text.TextUtils.isEmpty;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    ArrayList<TextInputLayout> gradeLayouts;
    final float GPA_LOWER_LIMIT = 60;
    final float GPA_MID_LIMIT = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gradeLayouts = new ArrayList<>();
        gradeLayouts.add(findViewById(R.id.et_1));
        gradeLayouts.add(findViewById(R.id.et_5));
        gradeLayouts.add(findViewById(R.id.et_2));
        gradeLayouts.add(findViewById(R.id.et_4));
        gradeLayouts.add(findViewById(R.id.et_3));

        Iterator<TextInputLayout> iter = gradeLayouts.iterator();
        while (iter.hasNext()) {
            TextInputEditText tbCourse = (TextInputEditText) iter.next().getEditText();
            if (tbCourse != null) {
                tbCourse.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }

                    // ... (Your TextWatcher implementation)
                });
            }
        }
    }

    private boolean isInvalidOrEmpty(ArrayList<TextInputLayout> layouts) {
        Iterator<TextInputLayout> iterator = layouts.iterator();
        boolean isInvalid = false;

        while (iterator.hasNext()) {
            TextInputLayout layout = iterator.next();
            String strText = layout.getEditText().getText().toString();

            if (isEmpty(strText)) {
                layout.setErrorEnabled(true);
                layout.setError("Input required!");
                isInvalid = true;
            } else {
                try {
                    float gradeVal = Float.parseFloat(strText);
                    if (gradeVal < 0 || gradeVal > 100) {
                        layout.setErrorEnabled(true);
                        layout.setError("Value should be between 0-100!");
                        isInvalid = true;
                    } else {
                        layout.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    // Handle the case when the input is not a valid number
                    layout.setErrorEnabled(true);
                    layout.setError("Invalid input!");
                    isInvalid = true;
                }
            }
        }
        return isInvalid;
    }

    private void setColor(View view, float gpa) {
        if (gpa <= GPA_LOWER_LIMIT) view.setBackgroundResource(R.color.red);
        else if (gpa <= GPA_MID_LIMIT) view.setBackgroundResource(R.color.yellow);
        else view.setBackgroundResource(R.color.green);
    }

    public void computeGPA(View view) {
        if (!isInvalidOrEmpty(gradeLayouts)) {
            int total = 0;
            Iterator<TextInputLayout> iterator = gradeLayouts.iterator();
            while (iterator.hasNext()) {
                TextInputLayout layout = iterator.next();
                total += Float.parseFloat(layout.getEditText().getText().toString());
                layout.getEditText().setText(""); // Clear the textboxes
            }
            float gpa = total / 5.0f; // Use floating-point division
            TextView textView7 = findViewById(R.id.tv7);
            textView7.setText(String.format("Your GPA is %.2f", gpa));
            Button btnGPA = findViewById(R.id.GPA_button);
            btnGPA.setText("Clear Form");

            // Set Background color as per GPA value
            ConstraintLayout conLayout = findViewById(R.id.constraint1);
            setColor(conLayout, gpa);
        }
    }
}