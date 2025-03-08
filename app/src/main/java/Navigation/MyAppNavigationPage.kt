package Navigation

import HomeScreen.MainScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.firebaseauthdemoapp.Pages.LivreurScreen
import com.example.firebaseauthdemoapp.Pages.LoginPage
import com.example.firebaseauthdemoapp.Pages.SignupPage
import com.example.firebaseauthdemoapp.services.AuthViewModel

@Composable
fun MyAppNavigation(modifier: Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    // Définition des routes et des pages dans le NavHost
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable("Signup") {
            SignupPage(modifier, navController, authViewModel)
        }
        composable("boutiquier") {
            MainScreen(modifier)
        }
        composable("livreur") {
            LivreurScreen(modifier, navController, authViewModel)
        }



    }
}
