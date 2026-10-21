package com.hasnitech.launcher

import android.os.Bundle
import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Void = Color(0xFF030611)
private val Navy = Color(0xFF08152A)
private val Ice = Color(0xFF66F4FF)
private val Blue = Color(0xFF278BFF)
private val Silver = Color(0xFFDCE8F7)
private val Dim = Color(0xFF8194B4)
private val Panel = Color(0xCC0B1830)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        window.statusBarColor = android.graphics.Color.rgb(3, 6, 17)
        window.navigationBarColor = android.graphics.Color.rgb(3, 6, 17)
        setContent { HasniTechDashboard() }
    }
}

@Composable
fun HasniTechApp() {
    var selected by remember { mutableIntStateOf(0) }
    val transition = rememberInfiniteTransition(label = "cosmic")
    val pulse by transition.animateFloat(
        initialValue = 0.55f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1800), RepeatMode.Reverse),
        label = "pulse"
    )
    MaterialTheme(colorScheme = darkColorScheme(
        background = Void, surface = Navy, primary = Ice,
        onBackground = Silver, onSurface = Silver
    )) {
        Scaffold(
            containerColor = Void,
            bottomBar = {
                NavigationBar(
                    containerColor = Color(0xFF071020),
                    tonalElevation = 0.dp,
                    modifier = Modifier.border(1.dp, Color(0xFF193557))
                ) {
                    val items = listOf(
                        Triple(Icons.Rounded.Home, "الرئيسية", 0),
                        Triple(Icons.Rounded.Apps, "مكتبة البرامج", 1),
                        Triple(Icons.Rounded.Tune, "النظام", 2)
                    )
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = selected == item.third,
                            onClick = { selected = item.third },
                            icon = { Icon(item.first, null, modifier = Modifier.size(22.dp)) },
                            label = { Text(item.second, fontSize = 10.sp, maxLines = 1) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Ice, selectedTextColor = Ice,
                                indicatorColor = Color(0xFF153354),
                                unselectedIconColor = Dim, unselectedTextColor = Dim
                            )
                        )
                    }
                }
            }
        ) { inset ->
            Box(Modifier.fillMaxSize().padding(inset).background(
                Brush.verticalGradient(listOf(Color(0xFF0B1C39), Void, Color(0xFF050916)))
            )) {
                Canvas(Modifier.fillMaxSize().alpha(0.45f)) {
                    val stars = listOf(
                        Offset(size.width*.08f,size.height*.10f), Offset(size.width*.88f,size.height*.08f),
                        Offset(size.width*.72f,size.height*.23f), Offset(size.width*.13f,size.height*.38f),
                        Offset(size.width*.91f,size.height*.48f), Offset(size.width*.22f,size.height*.66f),
                        Offset(size.width*.80f,size.height*.78f), Offset(size.width*.09f,size.height*.88f)
                    )
                    stars.forEachIndexed { i, p ->
                        drawCircle(Ice.copy(alpha = if (i % 2 == 0) pulse else .35f), radius = if(i%3==0) 2.2f else 1.2f, center = p)
                    }
                    drawCircle(Brush.radialGradient(listOf(Blue.copy(alpha=.13f), Color.Transparent), center=Offset(size.width*.82f,size.height*.20f), radius=size.width*.52f),
                        radius=size.width*.52f, center=Offset(size.width*.82f,size.height*.20f))
                }
                Column(
                    Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal=18.dp, vertical=16.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        DragonMark(pulse)
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text("HASNI", fontSize=27.sp, fontWeight=FontWeight.Black,
                                color=Silver, letterSpacing=3.sp, lineHeight=28.sp)
                            Text("T E C H   /   N E X T   G E N", fontSize=9.sp, color=Ice, letterSpacing=1.1.sp)
                        }
                        Box(Modifier.size(42.dp).clip(CircleShape).background(Color(0xFF0C1D36))
                            .border(1.dp, Color(0xFF244D78), CircleShape), contentAlignment=Alignment.Center) {
                            Icon(Icons.Rounded.NotificationsNone, null, tint=Silver, modifier=Modifier.size(21.dp))
                        }
                    }
                    HeroPanel(pulse)
                    Row(verticalAlignment=Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(when(selected) { 1 -> "مكتبة البرامج"; 2 -> "مركز النظام"; else -> "مركز القيادة" },
                                color=Silver, fontSize=20.sp, fontWeight=FontWeight.Bold)
                            Text("CONTROL  /  OVERVIEW", color=Dim, fontSize=9.sp, letterSpacing=1.6.sp)
                        }
                        Surface(color=Color(0xFF102B37), shape=RoundedCornerShape(50),
                            border=androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF236B78))) {
                            Row(Modifier.padding(horizontal=10.dp, vertical=6.dp), verticalAlignment=Alignment.CenterVertically) {
                                Box(Modifier.size(6.dp).background(Ice, CircleShape))
                                Spacer(Modifier.width(6.dp))
                                Text("SYSTEM READY", color=Ice, fontSize=9.sp, fontWeight=FontWeight.Bold)
                            }
                        }
                    }
                    Row(horizontalArrangement=Arrangement.spacedBy(11.dp)) {
                        MetricPanel(Modifier.weight(1f), Icons.Rounded.Apps, "التطبيقات", "00", "مكتبة البرامج")
                        MetricPanel(Modifier.weight(1f), Icons.Rounded.Memory, "محرك Windows", "قيد التجهيز", "Wine  /  Box64")
                    }
                    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp))
                        .background(Panel).border(1.dp, Color(0xFF1B3A60), RoundedCornerShape(20.dp)).padding(16.dp)) {
                        Row(verticalAlignment=Alignment.CenterVertically) {
                            Box(Modifier.size(36.dp).clip(RoundedCornerShape(11.dp))
                                .background(Brush.linearGradient(listOf(Blue.copy(alpha=.35f), Ice.copy(alpha=.12f)))),
                                contentAlignment=Alignment.Center) {
                                Icon(Icons.Rounded.Memory, null, tint=Ice)
                            }
                            Spacer(Modifier.width(10.dp))
                            Column(Modifier.weight(1f)) {
                                Text("بيئة التشغيل", color=Silver, fontWeight=FontWeight.Bold, fontSize=16.sp)
                                Text("WINDOWS COMPATIBILITY LAYER", color=Dim, fontSize=8.sp, letterSpacing=1.sp)
                            }
                            Icon(Icons.Rounded.ChevronRight, null, tint=Ice)
                        }
                        Spacer(Modifier.height(14.dp))
                        Text("الأساس جاهز. محرك تشغيل ملفات EXE لم يُدمج بعد.", color=Silver, fontSize=13.sp, lineHeight=20.sp)
                        Spacer(Modifier.height(12.dp))
                        Row(verticalAlignment=Alignment.CenterVertically) {
                            Icon(Icons.Rounded.PhoneAndroid, null, tint=Ice, modifier=Modifier.size(17.dp))
                            Spacer(Modifier.width(7.dp))
                            Text("POCO X4 PRO 5G", color=Ice, fontSize=10.sp, fontWeight=FontWeight.Bold, letterSpacing=1.sp)
                            Spacer(Modifier.weight(1f))
                            Text("ANDROID 13", color=Dim, fontSize=9.sp)
                        }
                    }
                    Text("QUICK ACCESS", color=Dim, fontSize=10.sp, letterSpacing=2.sp, fontWeight=FontWeight.Bold)
                    Row(horizontalArrangement=Arrangement.spacedBy(10.dp)) {
                        QuickTile(Modifier.weight(1f), Icons.Rounded.FolderOpen, "ملفاتي", "FILE MANAGER")
                        QuickTile(Modifier.weight(1f), Icons.Rounded.Terminal, "طرفية النظام", "SYSTEM TOOLS")
                        QuickTile(Modifier.weight(1f), Icons.Rounded.Settings, "الإعدادات", "PREFERENCES")
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.Center, verticalAlignment=Alignment.CenterVertically) {
                        Box(Modifier.width(22.dp).height(1.dp).background(Blue))
                        Spacer(Modifier.width(8.dp))
                        Text("HASNITECH  •  COSMIC DESKTOP", color=Dim, fontSize=8.sp, letterSpacing=1.5.sp)
                        Spacer(Modifier.width(8.dp))
                        Box(Modifier.width(22.dp).height(1.dp).background(Blue))
                    }
                }
            }
        }
    }
}


