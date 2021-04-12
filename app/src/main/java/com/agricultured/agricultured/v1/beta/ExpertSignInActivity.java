package com.agricultured.agricultured.v1.beta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class ExpertSignInActivity extends AppCompatActivity {
    private static int APP_REQUEST_CODE = 1234;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private DatabaseReference serverRef;
    private List<AuthUI.IdpConfig> providers;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 1;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        if (listener != null)
            firebaseAuth.removeAuthStateListener(listener);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void init() {
        providers = Arrays.asList(new AuthUI.IdpConfig.PhoneBuilder().build());

        serverRef = FirebaseDatabase.getInstance().getReference(Common.EXPERT_REF);
        firebaseAuth = FirebaseAuth.getInstance();
        listener = firebaseAuthLocal -> {
            FirebaseUser user = firebaseAuthLocal.getCurrentUser();
            if (user != null)
            {
                checkServerUserFromFirebase(user);
            }
            else {
                signIn();
            }
        };
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void checkServerUserFromFirebase(FirebaseUser user) {
        serverRef.child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        {
                            ExpertUserModel userModel = snapshot.getValue(ExpertUserModel.class);
                            if (userModel.isActive())
                            {
                                goToHomeActivity(userModel);
                            }
                            else
                            {
                                Toast.makeText(com.agricultured.agricultured.v1.beta.ExpertSignInActivity.this, "You must be authenticated by the admin to access the app", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            showRegisterDialog(user);
                            //user does not exist in database
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(com.agricultured.agricultured.v1.beta.ExpertSignInActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showRegisterDialog(FirebaseUser user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Register");
        builder.setMessage("Please fill in the information below.\nThe admin will then verify your account for authorization.");

        View itemView = LayoutInflater.from(this).inflate(R.layout.layout_register, null);
        EditText edt_name = (EditText)itemView.findViewById(R.id.edt_name);
        EditText edt_phone = (EditText)itemView.findViewById(R.id.edt_phone);
        EditText edt_organization = (EditText)itemView.findViewById(R.id.edt_organization);

        //Setting data
        edt_phone.setText(user.getPhoneNumber());
        builder.setNegativeButton("CANCEL", (dialog, which) -> {
            dialog.dismiss();
        })
                .setPositiveButton("REGISTER", (dialog, which) -> {
                    if (TextUtils.isEmpty(edt_name.getText().toString()))
                    {
                        Toast.makeText(com.agricultured.agricultured.v1.beta.ExpertSignInActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(edt_organization.getText().toString()))
                    {
                        Toast.makeText(com.agricultured.agricultured.v1.beta.ExpertSignInActivity.this, "Please enter your organization", Toast.LENGTH_SHORT).show();
                    }

                    ExpertUserModel expertUserModel = new ExpertUserModel();
                    expertUserModel.setUid(user.getUid());
                    expertUserModel.setName(edt_name.getText().toString());
                    expertUserModel.setOrganization(edt_organization.getText().toString());
                    expertUserModel.setActive(false); // Default false, as we must make the user active manually through Firebase.

                    serverRef.child(expertUserModel.getUid())
                            .setValue(expertUserModel)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(com.agricultured.agricultured.v1.beta.ExpertSignInActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            Toast.makeText(com.agricultured.agricultured.v1.beta.ExpertSignInActivity.this, "Congratulations! Registration successful. Admin will validate and activate your account soon.", Toast.LENGTH_SHORT).show();
                            //goToHomeActivity(serverUserModel);
                        }
                    });
                });

        builder.setView(itemView);

        AlertDialog registerDialog = builder.create();
        registerDialog.show();
    }

    private void goToHomeActivity(ExpertUserModel expertUserModel) {
        Common.currentExpertUser = expertUserModel;
        startActivity(new Intent(this, HomeActivityExpert.class));
        finish();
    }

    private void phoneLogin() {
        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(ExpertSignInActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }

        catch (ApiException e){
            Toast.makeText(ExpertSignInActivity.this, "Login unsuccessful: "+e, Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(ExpertSignInActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    showRegisterDialog(user);
                }

                else {
                    Toast.makeText(ExpertSignInActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    showRegisterDialog(null);
                }
            }
        });
    }
}