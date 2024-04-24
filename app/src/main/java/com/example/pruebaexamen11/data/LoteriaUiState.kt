package com.example.pruebaexamen11.data

data class LoteriaUiState(
    var textoUltimaAccion: String = "No has jugado ninguna lotería",
    var textoPartidasJugadas: String = "Has jugado 0 veces",
    var textoDineroApostado: String = "Has gastado 0 euros en lotería",
    var textoDineroGanado: String = "Has ganado 0 euros en lotería",
    var contador: Int = 0,
    var dineroTotalApostado: Int = 0,
)
