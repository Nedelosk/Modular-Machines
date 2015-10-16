package nedelosk.modularmachines.common.modular.module.basic;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.basic.basic.Module;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.client.renderers.modules.ModularMachineRenderer;
import net.minecraft.item.ItemStack;

public class ModuleCasing extends Module implements IModuleCasing {
	
	public ModuleCasing() {
	}
	
	public ModuleCasing(String modifier) {
		super(modifier);
	}
	
	@Override
	public String getModuleName() {
		return "Casing";
	}
	
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.CasingRenderer(moduleStack);
	}
	
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModularMachineRenderer.CasingRenderer(moduleStack);
	}

}
