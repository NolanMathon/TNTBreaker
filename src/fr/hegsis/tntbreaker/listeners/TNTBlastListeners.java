package fr.hegsis.tntbreaker.listeners;

import fr.hegsis.tntbreaker.Main;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class TNTBlastListeners implements Listener {

    @EventHandler
    public void onTNTExplode(EntityExplodeEvent e) {

        if (e.isCancelled()) return;

        if (!(e.getEntity() instanceof TNTPrimed)) return;

        Main main = Main.getINSTANCE();

        int radius = 3;
        Location TNTLoc = e.getLocation();
        // On va récupérer tous les blocks dans une rayon de 3 (range de l'explosion de base)
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    Location targetLoc = new Location(TNTLoc.getWorld(), TNTLoc.getX() + x, TNTLoc.getY() + y, TNTLoc.getZ() + z);
                    Block b = targetLoc.getBlock();

                    if (main.maxDurabilityPerItem.containsKey(b.getType())) { // Si le block à une durabilité
                        int maxDurability = main.maxDurabilityPerItem.get(b.getType()); // On récupère sa durabilité maximum
                        if (maxDurability == -1) e.blockList().remove(b); // Si le block est incassable
                        e.blockList().remove(b);
                        if (main.blocksDurability.containsKey(b.getLocation())) { // Si le block a déjà pris un ou plusieurs coups de TNT
                            int durability = main.blocksDurability.get(b.getLocation()); // On récupère la durabilité qui lui reste
                            durability--; // On enlève 1 à sa durabilité
                            main.blocksDurability.replace(b.getLocation(), durability);
                            if (durability <= 0) {
                                b.breakNaturally();
                                main.blocksDurability.remove(b.getLocation());
                            }
                        } else {

                            main.blocksDurability.put(b.getLocation(), maxDurability-1);
                        }
                    }
                }
            }
        }

    }

}
