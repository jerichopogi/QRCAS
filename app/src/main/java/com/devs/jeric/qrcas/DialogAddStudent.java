package com.devs.jeric.qrcas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DialogAddStudent extends AppCompatDialogFragment {
    private EditText lName, sNum;
    DatabaseReference reference, sectNameRef;
    private String currentGroupName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_student, null);

        currentGroupName = getActivity().getIntent().getExtras().get("SectionName").toString();
        Toast.makeText(getContext(), currentGroupName, Toast.LENGTH_LONG).show();

        sectNameRef = FirebaseDatabase.getInstance().getReference().child("Sections").child(currentGroupName);

        builder.setView(view).setTitle("Add Student").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddStudent();
            }
        });
        lName = view.findViewById(R.id.studLname);
        sNum = view.findViewById(R.id.studNum);
        return builder.create();
    }

    public void AddStudent(){
        String key = sectNameRef.push().getKey();
        String Lname = lName.getText().toString();
        String StudNum = sNum.getText().toString();

        if(Lname.isEmpty()||StudNum.isEmpty()){
            Toast.makeText(getContext(), "Please fill up!", Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String, Object> sectKey = new HashMap<>();
            sectNameRef.updateChildren(sectKey);

            reference = sectNameRef.child(key);

            HashMap<String, Object> studentInfo = new HashMap<>();
            studentInfo.put("LastName", Lname);
            studentInfo.put("StudentNumber", StudNum);

            reference.updateChildren(studentInfo);
            Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_LONG).show();
        }
    }
}
