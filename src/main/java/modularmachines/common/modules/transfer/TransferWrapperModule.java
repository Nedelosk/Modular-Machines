package modularmachines.common.modules.transfer;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.IModuleContainer;
import modularmachines.api.modules.Module;

public class TransferWrapperModule<H> implements ITransferHandlerWrapper<H> {
	
	protected final int index;
	protected final ModuleTransfer<H> moduleTransfer;
	//protected H handler;
	@Nullable
	protected Module module;
	
	public TransferWrapperModule(ModuleTransfer<H> moduleTransfer, int index) {
		this.moduleTransfer = moduleTransfer;
		this.index = index;
	}
	
	@Override
	public String getTabTooltip() {
		if (module == null) {
			return "UNKNOWN";
		}
		return module.getData().getDisplayName();
	}
	
	@Override
	public boolean isValid() {
		return module != null && moduleTransfer.isValid(this);
	}
	
	@Override
	public void init(IModuleContainer provider) {
		if (module == null) {
			module = provider.getModule(index);
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	@Nullable
	public Module getModule() {
		return module;
	}
	
	@Override
	public ModuleTransfer<H> getTransferModule() {
		return moduleTransfer;
	}
	
	@Override
	public ItemStack getTabItem() {
		if (module == null) {
			return ItemStack.EMPTY;
		}
		return module.getParentItem().copy();
	}
	
}
