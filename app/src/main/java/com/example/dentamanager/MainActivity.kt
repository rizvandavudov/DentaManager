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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
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
            // 🔥 Yeni əlavə edilən hissə: "Today" və "Upcoming"
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Bugünkü randevular
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "0", fontSize = 24.sp, color = Color.Blue)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Today", fontSize = 18.sp, color = Color.Gray)
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = "Today Icon",
                            tint = Color(0xFF9C27B0),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    // Qarşıdan gələn randevular
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "0", fontSize = 24.sp, color = Color.Blue)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Upcoming", fontSize = 18.sp, color = Color.Gray)
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow),
                            contentDescription = "Upcoming Icon",
                            tint = Color(0xFF9C27B0),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))

// 🔥 Pasiyentlər bölməsi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_patients),
                        contentDescription = "Pasiyentlər Icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Pasiyentlər", fontSize = 24.sp, color = Color.Black)
                }

                IconButton(onClick = { /* TODO: Yeni Pasiyent əlavə etmək üçün funksiyanı yaz */ }) {
                    Icon(Icons.Filled.Add, contentDescription = "Yeni Pasiyent")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // Ümumi Pasiyent sayı
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "0", fontSize = 24.sp, color = Color.Blue)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Ümumi Pasiyent sayı", fontSize = 18.sp, color = Color.Gray)
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = "Ümumi Pasiyentlər Icon",
                            tint = Color(0xFF9C27B0),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    // Təkrar çağırılacaq Pasiyentlər
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "0", fontSize = 24.sp, color = Color.Blue)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Təkrar çağırılacaq", fontSize = 18.sp, color = Color.Gray)
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow),
                            contentDescription = "Təkrar çağırılacaq Icon",
                            tint = Color(0xFF9C27B0),
                            modifier = Modifier.size(24.dp)
                        )
                    }
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {
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
