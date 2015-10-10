package nedelosk.modularmachines.common.modular.module.basic.energy;

import java.util.ArrayList;
import java.util.Vector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleBattery;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleCapacitor;
import nedelosk.modularmachines.api.modular.module.basic.inventory.ModuleInventory;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.IMachinePartCapacitor;
import nedelosk.modularmachines.common.modular.machines.modular.handlers.EnergyHandler;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetEnergyBar;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleBattery extends ModuleInventory implements IModuleBattery {

	public int energyStored;
	private int maxReceive;
    private int maxExtract;
    private int batteryCapacity;
    private int speedModifier;
    private int energyModifier;
	
	public ModuleBattery() {
	}
	
	public ModuleBattery(String modifier, int energyStored, int maxReceive, int maxExtract) {
		super(modifier);
		this.energyStored = energyStored;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}
	
	public ModuleBattery(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public void update(IModular modular) {
		if(batteryCapacity == 0)
			batteryCapacity = getMaxEnergyStored();
		int energyModifier = 0;
		int speedModifier = 0;
		if(ModularUtils.getModuleCapacitor(modular) != null){
			for(IModule module : ModularUtils.getModuleCapacitor(modular))
			{
				IModuleCapacitor capacitor = (IModuleCapacitor) module;
				if(capacitor.canWork(modular))
				{
					energyModifier = energyModifier + capacitor.getEnergyModifier();
					speedModifier = speedModifier + capacitor.getSpeedModifier();
				}
			}
			this.speedModifier = speedModifier;
			this.energyModifier = energyModifier;
			int capacity = batteryCapacity * energyModifier / 100;
			if(((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage().getMaxEnergyStored() != capacity)
				((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage().setCapacity(batteryCapacity + (batteryCapacity * energyModifier / 100));
		}
		else{
			if(((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage().getMaxEnergyStored() != batteryCapacity){
				((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage().setEnergyStored(batteryCapacity);
				((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage().setCapacity(batteryCapacity);
			}
		}
	}
	
	
	//Gui
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		gui.getWidgetManager().add(new WidgetEnergyBar(((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage(), 20, 8));
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}
	
	//Inventory
	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotCapacitor(modular.getMachine(), 0, 143, 17, getName()));
		list.add(new SlotCapacitor(modular.getMachine(), 1, 143, 35, getName()));
		list.add(new SlotCapacitor(modular.getMachine(), 2, 143, 53, getName()));
		return list;
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}
	
	//NBT
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("EnergyStored", energyStored);
		nbt.setInteger("MaxReceive", maxReceive);
		nbt.setInteger("MaxExtract", maxExtract);
		nbt.setInteger("BatteryCapacity", batteryCapacity);
		nbt.setInteger("speedModifier", speedModifier);
		nbt.setInteger("energyModifier", energyModifier);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.energyStored = nbt.getInteger("EnergyStored");
		this.maxReceive = nbt.getInteger("MaxReceive");
		this.maxExtract = nbt.getInteger("MaxExtract");
		batteryCapacity = nbt.getInteger("BatteryCapacity");
		speedModifier = nbt.getInteger("speedModifier");
		energyModifier = nbt.getInteger("energyModifier");
	}

	@Override
	public String getModuleName() {
		return "Battery";
	}

	@Override
	public int getMaxEnergyStored() {
		return energyStored;
	}
	
	@Override
	public int getMaxEnergyReceive() {
		return maxReceive;
	}
	
	@Override
	public int getMaxEnergyExtract() {
		return maxExtract;
	}
	
	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}
	
	public static class SlotCapacitor extends SlotModular{

		public SlotCapacitor(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, String page) {
			super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_, page);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			if(stack.getItem() instanceof IMachinePartCapacitor)
				return true;
			else if(ModuleRegistry.getModuleStack(stack) != null && ModuleRegistry.getModuleStack(stack).getModule() != null && ModuleRegistry.getModuleStack(stack).getModule() instanceof IModuleCapacitor)
				return true;
			return false;
		}
		
		@Override
		public void onSlotChanged() {
			super.onSlotChanged();
			if(((IModularTileEntity)inventory).getModular().getModules().get(page) == null || ((IModularTileEntity)inventory).getModular().getModules().get(page).size() < 3){
				Vector<ModuleStack> modules = new Vector<ModuleStack>(3);
				for(int i = 0;i < 3;i++){
					modules.add(null);
				}
				((IModularTileEntity)inventory).getModular().getModules().put(page, modules);
			}
			Vector<ModuleStack> modules = ((IModularTileEntity)inventory).getModular().getModules().get(page);
			if(getStack() == null){
				if(modules.get(getSlotIndex()) != null){
					modules.set(getSlotIndex(), null);
				}
			}else{
				if(getStack().getItem() instanceof IMachinePartCapacitor){
					((IModularTileEntity)inventory).getModular().getModules().get(page).set(getSlotIndex(), ((IMachinePartCapacitor)getStack().getItem()).buildModule(getStack()));
				}else if(ModuleRegistry.getModuleStack(getStack()) != null && ModuleRegistry.getModuleStack(getStack()).getModule() != null && ModuleRegistry.getModuleStack(getStack()).getModule() instanceof IModuleCapacitor){
					((IModularTileEntity)inventory).getModular().getModules().get(page).set(getSlotIndex(), ModuleRegistry.getModuleStack(getStack()));
				}
			}
		}
		
	}

}
