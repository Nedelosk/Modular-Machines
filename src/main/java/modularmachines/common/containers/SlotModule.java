package modularmachines.common.containers;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import net.minecraftforge.items.SlotItemHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.components.IItemHandlerComponent;
import modularmachines.common.modules.components.ItemHandlerComponent;

public class SlotModule extends SlotItemHandler {
	
	public IItemHandlerComponent component;
	@Nullable
	private final String backgroundTexture;
	
	public SlotModule(ItemHandlerComponent itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		component = itemHandler;
		IItemHandlerComponent.IItemSlot slot = itemHandler.getSlot(index);
		backgroundTexture = slot.getBackgroundTexture();
	}
	
	@Override
	public void onSlotChanged() {
		component.getProvider().getContainer().getLocatable().markLocatableDirty();
	}
	
	@Nullable
	@SideOnly(Side.CLIENT)
	@Override
	public TextureAtlasSprite getBackgroundSprite() {
		if (backgroundTexture != null) {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("modularmachines:gui/" + backgroundTexture);
		} else {
			return null;
		}
	}
}