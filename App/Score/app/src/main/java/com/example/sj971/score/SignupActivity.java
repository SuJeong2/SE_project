package com.example.sj971.score;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText id;
    EditText pw;
    EditText name;
    Button registerBtn;

    RadioGroup user_type;
    RadioButton student;
    RadioButton professor;
    RadioButton manager;

    String user;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        id = (EditText)findViewById(R.id.signupId);
        pw = (EditText)findViewById(R.id.signupPw);
        name = (EditText)findViewById(R.id.signupName);
        registerBtn = (Button)findViewById(R.id.registerBtn);

        user_type = (RadioGroup) findViewById(R.id.user_type);
        student = (RadioButton) findViewById(R.id.student);
        professor = (RadioButton) findViewById(R.id.professor);
        manager = (RadioButton) findViewById(R.id.manager);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Mobile/users");

        user_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.student) {
                    user="student";
                }
                else if(i==R.id.professor){
                    user="professor";
                }
                else {
                    user="manager";
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id_num = id.getText().toString();

                databaseReference.child(user).child(id_num).child("ID").setValue(id.getText().toString());
                databaseReference.child(user).child(id_num).child("PW").setValue(pw.getText().toString());
                databaseReference.child(user).child(id_num).child("NAME").setValue(name.getText().toString());
                databaseReference.child(user).child(id_num).child("type").setValue(user);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "signup success, please login", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}