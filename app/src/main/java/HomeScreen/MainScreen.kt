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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauthdemoapp.Pages.ProfilePage
import com.example.firebaseauthdemoapp.services.AuthViewModel
import kotlinx.coroutines.launch
import np.com.bimalkafle.firebaseauthdemoapp.pages.HomePage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home,0),
        NavItem("Notification", Icons.Default.Notifications,5),
        NavItem("Settings", Icons.Default.Settings,0),
        NavItem("Profile", Icons.Default.Person,0),

        )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    // Contenu du drawer
    val drawerContent = @androidx.compose.runtime.Composable {
        ModalDrawerSheet(
            modifier = Modifier.fillMaxWidth(0.8f), // Largeur du drawer (80% de l'écran)
            drawerContainerColor = Color.White // Couleur de fond du drawer
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Menu 1", modifier = Modifier.padding(vertical = 8.dp))
                Text("Menu 2", modifier = Modifier.padding(vertical = 8.dp))
                Text("Menu 3", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }



    ModalNavigationDrawer(
        drawerContent = { drawerContent() },
        drawerState = drawerState,
        modifier = Modifier.fillMaxSize()
    ){
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


            bottomBar = {
                NavigationBar {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected =  selectedIndex == index ,
                            onClick = {
                                selectedIndex = index
                            },
                            icon = {
                                BadgedBox(badge = {
                                    if(navItem.badgeCount>0)
                                        Badge(){
                                            Text(text = navItem.badgeCount.toString())
                                        }
                                }) {
                                    Icon(imageVector = navItem.icon, contentDescription = "Icon")
                                }

                            },
                            label = {
                                Text(text = navItem.label)
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            ContentScreen(modifier = Modifier.padding(innerPadding),selectedIndex, navController= rememberNavController() , authViewModel = AuthViewModel())
        }
    }

}


@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex : Int, navController: NavController, authViewModel: AuthViewModel) {
    when(selectedIndex){
        0-> HomePage(modifier,navController,authViewModel)
        1-> NotificationPage()
        2-> SettingsPage()
        3-> ProfilePage(modifier,navController,authViewModel)
    }
}


















