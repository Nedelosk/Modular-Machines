package nedelosk.modularmachines.api.techtree;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class TechTreeData implements IExtendedEntityProperties {

	public ArrayList<String> techEntrys = new ArrayList<String>();
	public int[] techPoints = new int[TechPointTypes.values().length];
	
	public EntityPlayer player;
	
	public TechTreeData(EntityPlayer player) {
		this.player = player;
		for(Map.Entry<String, TechTreeCategoryList> entryList : TechTreeCategories.entryCategories.entrySet())
		{
			for(TechTreeEntry entry : entryList.getValue().entrys.values())
				if(entry.isAutoUnlock())
					if(!techEntrys.contains(entry.key))
						techEntrys.add(entry.key);
		}
	}
	
	public TechTreeData(EntityPlayer player, String techEntrys) {
		this.player = player;
		this.techEntrys.add(techEntrys);
	}
	
	public TechTreeData(EntityPlayer player, int points, TechPointTypes type) {
		this.player = player;
		this.techPoints[type.ordinal()]+= points;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagList tagList = new NBTTagList();
		if(techEntrys != null && techEntrys.size() > 0)
		{
			for(Object key : techEntrys)
			{
				if(key != null && TechTreeCategories.getEntry((String) key) != null)
				{
					if(TechTreeCategories.getEntry((String) key) != null && (!TechTreeCategories.getEntry((String) key).isAutoUnlock()))
					{
						NBTTagCompound tagCompound = new NBTTagCompound();
						tagCompound.setString("key", (String)key);
						tagList.appendTag(tagCompound);
					}
				}
			}
		}
		compound.setTag("TREETECH.ENTRYS", tagList);
		for(int i = 0;i < TechPointTypes.values().length;i++)
		{
			int o = techPoints[i];
			compound.setInteger(TechPointTypes.values()[i].name(), o);
		}
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagList tagList = compound.getTagList("TREETECH.ENTRYS", 10);
		for(int i = 0;i < tagList.tagCount();i++)
		{
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			if(tagCompound.hasKey("key"))
			{
				if(!techEntrys.contains(tagCompound.getString("key")))
					techEntrys.add(tagCompound.getString("key"));
			}
		}
		for(TechTreeCategoryList list :TechTreeCategories.entryCategories.values())
		{
			for(TechTreeEntry entry : list.entrys.values())
				if(entry.isAutoUnlock())
					techEntrys.add(entry.key);
		}
		for(TechPointTypes type : TechPointTypes.values())
		{
			techPoints[type.ordinal()] = compound.getInteger(type.name());
		}
	}

	@Override
	public void init(Entity entity, World world) {
		
	}

}
