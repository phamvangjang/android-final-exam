package com.example.cau1de2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cau1de2.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        addEvent();
    }

    private void addEvent() {
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0 ; i < Integer.valueOf(binding.edtNumbOfView.getText().toString());i++){
                            Message msg = new Message();
                            if (i % 2 == 0){
                                msg.what = 1;
                            }else{
                                msg.what = 2;
                            }
                            handler.sendMessage(msg);
                        }
                    }
                });
                thread.start();
            }
        });
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                int random = new Random().nextInt(100);
                switch (msg.what){
                    case 1:
                        Button button = new Button(MainActivity.this);
                        button.setText(String.valueOf(random));
                        binding.container.addView(button);
                        break;
                    case 2:
                        EditText edittext = new EditText(MainActivity.this);
                        edittext.setText(String.valueOf(random));
                        binding.container.addView(edittext);
                        break;
                }
                return true;
            }
        });
    }
}