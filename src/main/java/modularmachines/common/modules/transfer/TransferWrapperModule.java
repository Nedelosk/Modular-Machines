package modularmachines.common.modules.transfer;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.logic.IModuleLogic;

public class TransferWrapperModule<H> implements ITransferHandlerWrapper<H> {
	
	protected H handler;
	protected final int index;
	protected Module module;
	protected final ModuleTransfer<H> moduleTransfer;
	
	public TransferWrapperModule(ModuleTransfer<H> moduleTransfer, int index) {
		this.moduleTransfer = moduleTransfer;
		this.index = index;
	}

	@Override
	public String getTabTooltip() {
		return null;
	}

	@Override
	public boolean isValid() {
		return module != null;
	}

	@Override
	public void init(IModuleLogic logic) {
		if(module == null){
			module = logic.getModule(index);
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	public Module getModule() {
		return module;
	}
	
	@Override
	public ModuleTransfer<H> getTransferModule() {
		return moduleTransfer;
	}
	
	@Override
	public ItemStack getTabItem() {
		if(module == null){
			return ItemStack.EMPTY;
		}
		return module.getParentItem().copy();
	}
	
}
