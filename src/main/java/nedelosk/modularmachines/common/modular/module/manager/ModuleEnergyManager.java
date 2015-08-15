package nedelosk.modularmachines.common.modular.module.manager;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.basic.modular.IModular;
import nedelosk.modularmachines.api.basic.modular.module.Module;
import nedelosk.modularmachines.api.basic.modular.module.energy.IModuleCapacitor;
import nedelosk.modularmachines.api.basic.modular.module.manager.IModuleEnergyManager;
import nedelosk.modularmachines.api.basic.modular.module.recipes.NeiStack;
import nedelosk.modularmachines.common.modular.ModularMachine;
import nedelosk.modularmachines.common.modular.handler.EnergyHandler;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetEnergyBar;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleEnergyManager extends Module implements IModuleEnergyManager {

	public int batteryCapacity;
	public int speedModifier;
	public int energyModifier;
	
	public ModuleEnergyManager() {
	}
	
	@Override
	public void update(IModular modular) {
		if(batteryCapacity == 0)
			batteryCapacity = modular.getBattery().getMaxEnergyStored();
		int energyModifier = 0;
		int speedModifier = 0;
		if(modular.getCapacitors() != null){
		for(IModuleCapacitor module : modular.getCapacitors())
		{
			if(module != null)
				if(module.canWork(modular))
				{
					energyModifier = energyModifier + module.getEnergyModifier();
					speedModifier = speedModifier + module.getSpeedModifier();
				}
		}
		this.speedModifier = speedModifier;
		this.energyModifier = energyModifier;
		((EnergyHandler)((ModularMachine)modular).getEnergyHandler()).getStorage().setCapacity(batteryCapacity + (batteryCapacity * energyModifier / 100));
		}
		else{
			((EnergyHandler)((ModularMachine)modular).getEnergyHandler()).getStorage().setEnergyStored(batteryCapacity);
			((EnergyHandler)((ModularMachine)modular).getEnergyHandler()).getStorage().setCapacity(batteryCapacity);
		}
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular) {
		return null;
	}
	
	@Override
	public ArrayList<NeiStack> addNEIStacks() {
		return null;
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		gui.getWidgetManager().add(new WidgetEnergyBar(((EnergyHandler)((ModularMachine)modular).getEnergyHandler()).storage, 82 , 12));
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
