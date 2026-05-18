package com.jenifferleme.temperando_sabores.utils.filters

import android.graphics.Bitmap
import android.graphics.Color
import com.jenifferleme.temperando_sabores.data.local.model.Filtro

class FiltroInverso : Filtro() {
    override fun aplicar(bitmap: Bitmap): Bitmap {
        val resultado = Bitmap.createBitmap(bitmap.width, bitmap.height,
            bitmap.config!!)
        for (x in 0 until bitmap.width) {
            for (y in 0 until bitmap.height) {
                val pixel = bitmap.getPixel(x, y)
                val r = 255 - Color.red(pixel)
                val g = 255 - Color.green(pixel)
                val b = 255 - Color.blue(pixel)
                resultado.setPixel(x, y, Color.rgb(r, g, b))
            }
        }
        return resultado
    }
}