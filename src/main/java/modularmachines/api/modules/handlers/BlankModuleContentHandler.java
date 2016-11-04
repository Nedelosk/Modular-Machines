package modularmachines.api.modules.handlers;

import java.util.List;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;

public abstract class BlankModuleContentHandler<M extends IModule> implements IModuleContentHandler<M> {

	protected final IModuleState<M> moduleState;
	private final String uid;

	public BlankModuleContentHandler(IModuleState<M> moduleState, String uid) {
		this.moduleState = moduleState;
		this.uid = uid;
	}

	@Override
	public IModuleState<M> getModuleState() {
		return moduleState;
	}

	@Override
	public String getUID() {
		return uid;
	}

	@Override
	public void addToolTip(List<String> tooltip, ItemStack stack, IModuleState<M> state) {
	}

	@Override
	public void cleanHandler(IModuleState state) {
	}

	@Override
	public boolean isCleanable() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}
