
package android.app;

import android.app.IThemeManagerCallback;

interface IThemeManager {

    /**
     * Register a callback that was receiving theme updates
     */
    void registerThemeCallback(IThemeManagerCallback cb);

    /**
     * Unregister a callback that was receiving theme updates
     */
    void unregisterThemeCallback(IThemeManagerCallback cb);

}
