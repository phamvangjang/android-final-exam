package com.example.cau2de3;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cau2de3.adapter.studentAdapter;
import com.example.cau2de3.database.Database;
import com.example.cau2de3.databinding.ActivityMainBinding;
import com.example.cau2de3.model.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    studentAdapter adapter;
    ArrayList<Student> students;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prepareDb();
        loadData();
        addEvents();
    }

    private void addEvents() {
        binding.lvStudent.setOnItemLongClickListener((parent, view, position, id) -> {
            Student s =students.get(position);
            openStudentDialog(s);
            return false;
        });
    }

    private void openStudentDialog(Student s) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        //fill student dialog
        EditText editName = dialog.findViewById(R.id.edtName);
        EditText editClass = dialog.findViewById(R.id.edtClass);
        EditText editCode = dialog.findViewById(R.id.edtCode);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data
                String name = editName.getText().toString();
                String Class = editClass.getText().toString();
                String code = editCode.getText().toString();

                db.execSql(" INSERT INTO " + db.TBL_NAME + " VALUES ( " + " ' " + name + " ', "
                                                                        + " ' " + Class + " ', "
                                                                        + " ' " + code + " ') ");
                loadData();

                // dong hop thoai
                dialog.dismiss();
            }
        });

        //handle btn cancle
        Button btnCancle = dialog.findViewById(R.id.btnCancel);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void loadData() {
        adapter = new studentAdapter(MainActivity.this, R.layout.list_item, getDataFromDb());
        binding.lvStudent.setAdapter(adapter);
    }

    private List<Student> getDataFromDb() {
        students = new ArrayList<>();
        Cursor cursor = db.queryData(" SELECT * FROM " + db.TBL_NAME);
        while (cursor.moveToNext()){
            students.add(new Student(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
        }
        cursor.close();
        return students;
    }

    private void prepareDb() {
        db = new Database(this);
        db.createSampleData();
    }
}