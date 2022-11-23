package com.example.androidexamenp2

import Modelo.UsuariosDataSource
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registro.*

class Registro : AppCompatActivity() {

    private lateinit var datasource: UsuariosDataSource

    var lenguajes = ""
    var estado = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        datasource = UsuariosDataSource(this)
        logica()
    }

    fun logicaSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.Escolaridad,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            with(spEscolaridad)
            {
                // Apply the adapter to the spinner
                spEscolaridad.adapter = adapter
            }
        }
    }

    fun getHabilidades() {
        var java = ""
        var cS = ""
        var cM = ""
        if(cbJava.isChecked) {
            java = "Java "
        }
        if(cbCS.isChecked) {
            cS = "C# "
        }
        if(cbCS.isChecked) {
            cM = "C++"        }

        lenguajes = "$java" + "$cS" + "$cM"

    }

    fun getEstado() {
        if(rbSoltero.isChecked) {
            estado = rbSoltero.text.toString()
        } else if(rbCasado.isChecked) {
            estado = rbCasado.text.toString()
        }
    }
    fun logica() {
        logicaSpinner()
        btnRegistrar2.setOnClickListener{
            if (validaciones()) {
              getEstado()
              getHabilidades()
              val escolaridad = spEscolaridad.selectedItem.toString()
              val estadoCivil = estado
              val habilidades = lenguajes
                guardarUsuario(escolaridad,estadoCivil, habilidades)
                onBackPressed()
            }
        }
        btnBack.setOnClickListener{
            onBackPressed()
        }
    }

    fun validaciones():Boolean {
        var validado = false
        var validados = 0
        if(txtUsername2.text.toString() != "") {
            validados += 1
        } else txtUsername2.error = "Ingrese un usuario"

        if(txtName.text.toString() != "") {
            validados += 1
        } else txtName.error="Ingrese un nombre"

        if(txtPassword2.text.toString() != "") {
            validados += 1
        } else txtPassword2.error="Ingrese una contraseña"

        if (spEscolaridad.selectedItem.toString() != "Seleccione su escolaridad") {
            validados += 1
        }
        if(rbSoltero.isChecked || rbCasado.isChecked) {
            validados += 1
        }
        if(cbJava.isChecked || cbCS.isChecked || cbCM.isChecked) {
            validados += 1
        }
        if (validados == 6) {
            validado = true
        }

        return validado
    }

    fun guardarUsuario(escolaridad: String, estadoCivil: String, habilidades: String){
            datasource.guardarUsuario(txtUsername2.text.toString(),txtName.text.toString(),txtPassword2.text.toString(), escolaridad,estadoCivil,habilidades)
            val toast = Toast.makeText(applicationContext, "Se registró correctamente", Toast.LENGTH_SHORT)
            toast.show()
    }


}