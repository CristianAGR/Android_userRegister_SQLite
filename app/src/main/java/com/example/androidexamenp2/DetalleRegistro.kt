package com.example.androidexamenp2

import Modelo.UsuariosDataSource
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detalle_registro.*


class DetalleRegistro : AppCompatActivity() {

    private lateinit var datasource: UsuariosDataSource
    private var id = 0
    private var usuario = ""
    private var nombre = ""
    private var contraseña = ""
    private var escolaridad = ""
    private var estadoCivil = ""
    private var habilidades = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_registro)
        datasource = UsuariosDataSource(this)
        // recuperar info para el detalle de registro
        val b = this.intent.extras
        if(b != null){
            id = b.getInt("id")
            usuario = b.getString("usuario").toString()
            nombre = b.getString("nombre").toString()
            contraseña = b.getString("contraseña").toString()
            escolaridad = b.getString("escolaridad").toString()
            estadoCivil = b.getString("estadoCivil").toString()
            habilidades = b.getString("habilidades").toString()
            txtUsername3.setText(usuario)
            txtName2.setText(nombre)
            txtPassword3.setText(contraseña)
            txtEscolaridad.setText(escolaridad)
            txtEstadoCIvil.setText(estadoCivil)
            txtHabilidades.setText(habilidades)
            val url = "https://picsum.photos/300/200?image=$id"
            Glide.with(this)
                .load(url)
                .thumbnail(Glide.with(this).load(R.drawable.jar_loading))
                .into(imagenDetalle)
        }

        logica()
    }

    fun logica() {
        btnRegresar.setOnClickListener{ onBackPressed() }
        btnEliminar.setOnClickListener{
            eliminar()
            onBackPressed()}
    }
    fun eliminar(){
        if (datasource.eliminarUsuario(id)){
            val toast = Toast.makeText(applicationContext, "Se eliminó correctamente", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}