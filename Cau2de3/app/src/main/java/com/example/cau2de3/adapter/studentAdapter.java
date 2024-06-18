package com.example.cau2de3.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cau2de3.MainActivity;
import com.example.cau2de3.R;
import com.example.cau2de3.model.Student;

import java.util.List;

public class studentAdapter extends BaseAdapter {
    MainActivity context;
    int item_layout;
    List<Student> students;

    //constructor

    public studentAdapter(MainActivity context, int item_layout, List<Student> students) {
        this.context = context;
        this.item_layout = item_layout;
        this.students = students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
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
            hodel.txtName = convertView.findViewById(R.id.txtName);
            hodel.txtClass = convertView.findViewById(R.id.txtClass);
            hodel.txtCode = convertView.findViewById(R.id.txtCode);
        }else {
            hodel = (ViewHodel) convertView.getTag();
        }

        //binding data
        try{
            Student s = students.get(position);
            hodel.txtName.setText(s.getStudentName());
            hodel.txtClass.setText(s.getStudentClass());
            hodel.txtCode.setText(s.getStudentCode());
        }catch (Exception e){
            Log.e("getView ", "Error: "+ e.getMessage());
        }

        return convertView;
    }
    public static class ViewHodel{
        TextView txtName, txtClass, txtCode;
    }
}
