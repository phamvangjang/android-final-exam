package com.example.cau2de4;

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

import com.example.cau2de4.adapter.productAdapter;
import com.example.cau2de4.database.Database;
import com.example.cau2de4.databinding.ActivityMainBinding;
import com.example.cau2de4.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    productAdapter adapter;
    ArrayList<Product> products;
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
        binding.lvProduct.setOnItemLongClickListener((parent, view, position, id) -> {
            Product p =products.get(position);
            openStudentDialog(p);
            return false;
        });
    }

    private void openStudentDialog(Product p) {
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
        adapter = new productAdapter(MainActivity.this, R.layout.list_item, getDataFromDb());
        binding.lvProduct.setAdapter(adapter);
    }

    private List<Product> getDataFromDb() {
        products = new ArrayList<>();
        Cursor cursor = db.queryData(" SELECT * FROM " + db.TBL_NAME);
        while (cursor.moveToNext()){
            products.add(new Product(cursor.getString(0), cursor.getString(1), cursor.getDouble(2)));
        }
        cursor.close();
        return products;
    }

    private void prepareDb() {
        db = new Database(this);
        db.createSampleData();
    }
}