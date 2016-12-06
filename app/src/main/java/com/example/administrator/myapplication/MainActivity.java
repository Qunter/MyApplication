package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapplication.password.save.SharedPreferencesHelper;
import com.example.administrator.myapplication.password.ui.DrawPaswSetActivity;
import com.example.administrator.myapplication.password.ui.NumPaswSetActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button numPaswSetBtn,drawPaswSetBtn,numPaswVerifyBtn,drawPaswVerifyBtn,numPaswClearBtn,drawPaswClearBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBtn();
    }
    private void initBtn(){
        numPaswSetBtn = (Button) findViewById(R.id.numPaswSetBtn);
        drawPaswSetBtn = (Button) findViewById(R.id.drawPaswSetBtn);
        numPaswVerifyBtn = (Button) findViewById(R.id.numPaswVerifyBtn);
        drawPaswVerifyBtn = (Button) findViewById(R.id.drawPaswVerifyBtn);
        numPaswClearBtn = (Button) findViewById(R.id.numPaswClearBtn);
        drawPaswClearBtn = (Button) findViewById(R.id.drawPaswClearBtn);
        numPaswSetBtn.setOnClickListener(this);
        drawPaswSetBtn.setOnClickListener(this);
        numPaswVerifyBtn.setOnClickListener(this);
        drawPaswVerifyBtn.setOnClickListener(this);
        numPaswClearBtn.setOnClickListener(this);
        drawPaswClearBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.numPaswSetBtn:
                Intent numIt = new Intent(MainActivity.this, NumPaswSetActivity.class);
                startActivity(numIt);
                break;
            case R.id.drawPaswSetBtn:
                Intent drawIt = new Intent(MainActivity.this, DrawPaswSetActivity.class);
                startActivity(drawIt);
                break;
            case R.id.numPaswVerifyBtn:
                break;
            case R.id.drawPaswVerifyBtn:
                break;
            case R.id.numPaswClearBtn:
                String numpasw;
                SharedPreferencesHelper numsph = SharedPreferencesHelper.getInstance(getApplicationContext());
                numpasw = numsph.getString("numpasw","");
                if(numpasw.equals("")){
                    Toast.makeText(getApplicationContext(),"尚未设置密码  无需清除",Toast.LENGTH_SHORT).show();
                }else {
                    numsph.putString("numpasw","");
                    Toast.makeText(getApplicationContext(),"密码清除成功",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.drawPaswClearBtn:
                String drawpasw;
                SharedPreferencesHelper drawsph = SharedPreferencesHelper.getInstance(getApplicationContext());
                drawpasw = drawsph.getString("drawpasw","");
                if(drawpasw.equals("")){
                    Toast.makeText(getApplicationContext(),"尚未设置密码  无需清除",Toast.LENGTH_SHORT).show();
                }else {
                    drawsph.putString("drawpasw","");
                    Toast.makeText(getApplicationContext(),"密码清除成功",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
