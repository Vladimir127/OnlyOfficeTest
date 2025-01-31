package com.example.onlyofficetest.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.onlyofficetest.R
import com.example.onlyofficetest.databinding.ActivityMainBinding
import com.example.onlyofficetest.presentation.main.documents.DocumentsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initNavigation()
    }

    private fun initNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
    }

    override fun onBackPressed() {
        // Извлекаем текущий фрагмент с NavController
        val currentDestination = navController.currentDestination

        if (currentDestination?.id == R.id.documentsFragment) {
            // Получаем ViewModel из фрагмента
            val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.fragments?.find { it is DocumentsFragment } as? DocumentsFragment

            fragment?.let {
                if (!fragment.handleBackPressed()) {
                    super.onBackPressed()
                }
            }
        } else {
            // Если текущий фрагмент - не DocumentsFragment, используем стандартное поведение
            super.onBackPressed()
        }
    }
}