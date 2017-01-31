package modularmachines.common.modules.machine;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.IRecipeHeat;
import modularmachines.common.utils.ModuleUtil;

public abstract class ModuleHeatMachine extends ModuleMachine {

	protected double heatToRemove = 0;
	protected double heatRequired = 0;
	
	public ModuleHeatMachine(IModuleStorage storage, int workTimeModifier) {
		super(storage, workTimeModifier);
	}
	
	@Override
	public void update() {
		boolean needUpdate = false;
		if (canWork()) {
			if (workTime >= workTimeTotal || recipe == null) {
				IRecipe validRecipe = getValidRecipe();
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
					if(validRecipe instanceof IRecipeHeat){
						IRecipeHeat heatRecipe = (IRecipeHeat) recipe;
						heatToRemove = heatRecipe.getHeatToRemove() / workTimeTotal;
						heatRequired = heatRecipe.getRequiredHeat();
					}
					needUpdate = true;
				}
			} else {
				int workTime = 0;
				IHeatSource heatBuffer = ModuleUtil.getHeat(logic);
				if (heatBuffer.getHeatStored() >= heatRequired) {
					heatBuffer.extractHeat(heatToRemove, false);
					workTime = 1;
				}
				if (workTime > 0) {
					needUpdate = true;
					this.workTime+=workTime;
				}
			}
			if (needUpdate) {
				sendModuleUpdate();
			}
		}
	}
	
	@Override
	protected boolean isRecipeValid(IRecipe recipe) {
		if(!super.isRecipeValid(recipe)){
			return false;
		}
		if(recipe instanceof IRecipeHeat){
			IRecipeHeat recipeHeat = (IRecipeHeat) recipe;
			IHeatSource heatBuffer = ModuleUtil.getHeat(logic);
			if (recipeHeat.getRequiredHeat() > heatBuffer.getHeatStored()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected boolean canWork() {
		IHeatSource heatBuffer = ModuleUtil.getHeat(logic);
		return heatBuffer.getHeatStored() > 0;
	}

}
