package com.example.learn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomDialog extends AppCompatDialogFragment {
    EditText word;
    EditText meaning;
    String w,m;
    DatabaseReference mref;
    FirebaseAuth mAuth;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    String date;

    public CustomDialog(){

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder((getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_custom_dialog, null);
        word = view.findViewById(R.id.word1);
        meaning = view.findViewById(R.id.meaning1);

        builder.setCancelable(false);
        builder.setView(view).setTitle("Add New Word For The Day").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                w = word.getText().toString();
                m = meaning.getText().toString();
                if(w.length()!=0&&m.length()!=0){
                    mAuth = FirebaseAuth.getInstance();
                    calendar = Calendar.getInstance();
                    dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    date = dateFormat.format(calendar.getTime());

                    mref = FirebaseDatabase.getInstance().getReference(mAuth.getUid());
                    mref.child(date).child(w).setValue(m);
                }
                else{
                    if(w.length()==0)
                        word.setError("Empty Word");
                    else if(m.length()==0){
                        meaning.setError("Empty Meaning");
                    }
                }
            }
        });

        return builder.create();
    }
}