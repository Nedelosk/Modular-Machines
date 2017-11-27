package modularmachines.common.containers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

import net.minecraftforge.items.SlotItemHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.components.handlers.IItemHandlerComponent;

public class SlotModule extends SlotItemHandler {
	
	public IItemHandlerComponent component;
	@Nullable
	private final String backgroundTexture;
	
	public SlotModule(IItemHandlerComponent itemComponent, int index, int xPosition, int yPosition) {
		super(itemComponent, index, xPosition, yPosition);
		component = itemComponent;
		IItemHandlerComponent.IItemSlot slot = itemComponent.getSlot(index);
		if (slot == null) {
			throw new IllegalArgumentException();
		}
		backgroundTexture = slot.getBackgroundTexture();
	}
	
	@Override
	public void onSlotChanged() {
		component.getProvider().getContainer().getLocatable().markLocatableDirty();
	}
	
	@Override
	public boolean isItemValid(@Nonnull ItemStack stack) {
		IItemHandlerComponent.IItemSlot slot = component.getSlot(getSlotIndex());
		return slot != null && !slot.isOutput() && slot.getFilter().test(stack);
	}
	
	@SideOnly(Side.CLIENT)
	@Nullable
	@Override
	public TextureAtlasSprite getBackgroundSprite() {
		if (backgroundTexture != null) {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("modularmachines:gui/" + backgroundTexture);
		} else {
			return null;
		}
	}
}