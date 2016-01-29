package nedelosk.modularmachines.client.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import nedelosk.modularmachines.client.renderers.item.ItemRendererModular;
import nedelosk.modularmachines.client.renderers.tile.TileRendererModular;
import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
import nedelosk.modularmachines.common.proxy.CommonProxy;
import nedelosk.modularmachines.modules.ModuleModular;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	public void registerRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularMachine.class, new TileRendererModular());
		MinecraftForgeClient.registerItemRenderer(ModuleModular.BlockManager.Modular_Machine.item(), new ItemRendererModular());
	}

	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass) {
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}

	@Override
	public void init() {
		registerRenderer();
	}
}
