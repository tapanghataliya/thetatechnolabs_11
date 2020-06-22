package com.apphelp.help.thetatechnolabs.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.apphelp.help.thetatechnolabs.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    private TextInputEditText emailTextInputEditText;
    private TextInputEditText passwordTextInputEditText;
    LinearLayout lyl_sign_in;

    String E_EMAIL, E_PASSWORD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Initilization();
    }

    private void Initilization() {

        emailTextInputEditText = (TextInputEditText) findViewById(R.id.emailTextInputEditText);
        passwordTextInputEditText = (TextInputEditText) findViewById(R.id.passwordTextInputEditText);

        lyl_sign_in = (LinearLayout) findViewById(R.id.lyl_sign_in);

        lyl_sign_in.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lyl_sign_in:

                Intent i_data = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(i_data);
                break;
        }
    }
}
