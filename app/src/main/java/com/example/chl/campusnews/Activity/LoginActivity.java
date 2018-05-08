package com.example.chl.campusnews.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chl.campusnews.R;
import com.example.chl.campusnews.Util.GlobalMethod;

//登录界面
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mobilePhone;
    private EditText loginPasswrod;
    private String inputtelphone, inputpassword;

    private TextView findBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        mobilePhone = (EditText) findViewById(R.id.mobilePhone);
        loginPasswrod = (EditText) findViewById(R.id.password);

        findViewById(R.id.activity_login).setOnClickListener(this);
        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.userRegister).setOnClickListener(this);
        findViewById(R.id.clubRegister).setOnClickListener(this);
        findViewById(R.id.findPassword).setOnClickListener(this);

        findBtn = (TextView) findViewById(R.id.findPassword);
        findBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.activity_login: //点击空白的地方隐藏软键盘
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.login_btn: //登录
                if (validate()) {
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.userRegister: //用户注册
                RegisterActivity.startRegisterActivity(LoginActivity.this, RegisterActivity.USER);
                break;
            case R.id.clubRegister: //社团注册
                RegisterActivity.startRegisterActivity(LoginActivity.this, RegisterActivity.CLUB);
                break;
            case R.id.findPassword: //找回密码
                startActivity(new Intent(LoginActivity.this, RetrievePasswordOne.class));
                break;
        }
    }
    //验证方法
    public boolean validate() {
        inputtelphone = mobilePhone.getText().toString();
        inputpassword = loginPasswrod.getText().toString();
        if (inputtelphone.equals("") || inputpassword.equals("")) {
            GlobalMethod.toast("账号或密码不能为空");
            return false;
        }
//        if (inputpassword.length() < 6) {
//            Toast.makeText(this, "请输入六位以上密码", Toast.LENGTH_SHORT).show();
//            back false;
//        }
        if (inputtelphone.length() != 11) {
            GlobalMethod.toast("手机号码位数不正确");
            return false;
        }else {
            if (!GlobalMethod.isMobileNO(inputtelphone)) {
                GlobalMethod.toast("请填写正确的手机号码");
                return false;
            }
        }
        return true;
    }
}
