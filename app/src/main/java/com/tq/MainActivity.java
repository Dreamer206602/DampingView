package com.tq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tq.fruitviewlib.FruitView;

public class MainActivity extends AppCompatActivity {

    private FruitView mFruitView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
