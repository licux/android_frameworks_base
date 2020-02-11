package com.android.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import android.content.Context;
import android.app.IThemeManager;
import android.app.IThemeManagerCallback;

 
public class ThemeService extends SystemService {

    private String TAG = "ThemeService";

    public String THEME_ACTION = "android.intent.action.THEME_CHANGE";
    public String THEME_INTENT_KEY = "new_theme";

    private final Context mContext;

    private RemoteCallbackList<IThemeManagerCallback> callbackListener;

    public ThemeService(Context context){
        super(context);
        mContext = context;
        callbackListener = new RemoteCallbackList<IThemeManagerCallback>();

        IntentFilter intentFilter = new IntentFilter(THEME_ACTION);
        mContext.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent){

                String new_theme = intent.getStringExtra(THEME_INTENT_KEY);

                int i = callbackListener.beginBroadcast();
                while (i > 0) {
                    i--;
                    try {
                        callbackListener.getBroadcastItem(i).onThemeChanged(new_theme);
                    } catch (RemoteException e) {
                        // error handling
                    }
                }
                callbackListener.finishBroadcast();
            }
        }, intentFilter);
    }

 
    @Override
    public void onStart(){
        publishBinderService(Context.THEME_SERVICE, mService);
    }

    private IThemeManager.Stub mService = new IThemeManager.Stub(){
        @Override
        public void registerThemeCallback(IThemeManagerCallback cb){
            callbackListener.register(cb);
        }

        @Override
        public void  unregisterThemeCallback(IThemeManagerCallback cb){
            callbackListener.unregister(cb);
        }
    };
}

