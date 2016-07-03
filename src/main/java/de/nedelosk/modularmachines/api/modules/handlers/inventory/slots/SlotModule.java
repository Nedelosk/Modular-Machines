package de.nedelosk.modularmachines.api.modules.handlers.inventory.slots;

import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotModule extends SlotItemHandler {

	public IModuleState module;
	private String backgroundTexture = null;

	public SlotModule(IModuleState moduleState, int index, int xPosition, int yPosition) {
		super((IItemHandler) moduleState.getContentHandler(ItemStack.class), index, xPosition, yPosition);
		this.module = moduleState;
	}

	@Override
	public void onSlotChanged() {
		module.getModular().getHandler().markDirty();;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(super.isItemValid(stack)){
			IModuleInventory inventory = (IModuleInventory) module.getContentHandler(ItemStack.class);
			if (inventory.isInput(getSlotIndex())) {
				return inventory.getInsertFilter().isValid(getSlotIndex(), stack, module);
			}
		}
		return false;
	}

	public SlotModule setBackgroundTexture(String backgroundTexture) {
		this.backgroundTexture = backgroundTexture;
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public TextureAtlasSprite getBackgroundSprite() {
		ItemStack stack = getStack();
		if (backgroundTexture != null) {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("modularmachines:gui/" + backgroundTexture);
		} else {
			return null;
		}
	}
}