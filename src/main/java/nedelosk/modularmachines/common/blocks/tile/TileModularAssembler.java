package nedelosk.modularmachines.common.blocks.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import nedelosk.modularmachines.api.IModularAssembler;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.RendererSides;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModule;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.api.modular.module.ModuleItem;
import nedelosk.modularmachines.api.modular.module.ModuleStack;
import nedelosk.modularmachines.client.gui.assembler.GuiModularAssembler;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import nedelosk.modularmachines.common.modular.ModularMachine;
import nedelosk.nedeloskcore.common.blocks.tile.TileBase;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

public class TileModularAssembler extends TileBaseInventory implements IEnergyHandler, IModularAssembler {

	public HashMap<String, ItemStack[]> slot;
	public String page;
	public HashMap<String, ArrayList<ModuleEntry>> moduleEntrys = new HashMap<String, ArrayList<ModuleEntry>>();
	public int capacity;
	public EnergyStorage storage;
	public int timer;
	public int timerTotal = 35;
	
	public TileModularAssembler() {
		super(1);
		if(slot == null)
		{
			slot = new HashMap<String, ItemStack[]>();
			for(Map.Entry<String, ArrayList<ModuleEntry>> entry : ModularMachinesApi.moduleEntrys.entrySet())
			{
				slot.put(entry.getKey(), new ItemStack[entry.getValue().size()]);
			}
		}
		
		for(Map.Entry<String, ArrayList<ModuleEntry>> entrys : ModularMachinesApi.moduleEntrys.entrySet())
		{
			this.moduleEntrys.put(entrys.getKey(), entrys.getValue());
		}
		
		if(page == null)
			page = "Basic";
	}
	
	public ModularMachine buildMachine()
	{
		ArrayList<ModuleStack> moduleStacks = new ArrayList<ModuleStack>();
		ArrayList<String> modules = new ArrayList<String>();
		for(Map.Entry<String, ItemStack[]> entry : slot.entrySet())
		{
			for(int i = 0;i < entry.getValue().length;i++)
			{
				ItemStack stack = entry.getValue()[i];
				ModuleEntry entryModule = moduleEntrys.get(entry.getKey()).get(i);
				if(entryModule.isActivate && entryModule.canActivate)
				{
					ModuleItem module = ModularMachinesApi.getModuleItem(stack);
					if(module != null)
					{
						moduleStacks.add(new ModuleStack(module.getModule(), module.getTier()));
						modules.add(module.getModuleName());
					}
				}
			}
		}
		
		for(String module : ModularMachinesApi.getRequiredModule())
		{
			if(!modules.contains(module))
				return null;
		}
		return ModularMachine.crateModularMachine(moduleStacks);
	}

	@Override
	public String getMachineTileName() {
		return null;
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerModularAssembler(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiModularAssembler(this, inventory);
	}
	
	public void updateEntrys()
	{
		for(Map.Entry<String, ArrayList<ModuleEntry>> entrys : moduleEntrys.entrySet())
		{
			for(ModuleEntry entry : entrys.getValue())
			{
				entry.activateEntry(getStackInSlot(entry.page, entry.ID), entry.parent == null ? null : getStackInSlot(entry.parent.page, entry.parent.ID), this);
			}
		}
	}

	@Override
	public void updateClient() {
	}

	@Override
	public void updateServer() {
		if(this.timer >= timerTotal)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			updateEntrys();
		}
		else
			timer++;
	}
	
	public ItemStack getStackInSlot(String page, int i) {
		return this.slot.get(page)[i];
	}
	
	public ItemStack decrStackSize(String page, int i, int amount) {
		if(this.slot.get(page)[i] != null){
			ItemStack itemstack;
				
		    if(this.slot.get(page)[i].stackSize <= amount){
		    	itemstack = this.slot.get(page)[i];
		    	this.slot.get(page)[i] = null;
		    	return itemstack;
		    }
		    else{
		    	itemstack = this.slot.get(page)[i].splitStack(amount);
		    	
		    	if(this.slot.get(page)[i].stackSize == 0){
		    		this.slot.get(page)[i] = null;
		    	}
		    }
		}
		
		return null;
		
	}
	
	public void setInventorySlotContents(String page, int i, ItemStack itemstack) {
		this.slot.get(page)[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()){
			itemstack.stackSize = this.getInventoryStackLimit();
		}
		
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int p_102008_3_) 
	{
		return false;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(Map.Entry<String, ItemStack[]> entry : slot.entrySet())
		{
			NBTTagList nbtTagList = new NBTTagList();
			for(int i = 0; i< entry.getValue().length; i++){
				if (entry.getValue()[i] != null){
					NBTTagCompound item = new NBTTagCompound();
					item.setByte("item", (byte)i);
					entry.getValue()[i].writeToNBT(item);
					nbtTagList.appendTag(item);
				}
			}
			nbt.setTag("slot" + entry.getKey(), nbtTagList);
		}
		
		for(Map.Entry<String, ArrayList<ModuleEntry>> moduleEntry : moduleEntrys.entrySet())
		{
			NBTTagList list = new NBTTagList();
			for(ModuleEntry entry : moduleEntry.getValue())
			{
				NBTTagCompound nbtTag = new NBTTagCompound();
				entry.writeToNBT(nbtTag);
				list.appendTag(nbtTag);
			}
			nbt.setTag("module" + moduleEntry.getKey(), list);
		}
		
		nbt.setString("Page", page);
		
		nbt.setInteger("Capacity", capacity);
		NBTTagCompound nbtTag = new NBTTagCompound();
		storage.writeToNBT(nbtTag);
		nbt.setTag("Storage", nbtTag);
		
		nbt.setInteger("Timer", timer);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(Map.Entry<String, ItemStack[]> entry : slot.entrySet())
		{
		NBTTagList nbtTagList = nbt.getTagList("slot" + entry.getKey(), 10);
		
		for(int i = 0; i < nbtTagList.tagCount(); i++){
			NBTTagCompound item = nbtTagList.getCompoundTagAt(i);
			byte itemLocation = item.getByte("item");
			slot.get(entry.getKey())[itemLocation] = ItemStack.loadItemStackFromNBT(item);
		}
		}
		
		for(Map.Entry<String, ArrayList<ModuleEntry>> entrys : ModularMachinesApi.moduleEntrys.entrySet())
		{
			if(nbt.hasKey("module" +entrys.getKey()))
			{
				ArrayList<ModuleEntry> entryList = new ArrayList<ModuleEntry>();
				NBTTagList list = nbt.getTagList("module" + entrys.getKey(), 10);
				for(int i = 0;i < list.tagCount();i++)
				{
					NBTTagCompound nbtTag = list.getCompoundTagAt(i);
					entryList.add(new ModuleEntry(nbtTag));
				}
				this.moduleEntrys.put(entrys.getKey(), entryList);
			}
			else
			{
				this.moduleEntrys.put(entrys.getKey(), entrys.getValue());
			}
		}
		
		page = nbt.getString("Page");
		capacity = nbt.getInteger("Capacity");
		storage = new EnergyStorage(nbt.getInteger("Capacity"));
		NBTTagCompound nbtTag = nbt.getCompoundTag("Storage");
		storage.readFromNBT(nbtTag);
		
		this.timer = nbt.getInteger("Timer");
		
	}
	
	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
		storage = new EnergyStorage(capacity);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if(from == ForgeDirection.DOWN)
			return true;
		return false;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}

	public IEnergyStorage getStorage() {
		return storage;
	}

}
