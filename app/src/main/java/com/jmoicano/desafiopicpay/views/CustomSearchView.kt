package com.jmoicano.desafiopicpay.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.jmoicano.desafiopicpay.R
import kotlinx.android.synthetic.main.custom_search_view.view.*

class CustomSearchView(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomSearchView, 0, 0)
        val queryHint = typedArray.getString(R.styleable.CustomSearchView_android_hint)
        typedArray.recycle()

        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_search_view, this, true)

        customSearchViewSearchBar.hint = queryHint
        customSearchViewCloseIcon.setOnClickListener {
            customSearchViewSearchBar.setText("")
        }
        setViews(customSearchViewSearchBar.isFocused)
        customSearchViewSearchBar.setOnFocusChangeListener { _, hasFocus ->
            setViews(hasFocus)
        }
    }

    private fun setViews(focused: Boolean) {
        if (focused) {
            background = context.getDrawable(R.drawable.searchview_focused_background)
            customSearchViewEndSpace.visibility = View.GONE
            customSearchViewStartSpace.visibility = View.GONE
            val color = ContextCompat.getColor(context, android.R.color.white)
            customSearchViewSearchIcon.setColorFilter(color)
            customSearchViewCloseIcon.visibility = View.VISIBLE
            customSearchViewCloseIcon.setColorFilter(color)
        } else {
            background = context.getDrawable(R.drawable.searchview_unfocused_background)
            customSearchViewEndSpace.visibility = View.VISIBLE
            customSearchViewStartSpace.visibility = View.VISIBLE
            val color = ContextCompat.getColor(context, R.color.searchViewUnfocusedTextColor)
            customSearchViewSearchIcon.setColorFilter(color)
            customSearchViewCloseIcon.visibility = View.GONE
        }
    }

    fun setOnQueryTextListener(listener: SearchView.OnQueryTextListener) {
        customSearchViewSearchBar.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.onQueryTextChange(s.toString())
            }

        })
        customSearchViewSearchBar.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                listener.onQueryTextSubmit(v.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}