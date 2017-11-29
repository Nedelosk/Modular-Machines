package modularmachines.common.modules.components.process.criteria;

import javax.annotation.Nullable;
import java.io.IOException;

import net.minecraft.network.PacketBuffer;

import modularmachines.api.modules.components.process.IProcessComponent;
import modularmachines.api.modules.components.process.IProcessCriterion;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.errors.IErrorState;

public abstract class AbstractProcessCriterion<R> implements IProcessCriterion<R> {
	protected final IProcessComponent component;
	protected R requirement;
	private boolean state;
	private boolean dirty;
	
	public AbstractProcessCriterion(IProcessComponent component, R requirement) {
		this.component = component;
		this.requirement = requirement;
		markDirty();
		IModuleContainer container = component.getProvider().getContainer();
		registerListeners(container);
	}
	
	protected void registerListeners(IModuleContainer container) {
	}
	
	@Override
	public boolean getState() {
		return state;
	}
	
	protected void setState(boolean state) {
		if (state != this.state) {
			this.state = state;
			component.getProvider().sendToClient();
		}
	}
	
	@Override
	public R getRequirement() {
		return requirement;
	}
	
	/**
	 * Only for internal use
	 */
	public void setRequirement(R requirement) {
		this.requirement = requirement;
		markDirty();
	}
	
	@Nullable
	@Override
	public IErrorState getError() {
		return null;
	}
	
	@Override
	public IProcessComponent getParent() {
		return component;
	}
	
	@Override
	public void writeData(PacketBuffer data) {
		data.writeBoolean(state);
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
		state = data.readBoolean();
	}
	
	@Override
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	@Override
	public void markDirty() {
		setDirty(true);
	}
	
	@Override
	public boolean isDirty() {
		return dirty;
	}
}