@Composable
private fun HasniTechDashboard() {
    val context = LocalContext.current
    var page by remember { mutableIntStateOf(0) }
    var showWindowsInfo by remember { mutableStateOf(false) }
    val pulseTransition = rememberInfiniteTransition(label = "dashboardPulse")
    val pulse by pulseTransition.animateFloat(0.65f, 1f, infiniteRepeatable(tween(1400), RepeatMode.Reverse), label = "dashboardGlow")
    val cyan = Color(0xFF38EFFF)
    val blue = Color(0xFF126CFF)
    val panel = Color(0xE9081022)
    MaterialTheme(colorScheme = darkColorScheme(background = Void, surface = Navy, primary = cyan, onSurface = Silver)) {
        Box(Modifier.fillMaxSize().background(Brush.linearGradient(listOf(Color(0xFF020510), Color(0xFF071B3A), Color(0xFF08051B))))) {
            Canvas(Modifier.fillMaxSize()) {
                for (i in 0..90) {
                    val x = size.width * (((i * 73) % 997) / 997f)
                    val y = size.height * (((i * 137) % 991) / 991f)
                    drawCircle(cyan.copy(alpha = if (i % 5 == 0) .7f * pulse else .2f), if (i % 7 == 0) 1.8f else .8f, Offset(x,y))
                }
                drawCircle(Brush.radialGradient(listOf(blue.copy(alpha=.19f), Color.Transparent), Offset(size.width*.53f,size.height*.52f), size.height*.8f), size.height*.8f, Offset(size.width*.53f,size.height*.52f))
            }
            Row(Modifier.fillMaxSize().padding(10.dp), horizontalArrangement=Arrangement.spacedBy(9.dp)) {
                // Fixed left navigation rail, matching the reference dashboard.
                Column(Modifier.fillMaxWidth(.19f).fillMaxHeight().clip(RoundedCornerShape(16.dp))
                    .background(panel).border(1.dp, cyan.copy(alpha=.45f), RoundedCornerShape(16.dp)).padding(8.dp),
                    verticalArrangement=Arrangement.spacedBy(5.dp)) {
                    Row(verticalAlignment=Alignment.CenterVertically, modifier=Modifier.padding(vertical=8.dp, horizontal=3.dp)) {
                        DragonMark(pulse)
                        Spacer(Modifier.width(6.dp))
                        Column {
                            Text("HASNITECH", color=cyan, fontWeight=FontWeight.Black, fontSize=13.sp, maxLines=1)
                            Text("WINDOWS IN POCKET", color=Silver, fontSize=6.sp, maxLines=1)
                        }
                    }
                    HorizontalDivider(color=cyan.copy(alpha=.3f))
                    listOf(Triple(Icons.Rounded.Home,"الرئيسية",0),Triple(Icons.Rounded.Window,"تشغيل Windows",1),Triple(Icons.Rounded.Apps,"التطبيقات",2),Triple(Icons.Rounded.Folder,"الملفات",3),Triple(Icons.Rounded.Settings,"الإعدادات",4),Triple(Icons.Rounded.Build,"الأدوات",5),Triple(Icons.Rounded.HeadsetMic,"الدعم",6)).forEach { item ->
                        Surface(onClick={page=item.third}, color=if(page==item.third) Color(0xFF103A67) else Color.Transparent,
                            shape=RoundedCornerShape(9.dp), border=androidx.compose.foundation.BorderStroke(1.dp,if(page==item.third) cyan.copy(alpha=.8f) else Color.Transparent),
                            modifier=Modifier.fillMaxWidth().weight(1f)) {
                            Row(Modifier.fillMaxWidth().padding(horizontal=8.dp), verticalAlignment=Alignment.CenterVertically) {
                                Icon(item.first,null,tint=if(page==item.third) cyan else Color(0xFF77A8E8), modifier=Modifier.size(19.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(item.second,color=Silver,fontSize=10.sp,maxLines=1,softWrap=false,overflow=TextOverflow.Clip, modifier=Modifier.weight(1f))
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Surface(color=Color(0xFF101B40), shape=RoundedCornerShape(9.dp), modifier=Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(8.dp)) {
                            Icon(Icons.Rounded.SportsEsports,null,tint=Color(0xFF9B7BFF),modifier=Modifier.size(22.dp))
                            Text("وضع الألعاب",color=Silver,fontSize=10.sp,maxLines=1,softWrap=false,overflow=TextOverflow.Clip)
                            Text("OFF",color=cyan,fontSize=9.sp)
                        }
                    }
                }
                // Main holographic stage.
                Column(Modifier.weight(1.35f).fillMaxHeight(), verticalArrangement=Arrangement.spacedBy(8.dp)) {
                    Row(Modifier.fillMaxWidth().height(48.dp), verticalAlignment=Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("HASNITECH  /  CONTROL CENTER", color=cyan, fontSize=11.sp, fontWeight=FontWeight.Bold, letterSpacing=1.2.sp)
                            Text(when(page){1->"WINDOWS ENVIRONMENT";2->"APPLICATION LIBRARY";3->"FILE CENTER";4->"SYSTEM SETTINGS";5->"SYSTEM TOOLS";6->"SUPPORT CENTER";else->"قوة التكنولوجيا بين يديك"}, color=Silver, fontSize=9.sp, maxLines=1, overflow=TextOverflow.Ellipsis)
                        }
                        Surface(color=Color(0xFF092E2B), shape=CircleShape, border=androidx.compose.foundation.BorderStroke(1.dp,Color(0xFF27D9C0))) {
                            Row(Modifier.padding(horizontal=9.dp,vertical=5.dp), verticalAlignment=Alignment.CenterVertically) {
                                Box(Modifier.size(6.dp).background(Color(0xFF37FFD1),CircleShape)); Spacer(Modifier.width(5.dp))
                                Text("SYSTEM ONLINE",color=Color(0xFF62FFDC),fontSize=8.sp)
                            }
                        }
                    }
                    Box(Modifier.weight(1f).fillMaxWidth().clip(RoundedCornerShape(16.dp))
                        .background(Brush.radialGradient(listOf(Color(0xFF102C66),Color(0xFF080F2A),Color(0xFF030713))))
                        .border(1.dp,cyan.copy(alpha=.6f*pulse),RoundedCornerShape(16.dp))) {
                        Canvas(Modifier.fillMaxSize()) {
                            val cx=size.width*.5f; val cy=size.height*.52f
                            drawCircle(Brush.radialGradient(listOf(cyan.copy(alpha=.12f*pulse),Color.Transparent),Offset(cx,cy),size.height*.65f),size.height*.65f,Offset(cx,cy))
                            for (ring in 1..5) drawOval((if(ring%2==0) cyan else blue).copy(alpha=.55f/ring),Offset(cx-size.width*(.11f+ring*.035f),cy+size.height*.25f),androidx.compose.ui.geometry.Size(size.width*(.22f+ring*.07f),size.height*(.025f+ring*.009f)),style=androidx.compose.ui.graphics.drawscope.Stroke(width=1.1f))
                        }
                        when (page) {
                            1 -> WindowsRuntimePanel(context, cyan, Silver, Dim, blue)
                            2 -> AppInstallPanel(context, cyan, Silver, Dim, blue)
                            3 -> SimpleDashboardPanel("مركز الملفات", "FILE EXPLORER", "افتح ملفات الهاتف أو اختر مُثبّت برنامج Windows لتجهيزه لاحقًا.", Icons.Rounded.FolderOpen, cyan, Silver, Dim)
                            4 -> SimpleDashboardPanel("إعدادات HASNITECH", "SYSTEM SETTINGS", "إعدادات واجهة التطبيق والجهاز. محرك Windows يعمل عبر بيئة توافق منفصلة في هذه المرحلة.", Icons.Rounded.Settings, cyan, Silver, Dim)
                            5 -> SimpleDashboardPanel("أدوات النظام", "SYSTEM TOOLS", "أدوات إدارة الملفات وتشغيل بيئة التوافق ستُضاف تدريجيًا.", Icons.Rounded.Build, cyan, Silver, Dim)
                            6 -> SimpleDashboardPanel("مركز الدعم", "SUPPORT CENTER", "HASNITECH قيد التطوير. أبلغ عن البرنامج الذي تريد تجربته أولًا.", Icons.Rounded.HeadsetMic, cyan, Silver, Dim)
                            else -> HologramFigure(pulse,Modifier.fillMaxSize().padding(horizontal=22.dp, vertical=4.dp))
                        }
                    }
                    Row(Modifier.fillMaxWidth().height(70.dp), horizontalArrangement=Arrangement.spacedBy(7.dp)) {
                        listOf(Triple(Icons.Rounded.Home,"الرئيسية",0),Triple(Icons.Rounded.Folder,"الملفات",3),Triple(Icons.Rounded.Apps,"التطبيقات",2),Triple(Icons.Rounded.Settings,"الإعدادات",4),Triple(Icons.Rounded.Build,"الأدوات",5)).forEach { item ->
                            Surface(onClick={page=item.third}, modifier=Modifier.weight(1f).fillMaxHeight(),
                                color=Color(0xD90A1730),shape=RoundedCornerShape(11.dp),
                                border=androidx.compose.foundation.BorderStroke(1.dp,cyan.copy(alpha=.25f))) {
                                Column(horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center) {
                                    Icon(item.first,null,tint=cyan,modifier=Modifier.size(20.dp))
                                    Spacer(Modifier.height(4.dp)); Text(item.second,color=Silver,fontSize=9.sp,maxLines=1,overflow=TextOverflow.Ellipsis)
                                }
                            }
                        }
                    }
                }
                // Right-side device status and launchable shortcuts.
                Column(Modifier.fillMaxWidth(.27f).fillMaxHeight(), verticalArrangement=Arrangement.spacedBy(8.dp)) {
                    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(panel).border(1.dp,cyan.copy(alpha=.4f),RoundedCornerShape(14.dp)).padding(11.dp)) {
                        Text("الجهاز والنظام",color=cyan,fontSize=12.sp,fontWeight=FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        listOf("POCO X4 Pro 5G","Snapdragon 695","RAM 8 GB","Android 13").forEach { Text("▣  $it",color=Silver,fontSize=9.sp,modifier=Modifier.padding(vertical=2.dp)) }
                        Spacer(Modifier.height(6.dp)); HorizontalDivider(color=cyan.copy(alpha=.25f)); Spacer(Modifier.height(6.dp))
                        Row(verticalAlignment=Alignment.CenterVertically) {
                            Icon(Icons.Rounded.BatteryChargingFull,null,tint=Color(0xFF3FFFC8),modifier=Modifier.size(20.dp))
                            Text("  جهاز محمول",color=Color(0xFF3FFFC8),fontSize=10.sp)
                        }
                    }
                    Column(Modifier.weight(1f).fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(panel).border(1.dp,cyan.copy(alpha=.35f),RoundedCornerShape(14.dp)).padding(9.dp)) {
                        Text(if(page==2) "مكتبة التطبيقات" else "التطبيقات السريعة",color=cyan,fontSize=12.sp,fontWeight=FontWeight.Bold)
                        Spacer(Modifier.height(7.dp))
                        val apps=listOf(Triple(Icons.Rounded.Language,"Chrome","Browser"),Triple(Icons.Rounded.PlayArrow,"YouTube","Media"),Triple(Icons.Rounded.Chat,"WhatsApp","Messages"),Triple(Icons.Rounded.Gamepad,"Games","Gaming"),Triple(Icons.Rounded.Description,"Office","Documents"),Triple(Icons.Rounded.Photo,"Photos","Gallery"))
                        apps.forEach { app ->
                            Surface(onClick={ try { when(app.second) { "Chrome" -> context.startActivity(android.content.Intent(android.content.Intent.ACTION_MAIN).addCategory(android.content.Intent.CATEGORY_APP_BROWSER)); "YouTube" -> context.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW,android.net.Uri.parse("https://www.youtube.com"))); "WhatsApp" -> context.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW,android.net.Uri.parse("https://wa.me/"))); "Photos" -> context.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)); "Office" -> context.startActivity(android.content.Intent(android.content.Intent.ACTION_OPEN_DOCUMENT).setType("application/pdf").addCategory(android.content.Intent.CATEGORY_OPENABLE)); else -> { page=1; showWindowsInfo=true } } } catch (_:Exception) { showWindowsInfo=true } },
                                color=Color(0xFF0B1B36),shape=RoundedCornerShape(9.dp),border=androidx.compose.foundation.BorderStroke(1.dp,Color(0xFF173D6B)),
                                modifier=Modifier.fillMaxWidth().weight(1f).padding(vertical=2.dp)) {
                                Row(Modifier.padding(horizontal=8.dp),verticalAlignment=Alignment.CenterVertically) {
                                    Icon(app.first,null,tint=if(app.second=="Games") Color(0xFFAA83FF) else cyan,modifier=Modifier.size(21.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Column(Modifier.weight(1f), verticalArrangement=Arrangement.Center) {
                                        Text(app.second,color=Silver,fontSize=10.sp,fontWeight=FontWeight.Bold,maxLines=1,softWrap=false,overflow=TextOverflow.Clip)
                                        Text(app.third,color=Dim,fontSize=7.sp,maxLines=1,softWrap=false,overflow=TextOverflow.Clip)
                                    }
                                    Icon(Icons.Rounded.ChevronRight,null,tint=Dim,modifier=Modifier.size(15.dp))
                                }
                            }
                        }
                    }
                    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(13.dp)).background(panel).border(1.dp,Color(0xFF4D3D99),RoundedCornerShape(13.dp)).padding(9.dp)) {
                        Row(verticalAlignment=Alignment.CenterVertically) {
                            Icon(Icons.Rounded.Memory,null,tint=Color(0xFFB19AFF),modifier=Modifier.size(22.dp))
                            Spacer(Modifier.width(7.dp))
                            Column(Modifier.weight(1f)) { Text("مساعد HASNI AI",color=Silver,fontSize=10.sp,fontWeight=FontWeight.Bold,maxLines=1,overflow=TextOverflow.Ellipsis); Text("محرك Windows غير مدمج بعد",color=Dim,fontSize=7.sp,maxLines=1,overflow=TextOverflow.Ellipsis) }
                        }
                        if(showWindowsInfo) { Spacer(Modifier.height(6.dp)); Text("هذه واجهة اختصارات. يلزم ربط كل تطبيق بمحركه أو تطبيق مثبت لتشغيله فعليًا.",color=Silver,fontSize=8.sp) }
                    }
                }
            }
        }
    }
}


