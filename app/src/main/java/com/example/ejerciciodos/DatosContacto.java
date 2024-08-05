package com.example.ejerciciodos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DatosContacto extends AppCompatActivity {

    private TextView dtNombre;
    private TextView dtFecha;
    private TextView dtTelefono;
    private TextView dtEmail;
    private TextView dtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_contacto);

        Bundle parametros =getIntent().getExtras();
        String nombre = parametros.getString(getResources().getString(R.string.pNombre));
        String fecha  = parametros.getString(getResources().getString(R.string.pFecha));
        String telefono = parametros.getString(getResources().getString(R.string.pTelefono));
        String email = parametros.getString(getResources().getString(R.string.pEmail));
        String descripcion = parametros.getString(getResources().getString(R.string.pDescripcion));

        dtNombre = (TextView) findViewById(R.id.paramNombre);
        dtNombre.setText(nombre);
        dtFecha = (TextView) findViewById(R.id.paramFecha);
        dtFecha.setText(fecha);
        dtTelefono = (TextView) findViewById(R.id.paramTelefono);
        dtTelefono.setText(telefono);
        dtEmail = (TextView) findViewById(R.id.paramEmail);
        dtEmail.setText(email);
        dtDescripcion = (TextView) findViewById(R.id.paramDescripcion);
        dtDescripcion.setText(descripcion);

        SharedPreferences prefs = getSharedPreferences("guardarFecha", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("fechaSeleccionada", fecha);
        editor.apply();

        Button editarDatos = (Button) findViewById(R.id.btnEditar) ;
        editarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editar = new Intent();
                editar.putExtra(getResources().getString(R.string.pNombre), dtNombre.getText().toString());
                editar.putExtra(getResources().getString(R.string.pFecha), dtFecha.getText().toString());
                editar.putExtra(getResources().getString(R.string.pTelefono), dtTelefono.getText().toString());
                editar.putExtra(getResources().getString(R.string.pEmail), dtEmail.getText().toString());
                editar.putExtra(getResources().getString(R.string.pDescripcion), dtDescripcion.getText().toString());

                setResult(RESULT_OK, editar);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}