package nedelosk.modularmachines.common.machines.modular;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleBattery;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEngine;
import nedelosk.modularmachines.api.modular.module.producer.producer.IModuleProducer;
import nedelosk.modularmachines.api.modular.module.utils.ModularManager;
import nedelosk.modularmachines.api.modular.module.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.parts.IMachinePartBattery;
import nedelosk.modularmachines.api.modular.parts.IMachinePartEngine;
import nedelosk.modularmachines.common.machines.utils.MachineBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModularMachine extends ModularInventory {

	public ModularMachine() {
	}
	
	public ModularMachine(NBTTagCompound nbt, IModularTileEntity machine) {
		super(nbt, machine);
	}
	
	@Override
	public IModular buildItem(ItemStack[] stacks) {
		IModular modular = MachineBuilder.createMachine(getName());
		if(modular != null){
			ModuleStack battery = null;
			ModuleStack engine = null;
			ModuleStack producer = null;
			if(ModularManager.getModuleStack(stacks[0]).getModule() instanceof IModuleBattery)
				battery = ModularManager.getModuleStack(stacks[0]);
			else{
				if(stacks[0].getItem() instanceof IMachinePartBattery){
					IMachinePartBattery batteryItem = (IMachinePartBattery) stacks[0].getItem();
					battery = batteryItem.buildModule(stacks[0]);
				}else
					return null;
			}
			if(ModularManager.getModuleStack(stacks[1]).getModule() instanceof IModuleEngine)
				engine = ModularManager.getModuleStack(stacks[1]);
			else{
				if(stacks[1].getItem() instanceof IMachinePartEngine){
					IMachinePartEngine engineItem = (IMachinePartEngine) stacks[1].getItem();
					engine = engineItem.buildModule(stacks[1]);
				}else
					return null;
			}
			if(ModularManager.getModuleStack(stacks[2]).getModule() instanceof IModuleProducer)
				engine = ModularManager.getModuleStack(stacks[2]);
			else{
				if(stacks[2].getItem() instanceof IMachinePartEngine){
					IMachinePartEngine producerItem = (IMachinePartEngine) stacks[1].getItem();
					producer = producerItem.buildModule(stacks[2]);
				}else
					return null;
			}
			return modular;
		}
		return null;
	}

	@Override
	public String getName() {
		return "modularmachine";
	}

}
