package com.minseon.nonoise;

import android.widget.ImageButton;
import android.widget.ImageView;

public class ListViewItem {
    private String item_new ;
    private String contents;
    private ImageView img;

    public void setItem_new(String _item_new) {
        item_new = _item_new ;
    }
    public void setContents(String _contents){ contents = _contents; }
    public void setImage(ImageView _img) { img = _img; }

    public String getItem_new() {
        return this.item_new ;
    }
    public String getContents(){
        return this.contents;
    }
    public ImageView getImage() { return this.img; }
}

