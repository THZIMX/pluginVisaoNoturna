/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.potion.PotionEffectType
 */
package com.thzimx.nightvisionplus;

import com.thzimx.nightvisionplus.NightVisionPlus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

public class NightVisionListener
implements Listener {
    private final NightVisionPlus plugin;

    public NightVisionListener(NightVisionPlus nightVisionPlus) {
        this.plugin = nightVisionPlus;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player;
        if (this.plugin.getConfig().getBoolean("persistence.enabled") && this.plugin.isPersistent(player = playerJoinEvent.getPlayer())) {
            this.plugin.applyNightVision(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        playerQuitEvent.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
    }
}

