package de.nedelosk.modularmachines.common.inventory.slots;

import de.nedelosk.modularmachines.common.inventory.ContainerModuleCrafter;
import forestry.core.gui.slots.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class SlotModuleCrafterOutput extends SlotOutput {

	private ContainerModuleCrafter container;

	public SlotModuleCrafterOutput(int slotIndex, int posX, int posY, ContainerModuleCrafter container) {
		super(new InventoryCraftResult(), slotIndex, posX, posY);

		this.container = container;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
		FMLCommonHandler.instance().firePlayerCraftingEvent(playerIn, stack, container.getHandler());
		stack.onCrafting(playerIn.getEntityWorld(), playerIn, stack.stackSize);
		container.onResultTaken(playerIn, stack);

		super.onPickupFromSlot(playerIn, stack);
	}
}
