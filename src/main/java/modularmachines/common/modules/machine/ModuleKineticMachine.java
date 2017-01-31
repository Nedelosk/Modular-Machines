package modularmachines.common.modules.machine;

import modularmachines.api.modules.IModuleKinetic;
import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.energy.IKineticSource;
import modularmachines.api.recipes.IRecipe;
import modularmachines.common.utils.ModuleUtil;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleKineticMachine<R extends IRecipe> extends ModuleMachine<R> {

	protected final double maxSpeed;
	protected double speed = 0.0D;
	
	public ModuleKineticMachine(IModuleStorage storage, int workTimeModifier, double maxSpeed) {
		super(storage, workTimeModifier);
		this.maxSpeed = maxSpeed;
	}
	
	@Override
	protected boolean canWork() {
		for (IModuleKinetic module : ModuleUtil.getModules(logic, IModuleKinetic.class)) {
			IKineticSource source = module.getKineticSource();
			if (source.getStored() > 0) {
				return true;
			}
		}
		return true;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setDouble("Speed", speed);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		speed = compound.getDouble("Speed");
	}
	
	@Override
	public void update() {
		boolean needUpdate = false;
		if (canWork()) {
			if (workTime >= workTimeTotal || recipe == null) {
				R validRecipe = getValidRecipe();
				if (recipe != null) {
					if (addOutputs()) {
						recipe = null;
						workTimeTotal = 0;
						workTime = 0;
						chance = rand.nextFloat();
						needUpdate = true;
					}
				} else if (validRecipe != null) {
					recipe = validRecipe;
					workTimeTotal = createWorkTimeTotal(validRecipe.getSpeed());
					chance = rand.nextFloat();
					needUpdate = true;
				}
			} else {
				int workTime = 0;
				for (IModuleKinetic moduleKinetic : ModuleUtil.getModules(logic, IModuleKinetic.class)) {
					IKineticSource source = moduleKinetic.getKineticSource();
					double kinetic = source.getStored() / source.getCapacity();
					if (source.getStored() > 0F) {
						source.extractKineticEnergy(kinetic, false);
						if (speed < maxSpeed) {
							speed = speed + kinetic / 10F;
						} else if (speed > maxSpeed) {
							speed = maxSpeed;
						}
					} else {
						if (speed > 0F) {
							speed = speed - (kinetic / 5F);
						} else if (0 > speed) {
							speed = 0F;
						}
					}
				}
				if (speed > 0) {
					workTime += Math.round(speed);
				}

				if (workTime > 0) {
					needUpdate = true;
					this.workTime+= workTime;
				}
			}
			if (needUpdate) {
				sendModuleUpdate();
			}
		}
	}

}
