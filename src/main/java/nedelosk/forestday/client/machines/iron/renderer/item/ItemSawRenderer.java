package nedelosk.forestday.client.machines.iron.renderer.item;

import nedelosk.forestday.client.machines.iron.renderer.model.ModelSaw;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class ItemSawRenderer implements IItemRenderer {

	private TileEntitySpecialRenderer render;
	private ModelSaw file;
	
	public ItemSawRenderer(TileEntitySpecialRenderer renderer) {
        this.file = new ModelSaw();
        this.render = renderer;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		this.render.renderTileEntityAt(new TileEntity(), 0.0D, 0.0D, 0.0D, 0.0F);
	}

}
