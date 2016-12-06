package com.example.administrator.myapplication.password.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.password.draw.LocusPassWordView;
import com.example.administrator.myapplication.password.save.SharedPreferencesHelper;

/**
 * Created by Administrator on 2016/12/6.
 */

public class DrawPaswSetActivity extends Activity {

    private LocusPassWordView mPwdView;//九宫格界面
    private Context mContext;
    private String password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawpaswset);
        mContext = getApplicationContext();
        mPwdView = (LocusPassWordView) this.findViewById(R.id.mPassWordView);
        //监听用户是否画完
        mPwdView.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {
            @Override
            public void onComplete(String mPassword) {
                if(password.equals("")){
                    password = mPassword;
                    mPwdView.clearPassword();
                }else{
                    if(password.equals(mPassword)){
                        SharedPreferencesHelper sph = SharedPreferencesHelper.getInstance(getApplicationContext());
                        sph.putString("drawpasw",password);
                        Toast.makeText(mContext,"密码设置成功", Toast.LENGTH_SHORT).show();
                        DrawPaswSetActivity.this.finish();
                    }else{
                        password = "";
                        mPwdView.clearPassword();
                        Toast.makeText(mContext,"两次密码不一样  请重画", Toast.LENGTH_SHORT).show();
                    }
                }
                /*
                SharedPreferencesHelper sph = SharedPreferencesHelper.getInstance(getApplicationContext());
                String pwd = sph.getString("password", "");
                boolean passed = false;
                if (pwd.length() == 0) {
                    sph.putString("drawpasw",mPassword);
                    Toast.makeText(mContext, mContext.getString(R.string.pwd_setted), Toast.LENGTH_LONG).show();
                    DrawPaswSetActivity.this.finish();
                    return;
                } else {
                    String encodedPwd = mPassword;
                    if (encodedPwd.equals(pwd)) {
                        passed = true;
                    } else {
                        mPwdView.markError();
                    }
                }

                if (passed) {
//                  Log.d("hcj", "password is correct!");
                    Toast.makeText(mContext, mContext.getString(R.string.pwd_correct), Toast.LENGTH_LONG).show();
//					finish();
                }
                */
            }
        });
    }

}
