package nedelosk.modularmachines.common.modular.module.basic.energy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleBattery;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleCapacitor;
import nedelosk.modularmachines.api.modular.module.basic.gui.ModuleGui;
import nedelosk.modularmachines.common.modular.machines.modular.handlers.EnergyHandler;
import nedelosk.modularmachines.common.modular.utils.ModularUtils;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetEnergyBar;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleBattery extends ModuleGui implements IModuleBattery {

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
			batteryCapacity = ModularUtils.getModuleBattery(modular).getMaxEnergyStored();
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
	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		gui.getWidgetManager().add(new WidgetEnergyBar(((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage(), 82 , 12));
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


}
