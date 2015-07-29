package nedelosk.modularmachines.client.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import nedelosk.modularmachines.client.renderers.ItemModularMachinesRenderer;
import nedelosk.modularmachines.client.renderers.TileModularMachinesRenderer;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.core.MMBlocks;
import nedelosk.modularmachines.common.proxy.CommonProxy;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularAssembler.class, new TileModularMachinesRenderer());
		MinecraftForgeClient.registerItemRenderer(MMBlocks.Modular_Assembler.item(), new ItemModularMachinesRenderer());
	}
	
	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass){
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}
}
