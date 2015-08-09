package nedelosk.modularmachines.client.renderers;

import nedelosk.modularmachines.client.proxy.ClientProxy;
import nedelosk.modularmachines.common.blocks.item.ItemBlockModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularWorkbench;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemModularMachinesRenderer implements IItemRenderer {

	public TileModularAssemblerRenderer assembler;
	public TileModularWorkbenchRenderer workbench;
	
	public ItemModularMachinesRenderer() {
		this.assembler = (TileModularAssemblerRenderer )ClientProxy.getRenderer(TileModularAssembler.class);
		this.workbench = (TileModularWorkbenchRenderer )ClientProxy.getRenderer(TileModularWorkbench.class);
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if(item.getItem() instanceof ItemBlockModularAssembler)
			assembler.renderItem();
		else
			workbench.renderItem();
	}

}
