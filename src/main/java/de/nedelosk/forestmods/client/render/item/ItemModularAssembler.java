package de.nedelosk.forestmods.client.render.item;

import de.nedelosk.forestmods.client.core.ClientProxy;
import de.nedelosk.forestmods.client.render.tile.TileModularAssemblerRenderer;
import de.nedelosk.forestmods.client.render.tile.TileModularMachineRenderer;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.blocks.tile.TileModularMachine;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemModularAssembler implements IItemRenderer {

	public TileModularMachineRenderer modular;
	public TileModularAssemblerRenderer renderer;

	public ItemModularAssembler() {
		this.renderer = (TileModularAssemblerRenderer) ClientProxy.getRenderer(TileModularAssembler.class);
		this.modular = (TileModularMachineRenderer) ClientProxy.getRenderer(TileModularMachine.class);
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
		if (item.getItemDamage() == 0) {
			modular.renderTileEntityItem(item);
		} else {
			renderer.renderItem();
		}
	}
}
