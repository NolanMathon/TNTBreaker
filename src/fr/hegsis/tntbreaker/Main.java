package fr.hegsis.tntbreaker;

import fr.hegsis.tntbreaker.commands.TNTBreakerCommand;
import fr.hegsis.tntbreaker.listeners.RightClickBlock;
import fr.hegsis.tntbreaker.listeners.TNTBlastListeners;
import fr.hegsis.tntbreaker.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    private static Main INSTANCE;
    public static Main getINSTANCE() { return INSTANCE; }

    public Map<Material, Integer> maxDurabilityPerItem = new HashMap<>();
    public Map<Location, Integer> blocksDurability = new HashMap<>();

    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new TNTBlastListeners(), this);
        pm.registerEvents(new RightClickBlock(), this);

        getCommand("tntbreaker").setExecutor(new TNTBreakerCommand());

        maxDurabilityPerItem = FileUtils.getDefaultBlocksMap();
    }
}
