package modularmachines.common.modules.transfer;

import modularmachines.api.modules.pages.ModuleComponent;

public abstract class ModuleComponentTransfer<M extends ModuleTransfer<H>, H> extends ModuleComponent<M> {
	public final int index;
	
	public ModuleComponentTransfer(M parent, int index) {
		super(parent);
		this.index = index;
	}
	
	
	@Override
	public int getPlayerInvPosition() {
		return 161;
	}
	
}
