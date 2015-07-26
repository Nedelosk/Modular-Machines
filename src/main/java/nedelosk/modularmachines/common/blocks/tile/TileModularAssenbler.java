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

public class TileModularAssenbler extends TileBaseInventory implements IEnergyHandler, IModularAssembler {

	public HashMap<String, ItemStack[]> slot;
	public String page;
	public HashMap<String, ArrayList<ModuleEntry>> moduleEntrys;
	public int capacity;
	public EnergyStorage storage;
	
	public TileModularAssenbler() {
		super(1);
		if(slot == null)
		{
			slot = new HashMap<String, ItemStack[]>();
			for(Map.Entry<String, ArrayList<ModuleEntry>> entry : ModularMachinesApi.moduleEntrys.entrySet())
			{
				slot.put(entry.getKey(), new ItemStack[entry.getValue().size()]);
			}
		}
		
		moduleEntrys = new HashMap<String, ArrayList<ModuleEntry>>();
		for(Map.Entry<String, ArrayList<ModuleEntry>> entry : ModularMachinesApi.moduleEntrys.entrySet())
		{
			moduleEntrys.put(entry.getKey(), entry.getValue());
		}
		
		if(page == null)
			page = "Basic";
	}
	
	public ModularMachine buildMachine()
	{
		ArrayList<ModuleStack> moduleStacks = new ArrayList<ModuleStack>();
		ArrayList<String> modules = new ArrayList<String>();
		for(ItemStack[] stacks : slot.values())
		{
			for(ItemStack stack : stacks)
			{
				ModuleItem module = ModularMachinesApi.getModuleItem(stack);
				if(module != null)
				{
					moduleStacks.add(new ModuleStack(module.getModule(), module.getTier()));
					modules.add(module.getModuleName());
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
	
	public void buildMachine(ArrayList<String> list){
		ArrayList<String> modules = new ArrayList<String>();
		for(ItemStack[] stacks : slot.values())
		{
			for(ItemStack stack : stacks)
			{
				ModuleItem module = ModularMachinesApi.getModuleItem(stack);
				if(module != null)
				{
					modules.add(module.getModuleName());
				}
			}
		}
		
		for(String module : list)
		{
			if(modules.contains(module))
				list.remove(module);
		}
	}
	
    public ModuleEntry getModuleEntry(String page, int ID)
    {
    	ArrayList<ModuleEntry> entrys = moduleEntrys.get(page);
    	for(ModuleEntry entry : moduleEntrys.get(page))
    	{
    		if(entry.ID == ID && entry.page.equals(page))
    			return entry;
    	}
    	return null;
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

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
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
		
		/*for(Map.Entry<String, ArrayList<ModuleEntry>> ModuleEntry : moduleEntrys.entrySet())
		{	NBTTagList nbtTagList = new NBTTagList();
			for(ModuleEntry entry : ModuleEntry.getValue())
			{
				NBTTagCompound nbtTag = new NBTTagCompound();
				entry.writeToNBT(nbtTag);
				nbtTagList.appendTag(nbtTag);
			}
			nbt.setTag(ModuleEntry.getKey(), nbtTagList);
		}*/
		nbt.setString("Page", page);
		
		nbt.setInteger("Capacity", capacity);
		NBTTagCompound nbtTag = new NBTTagCompound();
		storage.writeToNBT(nbtTag);
		nbt.setTag("Storage", nbtTag);
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
		
		page = nbt.getString("Page");
		capacity = nbt.getInteger("Capacity");
		storage = new EnergyStorage(nbt.getInteger("Capacity"));
		NBTTagCompound nbtTag = nbt.getCompoundTag("Storage");
		storage.readFromNBT(nbtTag);
		
	}
	
	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
		storage = new EnergyStorage(capacity);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
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
