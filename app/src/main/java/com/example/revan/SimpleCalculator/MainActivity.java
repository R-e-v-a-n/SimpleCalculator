package com.example.revan.SimpleCalculator;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Locale;

import android.content.*;
import android.view.View;
import android.widget.*;

import java.text.*;

public class MainActivity extends AppCompatActivity {

    private EditText editDisplayHint;
    private EditText editDisplay;

    private Button negate;
    private Button clear;
    private Button allClear;
    private ImageButton backspace;

    private Button seven;
    private Button eight;
    private Button nine;
    private Button division;
    private Button four;
    private Button five;
    private Button six;
    private Button multiply;
    private Button one;
    private Button two;
    private Button three;
    private Button subtract;
    private Button decimal;
    private Button zero;
    private Button equals;
    private Button addition;

    ArrayList<Double> result = new ArrayList<>();

    private double number1;
    private double number2;

    private int currentOperation = 0;
    private int nextOperation;

    private final static int ADD      = 1;
    private final static int SUBTRACT = 2;
    private final static int MULTIPLY = 3;
    private final static int DIVISION = 4;
    private final static int EVAL     = 5;

    private final static String[] OPERATORS_STRING = {" ", "+", "-", "*", "÷"};

    private final static int DONT_CLEAR = 0;
    private final static int CLEAR      = 1;

    private int clearEditDisplay = CLEAR;

    private final static String FORMAT_PATTERN = "#0.####";
    private DecimalFormat formatter;

    private static final String APP_PREFERENCES = "MySettings";
    private static final String APP_PREFERENCES_RESULT = "Result";

    @Override
    protected void onStop(){
        super.onStop();

        String res = editDisplay.getText().toString();

        SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(APP_PREFERENCES_RESULT, res);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        editDisplay     = (EditText)    findViewById(R.id.editText);
        editDisplayHint = (EditText)    findViewById(R.id.editText1);

        negate      = (Button)      findViewById(R.id.buttonNegative);
        clear       = (Button)      findViewById(R.id.buttonCE);
        allClear    = (Button)      findViewById(R.id.buttonC);
        backspace   = (ImageButton) findViewById(R.id.buttonBack);

        seven       = (Button)      findViewById(R.id.button7);
        eight       = (Button)      findViewById(R.id.button8);
        nine        = (Button)      findViewById(R.id.button9);
        division    = (Button)      findViewById(R.id.buttonDiv);

        four        = (Button)      findViewById(R.id.button4);
        five        = (Button)      findViewById(R.id.button5);
        six         = (Button)      findViewById(R.id.button6);
        multiply    = (Button)      findViewById(R.id.buttonMul);

        one         = (Button)      findViewById(R.id.button1);
        two         = (Button)      findViewById(R.id.button2);
        three       = (Button)      findViewById(R.id.button3);
        subtract    = (Button)      findViewById(R.id.buttonSub);

        decimal    = (Button)       findViewById(R.id.buttonDecimal);
        zero       = (Button)       findViewById(R.id.button0);
        equals     = (Button)       findViewById(R.id.buttonEval);
        addition   = (Button)       findViewById(R.id.buttonAdd);

        formatter = new DecimalFormat(FORMAT_PATTERN, new DecimalFormatSymbols(Locale.ENGLISH));

        registerListeners();

        SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        if(settings.contains(APP_PREFERENCES_RESULT)) {
            editDisplay.setText(settings.getString(APP_PREFERENCES_RESULT, "0"));
            result.removeAll(result);
        }
    }

    private void registerListeners() {
        negate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!tryGetValue() || (number1 == 0)) { return; }
                editDisplay.setText(formatter.format(-1 * number1));
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editDisplay.setText("0");
                clearEditDisplay = CLEAR;
            }
        });

        allClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editDisplay.setText("0");
                editDisplayHint.setText("");
                clearEditDisplay = CLEAR;
                result.removeAll(result);
                currentOperation = 0;
                nextOperation = 0;
            }
        });

        backspace.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((!tryGetValue()) || (clearEditDisplay == CLEAR)) { return; }
                int minimumChars;

                minimumChars = number1 < 0 ? 2 : 1;

                String text = editDisplay.getText().toString();

                if (text.length() <= minimumChars) {
                    editDisplay.setText("0");
                    clearEditDisplay = CLEAR;
                    return;
                }
                editDisplay.setText(text.substring(0,text.length() - 1));
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doAppendNumber(7);
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doAppendNumber(8);
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doAppendNumber(9);
            }
        });

        division.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calcLogic(DIVISION);
            }
        });

        four.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doAppendNumber(4);
            }
        });

        five.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doAppendNumber(5);
            }
        });

        six.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doAppendNumber(6);
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calcLogic(MULTIPLY);
            }
        });

        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doAppendNumber(1);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doAppendNumber(2);
            }
        });

        three.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doAppendNumber(3);
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calcLogic(SUBTRACT);
            }
        });

        decimal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doAppendDecimalSeparator();
            }
        });

        zero.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doAppendNumber(0);
            }
        });

        equals.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calcLogic(EVAL);
            }
        });

        addition.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                calcLogic(ADD);
            }
        });
    }

    private boolean tryGetValue(){
        try{ // Check (INF, NAN, etc)
            number1 = Double.parseDouble(editDisplay.getText().toString());
        }
        catch (Exception e){
            return false;
        }
        return !(Double.isNaN(number1) || Double.isInfinite(number1));
    }

    private void doAppendNumber(Integer number){
        if ((number < 0) || (number > 9)) { return; }
        if (clearEditDisplay == CLEAR) {
            editDisplay.setText("");
        }
        clearEditDisplay = DONT_CLEAR;
        editDisplay.append(number.toString());
    }

    private void doAppendDecimalSeparator(){
        if (clearEditDisplay == CLEAR) {
            editDisplay.setText("0");
        } else {
            if (editDisplay.getText().toString().contains(".")) { return; }
        }
        clearEditDisplay = DONT_CLEAR;
        editDisplay.append(".");
    }

    private void calcLogic(int operator) {
        if (!tryGetValue()) { return; }

        if (clearEditDisplay != CLEAR) {
            result.add(number1);
            if ((currentOperation != 0) && (result.size() == 2)) {
                number1 = result.get(0);
                number2 = result.get(1);
                result.removeAll(result);

                switch (currentOperation) {
                    case ADD:
                        result.add(number1 + number2);
                        break;
                    case SUBTRACT:
                        result.add(number1 - number2);
                        break;
                    case MULTIPLY:
                        result.add(number1 * number2);
                        break;
                    case DIVISION:
                        result.add(number1 / number2);
                        break;
                }

                editDisplay.setText(formatter.format(result.get(0)));
            }
        } else if (currentOperation == 0){
            result.add(number1);
        }

        if (operator != EVAL) {
            editDisplayHint.setText(editDisplay.getText());
            editDisplayHint.append( OPERATORS_STRING[operator]);
            nextOperation = operator;
        } else {
            result.removeAll(result);
            nextOperation = 0;
            editDisplayHint.setText("");
        }
        clearEditDisplay = CLEAR;
        currentOperation = nextOperation;
    }
}
