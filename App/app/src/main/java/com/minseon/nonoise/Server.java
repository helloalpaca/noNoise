package com.minseon.nonoise;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.net.URLEncoder;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.minseon.nonoise.MainActivity.MainURL;
import static com.minseon.nonoise.MainActivity.id;
import static com.minseon.nonoise.SendMessage.contents;
import static com.minseon.nonoise.SendMessage.time;

public class Server {

    public void Test(){
        System.out.println("Server Test");
    }

    public void MainActivity() {

        Integer[] id, home_No, noise, vibration;
        String[] address, message, sensor_start, sensor_end;

        String errorString = null;
        String mJsonString = null;
        String getdata_url = "http://ec2-54-180-24-178.ap-northeast-2.compute.amazonaws.com:8080/api/home";

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            URL url = new URL(getdata_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); //전송방식
//            connection.setDoOutput(true);       //데이터를 쓸 지 설정
            connection.setDoInput(true);        //데이터를 읽어올지 설정

            InputStream is = connection.getInputStream();
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String result;
            while((result = br.readLine())!=null){
                sb.append(result+"\n");
                System.out.println(result);
            }

            result = sb.toString();
            mJsonString = sb.toString();
            //System.out.println("mJsonString : "+mJsonString);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //System.out.println("inside try!!!!");
//            mJsonString="[\n" +
//                    "    {\n" +
//                    "        \"id\": 10,\n" +
//                    "        \"address\": \"아파트\",\n" +
//                    "        \"home_No\": 101,\n" +
//                    "        \"message\": \"test1\",\n" +
//                    "        \"isOn\": 1\n" +
//                    "    },\n" +
//                    "    {\n" +
//                    "        \"id\": 2,\n" +
//                    "        \"address\": \"아파트\",\n" +
//                    "        \"home_No\": 102,\n" +
//                    "        \"message\": \"test2\",\n" +
//                    "        \"isOn\": 0\n" +
//                    "    },\n" +
//                    "    {\n" +
//                    "        \"id\": 3,\n" +
//                    "        \"address\": \"아파트\",\n" +
//                    "        \"home_No\": 103,\n" +
//                    "        \"message\": \"test3\",\n" +
//                    "        \"isOn\": 0\n" +
//                    "    }]";
            JSONArray jsonArray = new JSONArray(mJsonString);

            if(jsonArray.length()>0) {
                int length = jsonArray.length();
                id = new Integer[jsonArray.length()];
                address = new String[jsonArray.length()];
                home_No = new Integer[jsonArray.length()];
                message = new String[jsonArray.length()];
                sensor_start = new String[jsonArray.length()];
                sensor_end = new String[jsonArray.length()];

                noise = new Integer[jsonArray.length()];
                vibration = new Integer[jsonArray.length()];


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);

                    id[i] = item.getInt("id");
                    address[i] = item.getString("address");
                    home_No[i] = item.getInt("home_No");
                    message[i] = item.getString("message");
                    sensor_start[i] = item.getString("sensor_starttime");
                    sensor_end[i] = item.getString("sensor_endtime");
                    /*home_No[i] = item.getBoolean("home_No");
                    noise[i] = item.getInt("unit");
                    vibration[i] = item.getInt("number");
                    time[i] = item.getString("mission");
                    System.out.println("");*/
                    System.out.println(id[i]+" : "+address[i]+" : "+home_No[i]+" : "+message[i]+" : "+sensor_start[i]+" : "+sensor_end[i]);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    Date date = sdf.parse(sensor_start[i]);
                    System.out.println("start Date : "+date);
                }
            }

        } catch (JSONException e) {
            Log.d("TAG", "show result : ", e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void Request() {

        Integer[] id;

        String errorString = null;
        String mJsonString = null;
        String getdata_url = "http://ec2-54-180-24-178.ap-northeast-2.compute.amazonaws.com:8080/api/homeinfo_by_id";
        String serverURL = "?id=1";

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            URL url = new URL(getdata_url);

            //연결을 함
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");//POST방식으로 보냄
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
            writer.write(serverURL);
            writer.flush();
            writer.close();//버퍼를 받아줌

            //서버로 부터 데이터를 받음
            InputStream stream = con.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            JSONArray jsonArray = new JSONArray(mJsonString);
//            if (jsonArray.length() > 0) {
//                int length = jsonArray.length();
//                id = new Integer[jsonArray.length()];
//
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject item = jsonArray.getJSONObject(i);
//
//                    id[i] = item.getInt("id");
//                    System.out.println("id: " + id[i]);
//                }
//            }
//
//        } catch (JSONException e) {
//            Log.d("TAG", "show result : ", e);
//        }
    }

    public void SendMessage() {

    }

    public void SensorManage() {

    }
}
