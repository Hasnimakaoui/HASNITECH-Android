package com.xoralive.app;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
 private WebView web;
 private PermissionRequest pending;
 private static final int REQ=701;
 public void onCreate(Bundle b) {
  super.onCreate(b);
  web=new WebView(this); setContentView(web);
  WebSettings s=web.getSettings(); s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true); s.setMediaPlaybackRequiresUserGesture(false);
  web.setWebViewClient(new WebViewClient());
  web.setWebChromeClient(new WebChromeClient() {
   public void onPermissionRequest(PermissionRequest r) {
    runOnUiThread(() -> {
     boolean c=false,m=false;
     for(String x:r.getResources()){if(PermissionRequest.RESOURCE_VIDEO_CAPTURE.equals(x))c=true;if(PermissionRequest.RESOURCE_AUDIO_CAPTURE.equals(x))m=true;}
     boolean cok=!c||checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED;
     boolean mok=!m||checkSelfPermission(Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_GRANTED;
     if(cok&&mok)r.grant(r.getResources());
     else {pending=r;String[] p=c&&m?new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO}:c?new String[]{Manifest.permission.CAMERA}:new String[]{Manifest.permission.RECORD_AUDIO};requestPermissions(p,REQ);}
    });
   }
  });
  web.loadUrl("file:///android_asset/index.html");
 }
 public void onRequestPermissionsResult(int q,String[] p,int[] g){
  super.onRequestPermissionsResult(q,p,g);
  if(q==REQ&&pending!=null){boolean ok=g.length>0;for(int x:g)ok &= x==PackageManager.PERMISSION_GRANTED;if(ok)pending.grant(pending.getResources());else pending.deny();pending=null;}
 }
 public void onBackPressed(){if(web!=null&&web.canGoBack())web.goBack();else super.onBackPressed();}
}