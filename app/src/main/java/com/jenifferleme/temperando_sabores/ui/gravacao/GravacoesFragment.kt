package com.jenifferleme.temperando_sabores.ui.gravacao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jenifferleme.temperando_sabores.databinding.FragmentGravacoesBinding
import com.jenifferleme.temperando_sabores.ui.adapter.GravacoesAdapter
import com.jenifferleme.temperando_sabores.utils.gravacao.GravacaoHelper
import java.io.File

class GravacoesFragment : Fragment(), GravacaoHelper.Callback {
    private lateinit var binding: FragmentGravacoesBinding
    private lateinit var gravacaoHelper: GravacaoHelper
    private val gravacoes = mutableListOf<File>()
    private var isGravando = false
    private lateinit var adapter: GravacoesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGravacoesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gravacaoHelper = GravacaoHelper(requireContext(), this)
        adapter = GravacoesAdapter(gravacoes)
        binding.recyclerGravacoes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerGravacoes.adapter = adapter
        binding.btnGravar.setOnClickListener {
            if (!isGravando) {
                gravacaoHelper.iniciarGravacao(binding.edtTituloGravacao.text.toString())
            } else {
                gravacaoHelper.pararGravacao()
            }
            isGravando = !isGravando
        }
        carregarGravacoes()
    }
    private fun carregarGravacoes() {
        gravacoes.clear()
        val dir = requireContext().getExternalFilesDir(null)
        dir?.listFiles()?.filter { it.extension == "3gp" }?.let {
            gravacoes.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }
    override fun onGravacaoIniciada() {
        binding.txtStatus.text = "Gravando..."
        binding.btnGravar.text = "Parar Gravação"
    }
    override fun onGravacaoFinalizada(arquivo: File) {
        binding.txtStatus.text = "Gravação salva: ${arquivo.name}"
        binding.btnGravar.text = "Iniciar Gravação"
        carregarGravacoes()
    }
    override fun onErroGravacao(mensagem: String) {
        Toast.makeText(requireContext(), mensagem, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        gravacaoHelper.pararGravacao()
        super.onDestroyView()
    }
}