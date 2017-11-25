package modularmachines.common.modules.components.energy;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.components.IProcessComponent;
import modularmachines.common.modules.components.ModuleComponent;

public abstract class ProcessIndefinateComponent extends ModuleComponent implements IProcessComponent, INBTReadable, INBTWritable {
	private boolean inProgress;
	private float actionPauseProcess;
	private float actionCancelTask;
	
	public ProcessIndefinateComponent() {
		this.actionPauseProcess = 0.0f;
		this.actionCancelTask = 0.0f;
	}
	
	@Override
	public void setActive(boolean isActive) {
		this.inProgress = isActive;
	}
	
	@Override
	public boolean isActive() {
		return inProgress;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.inProgress = compound.getBoolean("progress");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("progress", this.inProgress);
		return compound;
	}
	
	@Override
	public void update() {
		if (this.canWork()) {
			if (!this.isActive() && this.canProgress()) {
				this.onStartTask();
			} else if (this.canProgress()) {
				this.progressTick();
				this.onTickTask();
			}
		} else if (this.isActive()) {
			this.onCancelTask();
		}
		if (this.actionPauseProcess > 0.0f) {
			--this.actionPauseProcess;
		}
		if (this.actionCancelTask > 0.0f) {
			--this.actionCancelTask;
		}
		if (this.inProgress != this.inProgress()) {
			this.inProgress = this.inProgress();
			provider.sendToClient();
		}
	}
	
	protected void progressTick() {
	}
	
	@Override
	public boolean canWork() {
		return this.actionCancelTask == 0.0f;
	}
	
	@Override
	public boolean canProgress() {
		return !(this.actionPauseProcess != 0.0f);
	}
	
	protected abstract boolean inProgress();
	
	protected void onCancelTask() {
	}
	
	protected void onStartTask() {
	}
	
	protected void onTickTask() {
	}
}
