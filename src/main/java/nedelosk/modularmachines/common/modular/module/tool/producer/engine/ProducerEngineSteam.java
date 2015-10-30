package nedelosk.modularmachines.common.modular.module.tool.producer.engine;

import java.util.ArrayList;

import nedelosk.forestday.api.guis.IContainerBase;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGui;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGuiWithWidgets;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.IProducerInventory;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.common.modular.utils.RecipeManagerEnergy;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ProducerEngineSteam extends ProducerEngine {

	public ProducerEngineSteam(String modifier, int speedModifier) {
		super(modifier, speedModifier, "Steam");
	}

	public ProducerEngineSteam(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public int getMaterialModifier(ModuleStack stack) {
		return 3;
	}
	
	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		super.updateServer(modular, stack);
	}

	@Override
	public IRecipeManager creatRecipeManager(IModular modular, String recipeName, int materialModifier, RecipeInput[] inputs) {
		return new RecipeManagerEnergy(modular, recipeName, materialModifier, inputs);
	}

	@Override
	public IRecipeManager creatRecipeManager() {
		return new RecipeManagerEnergy();
	}
	
}
