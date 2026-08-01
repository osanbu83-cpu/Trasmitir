package com.example.trasmitir;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SCREEN_CAPTURE = 1000;
    private MediaProjectionManager projectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnScan = findViewById(R.id.btnScan);
        Button btnStartCast = findViewById(R.id.btnStartCast);

        projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        btnScan.setOnClickListener(v -> {
            // Lógica de escaneo de red (mDNS / DLNA / Chromecast)
            Toast.In(MainActivity.this, "Buscando dispositivos en la red...", Toast.LENGTH_SHORT).show();
        });

        btnStartCast.setOnClickListener(v -> {
            // Solicitar permiso de Android para capturar la pantalla
            if (projectionManager != null) {
                Intent intent = projectionManager.createScreenCaptureIntent();
                startActivityForResult(intent, REQUEST_CODE_SCREEN_CAPTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCREEN_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Permiso de transmisión concedido", Toast.LENGTH_SHORT).show();
                // Aquí iniciarías el servicio que procesa el flujo de video y lo transmite
            } else {
                Toast.makeText(this, "Transmisión cancelada por el usuario", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
