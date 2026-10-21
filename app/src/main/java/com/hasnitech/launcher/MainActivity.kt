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
                                Text(item.second,color=Silver,fontSize=10.sp,maxLines=1)
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Surface(color=Color(0xFF101B40), shape=RoundedCornerShape(9.dp), modifier=Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(8.dp)) {
                            Icon(Icons.Rounded.SportsEsports,null,tint=Color(0xFF9B7BFF),modifier=Modifier.size(22.dp))
                            Text("وضع الألعاب",color=Silver,fontSize=10.sp)
                            Text("OFF",color=cyan,fontSize=9.sp)
                        }
                    }
                }
                // Main holographic stage.
                Column(Modifier.weight(1.35f).fillMaxHeight(), verticalArrangement=Arrangement.spacedBy(8.dp)) {
                    Row(Modifier.fillMaxWidth().height(48.dp), verticalAlignment=Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("HASNITECH  /  CONTROL CENTER", color=cyan, fontSize=11.sp, fontWeight=FontWeight.Bold, letterSpacing=1.2.sp)
                            Text(when(page){1->"WINDOWS ENVIRONMENT";2->"APPLICATION LIBRARY";3->"FILE CENTER";4->"SYSTEM SETTINGS";5->"SYSTEM TOOLS";6->"SUPPORT CENTER";else->"قوة التكنولوجيا بين يديك"}, color=Silver, fontSize=9.sp)
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
                        HologramFigure(pulse,Modifier.fillMaxSize().padding(horizontal=22.dp, vertical=4.dp))
                        Column(Modifier.align(Alignment.TopCenter).padding(top=9.dp),horizontalAlignment=Alignment.CenterHorizontally) {
                            Text("HASNITECH AI",color=cyan,fontSize=10.sp,fontWeight=FontWeight.Bold,letterSpacing=2.sp)
                            Text("HOLOGRAPHIC CORE  •  LIVE",color=Silver.copy(alpha=.75f),fontSize=7.sp,letterSpacing=1.sp)
                        }
                        Column(Modifier.align(Alignment.BottomCenter).padding(bottom=9.dp),horizontalAlignment=Alignment.CenterHorizontally) {
                            Text("قوة التكنولوجيا بين يديك",color=Silver,fontSize=11.sp,fontWeight=FontWeight.Bold)
                            Text("HOLOGRAM SIMULATION",color=cyan,fontSize=7.sp,letterSpacing=1.5.sp)
                        }
                    }
                    Row(Modifier.fillMaxWidth().height(70.dp), horizontalArrangement=Arrangement.spacedBy(7.dp)) {
                        listOf(Triple(Icons.Rounded.Home,"الرئيسية",0),Triple(Icons.Rounded.Folder,"الملفات",3),Triple(Icons.Rounded.Apps,"التطبيقات",2),Triple(Icons.Rounded.Settings,"الإعدادات",4),Triple(Icons.Rounded.Build,"الأدوات",5)).forEach { item ->
                            Surface(onClick={page=item.third}, modifier=Modifier.weight(1f).fillMaxHeight(),
                                color=Color(0xD90A1730),shape=RoundedCornerShape(11.dp),
                                border=androidx.compose.foundation.BorderStroke(1.dp,cyan.copy(alpha=.25f))) {
                                Column(horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center) {
                                    Icon(item.first,null,tint=cyan,modifier=Modifier.size(20.dp))
                                    Spacer(Modifier.height(4.dp)); Text(item.second,color=Silver,fontSize=9.sp)
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
                            Surface(onClick={ if(app.second=="Chrome") { val intent=android.content.Intent(android.content.Intent.ACTION_VIEW,android.net.Uri.parse("https://www.google.com")); try { context.startActivity(intent) } catch (_:Exception) { showWindowsInfo=true } } else showWindowsInfo=true },
                                color=Color(0xFF0B1B36),shape=RoundedCornerShape(9.dp),border=androidx.compose.foundation.BorderStroke(1.dp,Color(0xFF173D6B)),
                                modifier=Modifier.fillMaxWidth().weight(1f).padding(vertical=2.dp)) {
                                Row(Modifier.padding(horizontal=8.dp),verticalAlignment=Alignment.CenterVertically) {
                                    Icon(app.first,null,tint=if(app.second=="Games") Color(0xFFAA83FF) else cyan,modifier=Modifier.size(21.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Column { Text(app.second,color=Silver,fontSize=10.sp,fontWeight=FontWeight.Bold); Text(app.third,color=Dim,fontSize=7.sp) }
                                    Spacer(Modifier.weight(1f)); Icon(Icons.Rounded.ChevronRight,null,tint=Dim,modifier=Modifier.size(15.dp))
                                }
                            }
                        }
                    }
                    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(13.dp)).background(panel).border(1.dp,Color(0xFF4D3D99),RoundedCornerShape(13.dp)).padding(9.dp)) {
                        Row(verticalAlignment=Alignment.CenterVertically) {
                            Icon(Icons.Rounded.Memory,null,tint=Color(0xFFB19AFF),modifier=Modifier.size(22.dp))
                            Spacer(Modifier.width(7.dp))
                            Column { Text("مساعد HASNI AI",color=Silver,fontSize=10.sp,fontWeight=FontWeight.Bold); Text("محرك Windows غير مدمج بعد",color=Dim,fontSize=7.sp) }
                        }
                        if(showWindowsInfo) { Spacer(Modifier.height(6.dp)); Text("هذه واجهة اختصارات. يلزم ربط كل تطبيق بمحركه أو تطبيق مثبت لتشغيله فعليًا.",color=Silver,fontSize=8.sp) }
                    }
                }
            }
        }
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
    val scan by rememberInfiniteTransition(label = "holoScan").animateFloat(
        initialValue = 0.08f, targetValue = 0.92f,
        animationSpec = infiniteRepeatable(tween(2200), RepeatMode.Reverse),
        label = "scanLine"
    )
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val cx = w * .52f
        val cyan = Color(0xFF5CF6FF)
        val blue = Color(0xFF238BFF)
        val violet = Color(0xFF9A6BFF)
        // Holographic emitter rings and ambient light.
        drawCircle(Brush.radialGradient(listOf(blue.copy(alpha = .24f * pulse), violet.copy(alpha = .10f), Color.Transparent), Offset(cx, h * .55f), h * .57f), h * .57f, Offset(cx, h * .55f))
        drawOval(cyan.copy(alpha = .55f * pulse), Offset(cx - w*.31f, h*.84f), androidx.compose.ui.geometry.Size(w*.62f, h*.075f), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.4f))
        drawOval(blue.copy(alpha = .45f), Offset(cx - w*.23f, h*.86f), androidx.compose.ui.geometry.Size(w*.46f, h*.045f), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1f))
        // Shoulders, neck and head are drawn as luminous vector contours, not a static image.
        val shoulders = androidx.compose.ui.graphics.Path().apply {
            moveTo(cx-w*.34f, h*.82f)
            cubicTo(cx-w*.31f,h*.68f,cx-w*.19f,h*.68f,cx-w*.12f,h*.66f)
            lineTo(cx-w*.09f,h*.57f)
            cubicTo(cx-w*.18f,h*.49f,cx-w*.20f,h*.40f,cx-w*.18f,h*.30f)
            cubicTo(cx-w*.15f,h*.10f,cx+w*.15f,h*.10f,cx+w*.18f,h*.30f)
            cubicTo(cx+w*.20f,h*.40f,cx+w*.18f,h*.49f,cx+w*.09f,h*.57f)
            lineTo(cx+w*.12f,h*.66f)
            cubicTo(cx+w*.19f,h*.68f,cx+w*.31f,h*.68f,cx+w*.34f,h*.82f)
        }
        drawPath(shoulders, cyan.copy(alpha = .82f * pulse), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f))
        val face = androidx.compose.ui.graphics.Path().apply {
            moveTo(cx-w*.18f,h*.31f)
            lineTo(cx-w*.15f,h*.22f)
            lineTo(cx-w*.08f,h*.15f)
            lineTo(cx+w*.08f,h*.15f)
            lineTo(cx+w*.15f,h*.22f)
            lineTo(cx+w*.18f,h*.31f)
            lineTo(cx+w*.14f,h*.43f)
            lineTo(cx+w*.07f,h*.49f)
            lineTo(cx-w*.07f,h*.49f)
            lineTo(cx-w*.14f,h*.43f)
            close()
        }
        drawPath(face, Brush.verticalGradient(listOf(cyan.copy(alpha=.13f), blue.copy(alpha=.05f), violet.copy(alpha=.10f)), h*.14f, h*.51f))
        drawPath(face, cyan.copy(alpha = .95f), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.1f))
        // Brow, cheek and jaw details create a mechanical android face.
        drawLine(blue.copy(alpha=.9f), Offset(cx-w*.145f,h*.30f), Offset(cx-w*.055f,h*.285f), 2f)
        drawLine(blue.copy(alpha=.9f), Offset(cx+w*.055f,h*.285f), Offset(cx+w*.145f,h*.30f), 2f)
        drawLine(cyan.copy(alpha=.8f), Offset(cx-w*.12f,h*.40f), Offset(cx-w*.07f,h*.44f), 1.1f)
        drawLine(cyan.copy(alpha=.8f), Offset(cx+w*.12f,h*.40f), Offset(cx+w*.07f,h*.44f), 1.1f)
        // Glowing eyes with outer halos.
        listOf(-1f, 1f).forEach { side ->
            val eye = Offset(cx + side*w*.078f, h*.345f)
            drawCircle(blue.copy(alpha=.35f*pulse), w*.055f, eye)
            drawLine(cyan.copy(alpha=.95f), Offset(eye.x-w*.035f,eye.y), Offset(eye.x+w*.035f,eye.y), 3.2f, cap=StrokeCap.Round)
            drawCircle(Color.White.copy(alpha=.8f), 1.5f, eye)
        }
        drawLine(violet.copy(alpha=.9f), Offset(cx,h*.36f), Offset(cx-w*.018f,h*.405f), 1.5f)
        drawLine(cyan.copy(alpha=.9f), Offset(cx-w*.055f,h*.455f), Offset(cx+w*.055f,h*.455f), 1.4f)
        // Fine scanlines and animated scanning beam make the hologram visibly alive.
        var y = h*.16f
        while (y < h*.84f) {
            drawLine(cyan.copy(alpha=.075f), Offset(cx-w*.27f,y), Offset(cx+w*.27f,y), .8f)
            y += 5.5f
        }
        val beamY = h * scan
        drawLine(cyan.copy(alpha=.68f*pulse), Offset(w*.12f,beamY), Offset(w*.88f,beamY), 1.2f)
        drawCircle(cyan.copy(alpha=.85f*pulse), 2.4f, Offset(w*.12f,beamY))
        drawCircle(cyan.copy(alpha=.85f*pulse), 2.4f, Offset(w*.88f,beamY))
        // Floating data particles.
        for (i in 0..11) {
            val px = w * (.12f + ((i * 37) % 76) / 100f)
            val py = h * (.13f + ((i * 23) % 72) / 100f)
            drawCircle(if (i % 3 == 0) violet.copy(alpha=.8f*pulse) else cyan.copy(alpha=.65f), if (i%3==0) 1.8f else 1.1f, Offset(px,py))
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
