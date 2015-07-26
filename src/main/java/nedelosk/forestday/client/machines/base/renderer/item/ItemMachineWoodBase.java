package nedelosk.forestday.client.machines.base.renderer.item;

import org.lwjgl.opengl.GL11;

import nedelosk.forestday.client.machines.base.renderer.tile.TileCampfireRenderer;
import nedelosk.forestday.client.machines.base.renderer.tile.TileKilnRenderer;
import nedelosk.forestday.client.machines.base.renderer.tile.TileWorkbenchRenderer;
import nedelosk.forestday.client.proxy.ClientProxy;
import nedelosk.forestday.common.machines.base.wood.campfire.TileCampfire;
import nedelosk.forestday.common.machines.base.wood.kiln.TileKiln;
import nedelosk.forestday.common.machines.base.wood.workbench.TileWorkbench;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemMachineWoodBase implements IItemRenderer {

	public TileWorkbenchRenderer rendererWorkbench;
	public TileKilnRenderer rendererKiln;
	public TileCampfireRenderer rendererCampfire;
	
	public ItemMachineWoodBase() {
		this.rendererWorkbench = (TileWorkbenchRenderer)ClientProxy.getRenderer(TileWorkbench.class);
		this.rendererKiln = (TileKilnRenderer)ClientProxy.getRenderer(TileKiln.class);
		this.rendererCampfire = (TileCampfireRenderer)ClientProxy.getRenderer(TileCampfire.class);
	}
	
	@Override
	public boolean handleRenderType(ItemStack arg0, ItemRenderType arg1) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType arg0, ItemStack stack, Object... arg2) {
		if(stack.getItemDamage() == 0)
		{
			rendererCampfire.renderItem(stack.getItemDamage(), 0.0D, 0.0D, 0.0D, 0.0F);
		}
		else if(stack.getItemDamage() == 1 || stack.getItemDamage() == 2)
		{
			rendererWorkbench.renderItem(stack.getItemDamage(), 0.0D, 0.0D, 0.0D, 0.0F);
		}
		else
		{
			rendererKiln.renderItem();
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType arg0, ItemStack arg1, ItemRendererHelper arg2) {
		return true;
	}

}
