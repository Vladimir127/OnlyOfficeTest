package com.example.onlyofficetest.presentation.main.trash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlyofficetest.databinding.FragmentTrashBinding
import com.example.onlyofficetest.domain.models.FileListItem
import com.example.onlyofficetest.presentation.main.common.FileAdapter

class TrashFragment : Fragment() {
    private var _binding: FragmentTrashBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TrashViewModel
    private lateinit var fileAdapter: FileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@TrashFragment)[TrashViewModel::class.java]

        fileAdapter = FileAdapter(requireContext())
        binding.recyclerView.adapter = fileAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.trash.observe(viewLifecycleOwner) { trash ->
            showData(trash)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showError()
        }

        showLoading()
        viewModel.loadTrash()
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
            viewModel.loadTrash()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}