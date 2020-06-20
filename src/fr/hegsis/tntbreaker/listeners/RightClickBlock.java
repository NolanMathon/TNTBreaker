package fr.hegsis.tntbreaker.listeners;

import fr.hegsis.tntbreaker.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class RightClickBlock implements Listener {

    @EventHandler
    public void onPlayerRightClickOnBlock(PlayerInteractEvent e) {
        if (e.isCancelled()) return;

        // Si l'action n'est pas un clique droit sur un block
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        // Si le clique droit n'est pas fait sur un block
        if (e.getMaterial() != Material.STICK) return;

        Main main = Main.getINSTANCE();
        // Si le block cliqué ne fait pas partie de la liste
        if (!main.maxDurabilityPerItem.containsKey(e.getClickedBlock().getType())) return;

        // On récupère la durabilité max
        int maxDurability = main.maxDurabilityPerItem.get(e.getClickedBlock().getType());
        // Si la durabilité max vaut -1
        if (maxDurability == -1) {
            e.getPlayer().sendMessage("§cCe block est incassable !");
            return;
        }

        int durability = maxDurability;
        // Si le block cliqué a déjà été touché par une TNT, on met sa vraie durabilité
        if (main.blocksDurability.containsKey(e.getClickedBlock().getLocation())) {
            durability = main.blocksDurability.get(e.getClickedBlock().getLocation());
        }

        e.getPlayer().sendMessage("§6Durability §f: §c" + durability + "§f/§4" + maxDurability);
    }
}
