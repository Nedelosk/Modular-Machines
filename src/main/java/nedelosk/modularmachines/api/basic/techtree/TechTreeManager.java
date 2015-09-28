package nedelosk.modularmachines.api.basic.techtree;

import net.minecraft.entity.player.EntityPlayer;

public class TechTreeManager {

	public static void completeEntry(EntityPlayer player, String key)
	{
		if(player.getExtendedProperties("MODULARMACHINES:TECHTREE") != null)
		{
			((TechTreePlayerData)player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techEntrys.add(key);
		}
		else
		{
			player.registerExtendedProperties("MODULARMACHINES:TECHTREE", new TechTreePlayerData(player, key));
		}
	}
	
	public static boolean isEntryComplete(EntityPlayer player, String key)
	{
		if(player.getExtendedProperties("MODULARMACHINES:TECHTREE") != null)
		{
			return ((TechTreePlayerData)player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techEntrys.contains(key);
		}
		return false;
	}
	
	public static boolean isEntryComplete(EntityPlayer player, String[] keys)
	{
		if(player.getExtendedProperties("MODULARMACHINES:TECHTREE") != null)
		{
			for(String key : keys)
				if(!((TechTreePlayerData)player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techEntrys.contains(key))
					return false;
			return true;
		}
		return false;
	}
	
	public static void addTechPoints(EntityPlayer player, int points, TechPointTypes type)
	{
		if(player.getExtendedProperties("MODULARMACHINES:TECHTREE") != null)
		{
			((TechTreePlayerData)player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techPoints[type.ordinal()] += points;
		}
		else
		{
			player.registerExtendedProperties("MODULARMACHINES:TECHTREE", new TechTreePlayerData(player, points, type));
		}
	}
	
	public static void setTechPoints(EntityPlayer player, int points, TechPointTypes type)
	{
		if(player.getExtendedProperties("MODULARMACHINES:TECHTREE") != null)
		{
			((TechTreePlayerData)player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techPoints[type.ordinal()] = points;
		}
		else
		{
			player.registerExtendedProperties("MODULARMACHINES:TECHTREE", new TechTreePlayerData(player, points, type));
		}
	}
	
	public static void setTechPoints(EntityPlayer player, int[] points)
	{
		if(player.getExtendedProperties("MODULARMACHINES:TECHTREE") != null)
		{
			((TechTreePlayerData)player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techPoints = points;
		}
		else
		{
			player.registerExtendedProperties("MODULARMACHINES:TECHTREE", new TechTreePlayerData(player));
			((TechTreePlayerData)player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techPoints = points;
		}
	}
	
	public static int[] getTechPoints(EntityPlayer player)
	{
		if(player.getExtendedProperties("MODULARMACHINES:TECHTREE") != null)
		{
			return ((TechTreePlayerData)player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techPoints;
		}
		else
		{
			player.registerExtendedProperties("MODULARMACHINES:TECHTREE", new TechTreePlayerData(player));
			return null;
		}
	}
	
}
