package com.example.onlyofficetest.presentation.main.documents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlyofficetest.databinding.FragmentDocumentsBinding
import com.example.onlyofficetest.domain.models.FileListItem
import com.example.onlyofficetest.domain.models.Folder
import com.example.onlyofficetest.presentation.main.common.FileAdapter

class DocumentsFragment : Fragment() {
    private var _binding: FragmentDocumentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DocumentsViewModel
    private lateinit var fileAdapter: FileAdapter

    var title: String? = null
    private var folderId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_PARAM1)
            folderId = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDocumentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@DocumentsFragment)[DocumentsViewModel::class.java]

        fileAdapter = FileAdapter(requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = fileAdapter

        fileAdapter.onItemClickListener = { item ->
            if (item is Folder) {
                val action = DocumentsFragmentDirections.actionDocumentsFragmentSelf(item.title, item.id)
                findNavController(this).navigate(action)
            }
        }

        viewModel.documents.observe(viewLifecycleOwner) { documents ->
            showData(documents)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showError()
        }

        loadDocuments()
    }

    private fun loadDocuments(){
        showLoading()
        if (folderId == null) {
            viewModel.loadDocuments()
        } else {
            viewModel.loadDocuments(folderId!!)
        }
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
            loadDocuments()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_PARAM1 = "folderName"
        private const val ARG_PARAM2 = "folderId"
    }
}