package nedelosk.nedeloskcore.common.book;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.common.core.tracker.PlantTracker;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import nedelosk.forestbotany.common.genetics.templates.crop.CropChromosome;
import nedelosk.forestday.Forestday;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.world.World;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.SaveHandler;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class BookManager {

	public static boolean isEntryUnlock(String playerName, String key)
	{
		List unlock = Forestday.proxy.playerData.getEntrys(playerName);
		if(unlock != null && unlock.size() > 0)
		{
			if(BookDatas.getEntry(key).parents != null)
			{
				for(String tag : BookDatas.getEntry(key).parents)
				{
					if(!unlock.contains(tag))
					{
						return false;
					}
				}
			}
			return unlock.contains(key);
		}
		return false;
	}
	
	public static List<IPlant> getUnlockPlants(GameProfile player, String plantName, World world)
	{
		List<IPlant> plants = new ArrayList<IPlant>();
		if(PlantManager.getPlantManager(plantName) == null)
			return null;
		for(IPlant plant : PlantManager.getPlantManager(plantName).getTemplates())
		{
			if(plant.getGenome().getActiveAllele(CropChromosome.GENDER) == Allele.male && getPlantTracker(world, player).isDiscovered((IAllelePlant)plant.getGenome().getActiveAllele(CropChromosome.PLANT)))
			{
				plants.add(plant);
			}
		}
		return plants;
	}
	
	public static void unlockPlants(GameProfile player, IAllelePlant plant, World world)
	{
		getPlantTracker(world, player).registerPlant(plant);
	}
	
	public static PlantTracker getPlantTracker(World world, GameProfile player) {
		String filename = "PlantTracker." + (player == null ? "common" : player.getId());
		PlantTracker tracker = (PlantTracker) world.loadItemData(PlantTracker.class, filename);

		if (tracker == null) {
			tracker = new PlantTracker(filename);
			world.setItemData(filename, tracker);
		}

		return tracker;
	}
	
	public static boolean isKnowledgeUnlock(String playerName, String key)
	{
		List unlock = Forestday.proxy.getPlayerData().getKnowledges(playerName);
		if(unlock != null)
		{
			return unlock.contains(key);
		}
		return false;
	}
	
	public static ArrayList<String> getEntrysForPlayer(String playerName)
	{
		ArrayList<String> entrys = Forestday.proxy.getPlayerData().getEntrys(playerName);
		try
		{
			if(entrys == null && Forestday.proxy.getClientWorld() == null && MinecraftServer.getServer() != null)
			{
				Forestday.proxy.getPlayerData().getUnlockEntrys().put(playerName, new ArrayList());
				UUID id = UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(Charsets.UTF_8));
				EntityPlayerMP entityplayermp = new EntityPlayerMP(MinecraftServer.getServer(), MinecraftServer.getServer().worldServerForDimension(0), new GameProfile(id, playerName), new ItemInWorldManager(MinecraftServer.getServer().worldServerForDimension(0)));
				if (entityplayermp != null)
				{
				IPlayerFileData playerNBTManagerObj = MinecraftServer.getServer().worldServerForDimension(0).getSaveHandler().getSaveHandler();
				 
				SaveHandler sh = (SaveHandler)playerNBTManagerObj;
				File dir = (File)ObfuscationReflectionHelper.getPrivateValue(SaveHandler.class, sh, new String[] { "playersDirectory", "field_75771_c" });
				File file1 = new File(dir, id + ".book");
				File file2 = new File(dir, id + ".bookbak");
				loadBookDataFromPlayer(entityplayermp, file1, file2);
				}
			}
		}
		catch(Exception e)
		{
		}
		return entrys;
	}
	
	public static ArrayList<String> getUnlockEntrys(String playerName)
	{
		return Forestday.proxy.getPlayerData().getEntrys(playerName);
	}
	
	public static void unlockEntry(EntityPlayer player, String key)
	{
		ArrayList<String> list = getUnlockEntrys(player.getDisplayName());
		if(list == null)
		{
			list = new ArrayList();
		}
		if(!list.contains(key))
		{
			list.add(key);
			Forestday.proxy.getPlayerData().getUnlockEntrys().put(player.getDisplayName(), list);
		}
	}
	
	public static ArrayList<String> getUnlockKnowledges(String playerName)
	{
		return Forestday.proxy.getPlayerData().getKnowledges(playerName);
	}
	
	public static void unlockKnowledge(EntityPlayer player, String key)
	{
		ArrayList<String> list = getUnlockKnowledges(player.getDisplayName());
		if(list == null)
		{
			list = new ArrayList();
		}
		if(!list.contains(key))
		{
			list.add(key);
			Forestday.proxy.getPlayerData().getUnlockKnowledges().put(player.getDisplayName(), list);
		}
	}
	
	public static void loadBookDataFromPlayer(EntityPlayer player, File file, File backFile)
	{
		try {
			NBTTagCompound tag = null;
			if(file != null && file.exists())
			{
				try {
					FileInputStream stream = new FileInputStream(file);
					tag =  CompressedStreamTools.readCompressed(stream);
					stream.close();
				} catch (Exception e) {
					  e.printStackTrace();
				}
			}
			if(file == null || (!file.exists()) || tag == null || (tag.hasNoTags()))
			{
				if(backFile != null && backFile.exists())
				{
					try {
						FileInputStream stream = new FileInputStream(backFile);
						tag =  CompressedStreamTools.readCompressed(stream);
						stream.close();
					} catch (Exception e) {
						  e.printStackTrace();
					}
				}
			}
			if(tag != null)
			{
				loadEntrys(tag, player);
				loadKnowledges(tag, player);
			}
			
		} catch (Exception e) {
		}
	}
	
	public static void loadEntrys(NBTTagCompound tag, EntityPlayer player)
	{
		NBTTagList tagList = tag.getTagList("NC.BOOKDATA.ENTRYS", 10);
		for(int i = 0; i < tagList.tagCount();i++)
		{
			NBTTagCompound nbt = tagList.getCompoundTagAt(i);
			if(nbt.hasKey("key"))
			{
				unlockEntry(player, nbt.getString("key"));
			}
		}
	}
	
	public static void loadKnowledges(NBTTagCompound tag, EntityPlayer player)
	{
		NBTTagList tagList = tag.getTagList("NC.BOOKDATA.KNOWLEDGES", 10);
		for(int i = 0; i < tagList.tagCount();i++)
		{
			NBTTagCompound nbt = tagList.getCompoundTagAt(i);
			if(nbt.hasKey("key"))
			{
				unlockKnowledge(player, nbt.getString("key"));
			}
		}
	}
	
	public static void saveBookDataFromPlayer(EntityPlayer player, File file, File backFile)
	{
		try {
			NBTTagCompound tag = new NBTTagCompound();
			
			saveEntrys(tag, player);
			saveKnowledges(tag, player);
			if(file != null && file.exists())
			{
				try {
					Files.copy(file, backFile);
				} 
				catch (Exception e) {
				}
			}
			try {
				if(file != null)
				{
					FileOutputStream fileoutputstream = new FileOutputStream(file);
					CompressedStreamTools.writeCompressed(tag, fileoutputstream);
					fileoutputstream.close();
				}
			}
			catch (Exception e) {
				if(file.exists())
				{
					file.delete();
				}
			}
		} catch (Exception e) {
		}
	}
	
	public static void saveEntrys(NBTTagCompound tag, EntityPlayer player)
	{
		NBTTagList tagList = new NBTTagList();
		List list = getEntrysForPlayer(player.getDisplayName());
		if(list != null && list.size() > 0)
		{
			for(Object key : list)
			{
				if((String)key != null && BookDatas.getEntry((String)key) != null)
				{
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setString("key", (String)key);
					tagList.appendTag(nbt);
				}
			}
		}
		tag.setTag("NC.BOOKDATA.ENTRYS", tagList);
	}
	
	public static void saveKnowledges(NBTTagCompound tag, EntityPlayer player)
	{
		NBTTagList tagList = new NBTTagList();
		List list = Forestday.proxy.getPlayerData().getKnowledges(player.getDisplayName());
		if(list != null && list.size() > 0)
		{
			for(Object key : list)
			{
				if((String)key != null)
				{
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setString("key", (String)key);
					tagList.appendTag(nbt);
				}
			}
		}
		tag.setTag("NC.BOOKDATA.KNOWLEDGES", tagList);
	}
	
}
