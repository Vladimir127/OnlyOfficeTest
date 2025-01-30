package com.example.onlyofficetest.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.onlyofficetest.databinding.FragmentProfileBinding
import com.example.onlyofficetest.domain.models.UserProfile
import com.example.onlyofficetest.presentation.login.LoginActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@ProfileFragment)[ProfileViewModel::class.java]

        viewModel.userData.observe(viewLifecycleOwner) {
            showData(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showError()
        }

        binding.tryAgainButton.setOnClickListener { loadData() }

        loadData()

        viewModel.loggedOut.observe(viewLifecycleOwner) {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun loadData() {
        showLoading()
        viewModel.loadData()
    }

    private fun showLoading() {
        binding.errorLayout.visibility = View.INVISIBLE
        binding.dataLayout.visibility = View.INVISIBLE
        binding.loadingLayout.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.errorLayout.visibility = View.VISIBLE
        binding.dataLayout.visibility = View.INVISIBLE
        binding.loadingLayout.visibility = View.INVISIBLE
    }

    private fun showData(userProfile: UserProfile) {
        Glide.with(this)
            .load(userProfile.avatar)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.avatarImageView)

        binding.userNameTextView.text = "${userProfile.firstName} ${userProfile.lastName}"
        binding.emailTextView.text = userProfile.email

        binding.loadingLayout.visibility = View.INVISIBLE
        binding.dataLayout.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}