package com.minseon.nonoise;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.minseon.nonoise.MainActivity.id;
import static java.net.Proxy.Type.HTTP;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String MainURL = "http://ec2-54-180-24-178.ap-northeast-2.compute.amazonaws.com:8080/api/";
    public static String HomeInfoURL = MainURL + "homeinfo_by_id";
    public static String MetaURL = MainURL + "my_meta_id";
    public static String AboveURL = MainURL+"above_meta_id";
    public static String MySensorURL = MainURL+"my_sensor_data";
    public static String AboveSensorURL = MainURL+"above_sensor_data";

    /* homeinfo_id */
    static String[] address, message, id, home_No;

    /* my_meta_id */
    static String my_id = "1";
    static String my_address ="", my_home_No="", my_sensor_starttime, my_sensor_endtime;

    /* above_meta_id */
    static String above_id, above_address, above_home_No, above_sensor_starttime, above_sensor_endtime;

    /* my_sensor_data */
    static String[] my_vibration, my_noise, my_sensor_time;

    /* above_sensor_data */
    static String[] above_vibration, above_noise, above_sensor_time;

    TextView text1, text2, text3, text4, text5, text6;
    ImageView img;
    int img_width, img_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new MainActivity.JSONsensor().execute(MainURL);
//        GetLoginData task = new GetLoginData();
//        task.execute(userID, userPW);

        ImageButton btn1 = (ImageButton)findViewById(R.id.main_btn1);
        //img = (ImageView)findViewById(R.id.main_img1);
        text1 = (TextView)findViewById(R.id.main_text1); //아파트 이름
        text2 = (TextView)findViewById(R.id.main_text2); //호수
        text3 = (TextView)findViewById(R.id.main_text3); //아파트 이름
        text4 = (TextView)findViewById(R.id.main_text4); //호수
        text5 = (TextView)findViewById(R.id.main_text5); //호수
        text6 = (TextView)findViewById(R.id.main_text6); //메시지
        NavigationView navigationView = findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);

        Menu menu = navigationView.getMenu();
        MenuItem tools= menu.findItem(R.id.tools);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.RobotoTextViewStyle), 0, s.length(), 0);
        tools.setTitle(s);
        navigationView.setNavigationItemSelectedListener(this);

        HomeInfo_id task1 = new HomeInfo_id();
        task1.execute(HomeInfoURL);

        my_meta_id task2 = new my_meta_id();
        task2.execute(MetaURL);

        above_meta_id task3 = new above_meta_id();
        task3.execute(AboveURL);

        my_sensor_data task4 = new my_sensor_data();
        task4.execute(MySensorURL);


        above_sensor_data task5 = new above_sensor_data();
        task5.execute(AboveSensorURL);


