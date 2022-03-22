package be.kuleuven.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateStudySession extends AppCompatActivity {
    private StudySession studySession;
    private RequestQueue requestQueue;
    private final String CREATE_STUDY_SESSION="https://studev.groept.be/api/a20sd701/createSession/";
    private final String GET_SESSION_ID="https://studev.groept.be/api/a20sd701/getIDsession";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_session);
    }
    public void clkButtonCreate(View caller){
        clkCreateSession();
        requestQueue = Volley.newRequestQueue(this);
        addSessionToDB();

    }

    public void clkCreateSession()
    {
        EditText title=findViewById(R.id.create_SesisonTitle);
        EditText link=findViewById(R.id.create_sessionLink);
        EditText date=findViewById(R.id.create_sessionDate);
        EditText time=findViewById(R.id.create_SessionTime);
        EditText description=findViewById(R.id.create_sessionDescirption);

        studySession= new StudySession( 0,title.getText().toString(),link.getText().toString(),date.getText().toString(),time.getText().toString(),description.getText().toString()){

        };

    }

    public void addSessionToDB() {
        String createSession = CREATE_STUDY_SESSION +studySession.getSessionTitle()+"/"+studySession.getSessionLink()+"/"+studySession.getSessionDate()+"/"+studySession.getSessionTime()+"/"+studySession.getSessionDescription()+"/";

        StringRequest addSession = new StringRequest(Request.Method.GET, createSession, response -> {
            Toast.makeText(CreateStudySession.this, "Session created!.", Toast.LENGTH_SHORT).show();
            getSessionID();
        }, error -> Toast.makeText(CreateStudySession.this, "Unable to create a session.", Toast.LENGTH_LONG).show());
        requestQueue.add(addSession);
    }

        public void getSessionID(){

        String getSessionID_URL = GET_SESSION_ID +"/"+ studySession.getSessionTitle();
        JsonArrayRequest getSessionIDrequest = new JsonArrayRequest(Request.Method.GET, getSessionID_URL, null, response -> {
            try {
                JSONObject o = response.getJSONObject(0);
                int sessionID = o.getInt("sessionID");
                studySession.setSessionID(sessionID);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(CreateStudySession.this, "Unable to communicate with server.", Toast.LENGTH_LONG).show());
        requestQueue.add(getSessionIDrequest);
}

}