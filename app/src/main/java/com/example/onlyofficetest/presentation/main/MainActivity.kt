package com.example.onlyofficetest.presentation.main

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        initToolbar()
        initNavigation()
    }

    /**
     * Инициализирует Toolbar. Поскольку Toolbar должен иметь определённый цвет и размер,
     * определённый шрифт, выравнивание надписи по центру и кастомную иконку, в проекте используется
     * кастомный Toolbar - CenteredTitleToolbar
     */
    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)

        // Убираем границу между StatusBar и ActionBar, а также устаналиваем цвет
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))

        supportActionBar?.let { actionBar ->
            actionBar.title = resources.getString(R.string.documents)
        }

        binding.toolbar.findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            onBackPressed()
        }
    }

    private fun initNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        binding.bottomNavigationView.setOnApplyWindowInsetsListener(null)

        // В зависимости от фрагмента, к которому мы переходим. настраиваем внешний вид кастомного Toolbar:
        // видимость кнопки "Назад", выравнивание и текст заголовка
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when (destination.id) {
                R.id.documentsFragment -> {
                    binding.toolbar.backButton?.visibility = View.INVISIBLE
                    supportActionBar?.title = resources.getString(R.string.documents)

                    arguments?.let {
                        val title = arguments.getString(DocumentsFragment.ARG_PARAM1)
                        if (title != null) {
                            binding.toolbar.backButton.visibility = View.VISIBLE
                            supportActionBar?.title = title
                        }
                    }
                }

                R.id.roomsFragment -> {
                    binding.toolbar.backButton?.visibility = View.INVISIBLE
                    binding.toolbar.titleTextView?.gravity = Gravity.CENTER
                    supportActionBar?.title = resources.getString(R.string.rooms)
                }

                R.id.trashFragment -> {
                    binding.toolbar.backButton?.visibility = View.INVISIBLE
                    binding.toolbar.titleTextView?.gravity = Gravity.CENTER
                    supportActionBar?.title = resources.getString(R.string.trash)
                }

                R.id.profileFragment -> {
                    binding.toolbar.backButton?.visibility = View.INVISIBLE
                    binding.toolbar.titleTextView?.gravity = Gravity.CENTER
                    supportActionBar?.title = resources.getString(R.string.profile)
                }

                else -> {
                    supportActionBar?.title = ""
                }
            }
        }
    }
}