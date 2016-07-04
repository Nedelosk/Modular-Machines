package de.nedelosk.modularmachines.common.modules;

import java.util.Random;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleBattery;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraftforge.items.IItemHandler;

public abstract class ModuleToolEngine extends ModuleTool {

	public ModuleToolEngine(int speedModifier, int size) {
		super(speedModifier, size);
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		IModular modular = state.getModular();
		Random rand = modular.getHandler().getWorld().rand;

		if (canWork(state)) {
			IRecipe currentRecipe = getCurrentRecipe(state);
			if (getWorkTime(state) >= getWorkTimeTotal(state) || currentRecipe == null) {
				IRecipe recipe = getValidRecipe(state);
				if (currentRecipe != null) {
					if (addOutput(state)) {
						setCurrentRecipe(state, null);
						setBurnTimeTotal(state, 0);
						setWorkTime(state, 0);
						state.set(CHANCE, rand.nextInt(100));
						PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
					}
				} else if (recipe != null) {
					setCurrentRecipe(state, recipe);
					if (!removeInput(state)) {
						setCurrentRecipe(state, null);
						return;
					}
					setBurnTimeTotal(state, createBurnTimeTotal(state, recipe.getSpeed()) / state.getContainer().getMaterial().getTier());
					state.set(CHANCE, rand.nextInt(100));
					PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
				}
			}
		}
	}

	@Override
	public boolean assembleModule(IItemHandler itemHandler, IModular modular, IModuleState state) {
		if(modular.getModules(IModuleBattery.class).isEmpty()){
			return false;
		}
		if(modular.getModules(IModuleEngine.class).isEmpty()){
			return false;
		}
		return true;
	}

	@Override
	public boolean canWork(IModuleState state){
		return state.getModular().getHandler().getEnergyStored(null) > 0;
	}
}
