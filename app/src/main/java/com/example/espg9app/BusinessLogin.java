package com.example.espg9app;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BusinessLogin extends AppCompatActivity {

    EditText et_username, et_password;
    Button btn_login;
    Button btn_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businesslogin);
        Login();
        Signup();
    }

    void Login(){
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_login = (Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBAccess DB = new DBAccess();
                //DB.CheckPassword(et_username.getText().toString(), et_password.getText().toString(), false)
                if(et_username.getText().toString().equals("") && et_password.getText().toString().equals("")){
                    Toast.makeText(BusinessLogin.this, "Username and Password is correct", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BusinessLogin.this, BusinessMain.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(BusinessLogin.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void Signup(){
        btn_signup = (Button)findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusinessLogin.this, BusinessSignup.class);
                startActivity(intent);
                }
            }
        );
    }

}