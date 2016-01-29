package nedelosk.modularmachines.common.modular;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.basic.ModularInventory;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.basic.IModuleWithRenderer;
import nedelosk.modularmachines.api.modules.special.IModuleController;
import nedelosk.modularmachines.api.utils.ModularException;
import nedelosk.modularmachines.api.utils.ModularUtils;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ModularMachine extends ModularInventory {

	private ModularException lastException;

	public ModularMachine() {
		super();
	}

	public ModularMachine(NBTTagCompound nbt) {
		super(nbt);
	}

	@Override
	public void build() throws ModularException {
		ModuleStack<IModuleController> controller = null;
		for ( ModuleStack stack : getModuleStacks() ) {
			if (stack.getModule() instanceof IModuleController) {
				controller = stack;
			}
		}
		if (controller == null) {
			throw new ModularException(StatCollector.translateToLocal("modular.ex.find.controller"));
		}
		List<ModuleStack> modules = getModuleStacks();
		for ( ModuleStack stack : getModuleStacks() ) {
			stack.getModule().canBuildModular(this, stack, controller, modules);
		}
		controller.getModule().canBuildModular(this, controller);
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