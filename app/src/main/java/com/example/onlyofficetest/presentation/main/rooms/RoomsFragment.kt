package com.example.onlyofficetest.presentation.main.rooms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlyofficetest.databinding.FragmentRoomsBinding
import com.example.onlyofficetest.domain.models.FileListItem
import com.example.onlyofficetest.presentation.main.common.FileAdapter

class RoomsFragment : Fragment() {
    private var _binding: FragmentRoomsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RoomsViewModel
    private lateinit var fileAdapter: FileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@RoomsFragment)[RoomsViewModel::class.java]

        fileAdapter = FileAdapter(requireContext())
        binding.recyclerView.adapter = fileAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.rooms.observe(viewLifecycleOwner) { rooms ->
            showData(rooms)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showError()
        }

        showLoading()
        viewModel.loadRooms()
    }

    private fun showLoading() {
        binding.errorLayout.visibility = View.INVISIBLE
        binding.loadingLayout.visibility = View.VISIBLE
        binding.dataLayout.visibility = View.INVISIBLE
    }

    private fun showData(items: List<FileListItem>) {
        binding.errorLayout.visibility = View.INVISIBLE
        binding.loadingLayout.visibility = View.INVISIBLE
        binding.dataLayout.visibility = View.VISIBLE

        fileAdapter.setData(items)
    }

    private fun showError() {
        binding.errorLayout.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.INVISIBLE
        binding.dataLayout.visibility = View.INVISIBLE

        binding.tryAgainButton.setOnClickListener {
            showLoading()
            viewModel.loadRooms()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}