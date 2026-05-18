package com.jenifferleme.temperando_sabores.utils.filters

import android.graphics.Bitmap
import com.jenifferleme.temperando_sabores.data.local.model.Filtro

class FiltroBypass : Filtro() {
    override fun aplicar(bitmap: Bitmap): Bitmap = bitmap
}