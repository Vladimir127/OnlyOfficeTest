package com.example.onlyofficetest.presentation.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.onlyofficetest.R
import com.example.onlyofficetest.databinding.ActivityLoginBinding
import com.example.onlyofficetest.presentation.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        initValidation()

        viewModel.loginButtonEnabled.observe(this) { isEnabled ->
            binding.loginButton.isEnabled = isEnabled
        }

        // Проверяем, авторизован ли пользователь
        viewModel.isAuthorized.observe(this) { isAuthorized ->
            // Если пользователь уже авторизован, то запускаем MainActivity
            if (isAuthorized) {
                login()
            }
        }
        viewModel.checkIfIsAuthorized()

        viewModel.error.observe(this) {
            showError()
        }

        binding.loginButton.setOnClickListener {
            showLoading()
            viewModel.authorize(
                binding.portalEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
    }

    private fun initValidation() {
        // Валидация полей ввода. Каждому из трёх поле устанавливаем TextChangedListener и отправляем
        // содержимое во ViewModel для валидации, а также подписываемся на соответствующие объекты LiveData.
        // Если значение невалидно, будет отображена ошибка, если валидно - ошибка будет убрана
        viewModel.portalValid.observe(this) { isValid ->
            if (isValid) {
                binding.portalLayout.error = null
            } else {
                binding.portalLayout.error = getString(R.string.error)
            }
        }
        binding.portalEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.validatePortal(p0.toString())
            }
        })

        viewModel.emailValid.observe(this) { isValid ->
            if (isValid) {
                binding.emailLayout.error = null
            } else {
                binding.emailLayout.error = getString(R.string.error)
            }
        }
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.validateEmail(p0.toString())
            }
        })

        viewModel.passwordValid.observe(this) { isValid ->
            if (isValid) {
                binding.passwordLayout.error = null
            } else {
                binding.passwordLayout.error = getString(R.string.error)
            }
        }

        binding.passwordEditText.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.validatePassword(p0.toString())
                }
            })
        }
    }

    private fun showLoading() {
        binding.errorTextView.visibility = View.INVISIBLE
        binding.loginButton.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.errorTextView.visibility = View.VISIBLE
        binding.loginButton.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun login() {
        binding.progressBar.visibility = View.INVISIBLE

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}