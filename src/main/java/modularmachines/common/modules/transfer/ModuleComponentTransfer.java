package modularmachines.common.modules.transfer;

import modularmachines.api.modules.pages.PageComponent;

public abstract class ModuleComponentTransfer<M extends ModuleTransfer<H>, H> extends PageComponent<M> {
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
