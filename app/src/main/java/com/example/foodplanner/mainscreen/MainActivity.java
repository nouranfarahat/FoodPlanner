package com.example.foodplanner.mainscreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodplanner.ProfileActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.favorite.view.FavoriteFragment;
import com.example.foodplanner.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Button googleButton;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    public static final String PREF_NAME="APPINFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref=getSharedPreferences(PREF_NAME,0);

        String user=pref.getString("GUEST","unknown");
        floatingActionButton=findViewById(R.id.fab);
        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        getSupportActionBar().hide();
        NavController navController= Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        if(user.equals("YES")){
            floatingActionButton.setEnabled(false);
            bottomNavigationView.getMenu().getItem(2).setEnabled(false);
            bottomNavigationView.getMenu().getItem(3).setEnabled(false);
        }
        if (checkConnectivity() == false) {
            FavoriteFragment fragment = new FavoriteFragment();
            fragment.setArguments(new Bundle());
            navController.navigate(R.id.favoriteFragment);
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.homeFragment);
            MenuItem menuItem2 = menu.findItem(R.id.searchFragment);
            MenuItem menuItem3 = menu.findItem(R.id.userFragment);
            menuItem.setEnabled(false);
            menuItem2.setEnabled(false);
            menuItem3.setEnabled(false);

        }
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
    private boolean checkConnectivity(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        }
        boolean isConnected = networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        return isConnected;
    }


        /*bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.:
// Navigate to destination 1
                        navController.navigate(R.id.nav_host_fragment);
                        return true;
                    case R.id.menu_item_2:
// Navigate to destination 2
                        navController.navigate(R.id.nav_host_fragment);
                        return true;
                    case R.id.menu_item_3:
// Navigate to destination 3
                        navController.navigate(R.id.nav_host_fragment);
                        return true;
                    default:
                        return false;
                }
            }
            });*/
        //NavigationUI.setupWithNavController();
        /*googleButton=findViewById(R.id.googleBtn);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(MainActivity.this);
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
        });*/


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
                firebaseAuth(account.getIdToken());
                HomeActivity();
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
                            User currentUser=new User(user.getDisplayName(),user.getEmail());
                            database.getReference("User").child(user.getUid()).setValue(currentUser);
                            Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
                            //intent.putExtra("UserInfo",currentUser);


                            startActivity(intent);
                        }
                        else
                            Toast.makeText(MainActivity.this,"errrrror",Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void HomeActivity() {
        finish();
        Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
    /*private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Get the ID token from the GoogleSignInAccount object and exchange it for a Firebase credential.
            String idToken = account.getIdToken();
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                // Do something with the user object
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getActivity(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }*/
}