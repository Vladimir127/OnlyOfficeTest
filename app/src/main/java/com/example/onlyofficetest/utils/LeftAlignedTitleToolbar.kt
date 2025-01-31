package com.example.onlyofficetest.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import com.example.onlyofficetest.R

class LeftAlignedTitleToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    val titleTextView: TextView
    val backButton: AppCompatImageButton

    init {
        inflate(context, R.layout.toolbar_with_left_aligned_title, this)
        titleTextView = findViewById(R.id.title_text_view)
        backButton = findViewById(R.id.back_button)        
    }

    override fun setTitle(title: CharSequence?) {
        titleTextView.text = title
    }
}