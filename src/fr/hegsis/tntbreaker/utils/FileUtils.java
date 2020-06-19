package fr.hegsis.tntbreaker.utils;

import fr.hegsis.tntbreaker.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class FileUtils {

    public static Map<Material, Integer> getDefaultBlocksMap() {
        Map<Material, Integer> blocks = new HashMap<>();
        Main main = Main.getINSTANCE();
        ConfigurationSection cs = main.getConfig().getConfigurationSection("blocks-with-durability");
        if (cs != null) {
            for (String s : cs.getKeys(false)) {
                blocks.put(Material.valueOf(s), main.getConfig().getInt("blocks-with-durability." + s));
            }
        }

        return blocks;
    }

    public static void removeBlockOnConfig(Material material) {
        Main main = Main.getINSTANCE();
        if (main.getConfig().contains("blocks-with-durability."+material.toString())) {
            main.getConfig().set("blocks-with-durability."+material.toString(), null);
            main.saveConfig();
            main.reloadConfig();
        }
    }

    public static void setBlockOnConfig(Material material, int durability) {
        Main main = Main.getINSTANCE();
        main.getConfig().set("blocks-with-durability."+material.toString(), durability);
        main.saveConfig();
        main.reloadConfig();
    }
}
