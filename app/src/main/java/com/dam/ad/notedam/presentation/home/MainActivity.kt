package com.dam.ad.notedam.presentation.home

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dam.ad.notedam.R
import com.dam.ad.notedam.databinding.ActivityMainBinding
import com.dam.ad.notedam.presentation.data.Categoria
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import android.content.Context



import java.io.File


@AndroidEntryPoint
class MainActivity(private val context: Context) : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavMenu()

         }

    private fun initNavMenu() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
    }

    //NUEVO PARA DESARROLLAR

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE)
    }

    fun guardarOpcionAlmacenamientoLocal(almacenamientoLocal: Boolean) {
        sharedPreferences.edit().putBoolean("almacenamientoLocal", almacenamientoLocal).apply()
    }

    fun obtenerOpcionAlmacenamientoLocal(): Boolean {
        return sharedPreferences.getBoolean("almacenamientoLocal", false)
    }

    fun guardarCategorias(categorias: List<Categoria>) {
        val jsonString = Gson().toJson(categorias)
        // Aquí deberías decidir si guardar en local o remoto según la preferencia del usuario
        if (obtenerOpcionAlmacenamientoLocal()) {
            File(context.filesDir, "categorias.json").writeText(jsonString)
        } else {
            // Lógica para guardar en remoto
        }
    }

    fun cargarCategorias(): List<Categoria> {
        // Aquí deberías decidir si cargar desde local o remoto según la preferencia del usuario
        val jsonString = if (obtenerOpcionAlmacenamientoLocal()) {
            File(context.filesDir, "categorias.json").readText()
        } else {
            // Lógica para cargar desde remoto
            ""
        }
        val tipoLista = object : TypeToken<List<Categoria>>() {}.type
        return Gson().fromJson(jsonString, tipoLista) ?: emptyList()
    }


}