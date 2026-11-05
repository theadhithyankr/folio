package com.folio.reader;

import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
 @Override public void onCreate(android.os.Bundle state){registerPlugin(FolioNative.class);super.onCreate(state);getOnBackPressedDispatcher().addCallback(this,new androidx.activity.OnBackPressedCallback(true){@Override public void handleOnBackPressed(){getBridge().getWebView().evaluateJavascript("window.dispatchEvent(new Event('folio-back'))",null);}});getWindow().setStatusBarColor(android.graphics.Color.rgb(20,19,27));getWindow().setNavigationBarColor(android.graphics.Color.rgb(25,24,32));}
}
