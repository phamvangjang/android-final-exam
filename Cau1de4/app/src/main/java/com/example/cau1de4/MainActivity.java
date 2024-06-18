package com.example.cau1de4;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cau1de4.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private Handler handler;
    private static final int DRAWBUTTON = 1;

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
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                int ran = new Random().nextInt(9);
                switch (msg.what) {
                    case DRAWBUTTON:
                        Button button = new Button(MainActivity.this);
                        button.setText(String.valueOf(ran));
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1.0f
                        );
                        layoutParams.gravity = Gravity.CENTER;
                        button.setLayoutParams(layoutParams);
                        binding.container.addView(button);
                        break;
                }
                return true;
            }
        });
        addEvent();
    }

    private void addEvent() {
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numRows = Integer.parseInt(binding.editText.getText().toString());
                drawButtons(numRows);
            }
        });
    }

    private void drawButtons(int numRows) {
        binding.container.removeAllViews();

        for (int i = 0; i < numRows; i++) {
            LinearLayout rowLayout = createNewLinearLayout();

            for (int j = 0; j < 3; j++) {
                Message msg = new Message();
                msg.what = DRAWBUTTON;
                handler.sendMessage(msg);
                Random random = new Random();
                int randomNumber = random.nextInt(9);
                Button button = new Button(MainActivity.this);
                button.setText(String.valueOf(randomNumber));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                layoutParams.setMargins(15,15,15,15);
                button.setLayoutParams(layoutParams);

                if (randomNumber % 2 == 0) {
                    button.setBackgroundColor(Color.GREEN);
                } else {
                    button.setBackgroundColor(Color.GRAY);
                }

                rowLayout.addView(button);
            }

            binding.container.addView(rowLayout);
        }

    }

    private LinearLayout createNewLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(MainActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
    }
}