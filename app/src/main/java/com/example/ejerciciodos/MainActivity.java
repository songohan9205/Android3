package com.example.ejerciciodos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private TextInputEditText textInputEditFecha;
    private TextInputEditText dataNombre;
    private TextInputEditText dataFecha;
    private TextInputEditText dataTelefono;
    private TextInputEditText dataEmail;
    private TextInputEditText dataDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.textInputFecha);
        textInputEditFecha = findViewById(R.id.textInputEditFecha);
        textInputEditFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        Button button = (Button) findViewById(R.id.boton);
        dataNombre = findViewById(R.id.textInputEditNombre);
        dataFecha = findViewById(R.id.textInputEditFecha);
        dataTelefono = findViewById(R.id.textInputEditTelefono);
        dataEmail = findViewById(R.id.textInputEditEmail);
        dataDescripcion = findViewById(R.id.textInputEditDescripcion);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = dataNombre.getText().toString();
                String fecha = dataFecha.getText().toString();
                String telefono = dataTelefono.getText().toString();
                String email = dataEmail.getText().toString();
                String descripcion = dataDescripcion.getText().toString();

                Intent intent = new Intent(MainActivity.this, DatosContacto.class);
                intent.putExtra(getResources().getString(R.string.pNombre), nombre);
                intent.putExtra(getResources().getString(R.string.pFecha), fecha);
                intent.putExtra(getResources().getString(R.string.pTelefono), telefono);
                intent.putExtra(getResources().getString(R.string.pEmail), email);
                intent.putExtra(getResources().getString(R.string.pDescripcion), descripcion);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String nombre = data.getStringExtra(getResources().getString(R.string.pNombre));
            String fecha = data.getStringExtra(getResources().getString(R.string.pFecha));
            String telefono = data.getStringExtra(getResources().getString(R.string.pTelefono));
            String email = data.getStringExtra(getResources().getString(R.string.pEmail));
            String descripcion = data.getStringExtra(getResources().getString(R.string.pDescripcion));

            dataNombre.setText(nombre);
            dataFecha.setText(fecha);
            dataTelefono.setText(telefono);
            dataEmail.setText(email);
            dataDescripcion.setText(descripcion);

            if (fecha != null && !fecha.isEmpty()) {
                try {
                    long epoch = Long.parseLong(fecha);
                    // Set the restored date on the textInputEditFecha
                    textInputEditFecha.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(epoch)));
                } catch (NumberFormatException e) {
                    Log.e("MainActivity", "Fecha inválida", e);
                }
            }
        }
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona la fecha")
                .build();
        datePicker.show(getSupportFragmentManager(), "datePicker");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                textInputEditFecha.setText(datePicker.getHeaderText());
            }
        });
    }
}