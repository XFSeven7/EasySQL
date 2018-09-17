package com.qxf.easysql.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class RecycAdapter extends RecyclerView.Adapter<RecycAdapter.MyViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private ArrayList<String> datas = new ArrayList<>();

    public void add(String s) {
        datas.add(s);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<String> data) {
        datas.addAll(data);
        notifyDataSetChanged();
    }

    public ArrayList<String> getDatas() {
        return datas;
    }

    public void addAll(ArrayList<String> data, boolean isClear) {
        if (isClear) {
            datas.clear();
        }
        addAll(data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick((Integer) v.getTag());
                }
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(datas.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addAll(Set<String> data) {
        for (String s : data) {
            datas.add(s);
        }
        notifyDataSetChanged();
    }

    public void addAll(Set<String> data, boolean isClear) {
        if (isClear) {
            datas.clear();
        }
        addAll(data);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

}
