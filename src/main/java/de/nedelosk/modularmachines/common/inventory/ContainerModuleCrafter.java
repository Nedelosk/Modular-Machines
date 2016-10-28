package de.nedelosk.modularmachines.common.inventory;

import de.nedelosk.modularmachines.api.recipes.IModuleCrafterRecipe;
import de.nedelosk.modularmachines.api.recipes.ModuleCraftingWrapper;
import de.nedelosk.modularmachines.common.blocks.tile.TileModuleCrafter;
import de.nedelosk.modularmachines.common.inventory.slots.SlotModuleCrafter;
import de.nedelosk.modularmachines.common.inventory.slots.SlotModuleCrafterHolder;
import de.nedelosk.modularmachines.common.inventory.slots.SlotModuleCrafterOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.WorldServer;

public class ContainerModuleCrafter extends BaseContainer<TileModuleCrafter> {

	protected ModuleCraftingWrapper wrapper;
	protected SlotModuleCrafterOutput output;

	public ContainerModuleCrafter(TileModuleCrafter tile, InventoryPlayer inventory) {
		super(tile, inventory);
		wrapper = new ModuleCraftingWrapper(this, tile);
		onCraftMatrixChanged(tile);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				addSlotToContainer(new SlotModuleCrafter(handler, j + i * 3, 35 + j * 18, 17 + i * 18, this));
			}
		}
		addSlotToContainer(new SlotModuleCrafterHolder(handler, 9, 8, 17, this));
		addSlotToContainer(output = new SlotModuleCrafterOutput(0, 130, 35, this));
	}

	public ItemStack[] getRemainingItems(IModuleCrafterRecipe crafterRecipe) {
		if (crafterRecipe != null) {
			return crafterRecipe.getRemainingItems(wrapper);
		}
		return CraftingManager.getInstance().getRemainingItems(wrapper, player.worldObj);
	}

	public void onResultTaken(EntityPlayer player, ItemStack stack) {
		IModuleCrafterRecipe crafterRecipe = null;
		for(IRecipe recipe : CraftingManager.getInstance().getRecipeList()) {
			if (recipe instanceof IModuleCrafterRecipe) {
				if (((IModuleCrafterRecipe) recipe).matches(wrapper, player.worldObj)) {
					crafterRecipe = (IModuleCrafterRecipe) recipe;
					break;
				}
			}
		}
		net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
		ItemStack[] aitemstack = getRemainingItems(crafterRecipe);
		net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);
		for(int i = 0; i < aitemstack.length; ++i) {
			ItemStack itemstack = handler.getStackInSlot(i);
			ItemStack itemstack1 = aitemstack[i];
			if (itemstack != null) {
				handler.decrStackSize(i, 1);
				itemstack = handler.getStackInSlot(i);
			}
			if (itemstack1 != null) {
				if (itemstack == null) {
					handler.setInventorySlotContents(i, itemstack1);
				} else if (ItemStack.areItemsEqual(itemstack, itemstack1) && ItemStack.areItemStackTagsEqual(itemstack, itemstack1)) {
					itemstack1.stackSize += itemstack.stackSize;
					handler.setInventorySlotContents(i, itemstack1);
				} else if (!player.inventory.addItemStackToInventory(itemstack1)) {
					player.dropItem(itemstack1, false);
				}
			}
		}
		if (crafterRecipe != null && crafterRecipe.getHolder() != null) {
			handler.decrStackSize(9, 1);
		}
		onCraftMatrixChanged(handler);
	}

	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
		return slotIn != output && super.canMergeSlot(stack, slotIn);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		ItemStack result = null;
		for(IRecipe recipe : CraftingManager.getInstance().getRecipeList()) {
			if (recipe instanceof IModuleCrafterRecipe) {
				if (((IModuleCrafterRecipe) recipe).matches(wrapper, handler.getWorld())) {
					result = recipe.getCraftingResult(wrapper);
					break;
				}
			} else {
				if (recipe.matches(wrapper, handler.getWorld())) {
					result = recipe.getCraftingResult(wrapper);
					break;
				}
			}
		}
		output.inventory.setInventorySlotContents(0, result);
		if (!handler.getWorld().isRemote) {
			WorldServer server = (WorldServer) handler.getWorld();
			for(EntityPlayer player : server.playerEntities) {
				if (player.openContainer != this && player.openContainer instanceof ContainerModuleCrafter && this.sameGui((ContainerModuleCrafter) player.openContainer)) {
					((ContainerModuleCrafter) player.openContainer).output.inventory.setInventorySlotContents(0, result);
				}
			}
		}
	}
}