@Composable
private fun WindowsRuntimePanel(context: android.content.Context, cyan: Color, silver: Color, dim: Color, blue: Color) {
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Rounded.Window, null, tint = cyan, modifier = Modifier.size(48.dp))
        Spacer(Modifier.height(10.dp))
        Text("WINDOWS RUNTIME", color = cyan, fontSize = 17.sp, fontWeight = FontWeight.Black, letterSpacing = 1.5.sp)
        Text("Wine  •  Box64  •  ARM64", color = silver, fontSize = 11.sp)
        Spacer(Modifier.height(10.dp))
        Text("محرك Windows لم يُدمج داخل HASNITECH بعد. في هذه المرحلة يمكنك فتح Winlator إذا كان مثبتًا، وهو محرك توافق خارجي لتشغيل بعض برامج Windows.", color = silver, fontSize = 10.sp, lineHeight = 16.sp)
        Spacer(Modifier.height(14.dp))
        Button(onClick = {
            val intent = context.packageManager.getLaunchIntentForPackage("com.winlator")
            try {
                if (intent != null) context.startActivity(intent)
                else context.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://github.com/brunodev85/winlator/releases")))
            } catch (_: Exception) { }
        }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF123A66), contentColor = cyan)) {
            Icon(Icons.Rounded.PlayArrow, null); Spacer(Modifier.width(6.dp)); Text("فتح Winlator / تنزيله", fontSize = 11.sp)
        }
        Spacer(Modifier.height(6.dp))
        Text("تشغيل Office غير مضمون؛ يعتمد على إصدار البرنامج والتوافق والترخيص.", color = dim, fontSize = 9.sp)
    }
}

