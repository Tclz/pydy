package com.example.a13494.myapplication.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.a13494.myapplication.R;

import static com.example.a13494.myapplication.Util.JudgeFormat.isPasswordValid;
import static com.example.a13494.myapplication.Util.JudgeFormat.isPhoneNumberValid;
import static com.example.a13494.myapplication.Util.JudgeFormat.requestVerifyCode;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_tel;
    private EditText et_code;
    private Button bt_get_verify_code;
    private EditText et_password;
    private Button bt_commit;
    private String telephoneNumber;
    private String newPassword;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();
        setListener();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_verify_code:
                telephoneNumber = et_tel.getText().toString();
                boolean result = isPhoneNumberValid(telephoneNumber);
                if (result) {
                    requestVerifyCode();
                }else{
                    Toast.makeText(this,"The phone number is invalid.",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.modify_pwd_button:
                newPassword = et_password.getText().toString();
                if(isPasswordValid(newPassword))
                {
                    code = et_code.getText().toString();
                    attempToModifyPwd(telephoneNumber,newPassword,code);
                }
                break;

            case R.id.exist_account:
                break;

            default:
                break;
        }
    }


    public void attempToModifyPwd(String telephoneNumber,String newPassword,String code)
    {


    }
    public void setListener()
    {
        bt_get_verify_code.setOnClickListener(this);
        bt_commit.setOnClickListener(this);
    }


    public void initView()
    {
        et_tel = (EditText)findViewById(R.id.edit_tel);
        et_code = (EditText)findViewById(R.id.edit_verify_code);
        bt_get_verify_code = (Button)findViewById(R.id.get_verify_code);
        et_password = (EditText)findViewById(R.id.new_password_input);
        bt_commit = (Button)findViewById(R.id.modify_pwd_button);

    }
}
