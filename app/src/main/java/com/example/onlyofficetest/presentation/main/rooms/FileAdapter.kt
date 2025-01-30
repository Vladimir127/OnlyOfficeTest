package com.example.onlyofficetest.presentation.main.rooms

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlyofficetest.R
import com.example.onlyofficetest.databinding.ListItemBinding
import com.example.onlyofficetest.domain.models.FileListItem

/**
 * Адаптер для списка файлов и папок. Используется в разделах "Documents", "Rooms" и "Trash"
 */
class FileAdapter(val context: Context) :
    RecyclerView.Adapter<FileAdapter.FileViewHolder>() {
    var onItemClickListener: ((String) -> Unit)? = null

    private var items: List<FileListItem> = emptyList()

    fun setData(items: List<FileListItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return FileViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item, parent, false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val item = items[position]

        holder.bind(item)
    }

    inner class FileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemBinding.bind(view)

        private lateinit var fileListItem: FileListItem

        init {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(fileListItem.title)
            }
        }

        fun bind(product: FileListItem) {
            this.fileListItem = product

            binding.titleTextView.text = product.title
        }
    }
}