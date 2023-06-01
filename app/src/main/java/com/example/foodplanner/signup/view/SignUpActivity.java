package com.example.foodplanner.signup.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.R;
import com.example.foodplanner.mainscreen.MainActivity;
import com.example.foodplanner.signin.view.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    Button signUpButton;
    EditText userNameET;
    EditText emailET;
    TextView passwordET;
    TextView rePassword;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DatabaseReference userRef;//= FirebaseDatabase.getInstance().getReference().child("User");
    String regexPassword = "^[A-Za-z]\\w.{5,15}$";
    String regexEmail = "^[A-Za-z]\\w.{5,15}$";
    public static final String PREF_NAME="APPINFO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        userNameET=findViewById(R.id.usernameET);
        emailET=findViewById(R.id.emailET);
        passwordET=findViewById(R.id.passwordET);
        rePassword=findViewById(R.id.repasswordET);
        firebaseAuth = FirebaseAuth.getInstance();

        signUpButton=findViewById(R.id.signupBtn);
        userRef=FirebaseDatabase.getInstance().getReference();


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //boolean validEmail=isValidEmail(emailET.getText().toString());
                //boolean validPassword=isValidEmail(emailET.getText().toString());

                String username = userNameET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String confirm = rePassword.getText().toString();

                if (email.isEmpty()||username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

                } else if (!isValidEmail(username) || (!checkPassword(password , confirm))) {
                    Toast.makeText(SignUpActivity.this, "Strong user name&password is required ", Toast.LENGTH_SHORT).show();

                } else {
                    //createUserByEmail(email, password);
                    userRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                Toast.makeText(SignUpActivity.this, "User already exist", Toast.LENGTH_SHORT).show();
                            } else {
                                userRef.child("User").child(username).child("UserPassword").setValue(password);

                                SharedPreferences pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("EMAIL", username);
                                editor.putString("LOGIN" , "YES");
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("username" , userNameET.getText());
                                startActivity(intent);
                                Toast.makeText(SignUpActivity.this, "signup Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                //finish();
                //Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
                //startActivity(intent);
            }
        });


    }

  /*  private void createUserByEmail(String email, String password) {
        firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    SignInMethodQueryResult result = task.getResult();
                    if (result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                        // The email already exists in the Firebase Authentication database
                        Toast.makeText(SignUpActivity.this, "You are already registered", Toast.LENGTH_SHORT).show();
                    } else {
                        // The email does not exist in the Firebase Authentication database
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // User created successfully, add user data to Firestore
                                            UserData userData = new UserData(username, email);
                                            firebaseFirestore.collection("User")
                                                    .document(firebaseAuth.getCurrentUser().getUid())
                                                    .set(userData)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(SignUp.this, "Registered successfully and added to the Firestore", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(SignUp.this, HomeActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                String errorMessage = Objects.requireNonNull(task.getException()).getLocalizedMessage();
                                                                System.out.println(errorMessage);
                                                                Toast.makeText(SignUp.this, errorMessage, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            String errorMsg = task.getException().getLocalizedMessage();
                                            Toast.makeText(SignUp.this, "Error:" + errorMsg, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    String errorMsg = task.getException().getLocalizedMessage();
                    Toast.makeText(SignUp.this, "Error:" + errorMsg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/

    public boolean isValidEmail(String name) {
        if (name.matches(regexEmail)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPassword(String password, String confirmpassword) {
        if (password.equals(confirmpassword) && password.matches(regexPassword)) {
            return true;
        } else {
            return false;
        }
    }
}