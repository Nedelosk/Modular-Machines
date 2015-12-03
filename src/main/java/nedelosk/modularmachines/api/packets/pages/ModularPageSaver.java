package nedelosk.modularmachines.api.packets.pages;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ModularPageSaver implements IExtendedEntityProperties {

	public ArrayList<ModularPageTileSaver> saver = new ArrayList<ModularPageTileSaver>();
	
	public ModularPageSaver(ModularPageTileSaver save) {
		saver.add(save);
	}
	
	public ModularPageTileSaver getSave(int x, int y, int z) {
		for(ModularPageTileSaver save : saver)
		{
			if(save.x == x && save.y == y && save.z == z)
				return save;
		}
		return null;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		
		if(saver.size() > 0)
		{
			NBTTagList list = new NBTTagList();
			for(ModularPageTileSaver save : saver)
			{
				NBTTagCompound nbtTag = new NBTTagCompound();
				save.writeToNBT(nbtTag);
				list.appendTag(nbtTag);
			}
			compound.setTag("Saver", list);
		}
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		
		if(compound.hasKey("Saver"))
		{
			NBTTagList list = compound.getTagList("Saver", 10);
			for(int i = 0;i < list.tagCount();i++)
			{
				NBTTagCompound nbtTag = list.getCompoundTagAt(i);
				saver.add(new ModularPageTileSaver(nbtTag));
			}
		}
	}

	@Override
	public void init(Entity entity, World world) {
		
	}

}