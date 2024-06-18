package com.example.cau2de2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
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

import com.example.cau2de2.adapter.productAdapter;
import com.example.cau2de2.database.Database;
import com.example.cau2de2.databinding.ActivityMainBinding;
import com.example.cau2de2.model.Product;

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
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        prepareDb();
        loadData();
        addEvents();
    }

    private void addEvents() {
        binding.lvProduct.setOnItemLongClickListener((parent, view, position, id) -> {
            Product p = products.get(position);
            openProductDialog(p);
            return false;
        });

    }

    private void openProductDialog(Product p) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        //fill product to dialog
        EditText edtCode = dialog.findViewById(R.id.edtCode);
        edtCode.setText(String.valueOf(p.getProductCode()));
        edtCode.setEnabled(false);

        EditText edtName = dialog.findViewById(R.id.edtName);
        edtName.setText(p.getProductName());
        edtName.setEnabled(false);

        EditText edtPrice = dialog.findViewById(R.id.edtPrice);
        edtPrice.setText(String.valueOf(p.getProductCode()));
        edtPrice.setEnabled(false);

        //handle button delete
        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteConfirmDialog(p);
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

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void openDeleteConfirmDialog(Product p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm delete?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Do you want to delete product " + p.getProductName() + " ?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean delete = db.deleteData(p.getProductCode());
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

    private void loadData() {
        adapter = new productAdapter(MainActivity.this, R.layout.list_item, getDataFromDb());
        binding.lvProduct.setAdapter(adapter);
    }

    private List<Product> getDataFromDb() {
        products = new ArrayList<>();
        Cursor cursor = db.queryData("SELECT * FROM " + db.TBL_NAME);
        while (cursor.moveToNext()){
            products.add(new Product(cursor.getString(0), cursor.getString(1), cursor.getDouble(2)));
        }
        cursor.close();
        return products;
    }

    private void prepareDb() {
        db=new Database(this);
        db.createSampleData();
    }
}