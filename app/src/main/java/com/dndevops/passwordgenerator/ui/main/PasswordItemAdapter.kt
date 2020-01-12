package com.dndevops.passwordgenerator.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dndevops.passwordgenerator.R
import com.dndevops.passwordgenerator.model.PasswordItem
import kotlinx.android.synthetic.main.item.view.*
import java.text.DateFormat


class PasswordItemAdapter(private val PasswordItems: List<PasswordItem>) : RecyclerView.Adapter<PasswordItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = PasswordItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(
        PasswordItems[position]
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(PasswordItem: PasswordItem) {
            itemView.tvTitle.text = PasswordItem.title
            itemView.tvPassword.text = PasswordItem.password
            itemView.tvCreatedDate.text = DateFormat.getDateTimeInstance().format(PasswordItem.createdDate)
        }
    }
}