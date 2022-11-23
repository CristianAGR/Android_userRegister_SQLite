package com.example.androidexamenp2

import Entidades.Usuario
import Modelo.UsuariosDataSource
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.NonNull
import kotlinx.android.synthetic.main.activity_lista_personas.*
import kotlinx.android.synthetic.main.label_usuario.view.*

class ListaPersonas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_personas)

        LlenarInformacion()
    }

    fun LlenarInformacion(){
        val datasource = UsuariosDataSource(this)

        val registros = ArrayList<Usuario>()
        //Se esta llamando al método para traernos toda la información de la BD
        val cursor = datasource.consultarUsuarios()

        while(cursor.moveToNext()){
            val columnas =  Usuario(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
            )
            registros.add(columnas)
        }
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, registros);
        val adaptador = AdaptadorUsuarios(this, registros)

        listaUsuarios.adapter = adaptador
        listaUsuarios.setOnItemClickListener{ parent, view, position, id ->
            val item = parent.getItemAtPosition(position) as Usuario

            val intent = Intent(this@ListaPersonas, DetalleRegistro::class.java)

            intent.putExtra("id", item.ID_USUARIO)
            intent.putExtra("usuario", item.USUARIO)
            intent.putExtra("nombre", item.NOMBRE)
            intent.putExtra("contraseña", item.PASSWORD)
            intent.putExtra("escolaridad", item.ESCOLARIDAD)
            intent.putExtra("estadoCivil", item.ESTADOCIVIL)
            intent.putExtra("habilidades", item.HABILIDADES)

            startActivity(intent)

        }
    }



    override fun onPause() {
        super.onPause()
        this.LlenarInformacion()
    }

    override fun onResume() {
        super.onResume()
        this.LlenarInformacion()
    }

    internal class AdaptadorUsuarios(context: Context, datos:List<Usuario>):
        ArrayAdapter<Usuario>(context, R.layout.label_usuario, datos) {
        var _datos:List<Usuario>

        init {
            _datos = datos
        }

        @NonNull
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = convertView ?: LayoutInflater.from(context).inflate(R.layout.label_usuario, parent, false)
            val currentEntity = getItem(position)

            if (currentEntity != null) {
                inflater.LblTitulo.text = currentEntity.NOMBRE
                inflater.LblEscolaridad.text = currentEntity.ESCOLARIDAD
            }
            return inflater
        }
    }
}