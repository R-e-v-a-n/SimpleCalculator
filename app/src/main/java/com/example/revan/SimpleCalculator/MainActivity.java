package com.example.revan.SimpleCalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Locale;

import android.content.*;
import android.view.View;
import android.widget.*;

import java.text.*;

public class MainActivity extends AppCompatActivity {

    EditText editDisplayHint;
    EditText editDisplay;

    Button negate;
    Button clear;
    Button allClear;
    ImageButton backspace;

    Button seven;
    Button eight;
    Button nine;
    Button division;
    Button four;
    Button five;
    Button six;
    Button multiply;
    Button one;
    Button two;
    Button three;
    Button subtract;
    Button decimal;
    Button zero;
    Button equals;
    Button addition;

    ArrayList<Double> result = new ArrayList<Double>();

    double number1;
    double number2;

    int currentOperation = 0;
    int nextOperation;

    final static int ADD      = 1;
    final static int SUBTRACT = 2;
    final static int MULTIPLY = 3;
    final static int DIVISION = 4;
    final static int EVAL     = 5;

    final static String[] OPERATORS_STRING = {" ", "+", "-", "*", "÷"};

    final static int DONT_CLEAR = 0;
    final static int CLEAR      = 1;

    int clearEditDisplay = CLEAR;

    final static String FORMAT_PATTERN = "#0.####";
    DecimalFormat formatter;

    public static final String APP_PREFERENCES = "MySettings";
    public static final String APP_PREFERENCES_RESULT = "Result";

    @Override
    protected void onStop(){
        super.onStop();

        String res = editDisplay.getText().toString();

        SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(APP_PREFERENCES_RESULT, res);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void registerListeners() {
        negate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    number1 = Double.parseDouble(editDisplay.getText().toString());
                }
                catch (Exception e){
                    return;
                }
                if (number1 == 0) { return; }
                editDisplay.setText(formatter.format(-1 * number1));
                clearEditDisplay = DONT_CLEAR;
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
        return true;
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
