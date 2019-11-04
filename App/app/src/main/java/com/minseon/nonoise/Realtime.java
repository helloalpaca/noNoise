package com.minseon.nonoise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.minseon.nonoise.MainActivity.my_home_No;
import static com.minseon.nonoise.MainActivity.my_id;
import static com.minseon.nonoise.MainActivity.my_noise;

public class Realtime extends AppCompatActivity {

    TextView text1, text2, text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);

        text1 = (TextView)findViewById(R.id.realtime_text1);
        text3 = (TextView)findViewById(R.id.realtime_text3);

        text1.setText(my_home_No+"호");
        // 수정하기
        text3.setText(my_noise[1]);
    }
}
