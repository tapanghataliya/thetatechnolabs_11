package com.apphelp.help.thetatechnolabs.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.apphelp.help.thetatechnolabs.R;
import com.apphelp.help.thetatechnolabs.controller.Global;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    private TextInputEditText emailTextInputEditText;
    private TextInputEditText passwordTextInputEditText;
    LinearLayout lyl_sign_in;

    String E_EMAIL, E_PASSWORD, EDIT_EMAIL, EDIT_PASSWORD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Initilization();
    }

    private void Initilization() {

        sharedPreferences = getSharedPreferences(Global.THETA_TECHNOLABS, MODE_PRIVATE);

        EDIT_EMAIL = sharedPreferences.getString(Global.EMAIL,"");
        EDIT_PASSWORD = sharedPreferences.getString(Global.PASSWORD,"");

        emailTextInputEditText = (TextInputEditText) findViewById(R.id.emailTextInputEditText);
        passwordTextInputEditText = (TextInputEditText) findViewById(R.id.passwordTextInputEditText);

        lyl_sign_in = (LinearLayout) findViewById(R.id.lyl_sign_in);

        lyl_sign_in.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lyl_sign_in:

               Validation();
                break;
        }
    }

    private void Validation() {

        E_EMAIL = emailTextInputEditText.getText().toString().trim();
        E_PASSWORD = passwordTextInputEditText.getText().toString().trim();

        if (E_EMAIL.equalsIgnoreCase("")){

            emailTextInputEditText.setFocusable(true);
            emailTextInputEditText.setError("Field is Required");

        } else if (E_PASSWORD.equalsIgnoreCase("")){

            emailTextInputEditText.setError(null);
            passwordTextInputEditText.setFocusable(true);
            passwordTextInputEditText.setError("Field is Required");

        } else {

            if (!E_EMAIL.equalsIgnoreCase(EDIT_EMAIL)){

                Toast.makeText(LoginActivity.this,"Email id not valid", Toast.LENGTH_SHORT).show();

            } else if (!E_PASSWORD.equalsIgnoreCase(EDIT_PASSWORD)){

                Toast.makeText(LoginActivity.this,"Password is incorrect", Toast.LENGTH_SHORT).show();

            } else {

                Intent i_data = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i_data);
                LoginActivity.this.finish();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Global.CURRENT_USER_LOGIN, true);
                editor.putString(Global.L_EMAIL , E_EMAIL);
                editor.putString(Global.L_PASSWORD, E_PASSWORD);
                editor.apply();
            }

        }
    }
}
