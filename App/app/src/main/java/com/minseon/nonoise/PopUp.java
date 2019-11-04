package com.minseon.nonoise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.minseon.nonoise.MainActivity.home_No;
import static com.minseon.nonoise.MainActivity.message;
import static com.minseon.nonoise.NoiseList.noise_position;
import static com.minseon.nonoise.SendMessage.contents;

public class PopUp extends AppCompatActivity {

    TextView text1, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        text1 = findViewById(R.id.popup_home_No);
        text2 = findViewById(R.id.popup_message);

        text1.setText(home_No[noise_position]);
        text2.setText(message[noise_position]);
    }
}
