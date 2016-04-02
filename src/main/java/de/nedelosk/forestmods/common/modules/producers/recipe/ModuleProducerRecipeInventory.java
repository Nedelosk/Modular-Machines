package de.nedelosk.forestmods.common.modules.producers.recipe;

import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.common.producers.handlers.inventorys.ProducerInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public abstract class ModuleProducerRecipeInventory<M extends IModuleProducerRecipe, S extends IModuleSaver> extends ProducerInventory<M, S> {

	public ModuleProducerRecipeInventory(ModuleUID UID, int slots) {
		super(UID, slots);
	}

	@Override
	public boolean transferInput(ModuleStack<M, S> stackModule, IModularState tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem) {
		if (RecipeRegistry.isRecipeInput(stackModule.getModuleStack().getRecipeCategory(stackModule), new RecipeItem(slotID, stackItem))) {
			if (mergeItemStack(stackItem, 36 + slotID, 37 + slotID, false, container)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getSizeInventory(ModuleStack<M, S> stack, IModularDefault modular) {
		return stack.getModuleStack().getInputSlots(stack) + stack.getModuleStack().getOutputSlots(stack);
	}
}
