package com.insurgencedev.bossbaraddon.utils;

import org.bukkit.entity.Player;

@FunctionalInterface
interface BoosterMessageGenerator {
    String generate(Player player);
}