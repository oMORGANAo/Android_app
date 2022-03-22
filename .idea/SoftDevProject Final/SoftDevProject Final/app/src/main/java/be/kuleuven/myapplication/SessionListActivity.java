package be.kuleuven.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionListActivity extends AppCompatActivity {

    private static final String GET_SESSIONS_LIST_URL="https://studev.groept.be/api/a20sd701/sessionTitle/";
    private RequestQueue requestQueue;
    private TextView sessionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);
        requestQueue= Volley.newRequestQueue(this);
        getSessionList();

    }

    public void getSessionList()
    {
        sessionList=(TextView)findViewById(R.id.final_sessionList);

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, GET_SESSIONS_LIST_URL, null,

                response -> {
                    try {
                        String responseString = "";
                        for( int i = 0; i < response.length(); i++ )
                        {
                            JSONObject curObject = response.getJSONObject( i );
                            responseString += curObject.getString( "Session Title" )+"\n" + " : " + curObject.getString( "Link" )+"\n"+"\n";
                        }
                        sessionList.setText(responseString);

                    }
                    catch( JSONException e )
                    {
                        Log.e( "Database", e.getMessage(), e );
                    }
                },

                error -> sessionList.setText( error.getLocalizedMessage() )
        );

        requestQueue.add(submitRequest);
    }
}
