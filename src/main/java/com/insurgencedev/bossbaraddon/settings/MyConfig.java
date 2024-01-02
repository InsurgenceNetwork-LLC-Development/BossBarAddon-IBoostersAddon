package com.insurgencedev.bossbaraddon.settings;

import org.insurgencedev.insurgenceboosters.api.addon.AddonConfig;
import org.insurgencedev.insurgenceboosters.libs.fo.remain.CompBarColor;

public class MyConfig extends AddonConfig {

    public static String barMessage;
    public static CompBarColor barColor;
    public static boolean respectExternalBars;

    public MyConfig() {
        loadAddonConfig("config.yml", "config.yml");
    }

    @Override
    protected void onLoad() {
        barMessage = getString("Boss_Bar.Message");
        barColor = CompBarColor.fromKey(getString("Boss_Bar.Color", "Pink"));
        respectExternalBars = getBoolean("Respect_External_Bars");
    }
}
