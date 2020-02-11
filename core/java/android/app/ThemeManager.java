
package android.app;

import android.annotation.NonNull;
import android.content.Context;
import android.annotation.SystemService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceManager.ServiceNotFoundException;
import android.util.Log;

import java.util.ArrayList;

@SystemService(Context.THEME_SERVICE)
public class ThemeManager extends IThemeManagerCallback.Stub{
    private static final String TAG = "ThemeManager";

    private IThemeManager mService;

    private ArrayList<OnThemeChangedListener> callbackListener;

    public ThemeManager() throws ServiceNotFoundException {
        mService = IThemeManager.Stub.asInterface(
            ServiceManager.getServiceOrThrow(Context.THEME_SERVICE)
        );
        try{
            mService.registerThemeCallback(this);
        }catch(RemoteException e){
            throw e.rethrowFromSystemServer();
        }

        callbackListener = new ArrayList<OnThemeChangedListener>();
    }

    @Override
    public void onThemeChanged(String theme)
    {
        for(int i = 0; i < callbackListener.size(); i++){
            callbackListener.get(i).onThemeChanged(theme);
        }
    }

    public void registerThemeCallback(@NonNull OnThemeChangedListener cb)
    {
        callbackListener.add(cb);
    }

    /**
     * Unregister a callback that was receiving theme updates
     */
    public void unregisterThemeCallback(@NonNull OnThemeChangedListener cb)
    {
        callbackListener.remove(cb);
    }

    public interface OnThemeChangedListener {
        /**
         * Called when theme change.
         *
         * @param theme new theme name
         */
        public void onThemeChanged(String theme);
    }
}
