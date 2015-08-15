package nedelosk.modularmachines.api.basic.modular.module;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.basic.modular.IModularAssembler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ModuleEntry{
	
	public int x;
	public int y;
	public ModuleEntry parent;
	public String parentPage;
	public int parentID;
	public String[] moduleNames;
	public boolean[] activatedModuleNames;
	public int ID;
	public String page;
	public boolean isActivate;
	public boolean canActivate;
	
	public ModuleEntry(int x, int y, String page, String... moduleNames) {
		this.x = x + 12;
		this.y = y + 12;
		this.ID = ModularMachinesApi.moduleEntrys.get(page).size();
		this.moduleNames = moduleNames;
		this.page = page;
		this.canActivate = true;
		activatedModuleNames = new boolean[moduleNames.length];
		for(int i = 0;i < moduleNames.length;i++)
		{
			activatedModuleNames[i] = true;
		}
	}
	
	public void activateModuleName(String moduleName)
	{
		for(int i = 0;i < moduleNames.length;i++)
		{
			String name = moduleNames[i];
			if(name.equals(moduleName))
				activatedModuleNames[i] = true;
				
		}
	}
	
	public void deactivateModuleName(String moduleName)
	{
		for(int i = 0;i < moduleNames.length;i++)
		{
			String name = moduleNames[i];
			if(name.equals(moduleName))
				activatedModuleNames[i] = false;
				
		}
	}
	
	public ModuleEntry(NBTTagCompound nbt, IModularAssembler modular) {
		readFromNBT(nbt, modular);
	}
	
	public ModuleEntry setPage(String page) {
		this.page = page;
		return this;
	}
	
	public ModuleEntry setParent(ModuleEntry parent) {
		this.parent = parent;
		this.parentPage = parent.page;
		this.parentID = parent.ID;
		return this;
	}
	
	public void onActivate(IModularAssembler assembler)
	{
		
	}
	
	public void onDeactivate(IModularAssembler assembler)
	{
		
	}
	
	public void onActivateItem(IModularAssembler assembler)
	{
		
	}
	
	public void onDeactivateItem(IModularAssembler assembler)
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
			if(assembler.getStackInSlot(page, ID) != null)
				onActivateItem(assembler);
			else
				onDeactivateItem(assembler);
			isActivate = true;
			onActivate(assembler);
		}
		}
		else
		{
			ModuleEntry parent = assembler.getModuleEntry(parentPage, parentID);
			if(!parent.isActivate || !parent.canActivate || !canActivate)
			{
				if(isActivate)
					onDeactivate(assembler);
				isActivate = false;
			}
			else if(itemParent == null)
			{
				if(isActivate)
					onDeactivate(assembler);
				isActivate = false;
			}
			else{
				if(assembler.getStackInSlot(page, ID) != null)
					onActivateItem(assembler);
				else
					onDeactivateItem(assembler);
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
	
	public int getTier(IModularAssembler assembler) {
		if(assembler.getStackInSlot(page, ID) != null)
			for(int i= 0;i < moduleNames.length;i++)
			{
				if(activatedModuleNames[i])
				{
					String moduleName = moduleNames[i];
					if(ModularMachinesApi.getModuleItem(moduleName, assembler.getStackInSlot(page, ID)) != null)
						return ModularMachinesApi.getModuleItem(moduleName, assembler.getStackInSlot(page, ID)).getTier();
				}
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
		NBTTagList listModuleNames = new NBTTagList();
		for(int i = 0;i < moduleNames.length;i++)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			nbtTag.setString("ModuleName", moduleNames[i]);
			listModuleNames.appendTag(nbtTag);
		}
		nbt.setTag("ModuleNames", listModuleNames);
		NBTTagList listModuleNamesActivated = new NBTTagList();
		for(int i = 0;i < activatedModuleNames.length;i++)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			nbtTag.setBoolean("ModuleName", activatedModuleNames[i]);
			listModuleNamesActivated.appendTag(nbtTag);
		}
		nbt.setTag("ModuleNamesActivated", listModuleNamesActivated);
		nbt.setBoolean("isActivate", isActivate);
		nbt.setBoolean("canActivate", canActivate);
		if(parentPage != null)
		{
			NBTTagCompound nbtParent = new NBTTagCompound();
			nbtParent.setString("parentPage", parentPage);
			nbtParent.setInteger("parentID", parentID);
			nbt.setTag("Parent", nbtParent);
		}
	}
	
	public void readFromNBT(NBTTagCompound nbt, IModularAssembler modular)
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
		NBTTagList listModuleNamesActivated = nbt.getTagList("ModuleNamesActivated", 10);
		activatedModuleNames = new boolean[listModuleNamesActivated.tagCount()];
		for(int i = 0;i < listModuleNamesActivated.tagCount();i++)
		{
			NBTTagCompound nbtTag = listModuleNamesActivated.getCompoundTagAt(i);
			activatedModuleNames[i] = nbtTag.getBoolean("ModuleName");
		}
		isActivate = nbt.getBoolean("isActivate");
		canActivate = nbt.getBoolean("canActivate");
		if(nbt.hasKey("Parent"))
		{
			NBTTagCompound nbtTag = nbt.getCompoundTag("Parent");
			parentPage = nbtTag.getString("parentPage");
			parentID = nbtTag.getInteger("parentID");
			parent = modular.getModuleEntry(parentPage, parentID);
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
