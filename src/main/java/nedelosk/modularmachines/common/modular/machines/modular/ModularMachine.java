package nedelosk.modularmachines.common.modular.machines.modular;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerBattery;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerMachine;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.modular.machines.modular.handlers.EnergyHandler;
import nedelosk.modularmachines.common.modular.utils.MachineBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModularMachine extends ModularInventory {

	public ModularMachine() {
		super();
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
			ModuleStack manager_left = null;
			ModuleStack manager_right = null;
			ModuleStack manager_back = null;
			if(stacks[0] == null)
				return null;
			if(ModuleRegistry.getModuleItem(stacks[0]) != null && ModuleRegistry.getModuleItem(stacks[0]).getProducer() instanceof IProducerBattery)
				battery = ModuleRegistry.getModuleItem(stacks[0]);
			if(stacks[1] == null)
				return null;
			if(ModuleRegistry.getModuleItem(stacks[1]) != null && ModuleRegistry.getModuleItem(stacks[1]).getProducer() instanceof IProducerEngine)
				engine = ModuleRegistry.getModuleItem(stacks[1]);
			if(stacks[2] == null)
				return null;
			if(ModuleRegistry.getModuleItem(stacks[2]) != null && ModuleRegistry.getModuleItem(stacks[2]).getModule() instanceof IModuleCasing)
				casing = ModuleRegistry.getModuleItem(stacks[2]);
			else{
				return null;
			}
			if(stacks[3] == null)
				return null;
			if(ModuleRegistry.getModuleItem(stacks[3]) != null && ModuleRegistry.getModuleItem(stacks[3]).getProducer() instanceof IProducerMachine)
				producer = ModuleRegistry.getModuleItem(stacks[3]);
			if(modular.addModule(battery) && modular.addModule(engine) && modular.addModule(casing) && modular.addModule(producer)){
				modular.getManager().setEnergyHandler(new EnergyHandler(((IProducerBattery)battery.getProducer()).getStorage(battery)));
				return modular;
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return "modular.machine";
	}

	@Override
	public IModularRenderer getItemRenderer(IModular modular, ItemStack stack) {
		if(ModularUtils.getModuleStackMachine(modular) == null)
			return null;
		return ModularUtils.getModuleStackMachine(modular).getProducer().getItemRenderer(modular, ModularUtils.getModuleStackMachine(modular), stack);
	}

	@Override
	public IModularRenderer getMachineRenderer(IModular modular, IModularTileEntity tile) {
		if(ModularUtils.getModuleStackMachine(modular) == null)
			return null;
		return ModularUtils.getModuleStackMachine(modular).getProducer().getMachineRenderer(modular, ModularUtils.getModuleStackMachine(modular), tile);
	}

}