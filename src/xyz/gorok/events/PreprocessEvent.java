package xyz.gorok.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.gorok.main.GiveCacher;

import java.util.HashMap;

/**
 * Created by root on 15.08.2017.
 */
public class PreprocessEvent implements Listener {


    private HashMap<String, String> playerCommand = new HashMap<>();

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        if(!e.getPlayer().hasPermission("givecacher.give")){
            return;
        }
            if(e.getMessage().startsWith("/give")){
                String player = e.getMessage().split(" ")[1];
                String command = e.getMessage();

                boolean b;
                try{
                    Bukkit.getPlayer(player).isOnline();
                    b = true;
                }catch(NullPointerException ex){
                    b = false;
                }

                if(!b){
                    playerCommand.put(player, command);

                    String message = GiveCacher.getInstance().getConfig().getString("Failed.Message");
                    message = message.replace("%player%", player);
                    message = message.replace("%item%", e.getMessage().split(" ")[2]);
                    message = ChatColor.translateAlternateColorCodes('&', message);
                    e.getPlayer().sendMessage(message);
                    return;
                }else {
                }

            }


    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent e){
        if(e.getCommand().startsWith("give")){
            String player = e.getCommand().split(" ")[1];
            String command = e.getCommand();

            boolean b;
            try{
                Bukkit.getPlayer(player).isOnline();
                b = true;
            }catch(NullPointerException ex){
                b = false;
            }

            if(!b){
                playerCommand.put(player, command);

                String message = GiveCacher.getInstance().getConfig().getString("Failed.Message");
                message = message.replace("%player%", player);
                message = message.replace("%item%", e.getCommand().split(" ")[2]);
                message = ChatColor.translateAlternateColorCodes('&', message);
                System.out.println(message);
                return;
            }else {
            }

        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        if(playerCommand.containsKey(e.getPlayer().getDisplayName())){
            while(playerCommand.containsKey(e.getPlayer().getDisplayName())){
                String command = playerCommand.get(e.getPlayer().getDisplayName());
                giveItem(command);
                playerCommand.remove(e.getPlayer().getDisplayName());
            }
        }

    }

    protected void giveItem(String command){
        new BukkitRunnable(){
            public void run(){

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

            }
        }.runTaskLater(GiveCacher.getInstance(), 20);
    }

}
