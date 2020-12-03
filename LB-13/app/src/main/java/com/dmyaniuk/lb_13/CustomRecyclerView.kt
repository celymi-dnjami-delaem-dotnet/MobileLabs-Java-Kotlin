package com.dmyaniuk.lb_13

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dmyaniuk.models.GenericEntity
import kotlinx.android.synthetic.main.rows.view.*

class CustomRecyclerView(_posts: List<GenericEntity>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var posts: List<GenericEntity> = _posts

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.rows,
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.placeName.text = posts[position].toString()
    }

    override fun getItemCount(): Int = posts.size
}