@Composable
private fun AppInstallPanel(context: android.content.Context, cyan: Color, silver: Color, dim: Color, blue: Color) {
    val apkPicker = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            try {
                val installIntent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/vnd.android.package-archive")
                    addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                context.startActivity(installIntent)
            } catch (_: Exception) {
                try { context.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, uri)) } catch (_: Exception) { }
            }
        }
    }
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Rounded.Apps, null, tint = cyan, modifier = Modifier.size(44.dp))
        Spacer(Modifier.height(8.dp))
        Text("APPLICATION CENTER", color = cyan, fontSize = 16.sp, fontWeight = FontWeight.Black)
        Text("تثبيت تطبيقات Android بصيغة APK", color = silver, fontSize = 11.sp)
        Spacer(Modifier.height(12.dp))
        Button(onClick = { apkPicker.launch(arrayOf("application/vnd.android.package-archive", "application/octet-stream")) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF123A66), contentColor = cyan)) {
            Icon(Icons.Rounded.FileOpen, null); Spacer(Modifier.width(6.dp)); Text("اختيار ملف APK", fontSize = 11.sp)
        }
        Spacer(Modifier.height(8.dp))
        Text("هذا الزر يفتح مُثبّت Android لتطبيقات APK فقط. ملفات EXE تحتاج محرك Windows مثل Wine/Box64.", color = dim, fontSize = 9.sp, lineHeight = 14.sp)
    }
}

