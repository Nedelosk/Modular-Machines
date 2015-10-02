package nedelosk.modularmachines.common.machines.module.energy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleCapacitor;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEnergyManager;
import nedelosk.modularmachines.api.modular.module.basic.gui.ModuleGui;
import nedelosk.modularmachines.common.machines.handler.EnergyHandler;
import nedelosk.modularmachines.common.machines.utils.ModularUtils;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetEnergyBar;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleEnergyManager extends ModuleGui implements IModuleEnergyManager {

	public int batteryCapacity;
	public int speedModifier;
	public int energyModifier;
	
	public ModuleEnergyManager() {
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

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		gui.getWidgetManager().add(new WidgetEnergyBar(((EnergyHandler)modular.getManager().getEnergyHandler()).getStorage(), 82 , 12));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("BatteryCapacity", batteryCapacity);
		nbt.setInteger("speedModifier", speedModifier);
		nbt.setInteger("energyModifier", energyModifier);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		batteryCapacity = nbt.getInteger("BatteryCapacity");
		speedModifier = nbt.getInteger("speedModifier");
		energyModifier = nbt.getInteger("energyModifier");
	}

	@Override
	public String getModuleName() {
		return "EnergyManager";
	}
	
	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}

}
