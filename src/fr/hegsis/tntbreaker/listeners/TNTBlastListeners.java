package fr.hegsis.tntbreaker.listeners;

import fr.hegsis.tntbreaker.Main;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;

public class TNTBlastListeners implements Listener {

    @EventHandler
    public void onTNTExplode(EntityExplodeEvent e) {

        if (e.isCancelled()) return;

        Main main = Main.getINSTANCE();
        List<Block> dontExplodeBlockList = new ArrayList<>();
        List<Block> deadBlocks = new ArrayList<>();

        for (Block b : e.blockList()) {
            if (main.maxDurabilityPerItem.containsKey(b.getType())) { // Si le block à une durabilité
                int maxDurability = main.maxDurabilityPerItem.get(b.getType()); // On récupère sa durabilité maximum
                if (maxDurability == -1) dontExplodeBlockList.add(b); // Si le block est incassable

                if (main.blocksDurability.containsKey(b.getLocation())) { // Si le block a déjà pris un ou plusieurs coups de TNT
                    int durability = main.blocksDurability.get(b.getLocation()); // On récupère la durabilité qui lui reste
                    durability--; // On enlève 1 à sa durabilité
                    dontExplodeBlockList.add(b);
                    if (durability <= 0) deadBlocks.add(b);
                } else {
                    main.blocksDurability.put(b.getLocation(), maxDurability);
                }
            }
        }

        // On enlève les blocks de la liste des blocks touchés par l'explosion
        if (dontExplodeBlockList.size() > 0) {
            for (Block b : dontExplodeBlockList) {
                e.blockList().remove(b);
            }
        }

        // On drop les blocks qui sont morts
        if (deadBlocks.size() > 0) {
            for (Block b : deadBlocks) {
                b.breakNaturally();
                main.blocksDurability.remove(b.getLocation());
            }
        }

    }

}
