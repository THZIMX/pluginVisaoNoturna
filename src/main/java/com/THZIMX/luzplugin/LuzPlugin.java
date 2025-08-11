package com.THZIMX.luzplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LuzPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("LuzPlugin ativado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LuzPlugin desativado!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser usado por jogadores.");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("luz")) {
            if (!player.hasPermission("luz.use")) {
                player.sendMessage("§cVocê não tem permissão para usar esse comando.");
                return true;
            }

            if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.sendMessage("§eVisão noturna §cdesativada§e.");
            } else {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, false, false));
                player.sendMessage("§eVisão noturna §aativada§e.");
            }
            return true;
        }
        return false;
    }
}
