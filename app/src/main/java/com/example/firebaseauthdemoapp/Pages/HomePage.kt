package np.com.bimalkafle.firebaseauthdemoapp.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseauthdemoapp.Livreur.LivraisonP.CreerLivraison
import com.example.firebaseauthdemoapp.services.AuthState
import com.example.firebaseauthdemoapp.services.AuthViewModel
@Composable
fun HomePage(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("Home !")

        CreerLivraison(
            onDeliveryCreated = {
                // Ce qui doit se passer après que la livraison a été créée avec succès
                println("Livraison créée avec succès !")
                // Par exemple, vous pourriez vouloir naviguer vers une autre page ou afficher un message
            }
        )

    }
}