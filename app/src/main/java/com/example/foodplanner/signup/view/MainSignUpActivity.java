package com.example.foodplanner.signup.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodplanner.ProfileActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.home.view.HomeFragment;
import com.example.foodplanner.mainscreen.MainActivity;
import com.example.foodplanner.model.User;
import com.example.foodplanner.signin.view.SignInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class MainSignUpActivity extends AppCompatActivity {

    Button googleButton;
    Button signUpWithEmailButton;
    Button skipButton;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    public static final String PREF_NAME="APPINFO";
    SharedPreferences pref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_up);
        getSupportActionBar().hide();
        pref=getSharedPreferences(PREF_NAME,0);

        googleButton=findViewById(R.id.googleBtn);
        skipButton=findViewById(R.id.skipBtn);
        signUpWithEmailButton=findViewById(R.id.signUpWithEmailBtn);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("We are creating your account");
        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });
        signUpWithEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpActivity();
            }
        });
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("GUEST","YES");
                editor.commit();
                homeActivity();
            }
        });
    }
    int RC_SIGN_IN=40;
    private void signIn() {
        // Initialize sign in intent
        Intent intent = googleSignInClient.getSignInIntent();
        // Start activity for result
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            //  handleSignInResult(task);
            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                GoogleSignInAccount currentAccount = GoogleSignIn.getLastSignedInAccount(this);
                if(currentAccount!=null)
                    Toast.makeText(MainSignUpActivity.this,"User Exist",Toast.LENGTH_LONG).show();

                else
                    firebaseAuth(account.getIdToken());


                homeActivity();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser user=auth.getCurrentUser();
                            SharedPreferences.Editor editor=pref.edit();
                            editor.putString("USERNAME",user.getDisplayName());
                            editor.putString("EMAIL",user.getEmail());
                            editor.putString("ID",user.getUid());
                            editor.putString("LOGIN","YES");
                            editor.putString("GUEST","NO");
                            editor.commit();
                            User currentUser=new User(user.getDisplayName(),user.getEmail());

                            database.getReference("User").child(user.getUid()).setValue(currentUser);
                            Intent intent=new Intent(MainSignUpActivity.this, MainActivity.class);
                            //intent.putExtra("UserInfo",currentUser);


                            startActivity(intent);
                        }
                        else
                            Toast.makeText(MainSignUpActivity.this,"errrrror",Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void homeActivity() {
        finish();
        Intent intent=new Intent(MainSignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void signUpActivity() {
        finish();
        Intent intent=new Intent(MainSignUpActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}