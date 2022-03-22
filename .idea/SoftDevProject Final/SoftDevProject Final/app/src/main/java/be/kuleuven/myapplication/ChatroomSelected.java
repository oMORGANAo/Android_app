package be.kuleuven.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


public class ChatroomSelected extends AppCompatActivity {

    EditText message;
    TextView sender;

    private String userName, roomName;

    DatabaseReference reference;
    String tempKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_selected);
        message = (EditText)findViewById(R.id.msg);
        sender= (TextView)findViewById(R.id.senderName);

        userName = getIntent().getExtras().get("user_name").toString();
        roomName = getIntent().getExtras().get("room_name").toString();
        reference = FirebaseDatabase.getInstance().getReference().child(roomName);
        setTitle(" Room - "+ roomName);


        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NotNull DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);

            }

            @Override
            public void onChildChanged(@NotNull DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NotNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NotNull DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

    }


    public void send(View v)
    {
        Map<String,Object> map = new HashMap<>();
        tempKey = reference.push().getKey();
        reference.updateChildren(map);

        DatabaseReference child_ref = reference.child(tempKey);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("name", userName);
        map2.put("msg", message.getText().toString());
        child_ref.updateChildren(map2).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show());
        message.setText("");


    }
    public void append_chat(DataSnapshot ss)
    {
        String chat_msg,chat_username;
        Iterator i = ss.getChildren().iterator();
        while(i.hasNext())
        {
            chat_msg = ((DataSnapshot)i.next()).getValue().toString();
            chat_username = Objects.requireNonNull(((DataSnapshot) i.next()).getValue()).toString();
            sender.append(chat_username + ": " +chat_msg + " \n");
        }
    }



}

