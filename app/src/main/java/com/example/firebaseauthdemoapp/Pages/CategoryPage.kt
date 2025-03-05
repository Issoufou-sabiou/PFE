package com.example.firebaseauthdemoapp.Pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier

@Composable
fun CategoryPage(modifier: Modifier = Modifier, navController: NavController) {
    // Contenu de la page Category
    Column (
        modifier = modifier.fillMaxSize(),
    ) {
        Text("Category Page")
        // Ajouter des composants spécifiques à cette page

    }

}



