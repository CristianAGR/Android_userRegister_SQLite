package Modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import kotlin.Exception

class UsuariosDataSource (context: Context){
    private val openHelper: BdOpenHelper = BdOpenHelper(context)
    private val database: SQLiteDatabase
    object ColumnUsuarios {
        var ID_USUARIO = BaseColumns._ID
        var USUARIO = "usuario"
        var NOMBRE = "nombre"
        var PASSWORD = "contraseña"
        var ESCOLARIDAD = "escolaridad"
        var ESTADOCIVIL = "estadoCivil"
        var HABILIDADES = "habilidades"
    }

    init {
        database = openHelper.writableDatabase
    }

    fun consultarUsuarios():Cursor {
        return  database.rawQuery("select _id,usuario,nombre,contraseña,escolaridad,estadoCivil,habilidades from $USUARIOS_TABLE_NAME", null)
    }

    fun iniciarSesion(usuarioEntry: String, contraseña: String): Boolean {
        try {
            var cursor = database.rawQuery("select _id,usuario,contraseña from $USUARIOS_TABLE_NAME where usuario='$usuarioEntry' and contraseña='$contraseña'", null)

            if (cursor.count > 0) {
                //database.close()
                return true
            }

        } catch (ex: SQLException) {
        }
        //database.close()
        return false
    }

    fun guardarUsuario(usuario: String, nombre: String, contraseña: String, escolaridad: String, estadoCivil: String, habilidades: String){
        val values = ContentValues()
        values.put(ColumnUsuarios.USUARIO, usuario)
        values.put(ColumnUsuarios.NOMBRE, nombre)
        values.put(ColumnUsuarios.PASSWORD, contraseña)
        values.put(ColumnUsuarios.ESCOLARIDAD, escolaridad)
        values.put(ColumnUsuarios.ESTADOCIVIL, estadoCivil)
        values.put(ColumnUsuarios.HABILIDADES, habilidades)
        database.insert(USUARIOS_TABLE_NAME, null, values)
    }

    fun modificarUsuario(usuario: String, nombre: String, contraseña: String, escolaridad: String, estadoCivil: String, habilidades: String, IdUsuario: Int){
        val values = ContentValues()
        values.put(ColumnUsuarios.USUARIO, usuario)
        values.put(ColumnUsuarios.NOMBRE, nombre)
        values.put(ColumnUsuarios.PASSWORD, contraseña)
        values.put(ColumnUsuarios.ESCOLARIDAD, escolaridad)
        values.put(ColumnUsuarios.ESTADOCIVIL, estadoCivil)
        values.put(ColumnUsuarios.HABILIDADES, habilidades)
        val a = arrayOf("" + IdUsuario)
        database.update(USUARIOS_TABLE_NAME, values, "_id=?", a)
    }

    fun eliminarUsuario(IdUsuario: Int):Boolean {
        val whereArgs = arrayOf("" + IdUsuario)
        try{
            database.delete(USUARIOS_TABLE_NAME, "_id=?", whereArgs)
            return true
        }
        catch (ex: Exception){
            return false
        }
        finally {
            database.close()
        }
    }

    companion object {
        val USUARIOS_TABLE_NAME = "Usuarios"
        val STRING_TYPE = "text"
        val INT_TYPE = "integer"
        val CREATE_USUARIOS_SCRIPT = (
                "create table " + USUARIOS_TABLE_NAME + "(" +
                        ColumnUsuarios.ID_USUARIO + " " + INT_TYPE + " primary key autoincrement," +
                        ColumnUsuarios.USUARIO + " " + STRING_TYPE + " not null," +
                        ColumnUsuarios.NOMBRE + " " + STRING_TYPE + " not null," +
                        ColumnUsuarios.PASSWORD + " " + STRING_TYPE + " not null," +
                        ColumnUsuarios.ESCOLARIDAD + " " + STRING_TYPE + " not null," +
                        ColumnUsuarios.ESTADOCIVIL + " " + STRING_TYPE + " not null," +
                        ColumnUsuarios.HABILIDADES + " " + STRING_TYPE + " not null)")
        val INSERT_USUARIOS_SCRIPT = (
                "insert into " + USUARIOS_TABLE_NAME + " values " +
                        "(null,'Goro','Cristian','1234','Universidad','Soltero','Java C#')," +
                        "(null,'Cocho','Ricardo','4567','Universidad','Soltero','C# C++')," +
                        "(null,'Pigy','Carlos','7891','Universidad','Soltero','JAVA C++')")
    }

}