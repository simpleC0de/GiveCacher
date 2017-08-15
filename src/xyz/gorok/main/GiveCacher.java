package xyz.gorok.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.gorok.events.PreprocessEvent;

/**
 * Created by root on 15.08.2017.
 */
public class GiveCacher extends JavaPlugin{

    private static GiveCacher instance;

    public void onEnable(){
        instance = this;
        loadConfig();
        loadEvents();
    }

    public static GiveCacher getInstance(){
        return instance;
    }

    public void loadEvents(){
        Bukkit.getPluginManager().registerEvents(new PreprocessEvent(), this);
    }

    public void loadConfig(){
        if(getDataFolder().exists()){
            return;
        }

        getConfig().options().header("OPEN SOURCE");
        getConfig().set("Failed.Message", "Player %player% is not online, i'll give him the %item% when he logs back in.");
        saveConfig();

    }

    public void onDisable(){
        System.gc();
    }

}
