package com.jenifferleme.temperando_sabores.model

import android.graphics.Bitmap

abstract class Filtro {
    abstract fun aplicar(bitmap: Bitmap): Bitmap
}