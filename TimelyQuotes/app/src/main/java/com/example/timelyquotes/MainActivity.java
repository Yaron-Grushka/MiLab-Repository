package com.example.timelyquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    static boolean isActive = false;
    static int notifyId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Switch sendSwitch = (Switch)findViewById(R.id.send_button);
        sendSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    QuoteSender.send(sendSwitch.getContext());
                    isActive = true;
                } else {
                    QuoteSender.cancel(sendSwitch.getContext());
                    isActive = false;
                }
            }
        });
    }

    // Makes sure that notifications do not override one another:
    public static int incrementNotify(){
        notifyId++;
        return notifyId;
    }
}
