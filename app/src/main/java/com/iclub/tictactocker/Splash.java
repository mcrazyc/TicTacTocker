package com.iclub.tictactocker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void sender(View v){
        Intent start = new Intent(this,NormalGame.class);
        switch (v.getId()){
            case R.id.normal:
                start = new Intent(this,NormalGame.class);
                break;
            case R.id.three:
                start = new Intent(this,NormalGame3.class);
                break;
            case R.id.four:
                start = new Intent(this,NormalGame4.class);
                break;
            case R.id.five:
                start = new Intent(this,NormalGame5.class);
                break;
        }
        startActivity(start);
    }
}
