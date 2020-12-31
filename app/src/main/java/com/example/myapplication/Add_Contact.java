package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Add_Contact extends AppCompatActivity {

    private Button back, done;
    private TextInputLayout name, phone;
    private DatabaseReference reference;
    private String TAG = Add_Contact.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__contact);
        back = findViewById(R.id.back);
        done = findViewById(R.id.done);
        name = findViewById(R.id.text_input_add_name);
        phone = findViewById(R.id.text_input_add_number);

        reference = FirebaseDatabase.getInstance().getReference();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp_name = name.getEditText().getText().toString().trim();
                String temp_numb = phone.getEditText().getText().toString().trim();
                if(!validateName() | !validatePhoneNumber() ){
                    return ;
                }else {
                    final Map<String,Object> contactCreate = new HashMap<>();
                    contactCreate.put("name",temp_name);
                    contactCreate.put("number",temp_numb);
                    String temp_key = reference.push().getKey();
                    reference.child("contacts").child(temp_key).updateChildren(contactCreate).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "onComplete: contact is uploaded in database.");
                                finish();
                            }
                        }
                    });
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private boolean validateName(){
        String temp = name.getEditText().getText().toString().trim();

        if(temp.isEmpty())
        {
            name.setError("Please enter Name");
            return false;
        }else{
            name.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber(){
        String temp = phone.getEditText().getText().toString().trim();

        if(temp.isEmpty())
        {
            phone.setError("Please enter phone number");
            return false;
        }else{
            phone.setError(null);
            return true;
        }
    }

}