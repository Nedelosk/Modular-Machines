package nedelosk.modularmachines.common.blocks.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.auth.GGSSchemeBase;

import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import nedelosk.modularmachines.api.modular.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.IModule;
import nedelosk.modularmachines.api.modular.module.IModuleGui;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.api.modular.module.ModuleStack;
import nedelosk.modularmachines.api.modular.module.manager.IModuleStorageManager;
import nedelosk.modularmachines.client.gui.machine.GuiModularMachine;
import nedelosk.modularmachines.client.proxy.ClientProxy;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.inventory.machine.ContainerModularMachine;
import nedelosk.modularmachines.common.modular.ModularMachine;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularMachineNBT;
import nedelosk.modularmachines.common.network.packets.saver.ModularSaveModule;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileModularMachine extends TileBaseInventory implements IEnergyHandler, IFluidHandler, IModularTileEntity {

	public ModularMachine machine;
	public HashMap<String, ItemStack[]> slot = new HashMap<String, ItemStack[]>();
	public String page;
	
	public TileModularMachine()
	{
		super(0);
	}
	
	@Override
	public String getMachineTileName() {
		return "modular.machine";
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		machine.writeToNBT(nbt);
		
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
		
		nbt.setString("Page", page);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		page = nbt.getString("Page");
		
		machine = new ModularMachine(nbt, this);
		
		slot = new HashMap<String, ItemStack[]>();
		for(ModuleStack stack : machine.modules)
		{
			slot.put(stack.getModule().getName(), new ItemStack[64]);
		}
		
		for(Map.Entry<String, ItemStack[]> entry : slot.entrySet())
		{
		NBTTagList nbtTagList = nbt.getTagList("slot" + entry.getKey(), 10);
		
		for(int i = 0; i < nbtTagList.tagCount(); i++){
			NBTTagCompound item = nbtTagList.getCompoundTagAt(i);
			byte itemLocation = item.getByte("item");
			slot.get(entry.getKey())[itemLocation] = ItemStack.loadItemStackFromNBT(item);
		}
		}
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		//if(!worldObj.isRemote)
			//PacketHandler.INSTANCE.sendTo(new PacketModularMachineNBT(this), (EntityPlayerMP) inventory.player);
		return new ContainerModularMachine(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		if(inventory.player.getExtendedProperties(ModularSaveModule.class.getName()) != null)
			if(((ModularSaveModule)inventory.player.getExtendedProperties(ModularSaveModule.class.getName())).getSave(xCoord, yCoord, zCoord) != null)
				this.page = ((ModularSaveModule)inventory.player.getExtendedProperties(ModularSaveModule.class.getName())).getSave(xCoord, yCoord, zCoord).page;
			else
				page = getModuleGuis().get(0).getModule().getName();
		return new GuiModularMachine(this, inventory);
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		//worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		machine.update();
		
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		return machine.receiveEnergy(from, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,
			boolean simulate) {
		return machine.extractEnergy(from, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return machine.getEnergyStored(from);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return machine.getMaxEnergyStored(from);
	}

	public void setMachine(NBTTagCompound tagCompound) {
		machine = new ModularMachine(tagCompound, this);
		if(page == null)
			page = getModuleGuis().get(0).getModule().getName();
		slot = new HashMap<String, ItemStack[]>();
		for(ModuleStack stack : machine.modules)
		{
			slot.put(stack.getModule().getName(), new ItemStack[64]);
		}
	}

	public ArrayList<ModuleStack> getModuleGuis() {
		ArrayList<ModuleStack> guis = new ArrayList<ModuleStack>();
		for(ModuleStack module : machine.modules)
		{
			if(module.getModule() instanceof IModuleGui)
				guis.add(module);
		}
		return guis;
	}
	
	public ModuleStack getModuleGui()
	{
		for(ModuleStack module : getModuleGuis())
		{
			if(module.getModule().getName().equals(page))
				return module;
		}
		return null;
	}

	@Override
	public int getXCoord() {
		return xCoord;
	}

	@Override
	public int getYCoord() {
		return yCoord;
	}

	@Override
	public int getZCoord() {
		return zCoord;
	}
	
	public int getSizeInventory(String page) {
		return slot.get(page).length;
	}

	
	public ItemStack getStackInSlotOnClosing(String page, int i) {
		if(this.slot.get(page)[i] != null){
			ItemStack itemstack = this.slot.get(page)[i];
			this.slot.get(page)[i] = null;
			return itemstack;
		};
		return null;
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
	
	public boolean addToOutput(ItemStack output, int slotMin, int slotMax, String page) {
		if (output == null) return true;
		
		for(int i = slotMin; i < slotMax; i++){
			ItemStack itemStack = getStackInSlot(page, i);
			if (itemStack == null){
				setInventorySlotContents(page, i, output); 
				return true;
			}
			else{
				if (itemStack.getItem() == output.getItem() && itemStack.getItemDamage() == output.getItemDamage()){
					if (itemStack.stackSize < itemStack.getMaxStackSize()){
						int avaiableSpaceOnStack = itemStack.getMaxStackSize() - itemStack.stackSize;
						if (avaiableSpaceOnStack >= output.stackSize){
							itemStack.stackSize = itemStack.stackSize + output.stackSize;
							setInventorySlotContents(page, i, itemStack);
							return true;
						}else{
							return false;
						}
					}
				}
			}
		}
		return false;		
	}
	
	
	public void setInventorySlotContents(String page, int i, ItemStack itemstack) {
		this.slot.get(page)[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()){
			itemstack.stackSize = this.getInventoryStackLimit();
		}
		
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(machine.fluidHandler != null)
			return machine.fluidHandler.fill(from, resource, doFill);
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(machine.fluidHandler != null)
			return machine.fluidHandler.drain(from, resource, doDrain);
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(machine.fluidHandler != null)
			return machine.fluidHandler.drain(from, maxDrain, doDrain);
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(machine.fluidHandler != null)
			return machine.fluidHandler.canFill(from, fluid);
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if(machine.fluidHandler != null)
			return machine.fluidHandler.canDrain(from, fluid);
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if(machine.fluidHandler != null)
			return machine.fluidHandler.getTankInfo(from);
		return null;
	}

}
