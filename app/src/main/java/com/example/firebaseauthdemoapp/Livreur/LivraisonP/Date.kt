package com.example.firebaseauthdemoapp.Livreur.LivraisonP


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) } // État pour afficher l'erreur

    val currentDateMillis = System.currentTimeMillis()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentDateMillis,
        yearRange = IntRange(2025, 2100),
        initialDisplayMode = DisplayMode.Picker,
        initialDisplayedMonthMillis = currentDateMillis,
    )

    val selectedDate = remember {
        derivedStateOf {
            datePickerState.selectedDateMillis?.let { millis ->
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                dateFormat.format(Date(millis))
            } ?: ""
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = { },
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Choisir une date")
                }
            },
            isError = showError // Afficher une erreur si nécessaire
        )

        if (showError) {
            Text(
                text = "Veuillez sélectionner une date valide (aujourd'hui ou une date future).",
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val selectedDateMillis = datePickerState.selectedDateMillis
                            if (selectedDateMillis != null && selectedDateMillis >= currentDateMillis) {
                                onValueChange(selectedDate.value)
                                showDatePicker = false
                                showError = false // Masquer l'erreur
                            } else {
                                showError = true // Afficher l'erreur
                            }
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDatePicker = false }
                    ) {
                        Text("Annuler")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState
                )
            }
        }
    }
}
