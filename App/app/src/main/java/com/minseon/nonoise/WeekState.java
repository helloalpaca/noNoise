package com.minseon.nonoise;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.minseon.nonoise.MainActivity.my_noise;
import static com.minseon.nonoise.MainActivity.my_sensor_time;
import static com.minseon.nonoise.MainActivity.my_vibration;

public class WeekState extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_state);

        LineChart lineChart = (LineChart) findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();
        // myhome_data
        {
            int vib[] = new int[7];
            int sou[] = new int[7];
            int cnt[] = new int[7];

            // 현재 날짜+시각 가져오기
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            long now = System.currentTimeMillis();
            Date nowdate = new Date(now);
            long in_now[] = new long[7];
            Date in_date[] = new Date[7];
            for (int i = 0; i < 7; ++i) {
                in_now[i] = now - (i * 24 * 60 * 60 * 1000);
                in_date[i] = new Date(in_now[i]);
            }
            Date sensordate = null;

            // 2시간 내이면 vector<String> in2에 저장
            for (int i = 0; i < my_vibration.length; ++i) {

                // 센서 데이터의 날짜를 가져온다.
                try {
                    sensordate = sdf.parse(my_sensor_time[i]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // for문을 돌려서 현재 시간과 비교
                for (int k = 0; k < 7; ++k) {
                    int compare = in_date[k].compareTo(sensordate);
                    if (compare >= 0) {
                        vib[k] += (int)Float.parseFloat(my_vibration[i]);
                        sou[k] += (int)Float.parseFloat(my_noise[i]);
                        cnt[k]++;
                    }
                }
            }

            // 각각의 평균값 구하고 entries에 더하기
            int vib_avg[] = new int[7];
            int sou_avg[] = new int[7];
            for (int i = 0; i < 7; ++i) {
                if(cnt[i]!=0) {
                    vib_avg[i] = vib[i] / cnt[i];
                    sou_avg[i] = sou[i] / cnt[i];
                }
                else{
                    vib_avg[i] = vib[i];
                    sou_avg[i] = sou[i];
                }
                entries.add(new Entry(i,vib_avg[i]));
            }

        }
        // abovehome_data
        {
            int vib2[] = new int[7];
            int sou2[] = new int[7];
            int cnt2[] = new int[7];

            // 현재 날짜+시각 가져오기
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            long now2 = System.currentTimeMillis();
            Date nowdate2 = new Date(now2);
            long in_now2[] = new long[7];
            Date in_date2[] = new Date[7];
            for (int i = 0; i < 7; ++i) {
                in_now2[i] = now2 - (i * 24 * 60 * 60 * 1000);
                in_date2[i] = new Date(in_now2[i]);
            }
            Date sensordate = null;

            // 2시간 내이면 vector<String> in2에 저장
            for (int i = 0; i < my_vibration.length; ++i) {

                // 센서 데이터의 날짜를 가져온다.
                try {
                    sensordate = sdf2.parse(my_sensor_time[i]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // for문을 돌려서 현재 시간과 비교
                for (int k = 0; k < 7; ++k) {
                    int compare = in_date2[k].compareTo(sensordate);
                    if (compare >= 0) {
                        vib2[k] += (int)Float.parseFloat(my_vibration[i]);
                        sou2[k] += (int)Float.parseFloat(my_noise[i]);
                        cnt2[k]++;
                    }
                }
            }

            // 각각의 평균값 구하고 entries에 더하기
            int vib_avg2[] = new int[7];
            int sou_avg2[] = new int[7];
            for (int i = 0; i < 7; ++i) {
                if(cnt2[i]!=0) {
                    vib_avg2[i] = vib2[i] / cnt2[i];
                    sou_avg2[i] = sou2[i] / cnt2[i];
                }
                else{
                    vib_avg2[i] = vib2[i];
                    sou_avg2[i] = sou2[i];
                }
                entries2.add(new Entry(i,vib_avg2[i]));
            }
        }

//        entries.add(new Entry(0, 3));
//        entries.add(new Entry(1, 4));
//        entries.add(new Entry(2, 5));
//        entries.add(new Entry(3, 6));
//        entries.add(new Entry(4, 3));
//        entries.add(new Entry(5, 5));
//        entries.add(new Entry(6, 2));
//        entries.add(new Entry(7, 3));
//        entries.add(new Entry(8, 4));
//        entries.add(new Entry(9, 5));
//        entries.add(new Entry(10, 6));
//        entries.add(new Entry(11, 3));
//        entries.add(new Entry(12, 5));
//        entries.add(new Entry(13, 2));
//
//        entries2.add(new Entry(0, 1));
//        entries2.add(new Entry(1, 2));
//        entries2.add(new Entry(2, 3));
//        entries2.add(new Entry(3, 2));
//        entries2.add(new Entry(4, 1));
//        entries2.add(new Entry(5, 2));
//        entries2.add(new Entry(6, 3));
//        entries2.add(new Entry(7, 1));
//        entries2.add(new Entry(8, 2));
//        entries2.add(new Entry(9, 3));
//        entries2.add(new Entry(10, 2));
//        entries2.add(new Entry(11, 1));
//        entries2.add(new Entry(12, 2));
//        entries2.add(new Entry(13, 3));

        LineDataSet dataset = new LineDataSet(entries, "우리집");
        LineDataSet dataset2 = new LineDataSet(entries2,"윗집");


        dataset.setColor(R.color.myhome);
        dataset.setFillColor(R.color.myhome);
        dataset.setCircleColor(R.color.myhome);
        dataset.setValueTextColor(R.color.white);

        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataset.setDrawFilled(true);

        dataset2.setColor(R.color.abovehouse);
        dataset2.setFillColor(R.color.abovehouse);
        dataset2.setCircleColor(R.color.abovehouse);
        dataset2.setValueTextColor(R.color.white);

        dataset2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataset2.setDrawFilled(true);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(dataset);
        dataSets.add(dataset2);

        LineData data = new LineData(dataSets);

        //dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        /*dataset.setDrawCubic(true); //선 둥글게 만들기
        dataset.setDrawFilled(true); //그래프 밑부분 색칠*/

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);

        lineChart.getAxisLeft().setTextColor(getResources().getColor(R.color.white));
        lineChart.getAxisLeft().setAxisLineColor(getResources().getColor(R.color.white));

        lineChart.getLegend().setTextColor(Color.WHITE);

        lineChart.setBackgroundColor(Color.TRANSPARENT); //set whatever color you prefer
        lineChart.setDrawGridBackground(true);// this is a must


/*
        String[] values = { "Jan", "Feb", "Mar","Apr","May"};
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
*/
        lineChart.setData(data);
        lineChart.animateY(5000);

    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            return mValues[(int) value];
        }

    }

}
