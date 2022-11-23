package com.example.androidexamenp2

import Modelo.UsuariosDataSource
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detalle_registro.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registro.*

class MainActivity : AppCompatActivity() {
    private lateinit var datasource: UsuariosDataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        datasource = UsuariosDataSource(this)
        logica()
    }

    private fun logica() {
        btnEntrar.setOnClickListener{
            if(validaciones()) {
                login()
            }
        }
        btnRegistrar.setOnClickListener{
            val intent = Intent(this, Registro::class.java).apply {}
            startActivity(intent)
        }
    }

    fun validaciones():Boolean {
        var validado = false
        var validados= 0
        if(txtUsername.text.toString() != "") {
            validados+= 1
        } else txtUsername.error="Ingrese un usuario valido"

        if(txtPassword.text.toString() != "") {
            validados += 1
        } else txtPassword.error="Ingrese una contraseña valida"

        if(validados== 2) {
         // if(txtPassword.text.toString() == "123" && txtUsername.text.toString() == "Cristian") {
                validado = true
          //} else {
            //  val toast = Toast.makeText(applicationContext, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT)
              //toast.show()
          //}
        }
        return validado
    }

    private fun login(){
        val toast: Toast
        if (datasource.iniciarSesion(txtUsername.text.toString(),txtPassword.text.toString())) {
            toast = Toast.makeText(applicationContext, "Se inició sesión correctamente", Toast.LENGTH_SHORT)
            toast.show()
            val intent = Intent(this, ListaPersonas::class.java).apply {}
            startActivity(intent)
        } else toast = Toast.makeText(applicationContext, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT)
        toast.show()
    }

}