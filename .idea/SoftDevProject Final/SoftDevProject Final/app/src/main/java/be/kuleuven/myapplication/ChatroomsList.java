package be.kuleuven.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatroomsList extends AppCompatActivity {

    DatabaseReference reference;
    ArrayList<String> arrayList;

    EditText room;
    ListView rooms_list;
    ArrayAdapter<String> adapter;
    String name;
    EditText newName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatrooms_list);
        room = (EditText)findViewById(R.id.addRoom);
        rooms_list = (ListView)findViewById(R.id.studySessionList);
        arrayList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        rooms_list.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference().getRoot();
        request_username();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();

                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());

                }

                arrayList.clear();
                arrayList.addAll(set);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                Toast.makeText(ChatroomsList.this, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });


        rooms_list.setOnItemClickListener((adapterView, view, i, l) -> {


            Intent intent = new Intent(ChatroomsList.this, ChatroomSelected.class);
            intent.putExtra("room_name", ((TextView) view).getText().toString());
            intent.putExtra("user_name", name);
            startActivity(intent);

        });
    }

    public void request_username()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter the name you wish to use in the chat!");
        newName = new EditText(this);
        builder.setView(newName);
        builder.setPositiveButton("OK", (dialogInterface, i) -> name = newName.getText().toString());

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.cancel();
            request_username();
        });
        builder.show();

    }


    public void insert_data(View v)
    {

        Map<String,Object> map = new HashMap<>();
        map.put(room.getText().toString(), "");
        reference.updateChildren(map);

    }


}