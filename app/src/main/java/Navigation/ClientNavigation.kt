package Navigation

import HomeScreen.MainScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauthdemoapp.Pages.AccountPage
import com.example.firebaseauthdemoapp.Pages.CategoryPage
import com.example.firebaseauthdemoapp.Pages.HistoryPage
import com.example.firebaseauthdemoapp.Pages.LoginPage
import com.example.firebaseauthdemoapp.Pages.SignupPage
import com.example.firebaseauthdemoapp.services.AuthViewModel

@Composable
fun ClientNavigation(modifier: Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    // Définition des routes et des pages dans le NavHost
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable("Signup") {
            SignupPage(modifier, navController, authViewModel)
        }
        composable("Home") {
            MainScreen(modifier)
        }

        composable("Category") {
            CategoryPage(modifier, navController)
        }
        composable("History") {
            HistoryPage(modifier, navController)
        }
        composable("Account") {
            AccountPage(modifier, navController)
        }
    }
}
