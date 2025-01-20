package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.HelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegPage extends AppCompatActivity {


    TextInputEditText textEmailAddress, textPassword, textPersonName;
    Button signUp;

    TextView signIn;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    // База данных
    FirebaseDatabase database;
    DatabaseReference reference;

    // @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);



        // Сканирования es/ps
        textPersonName = findViewById(R.id.name_text);
        textEmailAddress = findViewById(R.id.email);
        textPassword = findViewById(R.id.password);
        // Авторизация
        signIn = findViewById(R.id.sign_in);
        // Регистрация
        signUp = findViewById(R.id.signup);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String email, password;
//                email = String.valueOf(textEmailAddress.getText());
//                password = String.valueOf(textPassword.getText());

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("email");
                String name = textPersonName.getText().toString();
                String email = textEmailAddress.getText().toString();
                String password = textPassword.getText().toString();
                // String name = signupName.getText().toString();

                HelperClass helperClass = new HelperClass(name, email, password);
                reference.child(name).setValue(helperClass);

                Toast.makeText(RegPage.this, "Вы успешно зарегестрировались!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegPage.this, LoginPage.class);
                startActivity(intent);

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegPage.this, "Введите email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegPage.this, "Введите пароль", Toast.LENGTH_SHORT).show();
                    return;
                }
//                firebaseAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if(task.isSuccessful()) {
//                                    Toast.makeText(RegPage.this, "Вы успешно зарегестрировались", Toast.LENGTH_SHORT).show();
//                                    Intent inten = new Intent(RegPage.this, LoginPage.class);
//                                    startActivity(inten);
//                                    finish();
//                                }
//                                else {
//                                    Toast.makeText(RegPage.this, "ВКЛЮЧАЕМ ВЕНТИЛЯТОРЫ 1488", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

            }
        });
    }

}
