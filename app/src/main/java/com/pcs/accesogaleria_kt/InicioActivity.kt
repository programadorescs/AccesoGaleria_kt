package com.pcs.accesogaleria_kt

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val btnAcceder = findViewById<Button>(R.id.bt_acceder)

        btnAcceder.setOnClickListener {
            if(!flags) {
                Toast.makeText(this, "La app no tiene permiso para acceder, active los permisos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        verificarPermisos()
    }

    companion object {
        private var flags: Boolean = false
        private const val REQUEST_CODE_ASK_PERMISSIONS: Int = 77
    }

    private fun verificarPermisos(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            flags = true
        } else {
            // Si algun permiso no esta asignado, entonces pedirlo
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Pedimos los permisos
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_ASK_PERMISSIONS)
            } else {
                // Caso contrario si ya todos los permisos fueron asignados, cambiar el flags a TRUE
                flags = true
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // OK, Permisos concedidos
                flags = true
            }
        }
    }
}