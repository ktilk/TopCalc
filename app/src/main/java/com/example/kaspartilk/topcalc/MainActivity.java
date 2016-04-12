package com.example.kaspartilk.topcalc;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private CalculatorScreen screen;
    private String _prevOp = "=";
    private Double _prevNum = .0;

    private static final String STATE_SCREEN = "screenValue";
    private static final String STATE_SCREEN_STATE = "screenState";
    private static final String STATE_MAIN_ACTIVITY_PREVIOUS_OP = "mainActivityPreviousOp";
    private static final String STATE_MAIN_ACTIVITY_PREVIOUS_NUM = "mainActivityPreviousNum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = new CalculatorScreen("0", (TextView)findViewById(R.id.textViewOutput));
        loadSavedInstanceState(savedInstanceState);
    }

    private void loadSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            screen.setValue(savedInstanceState.getString(STATE_SCREEN));
            screen.setAddNextSymbolAsNew(savedInstanceState.getBoolean(STATE_SCREEN_STATE));
            _prevOp = savedInstanceState.getString(STATE_MAIN_ACTIVITY_PREVIOUS_OP);
            _prevNum = savedInstanceState.getDouble(STATE_MAIN_ACTIVITY_PREVIOUS_NUM);
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(STATE_SCREEN, screen.toString());
        savedInstanceState.putBoolean(STATE_SCREEN_STATE, screen.getAddNextSymbolAsNew());
        savedInstanceState.putString(STATE_MAIN_ACTIVITY_PREVIOUS_OP, _prevOp);
        savedInstanceState.putDouble(STATE_MAIN_ACTIVITY_PREVIOUS_NUM, _prevNum);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void numberClicked(View view) {

        String number = ((Button) view).getText().toString();
        screen.addNumber(number);
    }

    public void operatorClicked(View view) {
        String operator = ((Button) view).getText().toString();
        if (_prevOp.equals("=")){
            _prevNum = screen.toDouble();
            screen.setValue(_prevNum);
        }else {
            if (!screen.getAddNextSymbolAsNew()){
                Intent intent = new Intent();
                intent.setAction("com.example.kaspartilk.CALCULATE");
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                intent.putExtra("numero1", _prevNum);
                intent.putExtra("numero2", screen.toDouble());
                intent.putExtra("operator", _prevOp);
                sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        _prevNum = Double.parseDouble(getResultData());
                        screen.setValue(_prevNum);
                    }
                }, null, Activity.RESULT_OK, null, null);
            }
        }
        _prevOp = operator;
        screen.setAddNextSymbolAsNew(true);
    }

    public void clearClicked(View view) {
        _prevNum = .0;
        _prevOp = "=";
        screen.clear();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStart called");
        }
    }// The activity is about to become visible.

    @Override
    protected void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onResume called");
        }
    }// The activity has become visible (it is now "resumed").   

    @Override
    protected void onPause() {
        super.onPause();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onPause called");
        }
        // Another activity is taking focus (this activity is about to be "paused").   
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStop called");
        }
    }// The activity is no longer visible (it is now "stopped")

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onDestroy called");
        }
    }// The activity is about to be destroyed.

}
