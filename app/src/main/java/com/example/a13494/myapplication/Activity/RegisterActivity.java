package com.example.a13494.myapplication.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a13494.myapplication.R;
import static com.example.a13494.myapplication.Util.JudgeFormat.isPasswordValid;
import static com.example.a13494.myapplication.Util.JudgeFormat.isPhoneNumberValid;
import static com.example.a13494.myapplication.Util.JudgeFormat.requestVerifyCode;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    //UI
    private EditText mEditTelView;
    private EditText mEditVerifyCodeView;
    private Button mGetVerifyCodeView;
    private EditText mEditPasswordView;
    private EditText mConfirmPassword;
    private Button mSignUpView;
    private TextView mExistAccountView;


    private String telephoneNumber;
    private String verifyCode;
    private String password;
    private String passwordConfirm;
    private Drawable dr_phone;
    private Drawable dr_password;
    private Drawable dr_confirm_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setListener();

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.get_verify_code:
                telephoneNumber = mEditTelView.getText().toString();
                boolean result = isPhoneNumberValid(telephoneNumber);
                if(result)
                {
                    requestVerifyCode();
                }else{
                   Toast.makeText(this,"The Phone Number is invalid.",Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.sign_up_button:
                int code = attempToSignUp();
                switch (code){

                }
                break;

            case R.id.exist_account:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();

            default:
                break;

            }

    }

    public int attempToSignUp()
    {

        if(!checkPassword()){
            return -1;
        }

        verifyCode = mEditVerifyCodeView.getText().toString();


        //发起请求进行注册
        return 0;

    }


    public boolean checkPassword(){

        password = mEditPasswordView.getText().toString();
        if(!isPasswordValid(password)){
            Toast.makeText(this,"Password is too short.",Toast.LENGTH_SHORT).show();
            return false;

        }else{
            passwordConfirm = mConfirmPassword.getText().toString();
            if(!passwordConfirm.equals(password)){
                   Toast.makeText(this, "Ensure that passwords are the same.",Toast.LENGTH_SHORT).show();
                   return false;
            }
        }
        return true;

    }

    public void setListener(){
        mGetVerifyCodeView.setOnClickListener(this);
        mSignUpView.setOnClickListener(this);
        mExistAccountView.setOnClickListener(this);

    }
    public void initView(){
        mEditTelView = (EditText)findViewById(R.id.edit_tel);
        dr_phone = getResources().getDrawable(R.drawable.phone);
        dr_phone.setBounds(0,0,50,50);
        mEditTelView.setCompoundDrawables(dr_phone,null,null,null);

        mEditVerifyCodeView = (EditText)findViewById(R.id.edit_verify_code);
        mGetVerifyCodeView = (Button)findViewById(R.id.get_verify_code);

        mEditPasswordView = (EditText)findViewById(R.id.password_input);
        dr_password = getResources().getDrawable(R.drawable.password);
        dr_password.setBounds(0,0,50,50);
        mEditPasswordView.setCompoundDrawables(dr_password,null,null,null);

        mConfirmPassword = (EditText)findViewById(R.id.password_confirm);
        dr_confirm_pwd = getResources().getDrawable(R.drawable.password);
        dr_confirm_pwd.setBounds(0,0,50,50);
        mConfirmPassword.setCompoundDrawables(dr_password,null,null,null);

        mSignUpView = (Button)findViewById(R.id.sign_up_button);
        mExistAccountView = (TextView)findViewById(R.id.exist_account);

    }
}
