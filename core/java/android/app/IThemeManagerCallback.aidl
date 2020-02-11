package android.app;

/**
 * Callback interface used by IThemeManager to send asynchronous 
 * notifications back to its clients.  Note that this is a
 * one-way interface so the server does not block waiting for the client.
 *
 */
oneway interface IThemeManagerCallback {
    /**
     * Called when theme change
     */
    void onThemeChanged(in String theme);

}