//        img.post(new Runnable(){
//            @Override
//            public void run() {
//                img_height = img.getHeight();
//                img_width = img.getWidth();
//                Log.i("[MainActivity]", "img height: " + img.getHeight());
//                Log.i("[MainActivity]", "img width: " + img.getWidth());    }
//        });
//
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) img.getLayoutParams();
//        params.width = 1080;
//        params.height = 1080;
//        img.setLayoutParams(params);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        text1.setText(my_address);
        text2.setText(my_home_No);
        //text3.setText(my_address);
        //text4.setText(my_home_No);

    }

    public void onButtonClicked2(View view){
        Intent intent = new Intent(MainActivity.this, SendMessage.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_realtime) {

            Intent intent = new Intent(getApplicationContext(), Realtime.class);
            startActivity(intent);

        } else if (id == R.id.nav_week) {

            //System.out.println("nav_gallery is clicked");
            Intent intent = new Intent(getApplicationContext(), WeekState.class);
            startActivity(intent);

        } else if (id == R.id.nav_today) {

            Intent intent = new Intent(getApplicationContext(),TodayState.class);
            startActivity(intent);

        } /*else if (id == R.id.nav_onoff) {

            Intent intent = new Intent(getApplicationContext(), SensorManage.class);
            startActivity(intent);

        }*/  else if (id == R.id.nav_send) {

            Intent intent = new Intent(getApplicationContext(), WriteInformation.class);
            startActivity(intent);

        } else if (id == R.id.nav_noiselist) {

            Intent intent = new Intent(getApplicationContext(), NoiseList.class);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class HomeInfo_id extends AsyncTask<String, String, String> {

        String mJsonString;

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id", "1");
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    System.out.println(urls[0]);
                    URL url = new URL(urls[0]);

                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송

                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

//                    checkedID = buffer.toString().trim();
//                    Log.e("id check: " , checkedID);

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            if (result == null) {
                //mTextViewResult.setText(errorString);
            } else {
                mJsonString = result;
                showResult();
                System.out.println(mJsonString);
                text5.setText(home_No[1]+"호");
                text6.setText(message[1]);
            }
        }

        private void showResult() {

            try {
                JSONArray jsonArray = new JSONArray(mJsonString);

                if(jsonArray.length()>0) {
                    int length = jsonArray.length();
                    id = new String[jsonArray.length()];
                    home_No = new String[jsonArray.length()];
                    message = new String[jsonArray.length()];
                    address = new String[jsonArray.length()];


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);

                        id[i] = item.getString("id");
                        home_No[i] = item.getString("home_No");
                        message[i] = item.getString("message");
                        address[i] = item.getString("address");
                        System.out.println(id[i]+" : "+home_No+" : "+message[i]+" : "+address[i]);
                    }

                }

            } catch (JSONException e) {
                Log.d("TAG", "show result : ", e);
            }
        }
    }

    public class my_meta_id extends AsyncTask<String, String, String> {

        String mJsonString;

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id", my_id);
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    System.out.println(urls[0]);
                    URL url = new URL(urls[0]);

                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송

                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

//                    checkedID = buffer.toString().trim();
//                    Log.e("id check: " , checkedID);

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            if (result == null) {
                //mTextViewResult.setText(errorString);
            } else {
                mJsonString = result;
                showResult();
                System.out.println(mJsonString);
//        text1.setText(my_address);
//        text2.setText(my_home_No);
//        text3.setText(my_address);
//        text4.setText(my_home_No);
            }
        }

        private void showResult() {

            try {
                JSONArray jsonArray = new JSONArray(mJsonString);

                JSONObject item = jsonArray.getJSONObject(0);
                my_address = item.getString("address");
                my_home_No = item.getString("home_No");
                my_sensor_starttime = item.getString("sensor_starttime");
                my_sensor_endtime = item.getString("sensor_endtime");
                System.out.println(my_address+" : "+my_home_No+" : "+my_sensor_starttime+" : "+my_sensor_endtime);

            } catch (JSONException e) {
                Log.d("TAG", "show result : ", e);
            }
        }

    }

    public class above_meta_id extends AsyncTask<String, String, String> {

        String mJsonString;

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id", my_id);
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    System.out.println(urls[0]);
                    URL url = new URL(urls[0]);

                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송

                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

//                    checkedID = buffer.toString().trim();
//                    Log.e("id check: " , checkedID);

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            if (result == null) {
                //mTextViewResult.setText(errorString);
            } else {
                mJsonString = result;
                showResult();
                System.out.println(mJsonString);
            }
        }

        private void showResult() {

            try {
                JSONArray jsonArray = new JSONArray(mJsonString);

                JSONObject item = jsonArray.getJSONObject(0);
                above_id = item.getString("id");
                above_address = item.getString("address");
                above_home_No = item.getString("home_No");
                above_sensor_starttime = item.getString("sensor_starttime");
                above_sensor_endtime = item.getString("sensor_endtime");

                System.out.println(above_id+" : "+above_address+" : "+above_home_No+" : "+above_sensor_starttime+" : "+above_sensor_endtime);

            } catch (JSONException e) {
                Log.d("TAG", "show result : ", e);
            }
        }
    }

    public class my_sensor_data extends AsyncTask<String, String, String> {

        String mJsonString;

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id", my_id);
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    System.out.println(urls[0]);
                    URL url = new URL(urls[0]);

                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송

                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                        System.out.println("line :"+line);
                    }

//                    checkedID = buffer.toString().trim();
//                    Log.e("id check: " , checkedID);

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            if (result == null) {
                //mTextViewResult.setText(errorString);
            } else {
                mJsonString = result;
                showResult();
                System.out.println(mJsonString);
            }
        }

        private void showResult() {

            try {
                JSONArray jsonArray = new JSONArray(mJsonString);

                if(jsonArray.length()>0) {
                    int length = jsonArray.length();
                    my_vibration = new String[jsonArray.length()];
                    my_noise = new String[jsonArray.length()];
                    my_sensor_time = new String[jsonArray.length()];


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);

                        my_vibration[i] = item.getString("vibration");
                        my_noise[i] = item.getString("noise");
                        my_sensor_time[i] = item.getString("time");
                        System.out.println(my_vibration[i]+" : "+my_noise[i]+" : "+my_sensor_time[i]);
                    }

                }

            } catch (JSONException e) {
                Log.d("TAG", "show result : ", e);
            }
        }
    }

    public class above_sensor_data extends AsyncTask<String, String, String> {

        String mJsonString;

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id", my_id);
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    System.out.println(urls[0]);
                    URL url = new URL(urls[0]);

                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송

                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

//                    checkedID = buffer.toString().trim();
//                    Log.e("id check: " , checkedID);

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            if (result == null) {
                //mTextViewResult.setText(errorString);
            } else {
                mJsonString = result;
                showResult();
                System.out.println(mJsonString);
            }
        }

        private void showResult() {

            try {
                JSONArray jsonArray = new JSONArray(mJsonString);

                if(jsonArray.length()>0) {
                    int length = jsonArray.length();
                    above_vibration = new String[jsonArray.length()];
                    above_noise = new String[jsonArray.length()];
                    above_sensor_time = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);

                        above_vibration[i] = item.getString("vibration");
                        above_noise[i] = item.getString("noise");
                        above_sensor_time[i] = item.getString("time");
                        System.out.println(above_vibration[i]+" : "+above_noise[i]+" : "+above_sensor_time[i]);
                    }

                }

            } catch (JSONException e) {
                Log.d("TAG", "show result : ", e);
            }
        }
    }

}
