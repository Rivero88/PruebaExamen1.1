package com.example.pruebaexamen11.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class LoteriaViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoteriaUiState())
    val uiState: StateFlow<LoteriaUiState> = _uiState.asStateFlow()

    var loteriaSeleccionada by mutableStateOf("")
    var dineroApostado by mutableStateOf("0")

    fun selectLoteria (loteriaElegida: String){
        loteriaSeleccionada = loteriaElegida
    }
    fun apuestaDinero (dineroGastado: String){
        dineroApostado = dineroGastado
    }

    fun realizarApuesta (loteriaSeleccionada: LoteriaTipo,loterias: ArrayList<LoteriaTipo>, dineroString: String){
        var dineroInt = dineroString.toInt()
        var contadorAct = _uiState.value.contador
        var dineroTotalApostadoAct = _uiState.value.dineroTotalApostado
        var numAleatorioAsignado = Random.nextInt(1,4)
        var numAleatorioGanador = Random.nextInt(1,4)
        var textoUltimaAccionAct = ""
        var textoPartidasJugadasAct = ""
        var textoDineroApostadoAct = ""
        var textoDineroGanadoAct = ""


        for(boleto in loterias) {
            if (boleto.nombre == loteriaSeleccionada.nombre) {
                if (dineroInt > 0) {
                    contadorAct++
                    textoUltimaAccionAct += "Has jugado a la lotería ${boleto.nombre} $dineroInt €"

                    textoPartidasJugadasAct += "Has jugado $contadorAct veces a la lotería."

                    dineroTotalApostadoAct += dineroInt
                    textoDineroApostadoAct += "Has gastado $dineroTotalApostadoAct € en lotería."

                    if(numAleatorioAsignado == numAleatorioGanador){
                        textoDineroGanadoAct += "Has ganado ${dineroInt * boleto.premio} €."
                    }else{
                        textoDineroGanadoAct += "Has perdido $dineroInt €."
                    }
                }else{
                    textoUltimaAccionAct += "No se puede comprar una lotería con 0 €."
                }
            }
        }

        _uiState.update {
            actualizarTexto -> actualizarTexto.copy(
                textoUltimaAccion = textoUltimaAccionAct,
                textoPartidasJugadas = textoPartidasJugadasAct,
                textoDineroApostado = textoDineroApostadoAct,
                textoDineroGanado = textoDineroGanadoAct,
                contador = contadorAct,
                dineroTotalApostado = dineroTotalApostadoAct,
            )
        }
    }

    fun realizarApuestaLoteriaSeleccionada (loteriaTextEditor: String, loterias: ArrayList<LoteriaTipo>, dineroString: String) {
        var textoUltimaAccionAct = ""
        var existe: Boolean = false
        var loteriaTipo: LoteriaTipo = LoteriaTipo("",0,0)

        for (boleto in loterias){
            if(boleto.nombre.equals(loteriaTextEditor,ignoreCase = true)){
                existe = true
                loteriaTipo=boleto.copy()
            }
        }
        if(existe){
            realizarApuesta(loteriaTipo,loterias,dineroString)
        }else{
            textoUltimaAccionAct += "No existe ninguna lotería con ese nombre."
            _uiState.update {
                    actualizarTexto -> actualizarTexto.copy(
                textoUltimaAccion = textoUltimaAccionAct
            )
            }
        }
    }
}