package com.example.mohitgupta.newtodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class AddTask extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    EditText editTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        database=FirebaseDatabase.getInstance();

    }

    public void addButtonClicked(View view)
    {
        editTask=(EditText)findViewById(R.id.editTask);

        String name=editTask.getText().toString();

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf=new SimpleDateFormat("MMM MM dd,yyy h:mm a");
        String dateString = sdf.format(date);

        myRef=database.getInstance().getReference().child("Tasks");
        DatabaseReference newTask=myRef.push();
        newTask.child("Name").setValue(name);
        newTask.child("Time").setValue(dateString).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(AddTask.this,MainActivity.class));
                finish();
            }
        });
    }
}
