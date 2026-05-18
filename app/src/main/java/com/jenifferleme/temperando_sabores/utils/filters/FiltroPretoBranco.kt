package com.jenifferleme.temperando_sabores.utils.filters

import android.graphics.Bitmap
import android.graphics.Color
import com.jenifferleme.temperando_sabores.data.local.model.Filtro

class FiltroPretoBranco : Filtro() {
    override fun aplicar(bitmap: Bitmap): Bitmap {
        val resultado = Bitmap.createBitmap(bitmap.width, bitmap.height,
            bitmap.config!!)
        for (x in 0 until bitmap.width) {
            for (y in 0 until bitmap.height) {
                val pixel = bitmap.getPixel(x, y)
                val media = (Color.red(pixel) + Color.green(pixel) +
                        Color.blue(pixel)) / 3
                resultado.setPixel(x, y, Color.rgb(media, media, media))
            }
        }
        return resultado
    }
}