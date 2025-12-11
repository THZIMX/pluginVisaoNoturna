/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.thzimx.nightvisionplus;

import com.thzimx.nightvisionplus.NightVisionListener;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVisionPlus
extends JavaPlugin {
    private static final int DURATION = 999999;
    private static final PotionEffect NIGHT_VISION_EFFECT = new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, false, false);

    public void onEnable() {
        this.saveDefaultConfig();
        if (this.getConfig().getBoolean("persistence.enabled")) {
            Bukkit.getPluginManager().registerEvents((Listener)new NightVisionListener(this), (Plugin)this);
        }
        this.getLogger().info("NightVisionPlus v" + this.getDescription().getVersion() + " ativado!");
    }

    public void onDisable() {
        this.getLogger().info("NightVisionPlus v" + this.getDescription().getVersion() + " desativado!");
        this.saveConfig();
    }

    public boolean isPersistent(Player player) {
        List list = this.getConfig().getStringList("persistent-players");
        return list.contains(player.getUniqueId().toString());
    }

    public void addPersistent(Player player) {
        List list = this.getConfig().getStringList("persistent-players");
        if (!list.contains(player.getUniqueId().toString())) {
            list.add(player.getUniqueId().toString());
            this.getConfig().set("persistent-players", (Object)list);
        }
    }

    public void removePersistent(Player player) {
        List list = this.getConfig().getStringList("persistent-players");
        if (list.contains(player.getUniqueId().toString())) {
            list.remove(player.getUniqueId().toString());
            this.getConfig().set("persistent-players", (Object)list);
        }
    }

    public void applyNightVision(Player player) {
        player.addPotionEffect(NIGHT_VISION_EFFECT);
    }

    public void removeNightVision(Player player) {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }

    private String getMessage(String string, String string2, String string3) {
        String string4 = this.getConfig().getString("messages." + string, "Mensagem n\u00e3o encontrada: " + string);
        string4 = string4.replace("%player%", string2 != null ? string2 : "");
        string4 = string4.replace("%sender%", string3 != null ? string3 : "");
        return ChatColor.translateAlternateColorCodes((char)'&', (String)string4);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] stringArray) {
        if (command.getName().equalsIgnoreCase("nightvision")) {
            Player player = null;
            String string2 = commandSender.getName();
            if (stringArray.length == 0) {
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(this.getMessage("only_players", null, null));
                    return true;
                }
                player = (Player)commandSender;
            } else if (stringArray.length == 1) {
                if (!commandSender.hasPermission("nightvisionplus.others")) {
                    commandSender.sendMessage(this.getMessage("no_permission", null, null));
                    return true;
                }
                player = Bukkit.getPlayer((String)stringArray[0]);
                if (player == null) {
                    commandSender.sendMessage(this.getMessage("player_not_found", stringArray[0], null));
                    return true;
                }
            } else {
                return false;
            }
            if (player == commandSender && commandSender instanceof Player && !commandSender.hasPermission("nightvisionplus.use")) {
                commandSender.sendMessage(this.getMessage("no_permission", null, null));
                return true;
            }
            if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                this.removeNightVision(player);
                this.removePersistent(player);
                if (player == commandSender) {
                    commandSender.sendMessage(this.getMessage("self_disabled", null, null));
                } else {
                    commandSender.sendMessage(this.getMessage("other_disabled", player.getName(), null));
                    player.sendMessage(this.getMessage("received_disabled", null, string2));
                }
            } else {
                this.applyNightVision(player);
                this.addPersistent(player);
                if (player == commandSender) {
                    commandSender.sendMessage(this.getMessage("self_enabled", null, null));
                } else {
                    commandSender.sendMessage(this.getMessage("other_enabled", player.getName(), null));
                    player.sendMessage(this.getMessage("received_enabled", null, string2));
                }
            }
            return true;
        }
        return false;
    }
}

