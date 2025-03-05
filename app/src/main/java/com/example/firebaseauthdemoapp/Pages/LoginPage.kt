package com.example.firebaseauthdemoapp.Pages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseauthdemoapp.services.AuthState
import com.example.firebaseauthdemoapp.services.AuthViewModel

@Composable
fun LoginPage(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel){

    // Variables pour stocker l'email et le mot de passe de l'utilisateur
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) } // État pour la visibilité du mot de passe


    // Observation de l'état d'authentification à partir du ViewModel
    val authState = authViewModel.authState.observeAsState()

    // Récupération du contexte de l'application (nécessaire pour afficher des toasts)
    val context = LocalContext.current

    // Effet qui se déclenche lorsqu'il y a un changement dans l'état de l'authentification
    LaunchedEffect(authState.value) {
        when(authState.value){
            // Si l'utilisateur est authentifié, on navigue vers la page d'accueil
            is AuthState.Authenticated -> navController.navigate("home")

            // Si une erreur se produit, un Toast s'affiche avec le message d'erreur
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()

            else -> Unit // Aucun traitement pour les autres états
        }
    }


    // Layout principal de la page de connexion
    Column (
        modifier = Modifier.fillMaxSize(), // L'interface occupe toute la taille disponible
        verticalArrangement = Arrangement.Center, // Centrage vertical du contenu
        horizontalAlignment = Alignment.CenterHorizontally // Centrage horizontal du contenu
    ){
        // Titre de la page de connexion
        Text(
            text = "Login page",
            fontSize = 32.sp // Taille du texte
        )
        Spacer(modifier = Modifier.height(16.dp)) // Espacement entre les éléments

        // Champ de saisie pour l'email
        OutlinedTextField(
            value = email, // Valeur actuelle de l'email
            onValueChange = {
                email = it // Mettre à jour l'email lorsque l'utilisateur saisit quelque chose
            },
            label = {
                Text(text = "Email") // Label pour le champ
            },
            //icon email qui sera placer a gauche du champs email
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email, // Icône email
                    contentDescription = "Email Icon",
                    tint = Color.Gray // Couleur de l'icône
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp)) // Espacement entre les éléments

        // Champ de saisie pour le mot de passe
        OutlinedTextField(
            value = password, // Valeur actuelle du mot de passe
            onValueChange = {
                password = it // Mettre à jour le mot de passe lorsque l'utilisateur saisit quelque chose
            },
            label = {
                Text(text = "Password") // Label pour le champ
            },
            // Icône pour le champ de mot de passe à gauche
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock, // Icône pour le mot de passe (verrouillage)
                    contentDescription = "Password Icon",
                    tint = Color.Gray // Couleur de l'icône
                )
            },

            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(), // Transformation pour cacher le mot de passe
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility // Changer la visibilité
                }) {
                    val icon: ImageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    Icon(
                        imageVector = icon,
                        contentDescription = "Toggle password visibility",
                        tint = Color.Gray
                    )
                }
            }
        )


        Spacer(modifier = Modifier.height(16.dp)) // Espacement entre les éléments

        // Bouton pour se connecter (envoie les informations à l'authViewModel pour la connexion)
        Button(
            onClick = {
                authViewModel.login(email, password) // Appel à la fonction de connexion
            },
            enabled = authState.value != AuthState.Loading // Le bouton est désactivé lorsque l'état est "Loading"
        ) {
            Text(text = "Login") // Texte du bouton
        }


        Spacer(modifier = Modifier.height(8.dp)) // Espacement entre les éléments

        // Lien pour rediriger l'utilisateur vers la page d'inscription si il n'a pas de compte
        TextButton(onClick = {
            navController.navigate("signup") // Navigation vers la page d'inscription
        }) {
            Text(text = "Don't have an account, signup") // Texte du lien
        }


    }
}
