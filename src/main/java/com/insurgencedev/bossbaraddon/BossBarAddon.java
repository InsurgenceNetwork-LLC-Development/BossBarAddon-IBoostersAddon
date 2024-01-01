package com.insurgencedev.bossbaraddon;

import com.insurgencedev.bossbaraddon.listeners.BoosterEventListener;
import com.insurgencedev.bossbaraddon.settings.MyConfig;
import org.insurgencedev.insurgenceboosters.api.addon.IBoostersAddon;
import org.insurgencedev.insurgenceboosters.api.addon.InsurgenceBoostersAddon;

@IBoostersAddon(name = "BossBarAddon", version = "1.0.0", author = "InsurgenceDev", description = "Display bossbar when a global booster is active")
public class BossBarAddon extends InsurgenceBoostersAddon {

    @Override
    public void onAddonReloadablesStart() {
        new MyConfig();
        registerEvent(new BoosterEventListener());
    }
}
