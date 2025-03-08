package com.example.firebaseauthdemoapp.Pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.firebaseauthdemoapp.services.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

@Composable
fun ProfilePage(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()
    val userId = firebaseAuth.currentUser?.uid ?: ""

    var userName by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") } // Pour stocker l'e-mail de connexion

    // États pour le modal
    var showEditNameModal by remember { mutableStateOf(false) }
    var showEditNumeroModal by remember { mutableStateOf(false) }
    var newUserName by remember { mutableStateOf("") }
    var newNumero by remember { mutableStateOf("") }

    // État pour gérer le chargement
    var isLoading by remember { mutableStateOf(false) }


    var profileImageUrl by remember { mutableStateOf<String?>(null) }

    // Récupérer les données utilisateur depuis Firestore
    LaunchedEffect(userId) {
        val userDoc = firestore.collection("utilisateurs").document(userId).get().await()
        userName = userDoc.getString("nomComplet") ?: "Nom non disponible"
        numero = userDoc.getString("numeroTelephone") ?: ""
        profileImageUrl = userDoc.getString("profileImageUrl")
        email = firebaseAuth.currentUser?.email ?: ""
    }

    // Gestion du téléchargement de l'image
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val storageRef = storage.reference.child("profile_images/$userId")
            storageRef.putFile(it).addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    profileImageUrl = uri.toString()
                    firestore.collection("utilisateurs").document(userId).update("profileImageUrl", uri.toString())
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top=100.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier.size(120.dp) // Taille du conteneur
        ){
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                color = Color.LightGray
            ) {
                if (profileImageUrl != null) {
                    Image(
                        painter = rememberImagePainter(data = profileImageUrl),
                        contentDescription = "Photo de profil",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                        contentDescription = "Photo de profil",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.CameraAlt, // Icône de crayon
                contentDescription = "Changer la photo de profil", // Description pour l'accessibilité
                modifier = Modifier
                    .size(36.dp) // Taille de l'icône
                    .clickable { launcher.launch("image/*") } // Action au clic
                    .padding(4.dp) // Espacement autour de l'icône
                    .background(Color.Gray, CircleShape) // Fond blanc pour l'icône
                    .padding(4.dp), // Espacement interne
                tint = Color.Black // Couleur de l'icône
            )

        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cadre pour le nom complet
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = "Nom Complet",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Card(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f), // Prend tout l'espace disponible
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.Person, // Icône pour le nom
                            contentDescription = "Nom",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = userName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Modifier le nom",
                        modifier = Modifier
                            .size(18.dp)
                            .clickable {
                                newUserName = userName // Initialiser le champ avec la valeur actuelle
                                showEditNameModal = true // Ouvrir le modal pour le nom
                            },
                        tint = Color.Gray,

                    )

                }
            }
        }

        // Cadre pour le numéro de téléphone
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = "Numéro de Téléphone",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Card(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f), // Prend tout l'espace disponible
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.Phone, // Icône pour le numéro de téléphone
                            contentDescription = "Numéro de Téléphone",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = numero,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Modifier le numéro",
                        modifier = Modifier
                            .size(18.dp)
                            .clickable {
                                newNumero = "" // Initialiser le champ avec la valeur actuelle
                                showEditNumeroModal = true // Ouvrir le modal pour le numéro
                            },
                        tint = Color.Gray
                    )
                }
            }
        }

        // Cadre pour l'e-mail
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = "E-mail",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Card(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Email, // Icône pour l'e-mail
                        contentDescription = "E-mail",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = email,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
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

    // Modal pour modifier le nom
    if (showEditNameModal) {
        AlertDialog(
            onDismissRequest = { showEditNameModal = false }, // Fermer le modal
            title = { Text("Modifier le nom") },
            text = {
                Column {
                    TextField(
                        value = newUserName,
                        onValueChange = { newUserName = it },
                        label = { Text("Nouveau nom") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Mettre à jour le nom dans Firestore
                        isLoading = true
                        firestore.collection("utilisateurs").document(userId)
                            .update("nomComplet", newUserName)
                            .addOnSuccessListener {
                                // Mettre à jour l'état après la réussite
                                userName = newUserName
                                isLoading = false
                                showEditNameModal = false // Fermer le modal
                            }
                            .addOnFailureListener {
                                // Gérer l'erreur
                                isLoading = false
                                println("Erreur lors de la mise à jour du nom : ${it.message}")
                            }
                    },
                    enabled = !isLoading // Désactiver le bouton pendant le chargement
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White) // Afficher un indicateur de chargement
                    } else {
                        Text("Enregistrer")
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showEditNameModal = false } // Fermer le modal sans enregistrer
                ) {
                    Text("Annuler")
                }
            }
        )
    }

    // Modal pour modifier le numéro de téléphone
    if (showEditNumeroModal) {
        AlertDialog(
            onDismissRequest = { showEditNumeroModal = false }, // Fermer le modal
            title = { Text("Modifier le numéro de téléphone") },
            text = {
                Column {
                    TextField(
                        value = newNumero,
                        onValueChange = { newNumero = it },
                        label = { Text("Nouveau numéro") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Mettre à jour le numéro dans Firestore
                        isLoading = true
                        firestore.collection("utilisateurs").document(userId)
                            .update("numeroTelephone", newNumero)
                            .addOnSuccessListener {
                                // Mettre à jour l'état après la réussite
                                numero = newNumero
                                isLoading = false
                                showEditNumeroModal = false // Fermer le modal
                            }
                            .addOnFailureListener {
                                // Gérer l'erreur
                                isLoading = false
                                println("Erreur lors de la mise à jour du numéro : ${it.message}")
                            }
                    },
                    enabled = !isLoading // Désactiver le bouton pendant le chargement
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White) // Afficher un indicateur de chargement
                    } else {
                        Text("Enregistrer")
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showEditNumeroModal = false } // Fermer le modal sans enregistrer
                ) {
                    Text("Annuler")
                }
            }
        )
    }

}