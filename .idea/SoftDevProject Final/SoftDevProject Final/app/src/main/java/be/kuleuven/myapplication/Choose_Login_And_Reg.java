package be.kuleuven.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Choose_Login_And_Reg extends AppCompatActivity {

    private Button mLogin, mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_and_reg);

        mLogin = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);

        mLogin.setOnClickListener(v -> {

            Intent i = new Intent(Choose_Login_And_Reg.this, LoginActivity.class);
            startActivity(i);
        });

        mRegister.setOnClickListener(v -> {

            Intent i = new Intent(Choose_Login_And_Reg.this, RegisterActivity.class);
            startActivity(i);

        });
    }
}