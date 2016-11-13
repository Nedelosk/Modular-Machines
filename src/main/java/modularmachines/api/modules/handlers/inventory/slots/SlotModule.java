package modularmachines.api.modules.handlers.inventory.slots;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.handlers.inventory.IModuleInventory;
import modularmachines.api.modules.handlers.inventory.SlotInfo;

public class SlotModule extends SlotItemHandler {

	public IModulePage page;
	public SlotInfo info;

	public SlotModule(IModulePage page, SlotInfo info) {
		super(page.getInventory(), info.index, info.xPosition, info.yPosition);
		this.info = info;
		this.page = page;
	}

	@Override
	public boolean canTakeStack(EntityPlayer playerIn) {
		return ((IModuleInventory) this.getItemHandler()).extractItemInternal(getSlotIndex(), 1, true) != null;
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		return ((IModuleInventory) this.getItemHandler()).extractItemInternal(getSlotIndex(), amount, false);
	}

	@Override
	public void onSlotChanged() {
		page.getModuleState().getModular().getHandler().markDirty();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public TextureAtlasSprite getBackgroundSprite() {
		if (info.backgroundTexture != null) {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("modularmachines:gui/" + info.backgroundTexture);
		} else {
			return null;
		}
	}
}