@Composable
private fun SimpleDashboardPanel(title: String, subtitle: String, description: String, icon: androidx.compose.ui.graphics.vector.ImageVector, cyan: Color, silver: Color, dim: Color) {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, null, tint = cyan, modifier = Modifier.size(42.dp))
        Spacer(Modifier.height(10.dp))
        Text(title, color = cyan, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = dim, fontSize = 9.sp, letterSpacing = 1.4.sp)
        Spacer(Modifier.height(12.dp))
        Text(description, color = silver, fontSize = 11.sp, lineHeight = 17.sp)
    }
}

@Composable
private fun DragonMark(pulse: Float) {
    Box(Modifier.size(58.dp).shadow(14.dp, RoundedCornerShape(17.dp))
        .clip(RoundedCornerShape(17.dp))
        .background(Brush.linearGradient(listOf(Color(0xFF1B5791), Color(0xFF071326), Color(0xFF142D50))))
        .border(1.dp, Ice.copy(alpha=.65f*pulse), RoundedCornerShape(17.dp)),
        contentAlignment=Alignment.Center) {
        Canvas(Modifier.fillMaxSize().padding(8.dp)) {
            val w=size.width; val h=size.height
            val path = androidx.compose.ui.graphics.Path().apply {
                moveTo(w*.50f,h*.04f)
                lineTo(w*.82f,h*.19f); lineTo(w*.72f,h*.40f)
                lineTo(w*.96f,h*.34f); lineTo(w*.77f,h*.61f)
                lineTo(w*.68f,h*.88f); lineTo(w*.50f,h*.74f)
                lineTo(w*.31f,h*.91f); lineTo(w*.26f,h*.62f)
                lineTo(w*.04f,h*.40f); lineTo(w*.31f,h*.45f)
                lineTo(w*.18f,h*.18f); lineTo(w*.50f,h*.04f)
                close()
            }
            drawPath(path, Brush.linearGradient(listOf(Silver, Ice, Blue)))
            drawLine(Color(0xFF061020), Offset(w*.35f,h*.37f), Offset(w*.64f,h*.30f), strokeWidth=2.3f, cap=StrokeCap.Round)
            drawLine(Color(0xFF061020), Offset(w*.38f,h*.47f), Offset(w*.62f,h*.45f), strokeWidth=1.8f, cap=StrokeCap.Round)
        }
    }
}

