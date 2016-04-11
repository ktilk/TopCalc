package com.example.kaspartilk.topcalc;

import android.widget.TextView;

/**
 * Created by KasparTilk on 11.04.2016.
 */
public class CalculatorScreen {

    private String value = "";
    private boolean addNextSymbolAsNew = false;
    private TextView _screen;

    public CalculatorScreen(String s, TextView tv){
        value = s;
        _screen = tv;
        refreshScreen();
    }

    private void refreshScreen() {
        _screen.setText(value);
    }

    public void addNumber(String number) {
        if (addNextSymbolAsNew) {
            value = "0";
            addNextSymbolAsNew = false;
        }
        if (value.equals("0") && !number.equals(".")) {  //remove leading zero
            value = number;
        } else if (!number.equals(".") || !value.contains(".")) { //cant be two decimal point
            value+=number;
        }
        refreshScreen();
    }

    public double toDouble() {
        return Double.parseDouble(value);
    }

    private void setValue(String s) {
        this.value = s;
    }

    public void setValue(double d) {
        setValue(String.valueOf(d));
    }

    public boolean getAddNextSymbolAsNew() {
        return addNextSymbolAsNew;
    }

    public void setAddNextSymbolAsNew(boolean addNextSymbolAsNew) {
        this.addNextSymbolAsNew = addNextSymbolAsNew;
    }

    public void clear() {
        value = "0";
        refreshScreen();
    }
    @Override
    public String toString(){
        return value;
    }
}
