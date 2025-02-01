package com.example.onlyofficetest.presentation.main.documents

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlyofficetest.R
import com.example.onlyofficetest.databinding.ListItemBinding
import com.example.onlyofficetest.domain.models.FileListItem
import com.example.onlyofficetest.domain.models.Folder

/**
 * Адаптер для списка файлов и папок. Используется в разделе "Documents"
 * Имеет обработчик нажатия на пункт списка, позволяющий переходить по вложенным папкам
 */
class DocumentsAdapter(val context: Context) :
    RecyclerView.Adapter<DocumentsAdapter.FileViewHolder>() {

    var onItemClickListener: ((FileListItem) -> Unit)? = null

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
                onItemClickListener?.invoke(fileListItem)
            }
        }

        fun bind(item: FileListItem) {
            this.fileListItem = item

            binding.titleTextView.text = item.title

            val imageResource = if (item is Folder) {
                R.drawable.baseline_folder_open_white_24
            } else {
                R.drawable.baseline_description_white_24
            }

            binding.iconImageView.setImageResource(imageResource)
        }
    }
}