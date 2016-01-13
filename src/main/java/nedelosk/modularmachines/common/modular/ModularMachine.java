package nedelosk.modularmachines.common.modular;

import java.util.ArrayList;
import java.util.Vector;

import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.ModularInventory;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.special.IProducerController;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.api.utils.ModuleUtils;
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
		if (modular != null) {
			ModuleStack<IModule, IProducerController> controller = null;
			if (stacks.length == 0 || stacks[0] == null) {
				return null;
			}
			if (ModuleRegistry.getProducer(stacks[0]) != null && ModuleRegistry.getProducer(stacks[0]).getProducer() instanceof IProducerController) {
				controller = ModuleRegistry.getProducer(stacks[0]);
			}
			if (controller != null && controller.getProducer().buildMachine(modular, stacks, controller)) {
				ArrayList<String> moduleNames = new ArrayList<>();
				for ( Vector<ModuleStack> moduleStacks : modular.getModules().values() ) {
					for ( ModuleStack stack : moduleStacks ) {
						if (stack != null && stack.getModule() != null) {
							if (!moduleNames.contains(stack.getModule().getModuleName())) {
								moduleNames.add(stack.getModule().getModuleName());
							} else {
								return null;
							}
						}
					}
				}
				for ( Vector<ModuleStack> moduleStacks : modular.getModules().values() ) {
					for ( ModuleStack stack : moduleStacks ) {
						if (stack.getProducer() == null) {
							continue;
						}
						if (!stack.getProducer().onBuildModular(modular, stack, moduleNames)) {
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
		if (ModuleUtils.getModuleStackMachine(modular) == null || ModuleUtils.getModuleStackMachine(modular).getProducer() == null) {
			return null;
		}
		return ModuleUtils.getModuleStackMachine(modular).getProducer().getItemRenderer(modular, ModuleUtils.getModuleStackMachine(modular), stack);
	}

	@Override
	public IModularRenderer getMachineRenderer(IModular modular, IModularTileEntity tile) {
		if (ModuleUtils.getModuleStackMachine(modular) == null || ModuleUtils.getModuleStackMachine(modular).getProducer() == null) {
			return null;
		}
		return ModuleUtils.getModuleStackMachine(modular).getProducer().getMachineRenderer(modular, ModuleUtils.getModuleStackMachine(modular), tile);
	}
}