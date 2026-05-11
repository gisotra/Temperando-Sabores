package com.jenifferleme.temperando_sabores.filters

import android.graphics.Bitmap
import android.graphics.Color
import com.jenifferleme.temperando_sabores.model.Filtro

class FiltroBlur : Filtro() {
    override fun aplicar(bitmap: Bitmap): Bitmap {
        val radius = 1
        val width = bitmap.width
        val height = bitmap.height
        val resultado = Bitmap.createBitmap(width, height, bitmap.config!!)
        for (x in radius until width - radius) {
            for (y in radius until height - radius) {
                var somaR = 0
                var somaG = 0
                var somaB = 0
                var count = 0
                for (dx in -radius..radius) {
                    for (dy in -radius..radius) {
                        val pixel = bitmap.getPixel(x + dx, y + dy)
                        somaR += Color.red(pixel)
                        somaG += Color.green(pixel)
                        somaB += Color.blue(pixel)
                        count++
                    }
                }
                val r = (somaR / count).coerceIn(0, 255)
                val g = (somaG / count).coerceIn(0, 255)
                val b = (somaB / count).coerceIn(0, 255)
                resultado.setPixel(x, y, Color.rgb(r, g, b))
            }
        }
        return resultado
    }
}