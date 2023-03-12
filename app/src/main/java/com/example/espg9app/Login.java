package com.example.espg9app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.example.espg9app.ui.StudentMain.BusinessDetail;
import com.example.espg9app.ui.StudentMain.StudentMainFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login  extends AppCompatActivity{
    private FirebaseAuth mAuth;


    //Sign In Options
    private Button goToSignUp;
    private Button goToSignIn;
    private Group signOptions;
    private Group signScreen;

    //Sign In Screen
    private EditText signInEmail;
    private EditText signInPassword;
    private Button signInSubmit;
    private Button signInReturn;

    //Sign Up Screen
    private EditText signInConfirmPassword;
    private TextView signUpConfirmText;
    private Button signUpSubmit;
    private Button signUpReturn;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mAuth = FirebaseAuth.getInstance();


        //FIND BUTTONS

        goToSignUp = findViewById(R.id.GoToSignUp);
        goToSignIn = findViewById(R.id.GoToSignIn);
        signOptions = findViewById(R.id.SignOptions);
        signScreen =  findViewById(R.id.SignScreen);

        signInEmail = findViewById(R.id.emailType);
        signInPassword = findViewById(R.id.passwordType);
        signInSubmit = findViewById(R.id.signInButton);
        signInReturn = findViewById(R.id.signInReturn);

        signInConfirmPassword = findViewById(R.id.confirmPasswordType);
        signUpConfirmText = findViewById(R.id.confirmPasswordText);

        signUpSubmit = findViewById(R.id.signUpButton);
        signUpReturn = findViewById(R.id.signUpReturn);

        //

        //Option Screen

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Clear the email and password text fields (don't want it to carry over from last screen)
                signInEmail.setText("");  //Though to be fair we could probably keep the email one intact, consider removing?
                signInPassword.setText("");
                signInConfirmPassword.setText("");


                //Hide the options and show the sign in screen
                signOptions.setVisibility(Group.INVISIBLE);
                signScreen.setVisibility((Group.VISIBLE));

                //Also show the confirm password text and box since we're signing up
                signInConfirmPassword.setVisibility(View.VISIBLE);
                signUpConfirmText.setVisibility((View.VISIBLE));

                //and show the sign up submit/return while hiding the sign in submit/return
                signUpSubmit.setVisibility(View.VISIBLE);
                signUpReturn.setVisibility(View.VISIBLE);
                signInSubmit.setVisibility(View.INVISIBLE);
                signInReturn.setVisibility(View.INVISIBLE);


            }
        });

        goToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Clear the email and password text fields (don't want it to carry over from last screen)
                signInEmail.setText("");
                signInPassword.setText("");
                signInConfirmPassword.setText("");

                //Hide the options and show the sign in screen
                signOptions.setVisibility(Group.INVISIBLE);
                signScreen.setVisibility((Group.VISIBLE));

                //Also hide the confirm password text and box since we're signing in
                signInConfirmPassword.setVisibility(View.INVISIBLE);
                signUpConfirmText.setVisibility((View.INVISIBLE));

                //and show the sign in submit/return while hiding the sign out submit/return
                signInSubmit.setVisibility(View.VISIBLE);
                signInReturn.setVisibility(View.VISIBLE);
                signUpSubmit.setVisibility(View.INVISIBLE);
                signUpReturn.setVisibility(View.INVISIBLE);
            }
        });

        // Sign In Screen

        signInReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide the options and show the sign in screen
                signOptions.setVisibility(Group.VISIBLE);
                signScreen.setVisibility((Group.INVISIBLE));
            }
        });

        // Sign Up Screen

        signUpReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide the options and show the sign in screen
                signOptions.setVisibility(Group.VISIBLE);
                signScreen.setVisibility((Group.INVISIBLE));
            }
        });

        //Attempting to sign up
        signUpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signInEmail.getText().toString();
                String password = signInPassword.getText().toString();
                String password2 = signInConfirmPassword.getText().toString();

                //Use this to check if the registration has met conditions
                boolean validRegister = CheckEmail(email) &&
                        CheckPasswords(password, password2);

                if(validRegister){
                    //Sign up the account via Firebase
                    signUp(email, password);
                    //Tell the user there is a email verification sent
                    sendVerificationDialog();

                    //Hide the options and show the sign in screen
                    signOptions.setVisibility(Group.VISIBLE);
                    signScreen.setVisibility((Group.INVISIBLE));

                }
            }
        });

        //Attempting to sign in
        signInSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signInEmail.getText().toString();
                String password = signInPassword.getText().toString();

                signIn(email, password);
            }
        });
    }

    //Signing Up


    private boolean CheckEmail(String email){
        if (email.endsWith("@bath.ac.uk")) { //Check if the email ends with the bath thing
            return true;
        } else {
            notUniEmail(); //Use this to show a pop up
            return false;

        }
    }

    private boolean CheckPasswords(String password, String password2){
        if (password.equals(password2)) { //Check if the passwords match
            return true;
        } else {
            passwordDontMatch(); //Use this to show a pop up
            return false;
        }
    }

    //UI


    //Dialog

    private void notUniEmail(){
        OkDialog("Please use a University of Bath email. (ends with bath.ac.uk)");
    }

    private void passwordDontMatch(){
        OkDialog("Passwords provided do not match.");
    }

    private void OkDialog(String message){ //Use this to display a basic message dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked "OK" button
                // Perform the action you want to do when the user confirms
            }
        });
        builder.create().show();

    }

    private void emailExists(){
        OkDialog("This email is already registered with an account.");
    }

    private void sendVerificationDialog(){
        OkDialog("A verification link has been sent to your email.");
    }


    //FIREBASE

    private void signUp(String email, String password) {
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                    if (isNewUser) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()) {
                                        // Sign up success
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        sendEmailVerification(user);
                                        // Do something with the user object
                                    } else {
                                        // Sign up failed
                                        Exception exception = task2.getException();
                                        OkDialog("Failed to sign-up, please try again.");
                                        // Do something with the exception
                                    }
                                });
                    } else {
                        // Email already exists
                        OkDialog("This email already exists. Please choose a different one.");
                        return;
                    }
                });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user.isEmailVerified()) {
                            Intent showDetail = new Intent(getApplicationContext(), StudentMainFragment.class);
                            startActivity(showDetail);
                        } else {
                            // User is signed in but email is not verified
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage("Your account is not verified. Resend verification email?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sendEmailVerification(user);
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // User clicked "No" button
                                    // Perform the action you want to do when the user cancels
                                }
                            });
                            builder.create().show();
                            mAuth.signOut();
                            // Do something to handle this case
                        }
                    } else {
                        // Sign in failed
                        Exception exception = task.getException();
                        OkDialog("Failed to sign in. Please check your email and password.");
                        // Do something with the exception
                    }
                });
    }
    private void sendEmailVerification(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Email sent
                        //for some reason this is displaying even if the email already exists, please fix.
                        OkDialog("Verification email sent. Please check your inbox.");
                    } else {
                        // Email not sent
                        Exception exception = task.getException();
                        OkDialog("Failed to send verification email.");
                        // Do something with the exception
                    }
                });
    }
}
