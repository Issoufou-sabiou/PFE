package com.example.firebaseauthdemoapp.Livreur

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebaseauthdemoapp.services.AuthViewModel

@Composable
fun LivreurScreen(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenue, Livreur !")

        Button(onClick =  { navController.navigate("ajouterLivraison")}){
            Text("Aller à Ajouter une Livraison")

        }

        // Bouton de déconnexion
        Button(
            onClick = {
                authViewModel.signout() // Appel de la fonction de déconnexion
                navController.navigate("login") { // Redirection vers la page de connexion
                    popUpTo(navController.graph.startDestinationId) { // Supprime toutes les écrans de la pile
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Déconnexion")
        }
    }
}