@Composable
private fun HeroPanel(pulse: Float) {
    Row(
        Modifier.fillMaxWidth().height(250.dp)
            .shadow(22.dp, RoundedCornerShape(26.dp))
            .clip(RoundedCornerShape(26.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF071326), Color(0xFF101B3D), Color(0xFF071A2A))))
            .border(1.dp, Ice.copy(alpha = .72f * pulse), RoundedCornerShape(26.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1.05f).fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.Bolt, null, tint = Ice, modifier = Modifier.size(17.dp))
                Spacer(Modifier.width(6.dp))
                Text("HASNI AI  /  LIVE HOLOGRAM", color = Ice, fontSize = 10.sp, letterSpacing = 1.4.sp)
            }
            Spacer(Modifier.height(12.dp))
            Text("بوابتك إلى المستقبل", color = Silver, fontSize = 27.sp, fontWeight = FontWeight.Black, lineHeight = 33.sp)
            Spacer(Modifier.height(8.dp))
            Text("واجهة هولوغرافية تفاعلية داخل HASNITECH", color = Color(0xFFB6CBE6), fontSize = 12.sp)
            Spacer(Modifier.height(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.width(36.dp).height(3.dp).clip(CircleShape).background(Brush.horizontalGradient(listOf(Ice, Blue))))
                Spacer(Modifier.width(8.dp))
                Text("PROJECTION SIMULATION  •  ONLINE", color = Dim, fontSize = 8.sp, letterSpacing = 1.sp)
            }
        }
        Box(Modifier.weight(.95f).fillMaxHeight(), contentAlignment = Alignment.Center) {
            HologramFigure(pulse, Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun HologramFigure(pulse: Float, modifier: Modifier = Modifier) {
    val phase by rememberInfiniteTransition(label = "quantumHologram").animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(11000), RepeatMode.Restart), label = "quantumRotation"
    )
    val scan by rememberInfiniteTransition(label = "quantumScan").animateFloat(
        initialValue = .08f, targetValue = .92f,
        animationSpec = infiniteRepeatable(tween(2300), RepeatMode.Reverse), label = "quantumBeam"
    )
    val breathe by rememberInfiniteTransition(label = "quantumPulse").animateFloat(
        initialValue = .72f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1300), RepeatMode.Reverse), label = "quantumBreath"
    )
    Canvas(modifier) {
        val w=size.width; val h=size.height
        val cx=w*.5f; val cy=h*.46f
        val cyan=Color(0xFFFF2B4F); val ice=Color(0xFFFFE7EC)
        val blue=Color(0xFF78122B); val violet=Color(0xFFFF1744)
        val pink=Color(0xFFFF001E)
        val sw=(w*.0021f).coerceIn(.8f,1.8f)
        val radius=h*.285f
        // Entirely non-humanoid: a volumetric quantum hologram suspended in a projection chamber.
        drawCircle(Brush.radialGradient(listOf(cyan.copy(alpha=.17f*pulse),blue.copy(alpha=.10f),Color.Transparent),Offset(cx,cy),h*.57f),h*.57f,Offset(cx,cy))
        // Predatory containment field: expanding shock rings around the core.
        for(i in 0..3) {
            val rr=h*(.30f+i*.075f)
            val wobble=1f+0.025f*kotlin.math.sin(Math.toRadians((phase*2f+i*90f).toDouble())).toFloat()
            drawOval((if(i%2==0) pink else violet).copy(alpha=(.25f+i*.035f)*pulse),
                Offset(cx-rr*wobble,cy-rr*.58f),androidx.compose.ui.geometry.Size(rr*2*wobble,rr*1.16f),
                style=androidx.compose.ui.graphics.drawscope.Stroke(width=sw*(1.1f+i*.25f)))
            drawArc(ice.copy(alpha=.88f*pulse),phase*(if(i%2==0) 1.4f else -1.1f)+i*71f,
                54f,false,Offset(cx-rr*wobble,cy-rr*.58f),
                androidx.compose.ui.geometry.Size(rr*2*wobble,rr*1.16f),
                style=androidx.compose.ui.graphics.drawscope.Stroke(width=sw*2.4f,cap=StrokeCap.Round))
        }
        // Broad tilted orbital rails, split into animated luminous arcs.
        for(i in 0..5) {
            val rx=w*(.19f+i*.032f)
            val ry=h*(.13f+i*.022f)
            val rotation=phase*(if(i%2==0) 1f else -.72f)+i*37f
            drawOval((if(i%3==0) violet else cyan).copy(alpha=.34f-i*.025f),
                Offset(cx-rx,cy-ry),androidx.compose.ui.geometry.Size(rx*2,ry*2),
                style=androidx.compose.ui.graphics.drawscope.Stroke(width=sw))
            drawArc(if(i%2==0) ice.copy(alpha=.92f*pulse) else blue.copy(alpha=.95f),
                rotation+i*21f,42f,false,Offset(cx-rx,cy-ry),
                androidx.compose.ui.geometry.Size(rx*2,ry*2),
                style=androidx.compose.ui.graphics.drawscope.Stroke(width=sw*2.1f,cap=StrokeCap.Round))
            val a=Math.toRadians((rotation+i*21f+42f).toDouble())
            drawCircle(if(i%2==0) ice else pink,w*.0048f,
                Offset(cx+(kotlin.math.cos(a)*rx).toFloat(),cy+(kotlin.math.sin(a)*ry).toFloat()))
        }
        // Floating spherical wireframe: longitudes rotate, latitude bands breathe.
        for(i in -3..3) {
            val bandY=i*.25f
            val bandRadius=radius*kotlin.math.sqrt((1f-bandY*bandY).coerceAtLeast(.05f))
            drawOval(cyan.copy(alpha=.46f* (1f-kotlin.math.abs(i)*.11f)),
                Offset(cx-bandRadius,cy+bandY*radius*.88f-bandRadius*.13f),
                androidx.compose.ui.geometry.Size(bandRadius*2,bandRadius*.26f),
                style=androidx.compose.ui.graphics.drawscope.Stroke(width=sw))
        }
        for(i in 0..7) {
            val angle=Math.toRadians((phase+i*22.5f).toDouble())
            val rx=radius*kotlin.math.abs(kotlin.math.cos(angle)).toFloat()
            val ry=radius*.94f
            drawOval((if(i%2==0) cyan else violet).copy(alpha=.52f),
                Offset(cx-rx,cy-ry),androidx.compose.ui.geometry.Size(rx*2,ry*2),
                style=androidx.compose.ui.graphics.drawscope.Stroke(width=sw))
        }
        // Interlocking hexagonal containment cages.
        for(i in 0..2) {
            val rr=radius*(1.08f+i*.23f)
            val cage=androidx.compose.ui.graphics.Path()
            for(k in 0..6) {
                val a=Math.toRadians((phase*(if(i==1) -.6f else .45f)+k*60f+i*18f).toDouble())
                val px=cx+(kotlin.math.cos(a)*rr).toFloat()
                val py=cy+(kotlin.math.sin(a)*rr*.72f).toFloat()
                if(k==0) cage.moveTo(px,py) else cage.lineTo(px,py)
            }
            drawPath(cage,(if(i==1) violet else blue).copy(alpha=.63f),style=androidx.compose.ui.graphics.drawscope.Stroke(width=sw*1.25f))
        }
        // Jagged radial energy blades make the projection feel powerful and dangerous.
        for(i in 0..35) {
            val a=Math.toRadians((phase*(if(i%2==0) 1.15f else -.72f)+i*10f).toDouble())
            val inner=radius*(1.20f+(i%3)*.04f)
            val outer=radius*(1.52f+(i%4)*.13f)
            val p1=Offset(cx+(kotlin.math.cos(a)*inner).toFloat(),cy+(kotlin.math.sin(a)*inner*.78f).toFloat())
            val p2=Offset(cx+(kotlin.math.cos(a)*outer).toFloat(),cy+(kotlin.math.sin(a)*outer*.78f).toFloat())
            drawLine(if(i%4==0) pink.copy(alpha=.92f*pulse) else cyan.copy(alpha=.72f),
                p1,p2,sw*(if(i%4==0) 2.1f else 1.2f))
            if(i%3==0) drawCircle(ice.copy(alpha=.9f),w*.0038f,p2)
        }
        // Dense inner plasma nucleus and faceted energy lattice.
        drawCircle(Brush.radialGradient(listOf(Color(0xFF020207),Color(0xFF18000A),pink.copy(alpha=.78f*breathe),Color(0xFF5C001A).copy(alpha=.6f),Color.Transparent),Offset(cx,cy),radius*.82f),radius*.82f,Offset(cx,cy))
        // Black event horizon with a violently pulsing crimson corona.
        drawCircle(Color(0xFF020106),radius*.30f,Offset(cx,cy))
        drawCircle(Brush.radialGradient(listOf(Color(0xFF020106),Color(0xFF10000A),Color.Transparent),Offset(cx,cy),radius*.38f),radius*.38f,Offset(cx,cy))
        for(i in 0..47) {
            val a=Math.toRadians((phase*1.35f+i*7.5f).toDouble())
            val r1=radius*(.34f+(i%4)*.025f)
            val r2=radius*(.48f+(i%5)*.045f)
            val p1=Offset(cx+(kotlin.math.cos(a)*r1).toFloat(),cy+(kotlin.math.sin(a)*r1*.86f).toFloat())
            val p2=Offset(cx+(kotlin.math.cos(a+.045)*r2).toFloat(),cy+(kotlin.math.sin(a+.045)*r2*.86f).toFloat())
            drawLine(if(i%4==0) ice.copy(alpha=.95f*breathe) else pink.copy(alpha=.88f),p1,p2,sw*(if(i%4==0) 2f else 1.1f))
        }
        for(i in 0..11) {
            val a=Math.toRadians((phase*1.5f+i*30f).toDouble())
            val inner=radius*.20f
            val outer=radius*(.46f+if(i%2==0) .06f else 0f)
            val p1=Offset(cx+(kotlin.math.cos(a)*inner).toFloat(),cy+(kotlin.math.sin(a)*inner).toFloat())
            val p2=Offset(cx+(kotlin.math.cos(a)*outer).toFloat(),cy+(kotlin.math.sin(a)*outer).toFloat())
            drawLine(if(i%3==0) pink.copy(alpha=.88f) else ice.copy(alpha=.75f),p1,p2,sw*1.3f)
            drawCircle(cyan.copy(alpha=.9f),w*.0032f,p2)
        }
        drawCircle(ice.copy(alpha=.95f*pulse),radius*.16f,Offset(cx,cy))
        drawCircle(cyan.copy(alpha=.85f),radius*.27f,Offset(cx,cy),style=androidx.compose.ui.graphics.drawscope.Stroke(width=sw*1.8f))
        // Projection shafts and layered levitation platform.
        drawLine(cyan.copy(alpha=.26f),Offset(cx,cy+radius*.95f),Offset(cx,h*.78f),sw)
        for(i in 0..6) {
            val yy=h*(.765f+i*.022f)
            drawOval((if(i%2==0) cyan else violet).copy(alpha=.65f-i*.065f),
                Offset(cx-w*(.16f+i*.027f),yy),androidx.compose.ui.geometry.Size(w*(.32f+i*.054f),h*(.012f+i*.002f)),
                style=androidx.compose.ui.graphics.drawscope.Stroke(width=sw))
        }
        // Threat-level energy discharge: sharp moving lightning-like vectors.
        for(i in 0..8) {
            val a=Math.toRadians((phase*1.7f+i*40f).toDouble())
            val r1=radius*1.48f
            val r2=radius*(1.72f+(i%3)*.12f)
            val x1=cx+(kotlin.math.cos(a)*r1).toFloat()
            val y1=cy+(kotlin.math.sin(a)*r1*.78f).toFloat()
            val xm=cx+(kotlin.math.cos(a+.12)*((r1+r2)*.5f)).toFloat()
            val ym=cy+(kotlin.math.sin(a+.12)*((r1+r2)*.5f)*.78f).toFloat()
            val x2=cx+(kotlin.math.cos(a)*r2).toFloat()
            val y2=cy+(kotlin.math.sin(a)*r2*.78f).toFloat()
            drawLine(violet.copy(alpha=.72f*pulse),Offset(x1,y1),Offset(xm,ym),sw*1.4f)
            drawLine(ice.copy(alpha=.9f*pulse),Offset(xm,ym),Offset(x2,y2),sw*1.1f)
        }
        // Scanning light plane and fine volumetric particles.
        val beamY=h*scan
        drawLine(ice.copy(alpha=.8f*pulse),Offset(w*.055f,beamY),Offset(w*.945f,beamY),sw*1.2f)
        drawCircle(ice,w*.004f,Offset(w*.055f,beamY)); drawCircle(ice,w*.004f,Offset(w*.945f,beamY))
        for(i in 0..43) {
            val px=w*(.06f+((i*47)%89)/100f)
            val py=h*(.10f+((i*31)%79)/100f)
            val a=if(i%6==0) .9f*pulse else .42f
            drawCircle(if(i%9==0) pink.copy(alpha=a) else cyan.copy(alpha=a),if(i%6==0) 1.8f else 1f,Offset(px,py))
            if(i%8==0) {
                drawLine(violet.copy(alpha=.58f),Offset(px-5f,py+5f),Offset(px+7f,py-7f),sw)
                drawLine(cyan.copy(alpha=.4f),Offset(px+7f,py-7f),Offset(px+14f,py-7f),sw)
            }
        }
        // Side telemetry ticks frame the object without obscuring the hologram.
        for(i in 0..5) {
            val yy=h*(.24f+i*.088f)
            val len=w*(.025f+(i%3)*.012f)
            drawLine(blue.copy(alpha=.8f),Offset(w*.035f,yy),Offset(w*.035f+len,yy),sw)
            drawLine(violet.copy(alpha=.8f),Offset(w*.965f,yy),Offset(w*.965f-len,yy),sw)
        }
    }
}

