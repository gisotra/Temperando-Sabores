package com.jenifferleme.temperando_sabores.ui.adapter

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class GravacoesAdapter(private val arquivos: List<File>) :
    RecyclerView.Adapter<GravacoesAdapter.GravacaoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            GravacaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return GravacaoViewHolder(view)
    }
    override fun onBindViewHolder(holder: GravacaoViewHolder, position: Int)
    {
        val arquivo = arquivos[position]
        holder.titulo.text = arquivo.name
        holder.itemView.setOnClickListener {
            val mediaPlayer = MediaPlayer().apply {
                setDataSource(arquivo.absolutePath)
                prepare()
                start()
            }
        }
    }
    override fun getItemCount(): Int = arquivos.size
    inner class GravacaoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(android.R.id.text1)
    }
}