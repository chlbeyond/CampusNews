package com.example.chl.campusnews.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chl.campusnews.R;
import com.example.chl.campusnews.View.MyProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

public class RetrievePasswordOne extends BaseActivity implements View.OnClickListener{

    private EditText NumberCipher;
    private MyProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password_one);
        initView();
    }

    private void initView() {
        findViewById(R.id.retrieve1).setOnClickListener(this);
        findViewById(R.id.register_the_next_step1).setOnClickListener(this);
        NumberCipher = (EditText) findViewById(R.id.mobilePhone);
        progressBar = (MyProgressBar) findViewById(R.id.progress);
//        ((TextView) findViewById(R.id.nav_title)).setText("");
        ((TextView) findViewById(R.id.nav_title)).setText("找回密码");
        findViewById(R.id.nav_left_region).setOnClickListener(this);
        findViewById(R.id.nav).setBackgroundColor(Color.parseColor("#00000000"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_the_next_step1:
                progressBar.setVisibility(View.VISIBLE);
                String number = NumberCipher.getText().toString();
                boolean judge = RegisterActivity.isMobile(number);
                if (judge == true) {//还需要判断是否存在该用户
                    setnumber();
                } else {
                    Toast.makeText(RetrievePasswordOne.this, "您输入的手机号码格式不正确，请重新输入！", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                break;
            case R.id.retrieve1:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.nav_left_region:
                finish();
                break;
        }
    }

    public void setnumber(){
        String  phoneNumber =NumberCipher.getText().toString();
        Checkusername(phoneNumber);

    }

    private JSONObject obj;
    private void Checkusername(final String phoneNumber){
//        YueDongHttpPost.Checkusername(phoneNumber,new YueDongHttpPostCallback() {
//            @Override
//            public void callBack(final String backJson) {
//                RetrievePasswordone.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ServerAnswer json;
//                        try {
//                            json = new ServerAnswer(backJson);
//                            obj = new JSONObject(backJson);
//                            if (json.result == 1 && obj.getString("status").equals("n")) {
//                                progressBar.setVisibility(View.GONE);
//                                Intent intent = new Intent(RetrievePasswordone.this,RetrievePasswordActivityTwo.class);
//                                Bundle bundle = new  Bundle();
//                                bundle.putString("number",phoneNumber);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                            } else {
//                                Global.popup(RetrievePasswordone.this,"该账号未注册，不能找回密码");
//                                progressBar.setVisibility(View.GONE);
//                                back;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        });
    }
}
