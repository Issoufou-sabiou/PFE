package com.example.firebaseauthdemoapp.Livreur.LivraisonP


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CreerLivraison(
    onDeliveryCreated: () -> Unit
) {
    var recipientName by remember { mutableStateOf("") }
    var deliveryAddress by remember { mutableStateOf("") }
    var deliveryDescription by remember { mutableStateOf("") }
    var deliveryWeight by remember { mutableStateOf("") }
    var deliveryDate by remember { mutableStateOf("") } // Date de livraison

    var isLoading by remember { mutableStateOf(false) }
    val firestore = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Champ pour le nom du destinataire
        OutlinedTextField(
            value = recipientName,
            onValueChange = { recipientName = it },
            label = { Text("Nom du destinataire") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true

        )

        // Champ pour l'adresse de livraison
        OutlinedTextField(
            value = deliveryAddress,
            onValueChange = { deliveryAddress = it },
            label = { Text("Adresse de livraison") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true

        )

        // Champ pour la description de la livraison
        OutlinedTextField(
            value = deliveryDescription,
            onValueChange = { deliveryDescription = it },
            label = { Text("Description de la livraison") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Champ pour le poids de la livraison
        OutlinedTextField(
            value = deliveryWeight,
            onValueChange = { deliveryWeight = it },
            label = { Text("Poids (kg)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true

        )

        // Champ pour la date de livraison (remplacé par le Date Picker)
        DatePickerField(
            label = "Date de livraison",
            value = deliveryDate,
            onValueChange = { deliveryDate = it }
        )

        // Bouton pour créer la livraison et l'envoyer dans la base de donnee
        Button(
            onClick = {
                if (recipientName.isNotEmpty() && deliveryAddress.isNotEmpty() && deliveryDescription.isNotEmpty() && deliveryWeight.isNotEmpty() && deliveryDate.isNotEmpty()) {
                    isLoading = true

                    val delivery = hashMapOf(
                        "recipientName" to recipientName,
                        "deliveryAddress" to deliveryAddress,
                        "deliveryDescription" to deliveryDescription,
                        "deliveryWeight" to deliveryWeight,
                        "deliveryDate" to deliveryDate,
                        "status" to "En attente",
                        "timestamp" to System.currentTimeMillis()
                    )

                    firestore.collection("livraisons")
                        .add(delivery)
                        .addOnSuccessListener {
                            isLoading = false
                            onDeliveryCreated()
                        }
                        .addOnFailureListener { e ->
                            isLoading = false
                            println("Erreur lors de la création de la livraison : ${e.message}")
                        }
                } else {
                    println("Veuillez remplir tous les champs.")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("Créer la livraison")
            }
        }
    }
}
