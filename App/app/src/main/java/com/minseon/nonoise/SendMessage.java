package com.minseon.nonoise;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.minseon.nonoise.MainActivity.MainURL;
import static com.minseon.nonoise.MainActivity.my_address;
import static com.minseon.nonoise.MainActivity.my_home_No;
import static com.minseon.nonoise.MainActivity.my_id;

public class SendMessage extends AppCompatActivity {

    EditText edit;
    ImageButton imgbtn;
    Spinner month, date, times;
    public static String time, contents;
    String syear = "2019";
    String smonth, sdate, stimes;

    /* message */
    static String my_message, my_message_starttime, my_message_endtime;
    public static String MessageURL = MainURL+"message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        edit = findViewById(R.id.send_edit1);
        imgbtn = findViewById(R.id.send_imgbtn1);
        month = findViewById(R.id.spinner_month);
        date = findViewById(R.id.spinner_date);
        times = findViewById(R.id.spinner_time);

        month.setBackgroundColor(Color.LTGRAY);
        date.setBackgroundColor(Color.LTGRAY);
        times.setBackgroundColor(Color.LTGRAY);
    }

    public void onButtonClicked(View view){
        String m = month.getSelectedItem().toString();
        String d = date.getSelectedItem().toString();
        String t = times.getSelectedItem().toString();

        spinner_month(m);
        spinner_date(d);
        spinner_time(t);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        long now = System.currentTimeMillis();
        Date nowdate = new Date(now);

        String nnow = sdf.format(nowdate);
        String end = syear+"-"+smonth+"-"+sdate+stimes;

        System.out.println(edit.getText());
        System.out.println(nnow);
        System.out.println(end);

        message task = new message();
        task.execute(MessageURL);
    }

    public void spinner_month(String selected){
        if(selected.equals("1월")) smonth = "01";
        if(selected.equals("2월")) smonth = "02";
        if(selected.equals("3월")) smonth = "03";
        if(selected.equals("4월")) smonth = "04";
        if(selected.equals("5월")) smonth = "05";
        if(selected.equals("6월")) smonth = "06";
        if(selected.equals("7월")) smonth = "07";
        if(selected.equals("8월")) smonth = "08";
        if(selected.equals("9월")) smonth = "09";
        if(selected.equals("10월")) smonth = "10";
        if(selected.equals("11월")) smonth = "11";
        if(selected.equals("12월")) smonth = "12";
    }

    public void spinner_date(String selected){
        if(selected.equals("1일")) sdate = "01";
        if(selected.equals("2일")) sdate = "02";
        if(selected.equals("3일")) sdate = "03";
        if(selected.equals("4일")) sdate = "04";
        if(selected.equals("5일")) sdate = "05";
        if(selected.equals("6일")) sdate = "06";
        if(selected.equals("7일")) sdate = "07";
        if(selected.equals("8일")) sdate = "08";
        if(selected.equals("9일")) sdate = "09";
        if(selected.equals("10일")) sdate = "10";
        if(selected.equals("11일")) sdate = "11";
        if(selected.equals("12일")) sdate = "12";
        if(selected.equals("13일")) sdate = "13";
        if(selected.equals("14일")) sdate = "14";
        if(selected.equals("15일")) sdate = "15";
        if(selected.equals("16일")) sdate = "16";
        if(selected.equals("17일")) sdate = "17";
        if(selected.equals("18일")) sdate = "18";
        if(selected.equals("19일")) sdate = "19";
        if(selected.equals("20일")) sdate = "20";
        if(selected.equals("21일")) sdate = "21";
        if(selected.equals("22일")) sdate = "22";
        if(selected.equals("23일")) sdate = "23";
        if(selected.equals("24일")) sdate = "24";
        if(selected.equals("25일")) sdate = "25";
        if(selected.equals("26일")) sdate = "26";
        if(selected.equals("27일")) sdate = "27";
        if(selected.equals("28일")) sdate = "28";
        if(selected.equals("29일")) sdate = "29";
        if(selected.equals("30일")) sdate = "30";
        if(selected.equals("31일")) sdate = "31";
    }

    public void spinner_time(String selected){
        if(selected.equals("1시")) stimes = "T01:00:00.000";
        if(selected.equals("2시")) stimes = "T02:00:00.000";
        if(selected.equals("3시")) stimes = "T03:00:00.000";
        if(selected.equals("4시")) stimes = "T04:00:00.000";
        if(selected.equals("5시")) stimes = "T05:00:00.000";
        if(selected.equals("6시")) stimes = "T06:00:00.000";
        if(selected.equals("7시")) stimes = "T07:00:00.000";
        if(selected.equals("8시")) stimes = "T08:00:00.000";
        if(selected.equals("9시")) stimes = "T09:00:00.000";
        if(selected.equals("10시")) stimes = "T10:00:00.000";
        if(selected.equals("11시")) stimes = "T11:00:00.000";
        if(selected.equals("12시")) stimes = "T12:00:00.000";
        if(selected.equals("13시")) stimes = "T13:00:00.000";
        if(selected.equals("14시")) stimes = "T14:00:00.000";
        if(selected.equals("15시")) stimes = "T15:00:00.000";
        if(selected.equals("16시")) stimes = "T16:00:00.000";
        if(selected.equals("17시")) stimes = "T17:00:00.000";
        if(selected.equals("18시")) stimes = "T18:00:00.000";
        if(selected.equals("19시")) stimes = "T19:00:00.000";
        if(selected.equals("20시")) stimes = "T20:00:00.000";
        if(selected.equals("21시")) stimes = "T21:00:00.000";
        if(selected.equals("22시")) stimes = "T22:00:00.000";
        if(selected.equals("23시")) stimes = "T23:00:00.000";
        if(selected.equals("24시")) stimes = "T24:00:00.000";
    }

    public class message extends AsyncTask<String, String, String> {

        String mJsonString;

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id", my_id);/*
                jsonObject.accumulate("home_No", my_home_No);
                jsonObject.accumulate("address", my_address);*/
                jsonObject.accumulate("message", my_message);
                jsonObject.accumulate("message_starttime", my_message_starttime);
                jsonObject.accumulate("message_endtime", my_message_endtime);
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

//                    //서버로 부터 데이터를 받음
//                    InputStream stream = con.getInputStream();
//
//                    reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
//
//                    StringBuffer buffer = new StringBuffer();
//
//                    String line = "";
//                    while((line = reader.readLine()) != null){
//                        buffer.append(line);
//                    }
//
////                    checkedID = buffer.toString().trim();
////                    Log.e("id check: " , checkedID);
//
//                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌

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
                //mJsonString = result;
                //showResult();
                //System.out.println(mJsonString);
            }
        }
    }
}
