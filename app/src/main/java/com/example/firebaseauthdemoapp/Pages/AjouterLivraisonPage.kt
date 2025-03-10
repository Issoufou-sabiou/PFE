package com.example.firebaseauthdemoapp.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material3.TextButtonDefaults
import com.example.firebaseauthdemoapp.Livreur.LivraisonP.CreerLivraison

@Composable
fun AjouterLivraisonPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        // En-tête avec la flèche de retour et le titre
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.Green)
        ) {
            // Flèche de retour
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Retour",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Titre de la page "Ajouter une Livraison"
            Text(
                text = "Ajouter une Livraison",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 56.dp)
            )
        }

        // Le reste de la page est vide pour que tu puisses y ajouter ton contenu
        Spacer(modifier = Modifier.height(32.dp))  // Espacement pour séparer la partie supérieure


    }
}

@Composable
fun ModifierLivraisonPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        // En-tête avec la flèche de retour et le titre
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.Green)
        ) {
            // Flèche de retour
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Retour",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Titre de la page "Ajouter une Livraison"
            Text(
                text = "Modifier Livraison",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 66.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))  // Espacement pour séparer la partie supérieure


    }
}

@Composable
fun SupprimerLivraisonPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        // En-tête avec la flèche de retour et le titre
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.Green)
        ) {
            // Flèche de retour
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Retour",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Titre de la page "Ajouter une Livraison"
            Text(
                text = "Supprimer Livraison",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 66.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))  // Espacement pour séparer la partie supérieure


    }
}

@Composable
fun LivraisonPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)

    ) {
        // En-tête avec la flèche de retour et le titre
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.White)
        ) {
            // Flèche de retour
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Retour",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Titre de la page " Livraison"
            Text(
                text = "Livraisons",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 96.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.LightGray)
        ) {

            Box(
                modifier = Modifier
                    .weight(1f) // Prend une part égale de l'espace
            ) {
                TextButton(
                    onClick = { /* Action */ },
                ) {
                    Text("Tout")
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f) // Prend une part égale de l'espace
            ) {
                TextButton(
                    onClick = { /* Action */ },
                ) {
                    Text("Encours")
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f) // Prend une part égale de l'espace
            ) {
                TextButton(
                    onClick = { /* Action */ },
                ) {
                    Text("Livrer")
                }
            }


        }


    }
}


@Preview
@Composable
fun AjoutLivreur(){
    val navController = rememberNavController()

    // Appel du composable avec le NavController simulé
    AjouterLivraisonPage(navController = navController)
}

@Preview
@Composable
fun ModifierLivraisonPreview(){
    val navController = rememberNavController()

    // Appel du composable avec le NavController simulé
    ModifierLivraisonPage(navController = navController)
}

@Preview
@Composable
fun SupprimerLivraisonPreview(){
    val navController = rememberNavController()

    // Appel du composable avec le NavController simulé
    SupprimerLivraisonPage(navController = navController)
}

@Preview
@Composable
fun LivraisonPagePreview(){
    val navController = rememberNavController()

    // Appel du composable avec le NavController simulé
    LivraisonPage(navController = navController)
}
