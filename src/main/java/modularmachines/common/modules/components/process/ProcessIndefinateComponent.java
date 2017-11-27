package modularmachines.common.modules.components.process;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.components.process.IProcessComponent;
import modularmachines.common.modules.components.ModuleComponent;
import modularmachines.common.modules.components.process.criteria.ProcessLogic;

public abstract class ProcessIndefinateComponent extends ModuleComponent implements IProcessComponent, INBTReadable, INBTWritable {
	private final ProcessLogic logic;
	
	private boolean inProgress;
	private float actionPauseProcess;
	private float actionCancelTask;
	
	public ProcessIndefinateComponent() {
		this.actionPauseProcess = 0.0f;
		this.actionCancelTask = 0.0f;
		this.logic = new ProcessLogic(this);
	}
	
	@Override
	public void onCreateModule() {
		addCriteria(logic);
	}
	
	protected void addCriteria(ProcessLogic logic) {
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
			boolean canProgress = canProgress();
			if (!this.isActive() && canProgress) {
				this.onStartTask();
			} else if (canProgress) {
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
		return logic.canProgress() && !(this.actionPauseProcess != 0.0f);
	}
	
	protected abstract boolean inProgress();
	
	protected void onCancelTask() {
	}
	
	protected void onStartTask() {
	}
	
	protected void onTickTask() {
	}
}
