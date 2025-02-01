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

class DocumentsFragment : Fragment() {
    private var _binding: FragmentDocumentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DocumentsViewModel
    private lateinit var adapter: DocumentsAdapter

    /** Имя папки, открытой в данный момент. Используется для отображения на Toolbar в MainActivity */
    var title: String? = null

    /** ID папки, открытой в данный момент. Используется при запросе содержимого папки */
    private var folderId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Получаем имя и ID папки из аргументов
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

        adapter = DocumentsAdapter(requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // При нажатии на пункт списка проверяем, является ли выбранный пункт папкой.
        // Если является, переходим к новому экземпляру этого же фрагмента,
        // передавая в качестве аргументов имя и ID папки
        adapter.onItemClickListener = { item ->
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

        // Если ID папки отсутствует, значит, это корневая папка, и необходимо выполнить запрос без ID.
        // В противном случае получаем содержимое конкретной папки по её ID
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

        // Если список пустой, показываем вью-холдер
        if (items.isEmpty()) {
            binding.recyclerView.visibility = View.INVISIBLE
            binding.noDocumentsTextView.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.noDocumentsTextView.visibility = View.INVISIBLE
            adapter.setData(items)
        }
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