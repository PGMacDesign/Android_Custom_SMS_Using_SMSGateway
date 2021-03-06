package com.pgmacdesign.smsverification.myfiles;


import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * Created by pmacdowell on 5/14/2015.
 */
public class SharedPrefs implements SharedPreferences {

	/*
	//Global Variables
		public static final String PREFS_NAME = "Name_of_the_shared_preferences";
		SharedPrefs sp = new SharedPrefs();
		SharedPreferences settings;
		SharedPreferences.Editor editor;
	//Add this into the onCreate() or Initialize() section:
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();

	*/
    //End Boiler Plate Code


    /**
     * This allows doubles to be entered into the data field.
     * Used - IE) sp.putDouble(editor, "sales_dollars", 2.4231);
     * @param edit Editor being used
     * @param key Which 'column' the data is being entered to
     * @param value Value To Enter
     * @return editor object
     */
    public static Editor putDouble(final Editor edit, final String key, final double value){
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    /**
     * Returns a double from the shared preferences data field.
     * @param prefs SharedPreferences Variable
     * @param key The String key to match against
     * @param defaultValue default value to be returned if no value available
     * @return double
     */
    public static double getDouble(final SharedPreferences prefs, final String key, final double defaultValue){
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    /**
     *  This allows ints to be entered into the data field.
     * @param edit Editor being used
     * @param key String key to match against
     * @param value value to be inserted
     * @return editor object
     */
    public static Editor putInt (final Editor edit, final String key, final int value){
        return edit.putInt(key, (value));
    }

    /**
     * Returns an int from the shared preferences data field
     * @param prefs SharedPreferences Variable
     * @param key The String key to match against
     * @param defaultValue default value to be returned if no value available
     * @return double
     */
    public static int getInt(final SharedPreferences prefs, final String key, final int defaultValue){
        return prefs.getInt(key, (defaultValue));
    }

    /**
     * This allows Strings to be entered into the data field.
     * @param edit Editor being used
     * @param key String key to match against
     * @param value value to be inserted
     * @return returns an editor object
     */
    public static Editor putString (final Editor edit, final String key, final String value){
        return edit.putString(key, value);
    }

    /**
     * Returns a String from the shared preferences data field
     * @param prefs SharedPreferences Variable
     * @param key The String key to match against
     * @param defaultValue default value to be returned if no value available
     * @return String
     */
    public static String getString(final SharedPreferences prefs, final String key, final String defaultValue){
        return prefs.getString(key, defaultValue);
    }

    /**
     * Clears the shared pref
     * @param edit The editor being used
     * @param key The String key to be removed from shared preferences
     */
    public static void clearPref(final Editor edit, final String key){
        edit.remove(key);
        edit.commit();
    }

    /**
     * Clears the shared pref via an array
     * @param edit The editor being used
     * @param keys The String key to be removed from shared preferences
     */
    public static void clearPref(final Editor edit, String[] keys){
        for(int i = 0; i<keys.length; i++){
            edit.remove(keys[i]);
        }
        edit.commit();
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Below are the implemented shared preferences methods

    @Override
    public Map<String, ?> getAll() {
        return null;
    }

    @Nullable
    @Override
    public String getString(String key, String defValue) {
        return null;
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return null;
    }

    @Override
    public int getInt(String key, int defValue) {
        return 0;
    }

    @Override
    public long getLong(String key, long defValue) {
        return 0;
    }

    @Override
    public float getFloat(String key, float defValue) {
        return 0;
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return false;
    }

    @Override
    public boolean contains(String key) {
        return false;
    }

    @Override
    public Editor edit() {
        return null;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }
}
