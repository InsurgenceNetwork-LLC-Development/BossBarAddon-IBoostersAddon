package com.insurgencedev.bossbaraddon;

import com.insurgencedev.bossbaraddon.listeners.BoosterEventListener;
import com.insurgencedev.bossbaraddon.settings.MyConfig;
import org.insurgencedev.insurgenceboosters.api.addon.IBoostersAddon;
import org.insurgencedev.insurgenceboosters.api.addon.InsurgenceBoostersAddon;

@IBoostersAddon(name = "BossBarAddon", version = "1.0.0", author = "InsurgenceDev", description = "Bossbars when global booster is active")
public class BossBarAddon extends InsurgenceBoostersAddon {

    @Override
    public void onAddonStart() {
        new MyConfig();
    }

    @Override
    public void onAddonReloadablesStart() {
        registerEvent(new BoosterEventListener());
    }
}
