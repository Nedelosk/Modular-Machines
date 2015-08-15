package nedelosk.modularmachines.common.network.packets.saver;

import java.util.ArrayList;

import nedelosk.modularmachines.api.basic.modular.module.ModuleEntry;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ModularSaveModule implements IExtendedEntityProperties {

	public ModuleEntry entry;
	public ArrayList<ModularTileEntitySave> saver = new ArrayList<ModularTileEntitySave>();
	
	public ModularSaveModule(ModuleEntry entry) {
		this.entry = entry;
	}
	
	public ModularSaveModule(ModularTileEntitySave save) {
		saver.add(save);
	}
	
	public ModularTileEntitySave getSave(int x, int y, int z) {
		for(ModularTileEntitySave save : saver)
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
		for(ModularTileEntitySave save : saver)
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
			saver.add(new ModularTileEntitySave(nbtTag));
		}
		}
	}

	@Override
	public void init(Entity entity, World world) {
		
	}

}
