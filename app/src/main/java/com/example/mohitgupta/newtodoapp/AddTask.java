package com.example.mohitgupta.newtodoapp;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddTask extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    EditText editTask;
    public AlarmManager alarmManager;
    private Calendar calendar,setCalender;
    private int hour,min;
    private TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        database=FirebaseDatabase.getInstance();
        editTask=(EditText)findViewById(R.id.editTask);



        long date = System.currentTimeMillis();

        SimpleDateFormat sdf=new SimpleDateFormat("MMM MM dd,yyy h:mm a");
        String dateString = sdf.format(date);
        myRef=database.getReference().child("Tasks").child(dateString);
        calendar=Calendar.getInstance();
        setCalender=Calendar.getInstance();
       // calendar.add(Calendar.DATE,1);
        // myRef=database.getInstance().getReference().child("Tasks");
    }

    public void addButtonClicked(View view)
    {
        String name=editTask.getText().toString();
        Receiver.getTask(name);
        myRef.child("Name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                Intent intent = new Intent(AddTask.this, Receiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddTask.this, 001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                setCalender.set(calendar.HOUR,hour);
                setCalender.set(calendar.MINUTE,min);
                setCalender.set(calendar.SECOND,0);
                if(setCalender.before(calendar))
                {
                    setCalender.add(Calendar.DATE,1);
                }
                alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,setCalender.getTimeInMillis() , pendingIntent);
                startActivity(new Intent(AddTask.this,MainActivity.class));
                finish();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Timmer(View view) {

        Dialog mDialog=new Dialog(this);
        mDialog.setContentView(R.layout.timmerdialog);
        mDialog.show();
        timePicker=(TimePicker)mDialog.findViewById(R.id.Timee);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                final TextView timeMain=(TextView)findViewById(R.id.Time);
                Date date=new Date();
                hour=timePicker.getHour();
                min=timePicker.getMinute();
                timeMain.setText((timePicker.getHour()+":"+timePicker.getMinute()).toString());
                myRef.child("Time").setValue(timeMain.getText());

            }
        });
    }
}
