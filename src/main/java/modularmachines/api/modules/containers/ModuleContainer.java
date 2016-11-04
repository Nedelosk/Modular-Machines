package modularmachines.api.modules.containers;

import java.util.List;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleProperties;
import net.minecraft.item.ItemStack;

public class ModuleContainer<M extends IModule, P extends IModuleProperties> implements IModuleContainer<M, P> {

	protected final M module;
	protected final P properties;
	protected IModuleItemContainer itemContainer;

	public ModuleContainer(M module, P properties) {
		this.module = module;
		this.properties = properties;
	}

	@Override
	public M getModule() {
		return module;
	}

	@Override
	public P getProperties() {
		return properties;
	}

	@Override
	public IModuleItemContainer getItemContainer() {
		return itemContainer;
	}

	@Override
	public void setItemContainer(IModuleItemContainer itemContainer) {
		if (this.itemContainer == null && itemContainer != null) {
			this.itemContainer = itemContainer;
		}
	}

	@Override
	public String getUnlocalizedName() {
		return module.getUnlocalizedName(this);
	}

	@Override
	public String getDisplayName() {
		return module.getDisplayName(this);
	}

	@Override
	public String getDescription() {
		return module.getDescription(this);
	}

	@Override
	public int getIndex() {
		if (itemContainer == null) {
			return -1;
		}
		return itemContainer.getIndex(this);
	}

	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack) {
		module.addTooltip(tooltip, stack, this);
	}
}
