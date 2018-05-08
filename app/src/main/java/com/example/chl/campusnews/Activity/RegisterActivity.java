package com.example.chl.campusnews.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chl.campusnews.R;
import com.example.chl.campusnews.Util.GlobalMethod;

import org.json.JSONException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//注册界面
public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    public static final String CLUB = "club";//社团
    public static final String USER = "user";//用户
    private static String flag;

    private EditText mobilePhone;//手机号
    private EditText name;//社团名字
    private EditText identifying;//手机验证码
    private TextView sendVerificatioTextview;//发送验证码textview
    private EditText Password;//密码
    private EditText rePassword;//重输密码
    private ImageView checkIoc;//是否同意协议
    private TextView consent;//协议

    //手机号、社团名字、密码、再次输入密码、验证码
    private String inputtelphone, nameString, inputpassword, reInputpassword, inputverificationCode;

    private boolean agreeProtocol = false;//是否同意协议
    public static final String REGEX_PASSWORD = "[a-zA-Z0-9]{6,11}$";
    private int i = 0;//倒计时秒数

    public static void startRegisterActivity(Context context, String flag) {
        Intent intent = new Intent(context, RegisterActivity.class);
        RegisterActivity.flag = flag;
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        findViewById(R.id.activity_register).setOnClickListener(this);//点击空白地方
        if (CLUB.equals(RegisterActivity.flag)) {//如果是社团则可见
            findViewById(R.id.name_ll).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.title)).setText("社团注册");
        }
        mobilePhone = (EditText) findViewById(R.id.mobilePhone);
        name = (EditText) findViewById(R.id.name);
        identifying = (EditText) findViewById(R.id.identifying);
        sendVerificatioTextview = (TextView) findViewById(R.id.sendNumber);
        Password = (EditText) findViewById(R.id.Password);
        rePassword = (EditText) findViewById(R.id.rePassword);
        checkIoc = (ImageView) findViewById(R.id.check_ioc);
        checkIoc.setOnClickListener(this);
        consent = (TextView) findViewById(R.id.consent);
        consent.setOnClickListener(this);

        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.find).setOnClickListener(this);
        sendVerificatioTextview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.activity_register: //点击空白的地方隐藏软键盘
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.sendNumber://发送验证码
                inputtelphone = mobilePhone.getText().toString();
                inputverificationCode = sendVerificatioTextview.getText().toString();
                if (isMobile(inputtelphone)) {
                    CountDownTimer();
                } else {
                    GlobalMethod.toast("请输入格式正确的电话号码！");
                }
                break;
            case R.id.check_ioc://是否同意协议
                if (agreeProtocol == true) {
                    checkIoc.setImageResource(R.mipmap.choice_g3);
                    agreeProtocol = false;
                } else {
                    checkIoc.setImageResource(R.mipmap.choice_u1);
                    agreeProtocol = true;
                }
                break;
            case R.id.consent://查看协议
                startActivity(new Intent(RegisterActivity.this, ProtocolActivity.class));
                break;
            case R.id.login_btn:
                nameString = name.getText().toString();//社团名字
                inputpassword = Password.getText().toString();
                reInputpassword = rePassword.getText().toString();
                    if (validate() && agreeProtocol) {
                        if (isPassword(inputpassword)) {
                            //检验验证码有效性
                            checkAndRegister();//验证码验证是否成功
                        } else {
                            GlobalMethod.toast("亲，您注册的密码必须包含字母和数字哦！");
                        }
                    } else if (agreeProtocol == false) {
                        GlobalMethod.toast("你还没有同意校园新鲜事协议哦！");
                    }
                break;
            case R.id.register://直接登录
                intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//清除栈上面的页面
                startActivity(intent);
                break;
            case R.id.find:
                startActivity(new Intent(this, RetrievePasswordOne.class));//找回密码
                break;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    //实现倒计时
    private void CountDownTimer() {
        if (i > 0) {
            return;
        } else {
            getVerificationCode(inputtelphone);
            i = 60;
        }
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // handler自带方法实现定时器
                try {
                    handler.postDelayed(this, 1000);
                    if (i > 0) {
                        //  textLine.setVisibility(View.GONE);
                        sendVerificatioTextview.setText(Integer.toString(i--) + "秒后重新获取");
                    } else {
                        //    textLine.setVisibility(View.VISIBLE);
                        sendVerificatioTextview.setText("重新发送");
                        handler.removeCallbacks(this);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1);
    }

    private void getVerificationCode(String Telephone) {
        String type;
        if (USER.equals(flag)) {
            type = "0";
        } else {
            type = "1";
        }
        GlobalMethod.toast("手机验证码已经发送，请稍后留意！");
//        YueDongHttpPost.Sendsms(Telephone, type, new YueDongHttpPostCallback() {
//            @Override
//            public void callBack(final String backJson) {
//                RegistrationNumberActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ServerAnswer json;
//                        try {
//                            json = new ServerAnswer(backJson);
//                            if (json.obj.getString("status").equals("y")) {
//                                Global.popup(RegistrationNumberActivity.this, "手机验证码已经发送，请稍后留意！");
//                            } else {
//                                Global.popup(RegistrationNumberActivity.this, json.msg);
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

    // 验证方法
    public boolean validate() {
        inputtelphone = mobilePhone.getText().toString();
        inputverificationCode = identifying.getText().toString();
        inputpassword = Password.getText().toString();
        boolean judge = isMobile(inputtelphone);

        if (CLUB.equals(flag)) {//社团注册
            if (TextUtils.isEmpty(nameString)) {
                GlobalMethod.toast("未填写社团名字");
                return false;
            }
        } else {
            nameString = "";
        }
        if (inputtelphone.equals("")) {
            GlobalMethod.toast("请输入手机号码");
            return false;
        }
        if (judge == false) {
            GlobalMethod.toast("您输入的手机号码格式不正确，请重新输入！");
            return false;
        }
        if (inputverificationCode.equals("")) {
            GlobalMethod.toast("请输入验证码");
            return false;
        }
        if (inputpassword.equals("")) {
            GlobalMethod.toast("请输入密码");
            return false;
        }
        if (reInputpassword.equals("")) {
            GlobalMethod.toast("请再次输入密码");
            return false;
        }
//        if (inputpassword.length() < 6) {
//            Toast.makeText(this, "输入的密码不能少于6位数", Toast.LENGTH_SHORT).show();
//            back false;
//        }
        if (!inputpassword.equals(reInputpassword)) {
            GlobalMethod.toast("两次输入的密码不相同");
            return false;
        }
        return true;
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    private void checkAndRegister() {
        if (validate()) {
//            if (isPassword(inputpassword)) {
//                progressBar.setContent("正在注册，请稍后");
//                inputtelphone = mobilePhone.getText().toString();
//                inputpassword = Password.getText().toString();
//                inputverificationCode = identifying.getText().toString();//验证码
//                if (checkCharsters(inputtelphone) && checkCharsters(inputpassword)) {
//                    progressBar.setVisibility(View.VISIBLE);
//                    Register(inputtelphone, inputpassword, inputverificationCode, accountString, nameString);
//                }
//            } else {
//                Global.popup(this, "亲，您注册的密码必须包含字母和数字哦！");
//            }
        }
    }

    //注册前的字符检查
    private boolean checkCharsters(String str) {
        String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            GlobalMethod.toast("不允许输入特殊符号！");
            return false;
        }
//        if (str.length() < 6) {
//            GlobalMethod.toast("密码长度不能小于6位！");
//            back false;
//        }
        return true;
    }
}
