package com.example.cau1de3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cau1de3.databinding.ActivityMainBinding;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ExecutorService executorService;

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
        executorService = Executors.newSingleThreadExecutor();
        addEvent();
    }

    private void addEvent() {
        binding.btnDrawView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.txtNumb.setText("");
                binding.containerLayout.removeAllViews();
                executeLongRunningTask();
            }
        });
    }

    private void executeLongRunningTask() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
//                int numbOfView = Integer.parseInt(binding.editNumOfViews.getText().toString());
                Random random = new Random();
                for (int i = 1; i<=12 ;i++){
                    int percent = i*100/12;
                    int randNumb = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.txtPercent.setText(percent + "%");
                            binding.pbPercent.setProgress(percent);

//                            TableLayout.LayoutParams params = new TableLayout.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT);
//                              LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,ViewGroup.LayoutParams.WRAP_CONTENT);
                            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                            params.width = 0; // Chiều rộng là wrap_content
                            params.height = GridLayout.LayoutParams.WRAP_CONTENT; // Chiều cao là wrap_content
                            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
//                            params.setMargins(8, 8, 8, 8); // Đặt margin cho phần tử
                            params.setMargins(10,10,10,10);
                            Button button = new Button(MainActivity.this);
                            button.setText(String.valueOf(randNumb));

                            button.setTextSize(22);
                            button.setTextColor(Color.BLACK);

                            if(randNumb == 10){
                                button.setText("*");
                            }
                            if(randNumb == 11){
                                button.setText("0");
                            }
                            if(randNumb == 12){
                                button.setText("#");
                            }



                            // Thiết lập sự kiện OnClickListener cho button
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Xử lý sự kiện khi button được click
                                    String numb = binding.txtNumb.getText().toString();
                                    switch (randNumb) {
                                        case 10:
                                            // Xử lý khi button có giá trị 10 được click
                                            binding.txtNumb.setText(numb+"*");
                                            break;
                                        case 11:
                                            // Xử lý khi button có giá trị 11 được click
                                            binding.txtNumb.setText(numb+"0");
                                            break;
                                        case 12:
                                            // Xử lý khi button có giá trị 12 được click
                                            binding.txtNumb.setText(numb+"#");
                                            break;

                                        default:
                                            // Xử lý khi các button khác được click
                                            binding.txtNumb.setText(numb + String.valueOf(randNumb));
                                            break;
                                    }

                                }
                            });
                            button.setLayoutParams(params);


                            binding.containerLayout.addView(button);

                            if(percent == 100){
                                binding.txtPercent.setText("DONE!");
                            }
                        }
                    });
                    try{
                        Thread.sleep(300);
                    }catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }

            }
        });

    }
}