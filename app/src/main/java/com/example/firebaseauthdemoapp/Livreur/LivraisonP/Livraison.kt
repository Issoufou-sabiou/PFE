package com.example.firebaseauthdemoapp.Livreur.LivraisonP

data class Livraison(
    val id: String = "", // ID du document Firestore
    val recipientName: String = "",
    val deliveryAddress: String = "",
    val deliveryDescription: String = "",
    val deliveryWeight: String = "",
    val deliveryDate: String = "",
    val status: String = "" // Statut de la livraison (En attente, En cours, etc.)
)