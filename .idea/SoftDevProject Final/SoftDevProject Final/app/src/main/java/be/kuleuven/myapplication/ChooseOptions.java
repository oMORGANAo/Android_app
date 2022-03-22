package be.kuleuven.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseOptions extends AppCompatActivity {

    private Button mChat;
    private Button mCreateSession;
    private Button mViewSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_options);
        mChat = (Button) findViewById(R.id.goToChat);
        mCreateSession = (Button) findViewById(R.id.goToCreate);
        mViewSessions=(Button)findViewById(R.id.goToViewSession) ;

        mChat.setOnClickListener(v -> {

            Intent i = new Intent(ChooseOptions.this, ChatroomsList.class);
            startActivity(i);
        });

        mCreateSession.setOnClickListener(v -> {

            Intent i = new Intent(ChooseOptions.this, CreateStudySession.class);
            startActivity(i);

        });

        mViewSessions.setOnClickListener(v -> {

            Intent i = new Intent(ChooseOptions.this, SessionListActivity.class);
            startActivity(i);

        });
    }
}