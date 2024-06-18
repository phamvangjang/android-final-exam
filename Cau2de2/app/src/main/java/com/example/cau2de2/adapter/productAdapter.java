package com.example.cau2de2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cau2de2.MainActivity;
import com.example.cau2de2.R;
import com.example.cau2de2.model.Product;

import java.util.List;

public class productAdapter extends BaseAdapter {
    MainActivity context;
    int item_layout;
    List<Product> products;

    //constructor
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
        if(convertView==null){
            hodel = new ViewHodel();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_layout, null);

            //linking view
            hodel.txtName = convertView.findViewById(R.id.txtName);
            convertView.setTag(hodel);
        }else {
            hodel = (ViewHodel) convertView.getTag();
        }

        //binding data
        Product p = products.get(position);
        hodel.txtName.setText(p.getProductName());
        return convertView;
    }
    public static class ViewHodel{
        TextView txtName;
    }
}
