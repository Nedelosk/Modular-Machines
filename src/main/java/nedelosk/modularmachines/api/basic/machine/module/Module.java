package nedelosk.modularmachines.api.basic.machine.module;

import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class Module implements IModule{
	
	public String modifier;
	public String[][] entryKey;
	
	public Module() {
	}
	
	public Module(String modifier) {
		this.modifier = modifier;
	}
	
	public Module(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("Modifier"))
			this.modifier = nbt.getString("Modifier");
		
		if(nbt.hasKey("entryKeys"))
		{
			NBTTagCompound nbtEntrys = nbt.getCompoundTag("entryKeys");
			entryKey = new String[3][6];
			for(int i = 0;i < 3;i++)
			{
				if(nbtEntrys.hasKey("Entrys" + i))
				{
					NBTTagList listEntry = nbtEntrys.getTagList("Entrys" + i, 10);
					for(int a = 0;a < listEntry.tagCount();a++)
					{
						NBTTagCompound nbtTag = listEntry.getCompoundTagAt(a);
						entryKey[i][a] = nbt.getString("Key");
					}
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		readFromNBT(nbt);
	}

	@Override
	public void update(IModular modular) {
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if(modifier != null)
			nbt.setString("Modifier", modifier);
		
		if(entryKey != null)
		{
			NBTTagCompound nbtEntrys = new NBTTagCompound();
			for(int i = 0;i < entryKey.length;i++)
			{
				if(entryKey[i] != null)
				{
					NBTTagList listEntry = new NBTTagList();
					for(int a = 0;a < entryKey[i].length;a++)
					{
						NBTTagCompound nbtTag = new NBTTagCompound();
						nbtTag.setString("Key", entryKey[i][a]);
						listEntry.appendTag(nbtTag);
					}
					nbtEntrys.setTag("Entrys" + i, listEntry);
				}
			}
		}
	}
	
	@Override
	public String getModifier()
	{
		return modifier;
	}

	@Override
	public String getName() {
		return "module" + getModuleName() + ((modifier != null) ? modifier : "");
	}
	
	@Override
	public String[] getTechTreeKeys(int tier) {
		if(entryKey != null)
			return entryKey[tier - 1];
		return null;
	}
	
	@Override
	public String getTechTreeKeys() {
		if(entryKey != null)
			return entryKey[0][0];
		return null;
	}
	
	@Override
	public void setTechTreeKeys(int tier, String... keys) {
		entryKey[tier - 1] = keys;
	}

}
