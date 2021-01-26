package com.example.wemeet;

import android.view.View;
import android.widget.AdapterView;

public interface OnGroupClickListener {
    public void onItemClick(GroupAdapter.ViewHolder holder, View view, int position);
}
