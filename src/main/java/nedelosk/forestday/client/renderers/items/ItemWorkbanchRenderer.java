package nedelosk.forestday.client.renderers.items;

import org.lwjgl.opengl.GL11;

import com.sun.javafx.webkit.theme.Renderer;

import nedelosk.forestday.client.renderers.tile.TileWorkbanchRenderer;
import nedelosk.forestday.common.machines.wood.workbench.TileWorkbench;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemWorkbanchRenderer implements IItemRenderer {

	public TileWorkbanchRenderer renderer;
	
	public ItemWorkbanchRenderer(TileWorkbanchRenderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public boolean handleRenderType(ItemStack arg0, ItemRenderType arg1) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType arg0, ItemStack stack, Object... arg2) {
		renderer.renderItem(stack.getItemDamage(), 0.0D, 0.0D, 0.0D, 0.0F);;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType arg0, ItemStack arg1, ItemRendererHelper arg2) {
		return true;
	}

}
