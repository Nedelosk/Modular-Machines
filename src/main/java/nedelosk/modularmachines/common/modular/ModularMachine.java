package nedelosk.modularmachines.common.modular;

import java.util.ArrayList;
import java.util.Vector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.basic.ModularInventory;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.basic.IModuleWithRenderer;
import nedelosk.modularmachines.api.modules.special.IModuleController;
import nedelosk.modularmachines.api.utils.ModularUtils;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
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
			ModuleStack<IModule, IModuleController> controller = null;
			if (stacks.length == 0 || stacks[0] == null) {
				return null;
			}
			if (ModuleRegistry.getModule(stacks[0]) != null && ModuleRegistry.getModule(stacks[0]).getModule() instanceof IModuleController) {
				controller = ModuleRegistry.getModule(stacks[0]);
			}
			if (controller != null && controller.getModule().buildMachine(modular, stacks, controller)) {
				ArrayList<String> moduleNames = new ArrayList<>();
				for ( Vector<ModuleStack> moduleStacks : modular.getModuleContainers().values() ) {
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
				for ( Vector<ModuleStack> moduleStacks : modular.getModuleContainers().values() ) {
					for ( ModuleStack stack : moduleStacks ) {
						if (stack.getModule() == null) {
							continue;
						}
						if (!stack.getModule().onBuildModular(modular, stack, moduleNames)) {
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

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ItemStack stack) {
		if (ModularUtils.getMachine(modular) == null || ModularUtils.getMachine(modular).getStack().getModule() == null) {
			return null;
		}
		return ((IModuleWithRenderer) ModularUtils.getMachine(modular).getStack().getModule()).getItemRenderer(modular,
				ModularUtils.getMachine(modular).getStack(), stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, IModularTileEntity tile) {
		if (ModularUtils.getMachine(modular) == null || ModularUtils.getMachine(modular).getStack().getModule() == null) {
			return null;
		}
		return ((IModuleWithRenderer) ModularUtils.getMachine(modular).getStack().getModule()).getMachineRenderer(modular,
				ModularUtils.getMachine(modular).getStack(), tile);
	}
}