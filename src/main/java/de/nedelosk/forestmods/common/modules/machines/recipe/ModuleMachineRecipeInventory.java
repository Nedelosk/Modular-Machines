package de.nedelosk.forestmods.common.modules.machines.recipe;

import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachineSaver;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.inventory.ModuleInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public abstract class ModuleMachineRecipeInventory<M extends IModuleMachineRecipe, S extends IModuleMachineSaver> extends ModuleInventory<M, S> {

	public ModuleMachineRecipeInventory(String UID, int slots) {
		super(UID, slots);
	}

	@Override
	public boolean transferInput(ModuleStack<M, S> stackModule, IModularTileEntity tile, EntityPlayer player, int slotID, Container container,
			ItemStack stackItem) {
		RecipeItem input = RecipeRegistry.getRecipeInput(stackModule.getModule().getRecipeName(stackModule), new RecipeItem(slotID, stackItem));
		if (input != null) {
			if (mergeItemStack(stackItem, 36 + input.slotIndex, 37 + input.slotIndex, false, container)) {
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
