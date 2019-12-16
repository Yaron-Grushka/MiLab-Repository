package com.example.timelyquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    static int notifyId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Switch sendSwitch = (Switch)findViewById(R.id.send_button);

        // Switch is turned on when app is launched. Can be turned off to disable notifications.
        sendSwitch.setChecked(true);
        QuoteSender.send(sendSwitch.getContext());
        sendSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    QuoteSender.send(sendSwitch.getContext());
                } else {
                    QuoteSender.cancel(sendSwitch.getContext());
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
