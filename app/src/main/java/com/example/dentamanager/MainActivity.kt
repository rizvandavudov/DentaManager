package com.example.dentamanager
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DentaManagerApp()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DentaManagerApp() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("DentaManager") },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        drawerContent = {
            DrawerMenu(navController)
        }
    ) {
        NavigationGraph(navController)
    }
}

@Composable
fun DrawerMenu(navController: NavHostController) {
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        profileImageUri = uri
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.LightGray).padding(16.dp)) {
        Spacer(modifier = Modifier.height(30.dp))

        // İstifadəçi şəkli və ya default icon
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (profileImageUri == null) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Default Profile Icon",
                    modifier = Modifier.size(80.dp).clip(CircleShape).clickable {
                        imagePicker.launch("image/*")
                    },
                    tint = Color.Gray
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUri),
                    contentDescription = "User Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .clickable { imagePicker.launch("image/*") },
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Profil başlığı
        Text(
            text = "Profil",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        Divider()
        DrawerItem("Profil Məlumatları", "profile", navController)

        Spacer(modifier = Modifier.height(20.dp))

        // Digər menyular
        Text("Menü", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        Divider()
        DrawerItem("Ana Səhifə", "home", navController)
        DrawerItem("Pasiyentlər", "patients", navController)
        DrawerItem("Ayarlar", "settings", navController)

        Spacer(modifier = Modifier.height(20.dp))

        // Finans başlığı
        Text("Finans", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        Divider()
        DrawerItem("Gəlir və Xərclər", "finance", navController)

        Spacer(modifier = Modifier.height(20.dp))

        // Dəstək başlığı
        Text("Dəstək", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        Divider()
        DrawerItem("Əlaqə və Yardım", "support", navController)
    }
}

@Composable
fun DrawerItem(title: String, route: String, navController: NavHostController) {
    Text(
        text = title,
        fontSize = 18.sp,
        modifier = Modifier.fillMaxWidth().padding(12.dp).clickable {
            navController.navigate(route)
        }
    )
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen() }
        composable("patients") { PatientsScreen() }
        composable("settings") { SettingsScreen() }
        composable("profile") { ProfileScreen() }
        composable("finance") { FinanceScreen() }
        composable("support") { SupportScreen() }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Ana Səhifə", fontSize = 24.sp)
    }
}

@Composable
fun PatientsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Pasiyentlər Səhifəsi", fontSize = 24.sp)
    }
}

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Ayarlar", fontSize = 24.sp)
    }
}

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Profil Məlumatları", fontSize = 24.sp)
    }
}

@Composable
fun FinanceScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Gəlir və Xərclər", fontSize = 24.sp)
    }
}

@Composable
fun SupportScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Əlaqə və Yardım", fontSize = 24.sp)
    }
}
