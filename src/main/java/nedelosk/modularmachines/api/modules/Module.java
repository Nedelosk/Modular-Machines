package nedelosk.modularmachines.api.modules;

import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public abstract class Module implements IModule {

	protected String moduleModifier;

	public Module() {
	}

	public Module(String moduleModifier) {
		this.moduleModifier = moduleModifier;
	}

	@Override
	public String getModifier(ModuleStack stack) {
		return moduleModifier;
	}

	@Override
	public String getName(ModuleStack stack, boolean withTypeModifier) {
		return "module" + ((getModifier(stack) != null) ? getModifier(stack) : "")
				+ (withTypeModifier ? ((ModuleRegistry.getTypeModifier(stack) != null) ? ModuleRegistry.getTypeModifier(stack) : "") : "");
	}

	@Override
	public String getRegistryName() {
		return "module" + getModuleName() + moduleModifier;
	}

	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return null;
	}

	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IModule) {
			IModule module = (IModule) obj;
			if (module.getModuleName().equals(getModuleName()) && module.getRegistryName().equals(getRegistryName())) {
				return true;
			}
		}
		return false;
	}
}
