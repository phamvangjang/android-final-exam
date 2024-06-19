package com.example.cau2de5.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cau2de5.MainActivity;
import com.example.cau2de5.R;
import com.example.cau2de5.model.Book;

import java.util.List;

public class bookAdapter extends BaseAdapter {
    MainActivity context;
    int item_layout;
    List<Book> books;

    //constructor

    public bookAdapter(MainActivity context, int item_layout, List<Book> books) {
        this.context = context;
        this.item_layout = item_layout;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodel hodel;
        if (convertView==null){
            hodel = new ViewHodel();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_layout, null);

            //linking view
            hodel.txtCode = convertView.findViewById(R.id.txtCode);
            hodel.txtName = convertView.findViewById(R.id.txtName);
            hodel.txtPrice = convertView.findViewById(R.id.txtPrice);
        }else {
            hodel = (ViewHodel) convertView.getTag();
        }

        //binding data
        try{
            Book p = books.get(position);
            hodel.txtCode.setText(p.getBookCode());
            hodel.txtName.setText(p.getBookName());
            hodel.txtPrice.setText(String.valueOf(p.getBookPrice()));
        }catch (Exception e){
            Log.e("getView ", "Error: "+ e.getMessage());
        }

        return convertView;
    }
    public static class ViewHodel{
        TextView txtName, txtPrice, txtCode;
    }
}
