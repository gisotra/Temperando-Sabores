package com.jenifferleme.temperando_sabores

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jenifferleme.temperando_sabores.databinding.ActivityMainBinding
import com.jenifferleme.temperando_sabores.tool.CameraHelper
import android.Manifest
import android.view.View
import android.widget.AdapterView
import com.jenifferleme.temperando_sabores.filters.FiltroFactory
import com.jenifferleme.temperando_sabores.filters.TipoFiltro

class MainActivity : AppCompatActivity(), CameraHelper.Callback {
    private lateinit var binding : ActivityMainBinding
    private lateinit var cameraHelper: CameraHelper
    private lateinit var originalBitmap: Bitmap
    private val REQUEST_CAMERA_CODE = 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cameraHelper = CameraHelper(this, this)
        binding.btnCapture.setOnClickListener {
            tirarFoto()
        }
        binding.spinnerFiltros.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val tipoFiltro = TipoFiltro.entries.getOrNull(position)
                    aplicarFiltro(tipoFiltro)
                }
                override fun onNothingSelected(parent: AdapterView<*>) { }
            }
    }
    private fun tirarFoto() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            cameraHelper.tirarFoto()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_CODE
            )
        }
    }
    private fun aplicarFiltro(tipo: TipoFiltro?) {
        if (!::originalBitmap.isInitialized) return
        tipo?.let {
            runOnUiThread {
                val bitmap = FiltroFactory.criar(tipo)
                    .aplicar(originalBitmap)
                binding.imageView.setImageBitmap(bitmap)
            }
        }
    }
    override fun onFotoRecebida(bitmap: Bitmap) {
        originalBitmap = bitmap
        binding.imageView.setImageBitmap(bitmap)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions,
            grantResults)
        if (requestCode == REQUEST_CAMERA_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            tirarFoto()
        } else {
            Toast.makeText(this,
                "Permissão de camera negada",
                Toast.LENGTH_SHORT).show()
        }
    }
}