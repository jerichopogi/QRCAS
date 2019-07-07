package com.devs.jeric.qrcas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogAddSection extends AppCompatDialogFragment {
    private EditText editTextSection;
    DatabaseReference reference;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_section, null);

        builder.setView(view).setTitle("Add Section").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddSection();
            }
        });
        editTextSection = view.findViewById(R.id.sectionAdd);
        return builder.create();
    }

    public void AddSection(){
        final Sections sections = new Sections();
        String section = editTextSection.getText().toString();

        if(section.isEmpty()){
            Toast.makeText(getContext(), "Please fill up!", Toast.LENGTH_SHORT).show();
        }else {
            sections.setSection(section);
            reference = FirebaseDatabase.getInstance().getReference().child("Sections");

            reference.push().setValue(section);
            Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_LONG).show();
        }
    }
}
