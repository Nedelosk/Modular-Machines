package de.nedelosk.modularmachines.common.modules;

import java.util.Random;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.ModularHelper;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IRecipeManager;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import de.nedelosk.modularmachines.common.modules.tools.RecipeManager;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraftforge.items.IItemHandler;

public abstract class ModuleToolHeat extends ModuleTool {

	public ModuleToolHeat(int speedModifier, int size) {
		super(speedModifier, size);
	}

	@Override
	public void updateServer(IModuleState state) {
		IModular modular = state.getModular();
		Random rand = modular.getHandler().getWorld().rand;

		if (canWork(state)) {
			IRecipeManager manager = getRecipeManager(state);
			if (getWorkTime(state) >= getWorkTimeTotal(state) || manager == null) {
				IRecipe recipe = RecipeRegistry.getRecipe(getRecipeCategory(state), getInputs(state), getRecipeModifiers(state));
				if (manager != null) {
					if (addOutput(state)) {
						setRecipeManager(state, null);
						setBurnTimeTotal(state, 0);
						setWorkTime(state, 0);
						state.add(CHANCE, rand.nextInt(100));
						PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
					}
				} else if (recipe != null) {
					setBurnTimeTotal(state, createBurnTimeTotal(state, recipe.getRequiredSpeedModifier()) / state.getContainer().getMaterial().getTier());
					setRecipeManager(state, new RecipeManager(recipe.getRecipeCategory(), (recipe.getRequiredMaterial() * speedModifier) / getWorkTimeTotal(state),
							recipe.getInputs().clone(), getRecipeModifiers(state)));
					if (!removeInput(state)) {
						setRecipeManager(state, null);
						return;
					}
					IModuleState<IModuleCasing> casingState = modular.getModules(IModuleCasing.class).get(0);
					casingState.getModule().addHeat(casingState, -getConsumeHeat(state));
					state.add(CHANCE, rand.nextInt(100));
					PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
				}
			}
		}
	}

	protected abstract int getConsumeHeat(IModuleState state);
	
	@Override
	public boolean assembleModule(IItemHandler itemHandler, IModular modular, IModuleState state) {
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
