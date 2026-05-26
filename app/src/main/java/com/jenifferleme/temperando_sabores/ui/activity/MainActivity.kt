package com.jenifferleme.temperando_sabores.ui.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jenifferleme.temperando_sabores.ui.exercicios.ExerciciosFragment
import com.jenifferleme.temperando_sabores.R
import com.jenifferleme.temperando_sabores.databinding.ActivityMainBinding
import com.jenifferleme.temperando_sabores.ui.gravacao.GravacoesFragment
import android.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val PERMISSOES_REQUERIDAS = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )
}