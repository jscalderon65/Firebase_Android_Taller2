package com.example.autenticacinfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar, btnLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private SignInButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        TextEmail = (EditText) findViewById(R.id.Emailtxt);
        TextPassword = (EditText) findViewById(R.id.Passwordtxt);
        btnRegistrar = (Button) findViewById(R.id.BotonRegistrar);
        btnLogin = (Button) findViewById(R.id.Botonlogin);
        progressDialog = new ProgressDialog(this);
        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);



    }




    private void RegistrarUsuario(){

        String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un correo ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Realizando registro");
        progressDialog.show();


        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            Toast.makeText(MainActivity.this, "Se ha registrado el usuario con el email: " + TextEmail.getText(), Toast.LENGTH_LONG).show();

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(MainActivity.this, "El Usuario ya existe!!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    private void LoginUsuario() {
        final String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un correo ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Realizando consulta");
        progressDialog.show();


        //creating a new user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            int pos = email.indexOf("@");
                            String Usuario = email.substring(0, pos);
                            Toast.makeText(MainActivity.this, "Bienvenido: " + TextEmail.getText(), Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getApplication(), Welcome.class);
                            intencion.putExtra(Welcome.Usuario, Usuario);
                            startActivity(intencion);
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(MainActivity.this, "El Usuario ya existe!!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BotonRegistrar:
                RegistrarUsuario();
                break;
            case R.id.Botonlogin:
                LoginUsuario();
                break;
        }
        //Invocamos al método:

    }
}

