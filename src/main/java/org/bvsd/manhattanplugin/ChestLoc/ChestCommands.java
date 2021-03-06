/*
 * This file is part of ManhattanPlugin.
 * 
 * ManhattanPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ManhattanPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ManhattanPlugin.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 */
package org.bvsd.manhattanplugin.ChestLoc;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bvsd.manhattanplugin.DBmanager;

/**
 *
 * @author Donovan
 */
public class ChestCommands implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args){
        if(cs instanceof Player){
            Player p = (Player) cs;
            System.out.print("enter");
            if(args.length < 2){
                if(args.length<1){
                    return false;
                }else if(args[0].equalsIgnoreCase("unwand")){
                    p.sendMessage(ChatColor.BLUE + "You are no longer in chest wand mode");
                    ChestLock.getWands().remove(p.getUniqueId());
                }
            }
            if(args[0].equalsIgnoreCase("friend")){
                if(args[1].equalsIgnoreCase("add")){
                    if(args.length < 3){
                        DBmanager.getPlayerdats().get(p.getName()).getFriends().add(Bukkit.getOfflinePlayer(args[2]).getUniqueId()); //POF for rename
                    }else{
                        return false;
                    }
                }else if(args[1].equalsIgnoreCase("remove")){
                    if(args.length < 3){
                        DBmanager.getPlayerdats().get(p.getName()).getFriends().remove(Bukkit.getOfflinePlayer(args[2]).getUniqueId()); //POF for rename
                    }else{
                        return false;
                    }
                }else if(args[1].equalsIgnoreCase("list")){
                    p.sendMessage(Arrays.toString(DBmanager.getPlayerdats().get(p.getName()).getFriends().toArray()));
                    return true;
                }else if(args[1].equalsIgnoreCase("clear")){
                    DBmanager.getPlayerdats().get(p.getName()).getFriends().clear();
                    return true;
                }
            }else if(args[0].equalsIgnoreCase("req") || args[0].equalsIgnoreCase("request")){
                if(args[1].equalsIgnoreCase("b")||args[1].equalsIgnoreCase("ban")){ // ban
                    ChestDBmanager.getChestDB().get(ChestDBmanager.getWaiting().get(p.getUniqueId()).getCl().toString()).getBanned().add(ChestDBmanager.getWaiting().get(p.getUniqueId()).getReq());
                    return true;
                }else if(args[1].equalsIgnoreCase("a") || args[1].equalsIgnoreCase("accept")){ // accept
                    ChestDBmanager.getChestDB().get(ChestDBmanager.getWaiting().get(p.getUniqueId()).getCl().toString()).getApproved().add(ChestDBmanager.getWaiting().get(p.getUniqueId()).getReq());
                    return true;
                }
            }else if(args[0].equalsIgnoreCase("set")){
                if(args[1].equalsIgnoreCase("private")){
                    p.sendMessage(ChatColor.BLUE + "You are now in chest wand mode " + args[1]);
                    ChestLock.getWands().put(p.getUniqueId(), 3);
                    return true;
                }else if(args[1].equalsIgnoreCase("restricted")){
                    p.sendMessage(ChatColor.BLUE + "You are now in chest wand mode " + args[1]);
                    ChestLock.getWands().put(p.getUniqueId(), 2);
                    return true;
                }else if(args[1].equalsIgnoreCase("public")){
                    p.sendMessage(ChatColor.BLUE + "You are now in chest wand mode " + args[1]);
                    ChestLock.getWands().put(p.getUniqueId(), 1);
                    return true;
                }
            }
        }
        return false;
    }
}
