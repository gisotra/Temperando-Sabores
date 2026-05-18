package com.jenifferleme.temperando_sabores.utils.filters

import com.jenifferleme.temperando_sabores.data.local.model.Filtro

enum class TipoFiltro {
    SEM_FILTRO, PRETO_BRANCO, SEPIA, INVERTER, BLUR
}
object FiltroFactory {
    fun criar(tipo: TipoFiltro): Filtro {
        return when (tipo) {
            TipoFiltro.SEM_FILTRO -> FiltroBypass()
            TipoFiltro.PRETO_BRANCO -> FiltroPretoBranco()
            TipoFiltro.SEPIA -> FiltroSepia()
            TipoFiltro.INVERTER -> FiltroInverso()
            TipoFiltro.BLUR -> FiltroBlur()
        }
    }
}