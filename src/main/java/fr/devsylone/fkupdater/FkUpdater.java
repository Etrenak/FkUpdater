package fr.devsylone.fkupdater;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

public class FkUpdater extends JavaPlugin implements CommandExecutor
{
	@Override
	public void onEnable()
	{
		getCommand("fkupdate").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(sender instanceof ConsoleCommandSender)
		{
			if(command.getName().equals("fkupdate"))
			{
				if(args.length > 1)
				{
					for(Plugin p : Bukkit.getPluginManager().getPlugins())
					{
						if(p.getName().equalsIgnoreCase("FallenKingdom"))
						{
							Bukkit.getPluginManager().disablePlugin(p);
							break;
						}
					}
					new File(args[0]).delete();
					
					try
					{
						Plugin p  = Bukkit.getPluginManager().loadPlugin(new File(args[1]));
						Bukkit.getPluginManager().enablePlugin(p);
						Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
						{
							
							@Override
							public void run()
							{
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fallenkingdom:fk updated " +  FkUpdater.class.getProtectionDomain().getCodeSource().getLocation().getPath());
							}
						}, 40l);
					}catch(UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}
}
