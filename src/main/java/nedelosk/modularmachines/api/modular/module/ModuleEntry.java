package nedelosk.modularmachines.api.modular.module;

import nedelosk.modularmachines.api.IModularAssembler;
import nedelosk.modularmachines.api.ModularMachinesApi;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ModuleEntry{
	
	public int x;
	public int y;
	public ModuleEntry parent;
	public String[] moduleNames;
	public int ID;
	public String page;
	public boolean isActivate;
	public boolean canActivate;
	public ItemStack item;
	
	public ModuleEntry(int x, int y, String page, String... moduleNames) {
		this.x = x + 12;
		this.y = y + 12;
		this.ID = ModularMachinesApi.moduleEntrys.get(page).size();
		this.moduleNames = moduleNames;
		this.page = page;
		this.canActivate = true;
	}
	
	public ModuleEntry(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}
	
	public ModuleEntry setPage(String page) {
		this.page = page;
		return this;
	}
	
	public ModuleEntry setParent(ModuleEntry parent) {
		this.parent = parent;
		return this;
	}
	
	public void onActivate(IModularAssembler assembler)
	{
		
	}
	
	public void onDeactivate(IModularAssembler assembler)
	{
		
	}
	
	public boolean activateEntry(ItemStack stack, ItemStack itemParent, IModularAssembler assembler)
	{
		if(parent == null)
		{
		if(!canActivate)
		{
			if(isActivate)
				onDeactivate(assembler);
			isActivate = false;
		}
		else
		{
			item = stack;
			isActivate = true;
			onActivate(assembler);
		}
		}
		else
		{
			if(!parent.canActivate || !parent.isActivate)
			{
				if(isActivate)
					onDeactivate(assembler);
				canActivate = false;
				isActivate = false;
			}
			else if(itemParent == null)
			{
				if(isActivate)
					onDeactivate(assembler);
				isActivate = false;
			}
			else{
				item = stack;
				isActivate = true;
				onActivate(assembler);
			}
		}
		return isActivate;
	}
	
	public void setCanActivate(boolean canActivate) {
		this.canActivate = canActivate;
	}
	
	public boolean isActivate() {
		return isActivate;
	}
	
	public int getTier() {
		if(item != null)
			for(String moduleName : moduleNames)
			{
				if(ModularMachinesApi.getModuleItem(moduleName, item) != null)
					return ModularMachinesApi.getModuleItem(moduleName, item).getTier();
			}
		return 0;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("ID", this.ID);
		nbt.setString("page", this.page);
		nbt.setInteger("x", this.x);
		nbt.setInteger("y", this.y);
		NBTTagList list = new NBTTagList();
		for(int i = 0;i < moduleNames.length;i++)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			nbtTag.setString("ModuleName", moduleNames[i]);
			list.appendTag(nbtTag);
		}
		nbt.setTag("ModuleNames", list);
		nbt.setBoolean("isActivate", isActivate);
		nbt.setBoolean("canActivate", canActivate);
		if(item != null)
		{
		NBTTagCompound nbtTag = new NBTTagCompound();
		item.writeToNBT(nbtTag);
		nbt.setTag("Item", nbtTag);
		}
		if(parent != null)
		{
			NBTTagCompound nbtParent = new NBTTagCompound();
			parent.writeToNBT(nbtParent);
			nbt.setTag("Parent", nbtParent);
		}
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		this.ID = nbt.getInteger("ID");
		this.page = nbt.getString("page");
		this.x = nbt.getInteger("x");
		this.y = nbt.getInteger("y");
		NBTTagList list = nbt.getTagList("ModuleNames", 10);
		moduleNames = new String[list.tagCount()];
		for(int i = 0;i < list.tagCount();i++)
		{
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			moduleNames[i] = nbtTag.getString("ModuleName");
		}
		isActivate = nbt.getBoolean("isActivate");
		canActivate = nbt.getBoolean("canActivate");
		if(nbt.hasKey("Item"))
		{
		NBTTagCompound nbtTag = nbt.getCompoundTag("Item");
		item = ItemStack.loadItemStackFromNBT(nbtTag);
		}
		if(nbt.hasKey("Parent"))
		{
			NBTTagCompound nbtParent = nbt.getCompoundTag("Parent");
			parent = new ModuleEntry(nbtParent);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ModuleEntry)
		{
			ModuleEntry entry = (ModuleEntry) obj;
			if(this.page.equals(entry.page) && this.ID == entry.ID)
				return true;
		}
		return false;
	}
	
}
