package com.example.bhairavpardiwala.helloworldiot;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,DisplayOnOrOff {

    ListView ls;

    ListAdaptor adp;
    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
            "Android", "iPhone", "WindowsMobile" };
    ArrayList<Note> list ;
    EditText tv;
    ListAdaptor adapter;
    NoteManager manager;
    Button add,rem;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef ;
    MyBroadcastReceiver reciever=new MyBroadcastReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         add=(Button)findViewById(R.id.add);
       //  rem=(Button)findViewById(R.id.remove);
list= new ArrayList<Note>();
        add.setOnClickListener(this);
       // rem.setOnClickListener(this);

        ls=(ListView) findViewById(R.id.list);
tv=(EditText) findViewById(R.id.txtenter);

manager=new NoteManager();
        manager.context=this;
        manager.notes=list;



//       adapter = new ArrayAdapter<Note>(this,
//                android.R.layout.simple_list_item_1, list);

        adapter = new ListAdaptor(this,list);
ls.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        ls.setAdapter(adapter);
        manager.noteAdaptor=adapter;




        IntentFilter filter = new IntentFilter("com.example.bhairavpardiwala.helloworldiot.MyBroadcastReceiver.MY_NOTIFICATION");
       // intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
       this.registerReceiver(reciever, filter);

        myRef =  database. getReference("Notes");
        //myRef.setValue(manager.notes);

        manager.onoroff=this;
myRef.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        try
        {
            GenericTypeIndicator<List<Note>> t = new GenericTypeIndicator<List<Note>>() {};

            if (dataSnapshot.getValue() != null)
            {
                List<Note> Notes = dataSnapshot.getValue(t);
             manager.notes.clear();
             manager.notes.addAll(Notes);
                manager.Display();
                adapter.notifyDataSetChanged();
            }
            else
            {
                manager.notes.clear();
                adapter.notifyDataSetChanged();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try
                {
                    GenericTypeIndicator<List<Note>> t = new GenericTypeIndicator<List<Note>>() {};

                    if (dataSnapshot.getValue() != null)
                    {
                        List<Note> Notes = dataSnapshot.getValue(t);
                        manager.notes.clear();
                        manager.notes.addAll(Notes);
                        manager.Display();
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        manager.notes.clear();
                        adapter.notifyDataSetChanged();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

//registerReceiver(reciever,)

    }

    @Override
    public void onClick(View view) {
        if(view==add)
        {
            manager.AddNote(tv.getText().toString());
tv.setText("");
           // adapter.notifyDataSetChanged();
        }


    }

    @Override
    public void Sync(boolean tr) {
        List<Note> llist=manager.notes;
        myRef.setValue(llist);

    }

    @Override
    protected  void onDestroy()
    {
     super.onDestroy();
        this.unregisterReceiver(reciever);
    }
}
