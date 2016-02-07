package de.nedelosk.forestmods.common.modules.machines.recipe;

import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.inventory.ModuleInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public abstract class ModuleMachineRecipeInventory<M extends IModuleMachineRecipe, S extends IModuleSaver> extends ModuleInventory<M, S> {

	public ModuleMachineRecipeInventory(String UID, int slots) {
		super(UID, slots);
	}

	@Override
	public boolean transferInput(ModuleStack<M, S> stackModule, IModularTileEntity tile, EntityPlayer player, int slotID, Container container,
			ItemStack stackItem) {
		if (RecipeRegistry.isRecipeInput(stackModule.getModule().getRecipeCategory(stackModule), new RecipeItem(slotID, stackItem))) {
			if (mergeItemStack(stackItem, 36 + slotID, 37 + slotID, false, container)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getSizeInventory(ModuleStack<M, S> stack, IModularDefault modular) {
		return stack.getModule().getItemInputs(stack) + stack.getModule().getItemOutputs(stack);
	}
}
