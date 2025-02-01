package com.example.onlyofficetest.presentation.main.trash

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
 * Адаптер для списка файлов и папок. Используется в разделе "Trash"
 * Отображает список файлов и папок, но не имеет обработчика нажатия на пункт списка
 */
class TrashAdapter(val context: Context) :
    RecyclerView.Adapter<TrashAdapter.FileViewHolder>() {

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