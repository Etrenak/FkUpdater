package fr.devsylone.fkupdater;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FkUpdater extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getCommand("fkupdate").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(!(sender instanceof ConsoleCommandSender) || args.length < 2)
			return true;

		PluginManager pluginManager = this.getServer().getPluginManager();
		Plugin prevPlugin = pluginManager.getPlugin("FallenKingdom");
		if(prevPlugin != null)
			pluginManager.disablePlugin(prevPlugin);

		if(!new File(args[0]).delete())
			this.getLogger().severe("Unable to delete the previous version of the plugin.");

		try {
			Plugin nextPlugin = pluginManager.loadPlugin(new File(args[1]));
			if(nextPlugin == null)
				throw new IllegalArgumentException("Unable to load the next version of the plugin.");

			pluginManager.enablePlugin(nextPlugin);

			this.getServer().dispatchCommand(
					this.getServer().getConsoleSender(),
					"fallenkingdom:fk updated " + FkUpdater.class.getProtectionDomain().getCodeSource().getLocation().getPath()
			);

			pluginManager.disablePlugin(this);
		} catch (InvalidPluginException | InvalidDescriptionException e) {
			e.printStackTrace();
		}
		return true;
	}
}
