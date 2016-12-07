package com.example.administrator.myapplication.password.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.password.number.KeyboardUtil;
import com.example.administrator.myapplication.password.number.PasswordTextView;
import com.example.administrator.myapplication.password.save.SharedPreferencesHelper;

public class NumPaswActivity extends Activity {
    private RelativeLayout rl_keyboard;
    private KeyboardUtil keyboardUtil;
    private int change_type;
    private TextView hintTv;
    private PasswordTextView et_pwd1,et_pwd2,et_pwd3,et_pwd4;
    private String password = "";
    private String passwordhc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numpaswset);
        initHintTv();
        initPwdTv();
        initKeyboard();
        Intent it = getIntent();
        int index = it.getIntExtra("setOrValidate",2);
        if(index==0){
            initSetListener();
        }else if (index==1){
            SharedPreferencesHelper sph = SharedPreferencesHelper.getInstance(getApplicationContext());
            password = sph.getString("numpasw","");
            //判断是否未设置密码
            if(password==""){
                Toast.makeText(getApplicationContext(),R.string.pasw_null, Toast.LENGTH_SHORT).show();
                NumPaswActivity.this.finish();
            }else{
                initVerifyListener();
            }

        }
        showKeyBoard();
    }
    /**
     * 初始化提示textview
     */
    private void initHintTv(){
        hintTv = (TextView) findViewById(R.id.numPaswHintTv);
    }

    /**
     * 初始化自定义密码输入框
     */
    private void initPwdTv(){
        et_pwd1 = (PasswordTextView) findViewById(R.id.et_pwd1);
        et_pwd2 = (PasswordTextView) findViewById(R.id.et_pwd2);
        et_pwd3 = (PasswordTextView) findViewById(R.id.et_pwd3);
        et_pwd4 = (PasswordTextView) findViewById(R.id.et_pwd4);
    }

    /**
     * 初始化键盘
     */
    private void initKeyboard() {
        keyboardUtil = new KeyboardUtil(this,et_pwd1,et_pwd2,et_pwd3,et_pwd4);
        rl_keyboard = (RelativeLayout) findViewById(R.id.rl__keyboard);
        keyboardUtil.setKeyboardListener(new KeyboardUtil.KeyboardListener() {

            @Override
            public void onOK() {
                hideKeyBoard();
                change_type = -1;
            }
        });
    }
    /**
     * 显示键盘
     */
    protected void showKeyBoard() {
        rl_keyboard.setVisibility(View.VISIBLE);
        keyboardUtil.setType(change_type);
        keyboardUtil.showKeyboard();
    }

    /**
     * 隐藏键盘
     */
    protected void hideKeyBoard() {
        rl_keyboard.setVisibility(View.GONE);
        keyboardUtil.hideKeyboard();
        keyboardUtil.setType(-1);
    }
    /**
     * 修改按键事件
     * back键事件重写，按back时直接关闭布局
     * 如不重写则会先关闭键盘再关闭布局
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (rl_keyboard.getVisibility() != View.GONE) {
                //hideKeyBoard();
                NumPaswActivity.this.finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 设置界面的监听事件
     * 监听第四个tv的更改事件，如果第四个更改，则保存密码
     */
    private void initSetListener() {
        //监听最后一个密码框的文本改变事件回调
        et_pwd4.setOnTextChangedListener(new PasswordTextView.OnTextChangedListener() {
            @Override
            public void textChanged(String content) {
                if(password ==""){
                    password += et_pwd1.getTextContent();
                    password += et_pwd2.getTextContent();
                    password += et_pwd3.getTextContent();
                    password += et_pwd4.getTextContent();
                    clearText();
                    hintTv.setText(R.string.pasw_hint_again);
                }else{
                    passwordhc += et_pwd1.getTextContent();
                    passwordhc += et_pwd2.getTextContent();
                    passwordhc += et_pwd3.getTextContent();
                    passwordhc += et_pwd4.getTextContent();
                    if (password.equals(passwordhc)) {
                        SharedPreferencesHelper sph = SharedPreferencesHelper.getInstance(getApplicationContext());
                        sph.putString("numpasw", password);
                        Toast.makeText(getApplicationContext(),"密码设置成功",Toast.LENGTH_SHORT).show();
                        NumPaswActivity.this.finish();
                    }else{
                        clearText();
                        hintTv.setText(R.string.pasw_hint_error);
                        clearPsw();
                        clearPswhc();
                    }
                }
                startTimer();
            }
        });
    }
    /**
     * 验证界面的监听事件
     * 监听第四个tv的更改事件，如果第四个更改，则保存密码
     */
    private void initVerifyListener() {
        //监听最后一个密码框的文本改变事件回调
        et_pwd4.setOnTextChangedListener(new PasswordTextView.OnTextChangedListener() {
            @Override
            public void textChanged(String content) {
                if(password.equals("")){
                }else {
                    passwordhc += et_pwd1.getTextContent();
                    passwordhc += et_pwd2.getTextContent();
                    passwordhc += et_pwd3.getTextContent();
                    passwordhc += et_pwd4.getTextContent();
                    if (password.equals(passwordhc)) {
                        Toast.makeText(getApplicationContext(), "密码验证成功", Toast.LENGTH_SHORT).show();
                        NumPaswActivity.this.finish();
                    } else {
                        clearText();
                        hintTv.setText(R.string.pasw_verify_error);
                        clearPswhc();
                    }
                }
                startTimer();
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            clearText();
        }
    };

    //这里是为了让第四个显示的输入框先显示,然后再整个清除
    private void startTimer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    /**
     * 清除输入的内容--重输
     */
    private void clearText() {
        et_pwd1.setTextContent("");
        et_pwd2.setTextContent("");
        et_pwd3.setTextContent("");
        et_pwd4.setTextContent("");
    }
    /**
     * 清除密码缓存内的内容--重输
     */
    private void clearPswhc(){
        passwordhc = "";
    }

    /**
     * 清除密码内的内容--重输
     */
    private void clearPsw(){
        password = "";
    }

}