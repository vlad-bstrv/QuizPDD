package com.example.quizpdd.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.quizpdd.R
import com.example.quizpdd.databinding.FragmentRegisterBinding
import com.example.quizpdd.ui.common.UiState
import com.example.quizpdd.ui.common.isEmail
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        register()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        binding.loginTextView.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun register() {
        binding.registerBtn.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (validate(email, password)) {
                viewModel.register(email, password)
            }
        }
    }

    private fun validate(email: String, password: String): Boolean {
        if (!email.isEmail()) {
            binding.emailEditText.error = getString(R.string.error_email)
            return false
        }

        if (password.length < 6) {
            binding.passwordEditText.error = getString(R.string.error_password)
            return false
        }

        return true
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is UiState.Error -> showError(it.message)
                    is UiState.IsLoading -> showLoading(it.isLoading)
                    is UiState.Success -> showSuccess(it.data)
                }
            }
        }
    }

    private fun showSuccess(data: Boolean) {
        if (data) {
            binding.root.findNavController()
                .navigate(R.id.action_registerFragment_to_topicFragment)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            registerBtn.isEnabled = !isLoading
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                registerBtn.text = ""
            } else {
                progressBar.visibility = View.GONE
                registerBtn.text = resources.getString(R.string.register)
            }
        }

    }

    private fun showError(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        snackbar.setAction(getString(R.string.reload)) {
            //todo
        }
        snackbar.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}