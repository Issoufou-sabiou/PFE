package com.example.firebaseauthdemoapp.Pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun AccountPage(modifier: Modifier, navController: NavController) {


    // Contenu de la page Account
    Column(modifier = modifier) {
        Text("Account Page")
        // Ajouter des composants spécifiques à cette page

    }

}