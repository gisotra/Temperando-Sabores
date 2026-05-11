package com.jenifferleme.temperando_sabores.filters

import android.graphics.Bitmap
import com.jenifferleme.temperando_sabores.model.Filtro

class FiltroBypass : Filtro() {
    override fun aplicar(bitmap: Bitmap): Bitmap = bitmap
}