package com.example.mohitgupta.newtodoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class SingleTask extends AppCompatActivity {

    String task_key=null;
    TextView singleTask;
    TextView singleTime;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
        task_key=getIntent().getExtras().getString("TaskId");
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Tasks");
        singleTask=(TextView)findViewById(R.id.singleTask);
        singleTime=(TextView)findViewById(R.id.singleTime);

        mDatabase.child(task_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String task_title=(String) dataSnapshot.child("Name").getValue();
                String task_time=(String) dataSnapshot.child("Time").getValue();
                singleTask.setText(task_title);
                singleTime.setText(task_time);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
