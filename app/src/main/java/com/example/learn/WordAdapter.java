package com.example.learn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class WordAdapter extends BaseAdapter {

    Context context;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;
    private ArrayList<WordMeaning> mData = new ArrayList<>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    private LayoutInflater mInflater;

    public WordAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final WordMeaning item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final WordMeaning item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }
    public void clear(){
        mData.clear();
    }
    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public WordMeaning getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.disp, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.word);
                    holder.txtValue = (TextView) convertView.findViewById(R.id.meaning);
                    break;
                case TYPE_HEADER:
                    convertView = mInflater.inflate(R.layout.date, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.date);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(rowType == TYPE_ITEM){
            holder.textView.setText(mData.get(position).getWord());
            holder.txtValue.setText(""+mData.get(position).getMeaning());
        }else if(rowType == TYPE_HEADER){
            holder.textView.setText(mData.get(position).getDate());
        }


        return convertView;
    }


    public static class ViewHolder {
        public TextView textView;
        public TextView txtValue;
    }
}
