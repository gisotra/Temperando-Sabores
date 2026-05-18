package com.jenifferleme.temperando_sabores.ui.exercicios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jenifferleme.temperando_sabores.databinding.FragmentExerciciosBinding
import com.jenifferleme.temperando_sabores.utils.reconhecimento.ReconhecimentoHelper

class ExerciciosFragment : Fragment(), ReconhecimentoHelper.Callback {
    private lateinit var binding: FragmentExerciciosBinding
    private lateinit var reconhecimentoHelper: ReconhecimentoHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciciosBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        reconhecimentoHelper = ReconhecimentoHelper(requireContext(), this)
        binding.btnFalar.setOnClickListener {
            reconhecimentoHelper.iniciarReconhecimento("en-US")
        }
    }
    override fun onReconhecimentoFinalizado(texto: String?) {
        binding.txtTextoReconhecido.text = texto
        compararTranscricao(texto ?: "")
    }
    override fun onErroReconhecimento(mensagem: String) {
        binding.txtFeedback.text = mensagem
    }
    private fun compararTranscricao(transcricao: String) {
        val original = binding.btnFalar.text.toString().trim().lowercase()
        val falado = transcricao.trim().lowercase()
        val feedback = when {
            falado == original -> "Ótimo! Transcrição correta 🎉"
            original in falado || falado in original -> "Quase lá! Sua pronúncia está próxima."
            else -> "Tente novamente. Preste atenção na pronúncia."
        }
        binding.txtFeedback.text = feedback
    }
    override fun onDestroyView() {
        reconhecimentoHelper.liberar()
        super.onDestroyView()
    }
}