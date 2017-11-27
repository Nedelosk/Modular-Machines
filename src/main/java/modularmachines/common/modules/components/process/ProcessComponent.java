package modularmachines.common.modules.components.process;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.components.process.IProcessTimedComponent;

public abstract class ProcessComponent extends ProcessIndefinateComponent implements IProcessTimedComponent {
	private float progressAmount;
	
	public ProcessComponent() {
		this.progressAmount = 0.0f;
	}
	
	@Override
	public float getProgressPerTick() {
		return 100.0f / this.getProcessLength();
	}
	
	@Override
	protected void onStartTask() {
		this.progressAmount += 0.01f;
	}
	
	@Override
	protected void onCancelTask() {
		this.progressAmount = 0.0f;
	}
	
	@Override
	public void update() {
		super.update();
		if (this.progressAmount >= 100.0f) {
			this.onFinishTask();
			this.progressAmount = 0.0f;
		}
	}
	
	public void alterProgress(float amount) {
		this.progressAmount += amount;
	}
	
	@Override
	protected void progressTick() {
		super.progressTick();
		this.alterProgress(this.getProgressPerTick());
	}
	
	@Override
	public boolean inProgress() {
		return this.progressAmount > 0.0f;
	}
	
	@Override
	public float getProgress() {
		return this.progressAmount;
	}
	
	public void setProgress(float progress) {
		this.progressAmount = progress;
	}
	
	protected void onFinishTask() {
	}
	
	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.progressAmount = compound.getFloat("progress");
	}
	
	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
		NBTTagCompound nbt = super.writeToNBT(compound);
		nbt.setFloat("progress", this.progressAmount);
		return nbt;
	}
	
	@Override
	public abstract int getProcessLength();
}