@Composable
private fun MetricPanel(modifier: Modifier, icon: androidx.compose.ui.graphics.vector.ImageVector,
                        title: String, value: String, detail: String) {
    Column(modifier.clip(RoundedCornerShape(19.dp)).background(Panel)
        .border(1.dp, Color(0xFF1A3558), RoundedCornerShape(19.dp)).padding(14.dp)) {
        Row(verticalAlignment=Alignment.CenterVertically) {
            Icon(icon, null, tint=Ice, modifier=Modifier.size(20.dp))
            Spacer(Modifier.width(6.dp))
            Text(title, color=Dim, fontSize=10.sp, maxLines=1, overflow=TextOverflow.Ellipsis)
        }
        Spacer(Modifier.height(14.dp))
        Text(value, color=Silver, fontSize=if(value.length>6) 13.sp else 24.sp, fontWeight=FontWeight.ExtraBold, maxLines=1)
        Spacer(Modifier.height(5.dp))
        Text(detail, color=Dim, fontSize=9.sp)
    }
}

@Composable
private fun QuickTile(modifier: Modifier, icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String) {
    Column(modifier.clip(RoundedCornerShape(16.dp)).background(Color(0xB80B1830))
        .border(1.dp, Color(0xFF1C3555), RoundedCornerShape(16.dp)).padding(vertical=14.dp, horizontal=8.dp),
        horizontalAlignment=Alignment.CenterHorizontally) {
        Icon(icon, null, tint=Ice, modifier=Modifier.size(23.dp))
        Spacer(Modifier.height(9.dp))
        Text(title, color=Silver, fontSize=11.sp, fontWeight=FontWeight.Bold, maxLines=1)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, color=Dim, fontSize=6.sp, letterSpacing=.4.sp, maxLines=1)
    }
}
