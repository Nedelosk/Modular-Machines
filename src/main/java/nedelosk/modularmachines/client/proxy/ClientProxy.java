package nedelosk.modularmachines.client.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import nedelosk.modularmachines.client.renderers.item.ItemRendererModular;
import nedelosk.modularmachines.client.renderers.item.ItemRendererModularAssembler;
import nedelosk.modularmachines.client.renderers.tile.TileRendererModular;
import nedelosk.modularmachines.client.renderers.tile.TileRendererModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.proxy.CommonProxy;
import nedelosk.modularmachines.modules.ModuleModular;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	public void registerRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileModular.class, new TileRendererModular());
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularAssembler.class, new TileRendererModularAssembler());
		MinecraftForgeClient.registerItemRenderer(ModuleModular.BlockManager.Modular_Machine.item(), new ItemRendererModular());
		MinecraftForgeClient.registerItemRenderer(ModuleModular.BlockManager.Modular_Assembler.item(), new ItemRendererModularAssembler());
	}

	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass) {
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}

	@Override
	public void init() {
		registerRenderer();
	}
}
