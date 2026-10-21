package com.hasnitech.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF070B14)
private val Panel = Color(0xFF111827)
private val Cyan = Color(0xFF35E0D0)
private val Muted = Color(0xFF8B9BB4)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HasniTechApp() }
    }
}

@Composable
fun HasniTechApp() {
    var selected by remember { mutableStateOf(0) }
    MaterialTheme(colorScheme = darkColorScheme(
        background = Background, surface = Panel, primary = Cyan,
        onBackground = Color.White, onSurface = Color.White
    )) {
        Scaffold(
            containerColor = Background,
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF0C1220), contentColor = Cyan) {
                    listOf(Icons.Rounded.Home to "الرئيسية",
                        Icons.Rounded.Apps to "برامجي",
                        Icons.Rounded.Settings to "الإعدادات").forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selected == index,
                            onClick = { selected = index },
                            icon = { Icon(item.first, contentDescription = item.second) },
                            label = { Text(item.second) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Cyan, selectedTextColor = Cyan,
                                indicatorColor = Color(0xFF18343B),
                                unselectedIconColor = Muted, unselectedTextColor = Muted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            Column(
                Modifier.fillMaxSize().padding(padding)
                    .background(Brush.verticalGradient(listOf(Color(0xFF101D31), Background)))
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(48.dp).background(Cyan.copy(alpha=.13f), RoundedCornerShape(15.dp)),
                        contentAlignment = Alignment.Center) {
                        Icon(Icons.Rounded.Memory, null, tint = Cyan, modifier = Modifier.size(29.dp))
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("HASNI", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Black)
                        Text("TECH  /  WINDOWS ENVIRONMENT", color = Cyan, fontSize = 9.sp, letterSpacing = 1.5.sp)
                    }
                    Icon(Icons.Rounded.NotificationsNone, null, tint = Muted, modifier = Modifier.size(26.dp))
                }
                Column(Modifier.fillMaxWidth().background(
                    Brush.linearGradient(listOf(Color(0xFF163D4A), Color(0xFF172039))),
                    RoundedCornerShape(24.dp)).padding(22.dp)) {
                    Text("بيئة تشغيلك الذكية", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("مركزك لإدارة تطبيقاتك وتجهيز بيئة Windows على Android.", color = Color(0xFFB7C8D8), fontSize = 14.sp)
                    Spacer(Modifier.height(20.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.PhoneAndroid, null, tint = Cyan)
                        Spacer(Modifier.width(8.dp))
                        Text("POCO X4 Pro 5G", color = Cyan, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.weight(1f))
                        Surface(color = Color(0xFF253B3D), shape = RoundedCornerShape(50)) {
                            Text("ANDROID", color = Cyan, fontSize = 10.sp, modifier = Modifier.padding(horizontal=10.dp, vertical=6.dp))
                        }
                    }
                }
                Text(if (selected == 1) "مكتبة البرامج" else if (selected == 2) "إعدادات النظام" else "لوحة التحكم",
                    color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatusCard(Modifier.weight(1f), Icons.Rounded.Apps, "البرامج", "0", "بانتظار الإضافة")
                    StatusCard(Modifier.weight(1f), Icons.Rounded.Memory, "المحرك", "غير مدمج", "Wine / Box64")
                }
                Column(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(20.dp)).padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.TipsAndUpdates, null, tint = Cyan)
                        Spacer(Modifier.width(10.dp))
                        Text("الخطوة التالية", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("هذه نسخة أولية قابلة للبناء. لم يتم دمج محرك Windows بعد، ولن تعمل ملفات EXE قبل إضافة المحرك وتجربته.", color = Muted, fontSize = 13.sp, lineHeight = 20.sp)
                }
                Spacer(Modifier.weight(1f))
                Text("HASNITECH • BUILD 1.0 • PREVIEW", color = Color(0xFF506078), fontSize = 10.sp, letterSpacing = 1.2.sp)
            }
        }
    }
}

@Composable
private fun StatusCard(modifier: Modifier, icon: androidx.compose.ui.graphics.vector.ImageVector,
                       title: String, value: String, detail: String) {
    Column(modifier.background(Panel, RoundedCornerShape(20.dp)).padding(16.dp)) {
        Icon(icon, null, tint = Cyan, modifier = Modifier.size(25.dp))
        Spacer(Modifier.height(15.dp))
        Text(title, color = Muted, fontSize = 12.sp)
        Spacer(Modifier.height(4.dp))
        Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = if (value.length > 5) 14.sp else 22.sp)
        Spacer(Modifier.height(5.dp))
        Text(detail, color = Muted, fontSize = 10.sp)
    }
}
