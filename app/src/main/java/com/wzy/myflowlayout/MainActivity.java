package com.wzy.myflowlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyFlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowLayout = findViewById(R.id.my_flow_layout);
    }

    public void addButton(View view) {
//        Button addButton = new Button(this);
//        addButton.setText("添加的button");
//        ViewGroup.LayoutParams lp =
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        flowLayout.addView(addButton,lp);


        Button addButton = new Button(this);
        addButton.setText("添加的button");
        ViewGroup.MarginLayoutParams lp =
                new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 30;
        flowLayout.addView(addButton,lp);
    }
}
