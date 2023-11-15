package com.dam.ad.notedam.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dam.ad.notedam.R
import com.dam.ad.notedam.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val FILE_NAME = "nombre_del_archivo.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavMenu()

        // Comprobar si hay un archivo JSON
        val jsonContent = cargarDatos()

        if (jsonContent != null) {
            // Si hay un archivo JSON, cargarlo
            // Realiza acciones necesarias con el contenido cargado
            // ...
        } else {
            // Si no hay un archivo JSON, crear uno nuevo
            val nuevoJson = JSONObject()
            nuevoJson.put("clave", "valor") // Agrega datos según tus necesidades
            guardarDatos(nuevoJson)
        }
    }

    private fun initNavMenu() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun cargarDatos(): String? {
        val file = File(filesDir, FILE_NAME)

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
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            // El archivo no existe
            null
        }
    }

    private fun guardarDatos(nuevoJson: JSONObject) {
        // Convertir el nuevo JSON a String
        val nuevoJsonString = nuevoJson.toString()

        // Guardar el nuevo JSON en el archivo local
        try {
            val file = File(filesDir, FILE_NAME)
            file.writeText(nuevoJsonString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}