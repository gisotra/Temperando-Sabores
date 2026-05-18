package com.jenifferleme.temperando_sabores.ui.gravacao

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.jenifferleme.temperando_sabores.R
import com.jenifferleme.temperando_sabores.databinding.FragmentCameraBinding
import com.jenifferleme.temperando_sabores.utils.camera.CameraHelper
import com.jenifferleme.temperando_sabores.utils.filters.FiltroFactory
import com.jenifferleme.temperando_sabores.utils.filters.TipoFiltro

class CameraFragment : Fragment(R.layout.fragment_camera), CameraHelper.Callback {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraHelper: CameraHelper
    private lateinit var originalBitmap: Bitmap
    private val REQUEST_CAMERA_CODE = 1001

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCameraBinding.bind(view)

        // Passamos a Activity que hospeda o fragment para o CameraHelper
        val activityContext = requireActivity() as AppCompatActivity
        cameraHelper = CameraHelper(activityContext, this)

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
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }

    private fun tirarFoto() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            cameraHelper.tirarFoto()
        } else {
            // Em Fragments, usamos o requestPermissions do próprio Fragment
            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_CODE)
        }
    }

    private fun aplicarFiltro(tipo: TipoFiltro?) {
        if (!::originalBitmap.isInitialized) return
        tipo?.let {
            activity?.runOnUiThread {
                val bitmap = FiltroFactory.criar(tipo).aplicar(originalBitmap)
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            tirarFoto()
        } else {
            Toast.makeText(requireContext(), "Permissão de câmera negada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória do View Binding
    }
}