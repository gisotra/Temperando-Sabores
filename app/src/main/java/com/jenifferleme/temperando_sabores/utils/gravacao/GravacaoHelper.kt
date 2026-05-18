package com.jenifferleme.temperando_sabores.utils.gravacao

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File

class GravacaoHelper(
    private val context: Context,
    private val callback: Callback
) {
    interface Callback {
        fun onGravacaoIniciada()
        fun onGravacaoFinalizada(arquivo: File)
        fun onErroGravacao(mensagem: String)
    }
    private var mediaRecorder: MediaRecorder? = null
    private var arquivo: File? = null
    fun iniciarGravacao(nomeArquivo: String) {
        if (nomeArquivo.isBlank()) {
            callback.onErroGravacao("Nome do arquivo não pode ser vazio.")
            return
        }
        arquivo = File(context.getExternalFilesDir(null), "$nomeArquivo.3gp")
        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION") MediaRecorder()
        }
        try {
            mediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(arquivo!!.absolutePath)
                prepare()
                start()
            }
            callback.onGravacaoIniciada()
        } catch (e: Exception) {
            callback.onErroGravacao("Erro ao gravar: ${e.message}")
        }
    }
    fun pararGravacao() {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            callback.onGravacaoFinalizada(arquivo!!)
        } catch (e: Exception) {
            callback.onErroGravacao("Erro ao parar gravação: ${e.message}")
        }
        mediaRecorder = null
    }
}
