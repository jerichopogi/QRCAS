package com.devs.jeric.qrcas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewStudentsActivity extends AppCompatActivity {

    private String currentGroupName;
    AddFloatingActionButton floatingActionButton;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    ListView studList;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        floatingActionButton = (AddFloatingActionButton) findViewById(R.id.floatingaddStudent);
        studList = (ListView) findViewById(R.id.studentList);

        currentGroupName = getIntent().getExtras().get("SectionName").toString();
        InitializeActionbar();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Sections").child(currentGroupName);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddStudent dialogAddStudent = new DialogAddStudent();
                dialogAddStudent.show(getSupportFragmentManager(), "Student");
            }
        });

        studList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String current = parent.getItemAtPosition(position).toString();

                Intent intent = new Intent(ViewStudentsActivity.this, StudQR.class);
                intent.putExtra("StudentName", current);
                startActivity(intent);
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if(data.getKey().equals("LastName")){
                        String Name = data.getValue().toString();
                        Log.d("Specific Node Value" , Name);

                        arrayList.add(Name);
                        arrayAdapter = new ArrayAdapter<String>(ViewStudentsActivity.this, android.R.layout.simple_list_item_1, arrayList);
                        studList.setAdapter(arrayAdapter);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void InitializeActionbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(currentGroupName);
    }
}
