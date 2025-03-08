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
fun LoginPage(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel) {
    // États pour les champs de saisie
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    // Observation de l'état d'authentification à partir du ViewModel
    val authState = authViewModel.authState.observeAsState()

    // Récupération du contexte de l'application (nécessaire pour afficher des toasts)
    val context = LocalContext.current

    // Effet qui se déclenche lorsqu'il y a un changement dans l'état de l'authentification
    LaunchedEffect(authState.value) {
        when (authState.value) {
            // Si l'utilisateur est authentifié, on récupère son rôle et on redirige
            is AuthState.Authenticated -> {
                authViewModel.getUserRole { role ->
                    when (role) {
                        "Boutiquier" -> navController.navigate("boutiquier") // Redirection pour les boutiquiers
                        "Livreur" -> navController.navigate("livreur") // Redirection pour les livreurs
                        else -> Toast.makeText(context, "Rôle non reconnu", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // Si une erreur se produit, un Toast s'affiche avec le message d'erreur
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()

            else -> Unit // Aucun traitement pour les autres états
        }
    }

    // Layout principal de la page de connexion
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre de la page de connexion
        Text(
            text = "Login page",
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Champ de saisie pour l'email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Icon",
                    tint = Color.Gray
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Champ de saisie pour le mot de passe
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Password Icon",
                    tint = Color.Gray
                )
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    val icon: ImageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    Icon(
                        imageVector = icon,
                        contentDescription = "Toggle password visibility",
                        tint = Color.Gray
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour se connecter
        Button(
            onClick = {
                authViewModel.login(email, password)
            },
            enabled = authState.value != AuthState.Loading
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Lien pour rediriger l'utilisateur vers la page d'inscription
        TextButton(onClick = {
            navController.navigate("signup")
        }) {
            Text(text = "Don't have an account, signup")
        }
    }
}
