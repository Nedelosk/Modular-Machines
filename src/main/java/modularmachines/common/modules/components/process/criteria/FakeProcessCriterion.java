package modularmachines.common.modules.components.process.criteria;

import javax.annotation.Nullable;
import java.io.IOException;

import net.minecraft.network.PacketBuffer;

import modularmachines.api.modules.components.process.IProcessComponent;
import modularmachines.api.modules.components.process.IProcessCriterion;
import modularmachines.api.modules.errors.IErrorState;

public enum FakeProcessCriterion implements IProcessCriterion {
	INSTANCE;
	
	@Override
	public Object getRequirement() {
		return getClass();
	}
	
	@Override
	public void setRequirement(Object requirement) {
	}
	
	@Override
	public boolean getState() {
		return false;
	}
	
	@Override
	public void updateState() {
	}
	
	@Nullable
	@Override
	public IErrorState getError() {
		return null;
	}
	
	@Override
	public IProcessComponent getParent() {
		return null;
	}
	
	@Override
	public void markDirty() {
	}
	
	@Override
	public void setDirty(boolean dirty) {
	}
	
	@Override
	public boolean isDirty() {
		return false;
	}
	
	@Override
	public void writeData(PacketBuffer data) {
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
	}
}
