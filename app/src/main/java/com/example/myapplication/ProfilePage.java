package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.myapplication.ui.EditProfileActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {

    TextView user_name;
    // FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    // FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        user_name = findViewById(R.id.email_address);

        showAllUserData();

        // user_name.setText(String.valueOf(currentUser.getEmail()));
        // user_name.setText(String.valueOf("TEXT"));
    }

    public void showAllUserData(){
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        // String emailUser = intent.getStringExtra("email");
        // String usernameUser = intent.getStringExtra("username");
        // String passwordUser = intent.getStringExtra("password");
        // titleName.setText(nameUser);
        // titleUsername.setText(usernameUser);
        // profileName.setText(nameUser);
        user_name.setText(nameUser);
        // profileUsername.setText(usernameUser);
        // profilePassword.setText(passwordUser);
    }

    public String get_Name() {
        Intent intent = getIntent();
        return intent.getStringExtra("name");
    }

    public void passUserData(){
        String userUsername = user_name.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("email");
        Query checkUserDatabase = reference.orderByChild("name").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                    // String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                    // String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
                    // String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    Intent intent = new Intent(ProfilePage.this, EditProfileActivity.class);
                    intent.putExtra("name", nameFromDB);
                    // intent.putExtra("email", emailFromDB);
                    // intent.putExtra("username", usernameFromDB);
                    // intent.putExtra("password", passwordFromDB);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
