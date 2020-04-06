package com.minseon.nonoise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getItem_new(int position) { return listViewItemList.get(position).getItem_new(); }

    public String getContents(int position) { return listViewItemList.get(position).getContents(); }

    public ImageView getImage(int position) { return listViewItemList.get(position).getImage();}


    public void addItem(String item_new, String contents, ImageView img){
        ListViewItem item = new ListViewItem();

        item.setItem_new(item_new);
        item.setContents(contents);
        item.setImage(img);

        listViewItemList.add(item);
    }

    public void addItem(String contents){
        ListViewItem item = new ListViewItem();
        item.setContents(contents);
        item.setItem_new("NEW");
        listViewItemList.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView item_newTextView = (TextView) convertView.findViewById(R.id.item_new);
        TextView contentsTextView = (TextView) convertView.findViewById(R.id.item_contents);
        ImageView imageImageView = (ImageView) convertView.findViewById(R.id.item_img);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        item_newTextView.setText(listViewItem.getItem_new());
        contentsTextView.setText(listViewItem.getContents());
        // ?

        return convertView;
    }

}