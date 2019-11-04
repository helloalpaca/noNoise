package com.minseon.nonoise;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import static com.minseon.nonoise.MainActivity.above_noise;
import static com.minseon.nonoise.MainActivity.above_sensor_time;
import static com.minseon.nonoise.MainActivity.above_vibration;
import static com.minseon.nonoise.MainActivity.my_noise;
import static com.minseon.nonoise.MainActivity.my_sensor_time;
import static com.minseon.nonoise.MainActivity.my_vibration;

public class TodayState extends AppCompatActivity {

    private RadarChart chart1, chart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_state);

        chart1 = findViewById(R.id.today_chart1);
        chart2 = findViewById(R.id.today_chart2);
        //chart.setBackgroundColor(Color.rgb(60, 65, 82));

        chart1.getDescription().setEnabled(false);

        chart1.setWebLineWidth(1f);
        chart1.setWebColor(Color.LTGRAY);
        chart1.setWebLineWidthInner(1f);
        chart1.setWebColorInner(Color.LTGRAY);
        chart1.setWebAlpha(100);

        chart2.getDescription().setEnabled(false);

        chart2.setWebLineWidth(1f);
        chart2.setWebColor(Color.LTGRAY);
        chart2.setWebLineWidthInner(1f);
        chart2.setWebColorInner(Color.LTGRAY);
        chart2.setWebAlpha(100);

        myhomeData(chart1);
        aboveData(chart2);

        XAxis xAxis1 = chart1.getXAxis();
        xAxis1.setTextColor(Color.WHITE);
        YAxis yAxis1 = chart1.getYAxis();
        yAxis1.setTextColor(Color.WHITE);

        XAxis xAxis2 = chart2.getXAxis();
        xAxis2.setTextColor(Color.WHITE);
        YAxis yAxis2 = chart2.getYAxis();
        yAxis2.setTextColor(Color.WHITE);

        chart1.getLegend().setTextColor(Color.WHITE);
        chart2.getLegend().setTextColor(Color.WHITE);
    }

    private void myhomeData(RadarChart chart){

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        int vib[] = new int[12];
        int sou[] = new int[12];
        int cnt[] = new int[12];

        // 현재 날짜+시각 가져오기
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        long now = System.currentTimeMillis();
        Date nowdate = new Date(now);
        long in_now[] = new long[12];
        Date in_date[] = new Date[12];
        for(int i=0;i<12;++i){
            in_now[i] = now-(i*60*60*1000);
            in_date[i] = new Date(in_now[i]);
        }
        Date sensordate = null;

        // 2시간 내이면 vector<String> in2에 저장
        for(int i=0;i<my_vibration.length;++i){

            // 센서 데이터의 날짜를 가져온다.
            try {
                sensordate = sdf.parse(my_sensor_time[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // for문을 돌려서 현재 시간과 비교
            for(int k=0;k<12;++k){
                int compare = in_date[k].compareTo(sensordate);
                if(compare >= 0){
                    vib[k]+= (int)Float.parseFloat(my_vibration[i]);
                    sou[k]+= (int)Float.parseFloat(my_noise[i]);
                    cnt[k]++;
                }
            }
        }

        // 각각의 평균값 구하고 entries에 더하기
        int vib_avg[] = new int[12];
        int sou_avg[] = new int[12];
        for(int i=0;i<12;++i){
            if(cnt[i]!=0) {
                vib_avg[i] = vib[i] / cnt[i];
                sou_avg[i] = sou[i] / cnt[i];
            }
            else{
                vib_avg[i] = vib[i];
                sou_avg[i] = sou[i];
            }

            entries1.add(new RadarEntry(vib_avg[i]));
            //entries2.add((i*2)+2,new RadarEntry(sou_avg[i]));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);
/*
        RadarDataSet set2 = new RadarDataSet(entries2, "This Week");
        set2.setColor(Color.rgb(121, 162, 175));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);
*/
        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
       // sets.add(set2);

        RadarData data = new RadarData(sets);
        //data.setValueTypeface(tfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();

    }

    private void aboveData(RadarChart chart){

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        int vib[] = new int[12];
        int sou[] = new int[12];
        int cnt[] = new int[12];

        // 현재 날짜+시각 가져오기
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        long now = System.currentTimeMillis();
        Date nowdate = new Date(now);
        long in_now[] = new long[12];
        Date in_date[] = new Date[12];
        for(int i=0;i<12;++i){
            in_now[i] = now-(i*60*60*1000);
            in_date[i] = new Date(in_now[i]);
        }
        Date sensordate = null;

        // 2시간 내이면 vector<String> in2에 저장
        for(int i=0;i<above_vibration.length;++i){

            // 센서 데이터의 날짜를 가져온다.
            try {
                sensordate = sdf.parse(above_sensor_time[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // for문을 돌려서 현재 시간과 비교
            for(int k=0;k<12;++k){
                int compare = in_date[k].compareTo(sensordate);
                if(compare >= 0){
                    vib[k]+= (int)Float.parseFloat(above_vibration[i]);
                    sou[k]+= (int)Float.parseFloat(above_noise[i]);
                    cnt[k]++;
                }
            }
        }

        // 각각의 평균값 구하고 entries에 더하기
        int vib_avg[] = new int[12];
        int sou_avg[] = new int[12];
        for(int i=0;i<12;++i){
            if(cnt[i]!=0) {
                vib_avg[i] = vib[i] / cnt[i];
                sou_avg[i] = sou[i] / cnt[i];
            }
            else{
                vib_avg[i] = vib[i];
                sou_avg[i] = sou[i];
            }

            entries1.add((new RadarEntry(vib_avg[i])));
            //entries2.add((i*2)+2,new RadarEntry(sou_avg[i]));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);
/*
        RadarDataSet set2 = new RadarDataSet(entries2, "This Week");
        set2.setColor(Color.rgb(121, 162, 175));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);
*/
        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        //sets.add(set2);

        RadarData data = new RadarData(sets);
        //data.setValueTypeface(tfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();

    }

    private void setData(RadarChart chart) {

        float mul = 80;
        float min = 20;
        int cnt = 12;

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mul) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mul) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "This Week");
        set2.setColor(Color.rgb(121, 162, 175));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        //data.setValueTypeface(tfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();
    }

}
