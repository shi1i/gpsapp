package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {

    TextInputEditText textEmailAddress;
    TextInputEditText textPassword;
    static TextInputEditText textPeronName;
    Button signIn;
    TextView signUp;
    // git

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



    // @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);


        // Сканирования es/ps
        textPeronName = findViewById(R.id.name_text);
        // textEmailAddress = findViewById(R.id.email_reg);
        textPassword = findViewById(R.id.password_reg);
        // Авторизация
        signIn = findViewById(R.id.signin);
        // Регистрация
        signUp = findViewById(R.id.sign_up);


        // Регестрация нажатия перехода в активность регистрации
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, RegPage.class);
                startActivity(intent);
                finish();
            }
        });
//
//        signIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email, password;
//                email = String.valueOf(textEmailAddress.getText());
//                password = String.valueOf(textPassword.getText());
//
//                if (TextUtils.isEmpty(email)) {
//                    Toast.makeText(LoginPage.this, "Введите email", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(password)) {
//                    Toast.makeText(LoginPage.this, "Введите пароль", Toast.LENGTH_SHORT).show();
//                    return;
//                }

//                firebaseAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(LoginPage.this, "Вы успешно авторизовались", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    Toast.makeText(LoginPage.this, "Email и пароль неверны", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        });
//            }
//
//        });
//    }

        // Взаимодействие с данными из базы данных
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {
                } else {
                    checkUser();
                }
            }
        });
    }


    public void checkUser(){
        String userUsername = textPeronName.getText().toString().trim();
        String userPassword = textPassword.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("email");
        Query checkUserDatabase = reference.orderByChild("name").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    textPeronName.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userPassword)) {
                        textPeronName.setError(null);
                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                        // String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
                        Intent intent = new Intent(LoginPage.this, MainActivity.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        // intent.putExtra("username", usernameFromDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);
                    } else {
                        textPassword.setError("Неверный пароль");
                        textPassword.requestFocus();
                    }
                } else {
                    textPeronName.setError("Пользователя не существует");
                    textPeronName.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public Boolean validateUsername() {
        String val = textPeronName.getText().toString();
        if (val.isEmpty()) {
            textPeronName.setError("Поле не может быть пустым");
            return false;
        } else {
            textPeronName.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = textPassword.getText().toString();
        if (val.isEmpty()) {
            textPassword.setError("Поле не может быть пустым");
            return false;
        } else {
            textPassword.setError(null);
            return true;
        }
    }
    public static String getUserName() {
        return String.valueOf(textPeronName.getText());
    }
}
