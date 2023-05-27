//package com.example.foodplanner.signin.view;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.example.foodplanner.ProfileActivity;
//import com.example.foodplanner.R;
//import com.example.foodplanner.model.User;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//import com.google.firebase.database.FirebaseDatabase;
//
//
//public class SignInFragment extends Fragment {
//
//    Button googleButton;
//    GoogleSignInOptions googleSignInOptions;
//    GoogleSignInClient googleSignInClient;
//    FirebaseDatabase database;
//    FirebaseAuth auth;
//    ProgressDialog progressDialog;
//    public SignInFragment() {
//        // Required empty public constructor
//    }
//
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sign_in, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        googleButton=view.findViewById(R.id.googleBtn);
//        auth=FirebaseAuth.getInstance();
//        database=FirebaseDatabase.getInstance();
//        progressDialog=new ProgressDialog(getContext());
//        progressDialog.setTitle("Creating account");
//        progressDialog.setMessage("We are creating your account");
//        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        googleSignInClient= GoogleSignIn.getClient(getContext(),googleSignInOptions);
//
//        googleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn(v);
//
//            }
//        });
//    }
//    int RC_SIGN_IN=40;
//    private void signIn(View view) {
//        // Initialize sign in intent
//        Intent intent = googleSignInClient.getSignInIntent();
//        // Start activity for result
//        startActivityForResult(intent, RC_SIGN_IN);
//        //Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_homeFragment);
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode==RC_SIGN_IN)
//        {
//            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
//            //  handleSignInResult(task);
//            try {
//                GoogleSignInAccount account=task.getResult(ApiException.class);
//                firebaseAuth(account.getIdToken());
//                //HomeActivity();
//            } catch (ApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void firebaseAuth(String idToken) {
//        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
//        auth.signInWithCredential(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful())
//                        {
//                            FirebaseUser user=auth.getCurrentUser();
//                            User currentUser=new User(user.getDisplayName(),user.getEmail());
//                            database.getReference("User").child(user.getUid()).setValue(currentUser);
//                            Intent intent=new Intent(getContext(), ProfileActivity.class);
//                            //intent.putExtra("UserInfo",currentUser);
//
//
//                            startActivity(intent);
//                        }
//                        else
//                            Toast.makeText(getContext(),"errrrror",Toast.LENGTH_LONG).show();
//
//                    }
//                });
//    }
//
//   /* private void HomeActivity() {
//        finish();
//        Intent intent=new Intent(getContext(), ProfileActivity.class);
//        startActivity(intent);
//    }*/
//}