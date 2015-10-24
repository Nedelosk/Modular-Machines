package nedelosk.modularmachines.common.modular.machines.modular;

import java.util.ArrayList;
import java.util.Vector;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.tool.producer.basic.IProducerManager;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerBattery;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerMachine;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.modular.machines.modular.handlers.EnergyHandler;
import nedelosk.modularmachines.common.modular.machines.modular.handlers.FluidHandler;
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
			ModuleStack<IModule, IProducerManager>[] managers = new ModuleStack[4];
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
			if(stacks.length > 4)
			for(int i = 4;i < stacks.length;i++){
				if(stacks[i] != null){
					if(ModuleRegistry.getModuleItem(stacks[i]) != null && ModuleRegistry.getModuleItem(stacks[i]).getProducer() instanceof IProducerManager)
						managers[i-4] = ModuleRegistry.getModuleItem(stacks[i]);
				}
			}
			if(modular.addModule(battery) && modular.addModule(engine) && modular.addModule(casing) && modular.addModule(producer)){
				for(ModuleStack<IModule, IProducerManager> manager : managers)
					if(manager != null)
						if(!modular.addModule(manager))
							return null;
				for(Vector<ModuleStack> moduleStacks : modular.getModules().values()){
					for(ModuleStack stack : moduleStacks){
						if(stack != null && stack.getModule() != null && stack.getProducer() != null){
							if(!stack.getProducer().onBuildModular(modular, stack))
								return null;
						}
					}
				}
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