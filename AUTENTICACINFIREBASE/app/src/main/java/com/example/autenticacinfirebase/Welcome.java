package com.example.autenticacinfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Welcome extends AppCompatActivity implements View.OnClickListener {
    public static final String Usuario = "names";
    TextView txtUser;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        txtUser = (TextView) findViewById(R.id.ShowU);
        String usuario = getIntent().getStringExtra("names");
        txtUser.setText("Bienvenido " + usuario + "!");
        btnSalir=(Button) findViewById(R.id.Salirbtn);
        btnSalir.setOnClickListener(this);
        //FirebaseAuth.getInstance().signOut();
    }

    public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(Welcome.this, "Se ha cerrado la sesión", Toast.LENGTH_SHORT).show();
        Intent intencion = new Intent(getApplication(), MainActivity.class);
        startActivity(intencion);
    }
}
