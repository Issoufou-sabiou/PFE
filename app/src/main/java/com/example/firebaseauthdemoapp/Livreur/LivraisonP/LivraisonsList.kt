package com.example.firebaseauthdemoapp.Livreur.LivraisonP

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LivraisonsList(
    onBack: () -> Unit // Callback pour revenir à l'écran principal
) {
    var livraisons by remember { mutableStateOf<List<Livraison>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    val firestore = FirebaseFirestore.getInstance()

    // Récupérer les livraisons au chargement de l'écran
    LaunchedEffect(Unit) {
        isLoading = true
        firestore.collection("livraisons")
            .get()
            .addOnSuccessListener { result ->
                val livraisonList = result.map { document ->
                    Livraison(
                        id = document.id,
                        recipientName = document.getString("recipientName") ?: "",
                        deliveryAddress = document.getString("deliveryAddress") ?: "",
                        deliveryDescription = document.getString("deliveryDescription") ?: "",
                        deliveryWeight = document.getString("deliveryWeight") ?: "",
                        deliveryDate = document.getString("deliveryDate") ?: "",
                        status = document.getString("status") ?: ""
                    )
                }
                livraisons = livraisonList
                isLoading = false
            }
            .addOnFailureListener { e ->
                println("Erreur lors de la récupération des livraisons : ${e.message}")
                isLoading = false
            }
    }

    // Afficher la liste des livraisons
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Bouton Retour
        Button (
            onClick = onBack,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Retour")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn {
                items(livraisons) { livraison ->
                    LivraisonItem(livraison = livraison)
                }
            }
        }
    }
}


