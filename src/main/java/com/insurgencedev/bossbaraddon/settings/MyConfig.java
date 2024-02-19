package com.insurgencedev.bossbaraddon.settings;

import lombok.Getter;
import org.insurgencedev.insurgenceboosters.api.addon.AddonConfig;
import org.insurgencedev.insurgenceboosters.libs.fo.remain.CompBarColor;

public class MyConfig extends AddonConfig {



    @Getter
    private static MyConfig instance;

    public static String scope;
    public static boolean respectExternalBars;

    public MyConfig() {
        instance = this;
        loadAddonConfig("config.yml", "config.yml");
    }

    @Override
    protected void onLoad() {
        scope = getString("Scope");
        respectExternalBars = getBoolean("Respect_External_Bars", false);
    }

    public String getMessage(String key) {
        return getString(key, "");
    }

}
