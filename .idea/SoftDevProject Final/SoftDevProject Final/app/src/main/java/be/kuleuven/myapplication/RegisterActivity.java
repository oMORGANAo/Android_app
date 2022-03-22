package be.kuleuven.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {
    private Users user;
    private static final String GET_USERID_URL="https://studev.groept.be/api/a20sd701/getID";
    private static final String REGISTER_URL= "https://studev.groept.be/api/a20sd701/signUpUser";
    private static final String CHECK_EXISTING_URL="https://studev.groept.be/api/a20sd701/checkExisting/";
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void clkButtonRegister(View caller){
        checkInput();
        requestQueue = Volley.newRequestQueue(this);

    }

    public void checkInput(){
        EditText email = (EditText) findViewById(R.id.email);
        EditText name = (EditText) findViewById(R.id.name);
        EditText password = (EditText) findViewById(R.id.password);
        EditText major = (EditText) findViewById(R.id.school_major);
        CheckBox checkBox=findViewById(R.id.checkbox1);

        if(!checkBox.isChecked())
        {checkBox.setError("Please confirm you are Group T student");}

        if (email.getText().toString().trim().equalsIgnoreCase("")) {
            email.setError("This field can not be blank");
        }
        if (!email.getText().toString().contains("@student.kuleuven.be")) {
            email.setError("You need to use your student email");
        }
        if (name.getText().toString().trim().equalsIgnoreCase("")) {
            name.setError("This field can not be blank");
        }
        if (password.getText().toString().trim().equalsIgnoreCase("")) {
            password.setError("This field can not be blank");
        }
        if (major.getText().toString().trim().equalsIgnoreCase("")) {
            major.setError("This field can not be blank");
        }
        if(email.length()!=0 && name.length()!=0 && password.length()!=0 && major.length()!=0 && email.getText().toString().contains("@student.kuleuven.be")){
            registerUser();
            checkExisting();
        }

    }



    public void registerUser() {
        EditText email = (EditText) findViewById(R.id.email);
        EditText name = (EditText) findViewById(R.id.name);
        EditText password = (EditText) findViewById(R.id.password);
        EditText major = (EditText) findViewById(R.id.school_major);



        user = new Users(name.getText().toString(), email.getText().toString(), password.getText().toString(), major.getText().toString(),0);
    }

    public void checkExisting(){
        String checkExisting = CHECK_EXISTING_URL + user.getEmail();

        final JsonArrayRequest checkExistingUser = new JsonArrayRequest(Request.Method.GET, checkExisting, null, this::checkExistingUsersResponse, error -> Toast.makeText(RegisterActivity.this, "Unable to communicate with server.", Toast.LENGTH_LONG).show());
        requestQueue.add(checkExistingUser);
    }
    public void checkExistingUsersResponse(JSONArray response){
        if (response.length() == 0){
            registerInDB();
        }
        else{
            Toast.makeText(RegisterActivity.this, "You already have an account.", Toast.LENGTH_LONG).show();
        }
    }

    public void registerInDB(){

        String requestURL=REGISTER_URL +"/"+user.getName()+"/"+user.getEmail()+"/"+user.getMajor()+"/"+user.getPassword()+"/";

        StringRequest addUser = new StringRequest(Request.Method.GET, requestURL, response -> {
            Toast.makeText(RegisterActivity.this, "You are all set.", Toast.LENGTH_SHORT).show();
              getID();
        }, error -> Toast.makeText(RegisterActivity.this, "Unable to create an account.", Toast.LENGTH_LONG).show());
        requestQueue.add(addUser);
    }


    public void getID(){
        String getUserID_URL = GET_USERID_URL +"/"+ user.getEmail();
        JsonArrayRequest getUserIDJRequest = new JsonArrayRequest(Request.Method.GET, getUserID_URL, null, response -> {
            try {
                JSONObject o = response.getJSONObject(0);
                int userID = o.getInt("idUsers");
                user.setUserID(userID);
                openLogIn();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(RegisterActivity.this, "Unable to communicate with server.", Toast.LENGTH_LONG).show());
        requestQueue.add(getUserIDJRequest);

    }
    public void openLogIn(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        }
    }









