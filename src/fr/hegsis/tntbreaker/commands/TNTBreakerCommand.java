package fr.hegsis.tntbreaker.commands;

import fr.hegsis.tntbreaker.Main;
import fr.hegsis.tntbreaker.utils.Calculs;
import fr.hegsis.tntbreaker.utils.FileUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TNTBreakerCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Main main = Main.getINSTANCE();

        // Si la personne qui execute la commande n'est pas un joueur on arrête
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can perform that command !");
            return false;
        }

        Player p = (Player) sender;
        ItemStack it = p.getItemInHand();

        if (args.length >= 1) {
            String subCommand = args[0];

            // Si la commande est /tb list
            if (subCommand.equalsIgnoreCase("list")) {
                if (main.maxDurabilityPerItem.size() == 0) {
                    p.sendMessage("§cNo one durability defined !");
                    return false;
                }

                p.sendMessage("§2The list :");
                return true;
            }

            if (!checkItemInHand(it)) {
                p.sendMessage("§cItem on hand isn't a block !");
                return false;
            }

            // Si la commande est /tb remove
            if (subCommand.equalsIgnoreCase("remove")) {
                if (!main.maxDurabilityPerItem.containsKey(it.getType())) {
                    p.sendMessage("§cThe block havn't durability !");
                    return false;
                }

                main.maxDurabilityPerItem.remove(it.getType());
                FileUtils.removeBlockOnConfig(it.getType());
                p.sendMessage("§eThe block §c" + it.getType().toString() + " §ehas no durability !");
                return true;
            }

            if (args.length == 2) {

                // On check que le second argument est bien un nombre
                int durability = Calculs.isNumber(args[1]);
                if (durability == 0) {
                    p.sendMessage("§cThe number §4" + args[1] + " §cisn't correct or the durability can't be 0 !");
                    return false;
                }

                // Si la commande est /tb add [durability]
                if (subCommand.equalsIgnoreCase("add")) {
                    if (main.maxDurabilityPerItem.containsKey(it.getType())) {
                        p.sendMessage("§cThe block already have durability ! §7(/tb edit to change durability)");
                        return false;
                    }

                    main.maxDurabilityPerItem.put(it.getType(), durability);
                    FileUtils.setBlockOnConfig(it.getType(), durability);
                    p.sendMessage("§eThe block §c" + it.getType().toString() + " §eset with §c" + durability + " §eof durability !");
                    return true;
                }

                // Si la commande est /tb edit [duraility]
                if (subCommand.equalsIgnoreCase("edit")) {
                    if (!main.maxDurabilityPerItem.containsKey(it.getType())) {
                        p.sendMessage("§cThe block havn't durability ! §7(/tb add to set durability for a block)");
                        return false;
                    }

                    main.maxDurabilityPerItem.replace(it.getType(), durability);
                    FileUtils.setBlockOnConfig(it.getType(), durability);
                    p.sendMessage("§eThe block §c" + it.getType().toString() + " §ehas now §c" + durability + " §eof durability !");
                    return true;
                }
            }
        }

        //Si le joueur n'a pas la permission
        if (!p.hasPermission("tntbreaker-help")) {
            p.sendMessage("§cYou don't have permission to perform that command !");
            return false;
        }

        p.sendMessage("§7§m---------§c TNTBreaker Help §7§m---------");
        p.sendMessage("");
        p.sendMessage("§8• §c/tb list §f→ §eList of blocks with durability");
        p.sendMessage("§8• §c/tb remove §f→ §eRemove the durability of the block in your hand");
        p.sendMessage("§8• §c/tb add [durability] §f→ §eSet the durability of the block in your hand");
        p.sendMessage("§8• §c/tb edit [durability] §f→ §eEdit the durability of the block in your hand");
        p.sendMessage("");
        p.sendMessage("§7§m---------§c TNTBreaker Help §7§m---------");
        return false;
    }

    private boolean checkItemInHand(ItemStack it) {
        if (it == null) return false;
        if (it.getType() == Material.AIR) return false;
        if (!it.getType().isBlock()) return false;

        return true;
    }
}
