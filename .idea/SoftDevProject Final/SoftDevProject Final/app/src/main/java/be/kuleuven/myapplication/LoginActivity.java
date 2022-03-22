package be.kuleuven.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private RequestQueue requestQueue;
    private static final String LOGIN_URL = "https://studev.groept.be/api/a20sd701/logIn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void clkLogButton(View caller) {
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        requestQueue = Volley.newRequestQueue(this);
        verifyCredentialsReq();

    }

    public void verifyCredentialsReq() {
        String checkLogIN = LOGIN_URL + "/" + email.getText().toString() + "/" + password.getText().toString();
        final JsonArrayRequest verifyCredentialsReq = new JsonArrayRequest(Request.Method.GET, checkLogIN, null, this::verifyCredentials, error -> Toast.makeText(LoginActivity.this, "Unable to communicate with server.", Toast.LENGTH_LONG).show());
        requestQueue.add(verifyCredentialsReq);
    }

    private void verifyCredentials(JSONArray response) {
        if (response.length() == 1) {

            Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
            logIn();
        } else {
            Toast.makeText(LoginActivity.this, "Email or password is incorrect.", Toast.LENGTH_LONG).show();
        }
    }


    private void logIn() {

        Intent intent = new Intent(this, ChooseOptions.class);
        startActivity(intent);
    }
}
