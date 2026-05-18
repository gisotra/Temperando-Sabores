package com.jenifferleme.temperando_sabores.data.local.model

import android.graphics.Bitmap

abstract class Filtro {
    abstract fun aplicar(bitmap: Bitmap): Bitmap
}