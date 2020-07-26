package com.example.learn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Word extends AppCompatActivity {
    Button signout;
    FirebaseAuth mAuth;
    ListView lv;
    WordAdapter mAdapter;
    DatabaseReference mref;
    Button fab;
    TextView username;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        fab = findViewById(R.id.fab);
        mAuth = FirebaseAuth.getInstance();
        mref = FirebaseDatabase.getInstance().getReference(mAuth.getUid());
        lv=findViewById(R.id.lv);
        username = findViewById(R.id.username);
        signout = findViewById(R.id.signout);

        progress = new ProgressDialog(Word.this);
        progress.setTitle("Loading Saved Data");
        progress.setMessage("Please Wait...");
        progress.setCancelable(false);
        progress.show();

        username.setText("WELCOME "+mAuth.getCurrentUser().getDisplayName().toUpperCase());

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Word.this,MainActivity.class));
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        mAdapter = new WordAdapter(this);

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAdapter.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    mAdapter.addSectionHeaderItem(new WordMeaning(ds.getKey()));
                    for(DataSnapshot d: ds.getChildren()){
                        mAdapter.addItem(new WordMeaning(d.getKey(),d.getValue().toString()));
                    }
                }
                progress.dismiss();
                lv.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openDialog() {
        CustomDialog d = new CustomDialog();
        d.show(getSupportFragmentManager(), "Enter Class");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}