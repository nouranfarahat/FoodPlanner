package com.example.foodplanner.profile;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodplanner.R;
import com.example.foodplanner.mainscreen.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UserFragment extends Fragment {
    TextView nameTextView;
    TextView emailTextView;
    Button logoutButton;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    public static final String PREF_NAME="APPINFO";


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameTextView=view.findViewById(R.id.userNameTV);
        emailTextView=view.findViewById(R.id.emailTV);
        logoutButton=view.findViewById(R.id.logOutBtn);
        SharedPreferences pref=getContext().getSharedPreferences(PREF_NAME,0);

            String user=pref.getString("USERNAME","unknown");
            String email=pref.getString("EMAIL","email");
            System.out.println("I am "+user+" email "+email);


        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(getContext(),googleSignInOptions);
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(getContext());
        if(account!=null)
        {
            nameTextView.setText(account.getDisplayName());
            emailTextView.setText(account.getEmail());
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }
    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                UserFragment myFragment = new UserFragment();
                //FragmentManager fragmentManager = getFragmentManager();

                if (myFragment != null) {
                    assert getFragmentManager() != null;
                    getFragmentManager().beginTransaction().remove(myFragment).commit();
                }
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
    }
}