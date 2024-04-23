package com.example.pruebaexamen11.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoteriaViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoteriaUiState())
    val uiState: StateFlow<LoteriaUiState> = _uiState.asStateFlow()

    var loteriaSeleccionada by mutableStateOf("")
    var dineroApostado by mutableStateOf("")

    fun selectLoteria (loteriaElegida: String){
        loteriaSeleccionada = loteriaElegida
    }
    fun apuestaDinero (dineroGastado: String){
        dineroApostado = dineroGastado
    }

    fun realizarApuesta (loteriaSeleccionada: LoteriaTipo,loterias: ArrayList<LoteriaTipo>, dineroString: String){
        var dineroInt = dineroString.toInt()
        var contador by mutableStateOf(0)
        var textoUltimaAccionActualizar = ""
        var textoJugadoActualizar = ""
        var textoDineroGastadoAct = ""
        var textoDineroGanadoAct = ""

        for(boleto in loterias) {
            if (boleto.nombre == loteriaSeleccionada.nombre) {
                if (dineroInt > 0) {
                    boleto.apuestaDinero += dineroInt
                    contador = contador++  /** No funciona*/
                    boleto.dineroTotalApostado += dineroInt
                    /**funciona*/textoUltimaAccionActualizar += "Has jugado a la lotería ${boleto.nombre} $dineroInt €"
                    /**No funciona*/textoJugadoActualizar += "Has jugado $contador veces a la lotería."
                    /**funciona, pero no muestra total de todas porque aquí estoy buscando por nombre. Hacer de otra manera.*/
                    textoDineroGastadoAct += "Has gastado ${boleto.dineroTotalApostado} euros en lotería"
                    /**hacerlo*/textoDineroGanadoAct += ""
                }
            }
        }

        _uiState.update {
                actualizarTexto -> actualizarTexto.copy(
            textoUltimaAccion = textoUltimaAccionActualizar,
            textoJugado = textoJugadoActualizar,
            textoDineroGastado = textoDineroGastadoAct,
            textoDineroGanado = textoDineroGanadoAct
        )
        }
    }
}