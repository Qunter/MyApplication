package com.example.administrator.myapplication.password.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.password.number.KeyboardUtil;
import com.example.administrator.myapplication.password.number.PasswordTextView;
import com.example.administrator.myapplication.password.save.SharedPreferencesHelper;

/**
 * Created by Administrator on 2016/12/6.
 */

public class NumPaswSetActivity extends Activity {
    private RelativeLayout rl_keyboard;
    private KeyboardUtil keyboardUtil;
    private int change_type;
    private Button btn_showKey,btn_hideKey;
    private PasswordTextView et_pwd1,et_pwd2,et_pwd3,et_pwd4;
    private String password = "";
    private String passwordhc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numpaswset);
        initTestBtn();
        initPwdTv();
        initKeyboard();
        initListener();
    }

    private void initTestBtn() {
        btn_showKey = (Button) findViewById(R.id.btn_showKey);
        btn_hideKey = (Button) findViewById(R.id.btn_hideKey);
        btn_showKey.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showKeyBoard();
            }
        });
        btn_hideKey.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyBoard();
            }
        });
    }
    private void initPwdTv(){
        et_pwd1 = (PasswordTextView) findViewById(R.id.et_pwd1);
        et_pwd2 = (PasswordTextView) findViewById(R.id.et_pwd2);
        et_pwd3 = (PasswordTextView) findViewById(R.id.et_pwd3);
        et_pwd4 = (PasswordTextView) findViewById(R.id.et_pwd4);
    }


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
     * 显示键盘
     */
    protected void hideKeyBoard() {
        rl_keyboard.setVisibility(View.GONE);
        keyboardUtil.hideKeyboard();
        keyboardUtil.setType(-1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (rl_keyboard.getVisibility() != View.GONE) {
                hideKeyBoard();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    /*
    监听第四个tv的更改事件，如果第四个更改，则保存密码
     */
    private void initListener() {
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
                }else{
                    passwordhc += et_pwd1.getTextContent();
                    passwordhc += et_pwd2.getTextContent();
                    passwordhc += et_pwd3.getTextContent();
                    passwordhc += et_pwd4.getTextContent();
                    if (password.equals(passwordhc)) {
                        SharedPreferencesHelper sph = SharedPreferencesHelper.getInstance(getApplicationContext());
                        sph.putString("numpasw", password);
                        Toast.makeText(getApplicationContext(),"密码设置成功",Toast.LENGTH_SHORT).show();
                        NumPaswSetActivity.this.finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"两次输入不一致  请重新输入",Toast.LENGTH_SHORT).show();
                        clearText();
                        clearPsw();
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