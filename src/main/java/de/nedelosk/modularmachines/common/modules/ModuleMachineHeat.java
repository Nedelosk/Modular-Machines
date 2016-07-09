package de.nedelosk.modularmachines.common.modules;

import java.util.Random;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModuleIndexStorage;
import de.nedelosk.modularmachines.api.modular.ModularHelper;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.Recipe;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraftforge.items.IItemHandler;

public abstract class ModuleMachineHeat extends ModuleMachine {

	public ModuleMachineHeat(int speedModifier, int size) {
		super(speedModifier, size);
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		IModular modular = state.getModular();
		Random rand = modular.getHandler().getWorld().rand;

		if (canWork(state)) {
			boolean needUpdate = false;
			IRecipe currentRecipe = getCurrentRecipe(state);
			if (getWorkTime(state) >= getWorkTimeTotal(state) || currentRecipe == null) {
				IRecipe recipe = getValidRecipe(state);
				if (currentRecipe != null) {
					if (addOutput(state)) {
						setCurrentRecipe(state, null);
						setBurnTimeTotal(state, 0);
						setWorkTime(state, 0);
						state.set(CHANCE, rand.nextInt(100));
						needUpdate = true;
					}
				} else if (recipe != null) {
					setCurrentRecipe(state, recipe);
					if (!removeInput(state)) {
						setCurrentRecipe(state, null);
						return;
					}
					setBurnTimeTotal(state, createBurnTimeTotal(state, recipe.getSpeed()) /* state.getContainer().getMaterial().getTier() */);
					IModuleState<IModuleCasing> casingState = modular.getModules(IModuleCasing.class).get(0);
					casingState.getModule().addHeat(casingState, -getConsumeHeat(state));
					state.set(CHANCE, rand.nextInt(100));
					needUpdate = true;
				}
			}else{
				addWorkTime(state, 1);
				needUpdate = true;
			}
			if(needUpdate){
				PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
			}
		}
	}

	@Override
	protected boolean isRecipeValid(IRecipe recipe, IModuleState state) {
		IModuleState<IModuleCasing> casingState = state.getModular().getModules(IModuleCasing.class).get(0);
		if(recipe.get(Recipe.HEAT) < casingState.get(ModuleCasing.HEAT)){
			return true;
		}
		return false;
	}

	protected abstract int getConsumeHeat(IModuleState state);

	@Override
	public boolean assembleModule(IItemHandler itemHandler, IModular modular, IModuleState state, IModuleIndexStorage storage) {
		if(!modular.getModules(IModuleHeater.class).isEmpty()){
			return true;
		}
		return false;
	}

	@Override
	public boolean canWork(IModuleState state){
		IModuleState<IModuleCasing> casingState = ModularHelper.getCasing(state.getModular());
		return casingState.getModule().getHeat(casingState) > 0;
	}

}
