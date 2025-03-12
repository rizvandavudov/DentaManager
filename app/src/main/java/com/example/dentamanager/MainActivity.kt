package com.example.dentamanager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
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
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Randevular başlığı və + düyməsi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Randevular", fontSize = 24.sp, color = Color.Black)
                IconButton(onClick = { /* TODO: Yeni randevu əlavə etmək üçün funksiyanı yaz */ }) {
                    Icon(Icons.Filled.Add, contentDescription = "Yeni Randevu")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            NavigationGraph(navController)
        }
    }
}

@Composable
fun DrawerMenu(navController: NavHostController) {
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { pickImageLauncher.launch("image/*") }
        ) {
            if (profileImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUri),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Default Profile Icon",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text("Menyu", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        Divider()
        DrawerItem("Ana Səhifə", "home", navController)
        DrawerItem("Pasiyentlər", "patients", navController)
        DrawerItem("Ayarlar", "settings", navController)
        DrawerItem("Profil", "profile", navController)
        DrawerItem("Finans", "finance", navController)
        DrawerItem("Dəstək", "support", navController)
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
