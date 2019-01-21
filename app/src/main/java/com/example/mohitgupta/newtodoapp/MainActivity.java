package com.example.mohitgupta.newtodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mTaskList;
    private DatabaseReference mDatabase;
    private TextView v;
    private ProgressBar p;
    private ConstraintLayout c1,c2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c1=(ConstraintLayout)findViewById(R.id.ConstraintEmpty);
        c2=(ConstraintLayout)findViewById(R.id.c2);
        p=(ProgressBar)findViewById(R.id.Progress);
        mTaskList=(RecyclerView)findViewById(R.id.task_list);
        mTaskList.setHasFixedSize(true);
        mTaskList.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addButtonClicked(View view) {

        startActivity(new Intent(MainActivity.this,AddTask.class));
        finish();
    }


    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TaskViewHolder(View itemView){
            super(itemView);
            mView=itemView;
        }

        public void setName(String Name){
            TextView task_name=(TextView) mView.findViewById(R.id.taskName);
            task_name.setText(Name);
        }

        public void setTime(String Time){
            TextView task_time= (TextView) mView.findViewById(R.id.taskTime);
            task_time.setText(Time);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getUid() == null) {

            Intent i=new Intent(MainActivity.this,SignIn.class);
            startActivity(i);
            finish();

        } else {
            p.setVisibility(View.VISIBLE);
            mDatabase= FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("Tasks");

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!(dataSnapshot.exists()))
                    {
                        c2.setVisibility(View.GONE);
                        c1.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            v=(TextView)findViewById(R.id.subtitlex);
            SimpleDateFormat sdf=new SimpleDateFormat("EEEE");
            Date d=new Date();
            String dayOfTheWeek=sdf.format(d);
            v.setText(dayOfTheWeek);

            long date=System.currentTimeMillis();
            SimpleDateFormat sdff=new SimpleDateFormat("MMM MM dd,yyy h:mm a");
            String dateString=sdff.format(date);

            FirebaseRecyclerAdapter<Task, TaskViewHolder> FBRA = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(
                    Task.class,
                    R.layout.task_row,
                    TaskViewHolder.class,
                    mDatabase

            ) {
                @Override
                protected void populateViewHolder(TaskViewHolder viewHolder, Task model, int position) {

                    c1.setVisibility(View.GONE);
                    c2.setVisibility(View.VISIBLE);
                    p.setVisibility(View.GONE);
                    final String task_key = getRef(position).getKey().toString();
                    viewHolder.setName(model.getName());
                    viewHolder.setTime(model.getTime());

                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent singleTaskActivity = new Intent(MainActivity.this, SingleTask.class);
                            singleTaskActivity.putExtra("TaskId", task_key);
                            startActivity(singleTaskActivity);
                        }
                    });

                    viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {

                            mDatabase.child(task_key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                    notifyDataSetChanged();
                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                                    finish();
                                }
                            });


                            return true;
                        }
                    });

                }
            };
            mTaskList.setAdapter(FBRA);
        }

    }

}
