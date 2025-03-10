package HomeScreen

import HomePage.NotificationPage
import HomePage.SettingsPage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauthdemoapp.Pages.ProfilePage
import com.example.firebaseauthdemoapp.services.AuthViewModel
import kotlinx.coroutines.launch
import np.com.bimalkafle.firebaseauthdemoapp.pages.HomePage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Contenu du drawer
    val drawerContent = @Composable {
        ModalDrawerSheet(
            modifier = Modifier.fillMaxWidth(0.8f), // Largeur du drawer (80% de l'écran)
            drawerContainerColor = Color.White // Couleur de fond du drawer
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
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
                Text("Menu 2", modifier = Modifier.padding(vertical = 8.dp))
                Text("Menu 3", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }

    // Modal Navigation Drawer avec uniquement le TopAppBar
    ModalNavigationDrawer(
        drawerContent = { drawerContent() },
        drawerState = drawerState,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color.DarkGray)
                ) {
                    // Bouton du menu hamburger avec l'icône
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterStart) // Aligner à gauche
                            .padding(8.dp) // Ajouter un peu d'espace autour du bouton
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu, // Icône du menu hamburger
                            contentDescription = "Menu", // Description pour l'accessibilité
                            tint = Color.White // Couleur de l'icône
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            // ContentScreen remplacé par ta page de contenu
            ContentScreen(modifier = Modifier.padding(innerPadding), navController = rememberNavController(), authViewModel = AuthViewModel())
        }
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    // Ici tu peux ajouter les pages que tu veux afficher dans ton contenu
    HomePage(modifier = modifier, navController = navController, authViewModel = authViewModel)
}
