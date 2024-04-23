package com.example.pruebaexamen11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pruebaexamen11.data.LoteriaTipo
import com.example.pruebaexamen11.data.LoteriaUiState
import com.example.pruebaexamen11.data.LoteriaViewModel
import com.example.pruebaexamen11.ui.theme.PruebaExamen11Theme
import com.example.pruebaexamen11.data.DataSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaExamen11Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Principal(loterias = DataSource.loterias , viewModelLoteria = viewModel())
                }
            }
        }
    }
}

@Composable
fun Principal (modifier: Modifier = Modifier, loterias: ArrayList<LoteriaTipo>, viewModelLoteria: LoteriaViewModel){
    val uiState by viewModelLoteria.uiState.collectAsState()
    Column (modifier){
        Text(text = "Bienvenido a apuestas Ester Rivero.",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(start = 20.dp, top = 50.dp))
        PantallaLoterias(loterias = loterias,viewModelLoteria = viewModelLoteria)

        PantallaTextField(viewModelLoteria = viewModelLoteria)

        BotonJugarLoteria()

        PantallaTexto(uiState = uiState)
    }
}

@Composable
fun PantallaLoterias(modifier: Modifier = Modifier, loterias: ArrayList<LoteriaTipo>,viewModelLoteria: LoteriaViewModel){
    Column(modifier.height(200.dp)) {
        LazyHorizontalGrid(rows = GridCells.Fixed(1),
            horizontalArrangement= Arrangement.Center,
            verticalArrangement = Arrangement.Center){
            items(loterias){loteria->
                Card(modifier = Modifier.width(275.dp)
                    .padding(8.dp)) {
                    Text(text = "Nombre: ${loteria.nombre}",
                        modifier = Modifier
                            .background(Color.Yellow)
                            .padding(20.dp)
                            .fillMaxWidth())
                    Text(text = "Premio: ${loteria.premio} €",
                        modifier = Modifier
                            .background(Color.Cyan)
                            .padding(20.dp)
                            .fillMaxWidth())
                    Button(onClick = { viewModelLoteria.realizarApuesta(loteria, loterias, viewModelLoteria.dineroApostado) },
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                            .width(200.dp)) {
                        Text(text = "Apostar")
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTextField(modifier: Modifier = Modifier, viewModelLoteria: LoteriaViewModel){

    Row (modifier){
        TextField(value = viewModelLoteria.loteriaSeleccionada,
            onValueChange = { viewModelLoteria.selectLoteria(it) },
            label = { Text(text = "Loteria")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next),
            modifier = modifier.weight(1f)
                .padding(10.dp)
        )
        TextField(value = viewModelLoteria.dineroApostado,
            onValueChange = { viewModelLoteria.apuestaDinero(it) },
            label = { Text(text = "Dinero apostado")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next),
            modifier = modifier.weight(1f)
                .padding(10.dp)
        )
    }
}
@Composable
fun BotonJugarLoteria(modifier: Modifier = Modifier){
    Button(onClick = { },
        modifier = modifier.width(350.dp)
            .padding(8.dp)) {
        Text(text = "Jugar loteria escrita")
    }
}

@Composable
fun PantallaTexto(uiState: LoteriaUiState){
    Column (modifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray)
        .padding(50.dp)) {
        Text(text = "${uiState.textoUltimaAccion}",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Magenta))
        Text(text = "${uiState.textoJugado}",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White))
        Text(text = "${uiState.textoDineroGastado}",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow))
        Text(text = "${uiState.textoDineroGanado}",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray))
    }
}

