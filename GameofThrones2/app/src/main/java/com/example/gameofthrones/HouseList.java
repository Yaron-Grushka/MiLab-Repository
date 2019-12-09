package com.example.gameofthrones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class HouseList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_list);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.CharacterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Creating the arrays of the characters:
        Person[] stark = new Person[11];
        stark[0] = new Person("Eddard", R.drawable.eddardstark);
        stark[1] = new Person("Catelyn", R.drawable.catelynstark);
        stark[2] = new Person("Robb", R.drawable.robbstark);
        stark[3] = new Person("Sansa", R.drawable.sansastark);
        stark[4] = new Person("Arya", R.drawable.aryastark);
        stark[5] = new Person("Bran", R.drawable.branstark);
        stark[6] = new Person("Rickon", R.drawable.rickonstark);
        stark[7] = new Person("Jon Snow", R.drawable.jonsnow);
        stark[8] = new Person("Benjen", R.drawable.benjenstark);
        stark[9] = new Person("Lyanna", R.drawable.lyannastark);
        stark[10] = new Person("Tony", R.drawable.tonystark);

        Person[] lannister = new Person[9];
        lannister[0] = new Person("Tywin", R.drawable.tywinlannister);
        lannister[1] = new Person("Jaime", R.drawable.jaimelannister);
        lannister[2] = new Person("Cersei", R.drawable.cerseilannister);
        lannister[3] = new Person("Tyrion", R.drawable.tyrionlannister);
        lannister[4] = new Person("Joffrey", R.drawable.joffrey);
        lannister[5] = new Person("Myrcella", R.drawable.myrcella);
        lannister[6] = new Person("Tommen", R.drawable.tommen);
        lannister[7] = new Person("Kevan", R.drawable.kevanlannister);
        lannister[8] = new Person("Lancel", R.drawable.lancellannister);

        // Determine which house should be generated:
        Intent intent = getIntent();
        String house = intent.getStringExtra(MainActivity.HOUSE_NAME);
        String color = intent.getStringExtra(MainActivity.HOUSE_COLOR);
        if (house.equals("Stark")) {
            recyclerView.setAdapter(new CharactersAdapter(stark, color));
            recyclerView.setBackgroundColor(Color.parseColor("#808080"));

        } else if (house.equals("Lannister")){
            recyclerView.setAdapter(new CharactersAdapter(lannister, color));
            recyclerView.setBackgroundColor(Color.parseColor("#800000"));
        }

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }
}
