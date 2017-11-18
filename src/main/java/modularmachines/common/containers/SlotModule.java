package modularmachines.common.containers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import net.minecraftforge.items.SlotItemHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.common.inventory.ItemContainer;
import modularmachines.common.inventory.ItemHandlerModule;

public class SlotModule extends SlotItemHandler {
	
	public ItemContainer container;
	
	public SlotModule(ItemHandlerModule itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.container = itemHandler.getContainer(index);
	}
	
	@Override
	public void onSlotChanged() {
		container.markDirty();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public TextureAtlasSprite getBackgroundSprite() {
		if (container.getBackgroundTexture() != null) {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("modularmachines:gui/" + container.getBackgroundTexture());
		} else {
			return null;
		}
	}
}