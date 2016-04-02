package de.nedelosk.techtree.api;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class TechTreePlayerData implements IExtendedEntityProperties {

	public ArrayList<String> techEntrys = new ArrayList<String>();
	public ArrayList<ItemStack> craftetTechPoints = new ArrayList<ItemStack>();
	public int[] techPoints = new int[TechPointTypes.values().length];
	public EntityPlayer player;

	public TechTreePlayerData(EntityPlayer player) {
		this.player = player;
		for ( Map.Entry<String, TechTreeCategoryList> entryList : TechTreeCategories.entryCategories.entrySet() ) {
			for ( TechTreeEntry entry : entryList.getValue().entrys.values() ) {
				if (entry.isAutoUnlock()) {
					if (!techEntrys.contains(entry.key)) {
						techEntrys.add(entry.key);
					}
				}
			}
		}
	}

	public TechTreePlayerData(EntityPlayer player, String techEntrys) {
		this.player = player;
		this.techEntrys.add(techEntrys);
	}

	public TechTreePlayerData(EntityPlayer player, int points, TechPointTypes type) {
		this.player = player;
		this.techPoints[type.ordinal()] += points;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagList tagList = new NBTTagList();
		if (techEntrys != null && techEntrys.size() > 0) {
			for ( Object key : techEntrys ) {
				if (key != null && TechTreeCategories.getEntry((String) key) != null) {
					if (TechTreeCategories.getEntry((String) key) != null && (!TechTreeCategories.getEntry((String) key).isAutoUnlock())) {
						NBTTagCompound tagCompound = new NBTTagCompound();
						tagCompound.setString("key", (String) key);
						tagList.appendTag(tagCompound);
					}
				}
			}
		}
		compound.setTag("TREETECH.ENTRYS", tagList);
		for ( int i = 0; i < TechPointTypes.values().length; i++ ) {
			int o = techPoints[i];
			compound.setInteger(TechPointTypes.values()[i].name(), o);
		}
		NBTTagList items = new NBTTagList();
		for ( int i = 0; i < craftetTechPoints.size(); i++ ) {
			ItemStack stack = craftetTechPoints.get(i);
			NBTTagCompound nbtTag = new NBTTagCompound();
			stack.writeToNBT(nbtTag);
			items.appendTag(nbtTag);
		}
		compound.setTag("TREETECH.CRAFTET.ITEMS", items);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagList tagList = compound.getTagList("TREETECH.ENTRYS", 10);
		for ( int i = 0; i < tagList.tagCount(); i++ ) {
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			if (tagCompound.hasKey("key")) {
				if (!techEntrys.contains(tagCompound.getString("key"))) {
					techEntrys.add(tagCompound.getString("key"));
				}
			}
		}
		for ( TechTreeCategoryList list : TechTreeCategories.entryCategories.values() ) {
			for ( TechTreeEntry entry : list.entrys.values() ) {
				if (entry.isAutoUnlock()) {
					techEntrys.add(entry.key);
				}
			}
		}
		for ( TechPointTypes type : TechPointTypes.values() ) {
			techPoints[type.ordinal()] = compound.getInteger(type.name());
		}
		NBTTagList items = compound.getTagList("TREETECH.CRAFTET.ITEMS", 10);
		for ( int i = 0; i < items.tagCount(); i++ ) {
			NBTTagCompound nbtTag = items.getCompoundTagAt(i);
			ItemStack stack = ItemStack.loadItemStackFromNBT(nbtTag);
			craftetTechPoints.add(stack);
		}
	}

	@Override
	public void init(Entity entity, World world) {
	}
}
