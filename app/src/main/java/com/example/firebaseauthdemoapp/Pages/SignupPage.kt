package com.example.firebaseauthdemoapp.Pages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupPage(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nomComplet by remember { mutableStateOf("") }
    var numeroTelephone by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var phoneErrorMessage by remember { mutableStateOf<String?>(null) }

    val options = listOf("Livreur", "Boutiquier")
    var selectedRole by remember { mutableStateOf(options[0]) }
    var expanded by remember { mutableStateOf(false) }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Signup page", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nomComplet,
            onValueChange = { nomComplet = it },
            label = { Text(text = "Nom complet") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Person2, contentDescription = "Email Icon", tint = Color.Gray)
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Email, contentDescription = "Email Icon", tint = Color.Gray)
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = numeroTelephone,
            onValueChange = {
                if (it.length <= 8 && it.all { char -> char.isDigit() }) {
                    numeroTelephone = it
                    phoneErrorMessage = null
                } else {
                    phoneErrorMessage = "Le numéro de téléphone doit contenir exactement 8 chiffres."
                }
            },
            label = { Text(text = "Numéro de téléphone") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Call, contentDescription = "Email Icon", tint = Color.Gray)
            },
            singleLine = true
        )

        if (phoneErrorMessage != null) {
            Text(
                text = phoneErrorMessage!!,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password Icon", tint = Color.Gray)
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    val icon: ImageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    Icon(imageVector = icon, contentDescription = "Toggle password visibility", tint = Color.Gray)
                }
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            OutlinedTextField(
                value = selectedRole,
                onValueChange = {},
                label = { Text("Role") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                readOnly = true,
                modifier = Modifier
                    .menuAnchor(),
                singleLine = true
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedRole = option
                            expanded = false
                        },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (numeroTelephone.length != 8) {
                    phoneErrorMessage = "Le numéro de téléphone doit contenir exactement 8 chiffres."
                } else {
                    authViewModel.signup(email, password, nomComplet, numeroTelephone, selectedRole)
                }
            },
            enabled = authState.value != AuthState.Loading
        ) {
            Text(text = "Create account")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text(text = "Already have an account, Login")
        }
    }
}