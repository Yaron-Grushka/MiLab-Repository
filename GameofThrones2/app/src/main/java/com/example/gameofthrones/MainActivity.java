package com.example.gameofthrones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final static String HOUSE_NAME = "com.example.gameofthrones.HOUSE_NAME";
    final static String HOUSE_COLOR = "com.example.gameofthrones.HOUSE_COLOR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void ViewStarkList(View view) {
        Intent intent = new Intent(this, HouseList.class);
        intent.putExtra(HOUSE_NAME, "Stark");
        intent.putExtra(HOUSE_COLOR, "#000000");
        startActivity(intent);
    }

    public void ViewLannisterList(View view) {
        Intent intent = new Intent(this, HouseList.class);
        intent.putExtra(HOUSE_NAME, "Lannister");
        intent.putExtra(HOUSE_COLOR, "#FFFF00");
        startActivity(intent);
    }
}
