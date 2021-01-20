package com.sidorov.filemanager.utility;

import java.util.Locale;
import java.util.ResourceBundle;

public class BundleHolder {

    private static ResourceBundle bundle;

    private BundleHolder() {}

    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("bundle.strings", locale);
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }
}
