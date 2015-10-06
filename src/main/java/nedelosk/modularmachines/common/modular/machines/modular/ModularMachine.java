package nedelosk.modularmachines.common.modular.machines.modular;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleBattery;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEngine;
import nedelosk.modularmachines.api.modular.module.producer.producer.IModuleProducer;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.parts.IMachinePartBattery;
import nedelosk.modularmachines.api.parts.IMachinePartEngine;
import nedelosk.modularmachines.api.parts.IMachinePartProducer;
import nedelosk.modularmachines.common.modular.utils.MachineBuilder;
import nedelosk.modularmachines.common.modular.utils.ModularUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModularMachine extends ModularInventory {

	public ModularMachine() {
	}
	
	public ModularMachine(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public IModular buildItem(ItemStack[] stacks) {
		IModular modular = MachineBuilder.createMachine(getName());
		if(modular != null){
			ModuleStack battery = null;
			ModuleStack engine = null;
			ModuleStack casing = null;
			ModuleStack producer = null;
			if(stacks[0] == null)
				return null;
			if(ModuleRegistry.getModuleStack(stacks[0]) != null && ModuleRegistry.getModuleStack(stacks[0]).getModule() instanceof IModuleBattery)
				battery = ModuleRegistry.getModuleStack(stacks[0]);
			else{
				if(stacks[0].getItem() instanceof IMachinePartBattery){
					IMachinePartBattery batteryItem = (IMachinePartBattery) stacks[0].getItem();
					battery = batteryItem.buildModule(stacks[0]);
				}else
					return null;
			}
			if(stacks[1] == null)
				return null;
			if(ModuleRegistry.getModuleStack(stacks[1]) != null && ModuleRegistry.getModuleStack(stacks[1]).getModule() instanceof IModuleEngine)
				engine = ModuleRegistry.getModuleStack(stacks[1]);
			else{
				if(stacks[1].getItem() instanceof IMachinePartEngine){
					IMachinePartEngine engineItem = (IMachinePartEngine) stacks[1].getItem();
					engine = engineItem.buildModule(stacks[1]);
				}else
					return null;
			}
			if(stacks[2] == null)
				return null;
			if(ModuleRegistry.getModuleStack(stacks[2]) != null && ModuleRegistry.getModuleStack(stacks[2]).getModule() instanceof IModuleCasing)
				casing = ModuleRegistry.getModuleStack(stacks[2]);
			else{
				return null;
			}
			if(stacks[3] == null)
				return null;
			if(ModuleRegistry.getModuleStack(stacks[3]) != null && ModuleRegistry.getModuleStack(stacks[3]).getModule() instanceof IModuleProducer)
				engine = ModuleRegistry.getModuleStack(stacks[3]);
			else{
				if(stacks[3].getItem() instanceof IMachinePartProducer){
					IMachinePartProducer producerItem = (IMachinePartProducer) stacks[3].getItem();
					producer = producerItem.buildModule(stacks[3]);
				}else
					return null;
			}
			modular.addModule(battery);
			modular.addModule(engine);
			modular.addModule(casing);
			modular.addModule(producer);
			return modular;
		}
		return null;
	}

	@Override
	public String getName() {
		return "modular.machines";
	}

	@Override
	public IModularRenderer getItemRenderer(IModular modular, ItemStack stack) {
		return ModularUtils.getModuleProducer(modular).getItemRenderer(modular, stack);
	}

	@Override
	public IModularRenderer getMachineRenderer(IModular modular, IModularTileEntity tile) {
		return ModularUtils.getModuleProducer(modular).getMachineRenderer(modular, tile);
	}

}
