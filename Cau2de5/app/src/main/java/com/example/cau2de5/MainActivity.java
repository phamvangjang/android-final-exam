package com.example.cau2de5;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cau2de5.adapter.bookAdapter;
import com.example.cau2de5.database.Database;
import com.example.cau2de5.databinding.ActivityMainBinding;
import com.example.cau2de5.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    bookAdapter adapter;
    ArrayList<Book> books;
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
        binding.lvBook.setOnItemLongClickListener((parent, view, position, id) -> {
            Book p =books.get(position);
//            openStudentDialog(p);
//            openStudentDialogDelete(p);
            openStudentDialogUpdate(p);
            return false;
        });
    }

    private void openStudentDialogUpdate(Book p) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        //fill book to dialog
        EditText edtCode = dialog.findViewById(R.id.edtCode);
        edtCode.setText(String.valueOf(p.getBookCode()));
        edtCode.setEnabled(false);

        EditText edtName = dialog.findViewById(R.id.edtName);
        edtName.setText(p.getBookName());

        EditText edtPrice = dialog.findViewById(R.id.edtPrice);
        edtPrice.setText(String.valueOf(p.getBookPrice()));

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update data
                String updatedName = edtName.getText().toString();

                double updatedPrice;
                try {
                    updatedPrice = Double.parseDouble(edtPrice.getText().toString());
                    if (updatedPrice <= 0) {
                        Toast.makeText(MainActivity.this, "Price must be a positive number.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid price format.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    db.updateData(p.getBookCode(),updatedName,updatedPrice);
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    Log.e("updateData", "Error: " +e.getMessage());
                }
                loadData();
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

        //set view dialog
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void openStudentDialogDelete(Book p) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        //fill book to dialog
        EditText edtCode = dialog.findViewById(R.id.edtCode);
        edtCode.setText(String.valueOf(p.getBookCode()));
        edtCode.setEnabled(false);

        EditText edtName = dialog.findViewById(R.id.edtName);
        edtName.setText(p.getBookName());
        edtName.setEnabled(false);

        EditText edtPrice = dialog.findViewById(R.id.edtPrice);
        edtPrice.setText(String.valueOf(p.getBookPrice()));
        edtPrice.setEnabled(false);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteConfirmDialog(p);

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

        //set view dialog
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void openDeleteConfirmDialog(Book p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm delete?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Do you want to delete product " + p.getBookName() + " ?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean delete = db.deleteData(p.getBookCode());
                if (delete){
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                }else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void openStudentDialog(Book p) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        //fill student dialog
        EditText editCode = dialog.findViewById(R.id.edtCode);
        EditText editName = dialog.findViewById(R.id.edtName);
        EditText editPrice = dialog.findViewById(R.id.edtPrice);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data
                String code = editCode.getText().toString();
                String name = editName.getText().toString();
                String price = editPrice.getText().toString();

                db.execSql(" INSERT INTO " + db.TBL_NAME + " VALUES ( " + " ' " + code + " ', "
                                                                        + " ' " + name + " ', "
                                                                        + " ' " + price + " ') ");
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

        //set view dialog
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void loadData() {
        adapter = new bookAdapter(MainActivity.this, R.layout.list_item, getDataFromDb());
        binding.lvBook.setAdapter(adapter);
    }

    private List<Book> getDataFromDb() {
        books = new ArrayList<>();
        Cursor cursor = db.queryData(" SELECT * FROM " + db.TBL_NAME);
        while (cursor.moveToNext()){
            books.add(new Book(cursor.getString(0), cursor.getString(1), cursor.getDouble(2)));
        }
        cursor.close();
        return books;
    }

    private void prepareDb() {
        db = new Database(this);
        db.createSampleData();
    }
}