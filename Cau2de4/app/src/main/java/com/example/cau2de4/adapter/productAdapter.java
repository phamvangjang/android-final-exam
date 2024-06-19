package com.example.cau2de4.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cau2de4.MainActivity;
import com.example.cau2de4.R;
import com.example.cau2de4.model.Product;

import java.util.List;

public class productAdapter extends BaseAdapter {
    MainActivity context;
    int item_layout;
    List<Product> products;

    //construstor

    public productAdapter(MainActivity context, int item_layout, List<Product> products) {
        this.context = context;
        this.item_layout = item_layout;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
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
            Product p = products.get(position);
            hodel.txtCode.setText(p.getProductCode());
            hodel.txtName.setText(p.getProductName());
            hodel.txtPrice.setText(String.valueOf(p.getProductPrice()));
        }catch (Exception e){
            Log.e("getView ", "Error: "+ e.getMessage());
        }

        return convertView;
    }
    public static class ViewHodel{
        TextView txtName, txtPrice, txtCode;
    }
}
