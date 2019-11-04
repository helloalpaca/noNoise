package com.minseon.nonoise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.Vector;

import static com.minseon.nonoise.MainActivity.message;

public class NoiseList extends AppCompatActivity {

    String contents;
    static int noise_position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noise_list);

        final ListViewAdapter adapter=new ListViewAdapter();
        ListView listView =(ListView)findViewById(R.id.listview_item);
        listView.setAdapter(adapter);

        final Vector<Integer> v = new Vector<Integer>();

        for(int i=0;i< message.length;++i){
            if(message[i].equals("")) continue;
            adapter.addItem(message[i]);
            v.addElement(i);
        }
        //adapter.addItem("Hello, World!");
        //adapter.addItem("It's friday!");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contents = adapter.getContents(position);
                noise_position = v.elementAt(position);
                Intent intent = new Intent(NoiseList.this, PopUp.class);
                startActivity(intent);
            }
        });
    }
}
