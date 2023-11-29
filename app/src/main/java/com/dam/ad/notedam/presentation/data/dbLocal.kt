package com.dam.ad.notedam.presentation.data

import android.content.Context
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class dbLocal(private val context: Context) {

    private val FILE_NAME = "nombre_del_archivo.json"
    private val PREF_NAME = "nombre_pref"
    private val PREF_KEY_JSON = "clave_json"

    val piero ="no le gusto que pusiese su nombre como piegpt"
    fun cargarDatos(): String? {
        val file = File(context.filesDir, FILE_NAME)

        return if (file.exists()) {
            // El archivo JSON existe, cargarlo
            try {
                val fis = FileInputStream(file)
                val br = BufferedReader(InputStreamReader(fis))

                val jsonStringBuilder = StringBuilder()
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    jsonStringBuilder.append(line)
                }

                fis.close()
                jsonStringBuilder.toString()
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        } else {
            // El archivo no existe
            null
        }
    }

    fun guardarDatos(nuevoJson: JSONObject) {

        // Convertir el nuevo JSON a String
        val nuevoJsonString = nuevoJson.toString()

        // Guardar el nuevo JSON en SharedPreferences
        val editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
        editor.putString(PREF_KEY_JSON, nuevoJsonString)
        editor.apply()

        // Guardar el nuevo JSON también en el archivo local
        try {
            val file = File(context.filesDir, FILE_NAME)
            val fos = FileOutputStream(file)
            fos.write(nuevoJsonString.toByteArray())
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}