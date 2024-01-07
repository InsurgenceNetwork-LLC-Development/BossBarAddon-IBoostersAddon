package com.insurgencedev.bossbaraddon;

import com.insurgencedev.bossbaraddon.listeners.BoosterEventListener;
import com.insurgencedev.bossbaraddon.settings.MyConfig;
import org.insurgencedev.insurgenceboosters.api.addon.IBoostersAddon;
import org.insurgencedev.insurgenceboosters.api.addon.InsurgenceBoostersAddon;

@IBoostersAddon(name = "BossBarAddon", version = "1.0.2", author = "InsurgenceDev", description = "Display bossbar when a global booster is active")
public class BossBarAddon extends InsurgenceBoostersAddon {

    private static MyConfig config;

    @Override
    public void onAddonStart() {
        config = new MyConfig();
    }

    @Override
    public void onAddonReloadablesStart() {
        config.reload();
        registerEvent(new BoosterEventListener());
    }
}
