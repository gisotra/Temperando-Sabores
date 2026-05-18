package com.jenifferleme.temperando_sabores.utils.filters

import android.graphics.Bitmap
import android.graphics.Color
import com.jenifferleme.temperando_sabores.data.local.model.Filtro

class FiltroSepia : Filtro() {
    override fun aplicar(bitmap: Bitmap): Bitmap {
        val resultado = Bitmap.createBitmap(bitmap.width, bitmap.height,
            bitmap.config!!)
        for (x in 0 until bitmap.width) {
            for (y in 0 until bitmap.height) {
                val pixel = bitmap.getPixel(x, y)
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)
                val tr = (0.393 * r + 0.769 * g + 0.189 *
                        b).toInt().coerceAtMost(255)
                val tg = (0.349 * r + 0.686 * g + 0.168 *
                        b).toInt().coerceAtMost(255)
                val tb = (0.272 * r + 0.534 * g + 0.131 *
                        b).toInt().coerceAtMost(255)
                resultado.setPixel(x, y, Color.rgb(tr, tg, tb))
            }
        }
        return resultado
    }
}