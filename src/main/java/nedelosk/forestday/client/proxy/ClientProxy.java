package nedelosk.forestday.client.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import nedelosk.forestcore.library.multiblock.MultiblockClientTickHandler;
import nedelosk.forestday.client.renderer.item.ItemCampfireRenderer;
import nedelosk.forestday.client.renderer.item.ItemCharcoalKiln;
import nedelosk.forestday.client.renderer.item.ItemMachineWoodBase;
import nedelosk.forestday.client.renderer.tile.TileCampfireRenderer;
import nedelosk.forestday.client.renderer.tile.TileCharcoalKilnRenderer;
import nedelosk.forestday.client.renderer.tile.TileWorkbenchRenderer;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.blocks.tiles.TileWorkbench;
import nedelosk.forestday.common.multiblock.TileCharcoalKiln;
import nedelosk.forestday.common.proxy.CommonProxy;
import nedelosk.forestday.modules.ModuleCoal;
import nedelosk.forestday.modules.ModuleCore;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerTickHandlers() {
		super.registerTickHandlers();
		FMLCommonHandler.instance().bus().register(new MultiblockClientTickHandler());
	}

	@Override
	public void registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileCampfire.class, new TileCampfireRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileWorkbench.class, new TileWorkbenchRenderer());
		MinecraftForgeClient.registerItemRenderer(ModuleCore.BlockManager.Machine.item(), new ItemMachineWoodBase());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCharcoalKiln.class, new TileCharcoalKilnRenderer());
		MinecraftForgeClient.registerItemRenderer(ModuleCore.ItemManager.Curb.item(), new ItemCampfireRenderer("curb"));
		MinecraftForgeClient.registerItemRenderer(ModuleCore.ItemManager.Pot.item(), new ItemCampfireRenderer("pot"));
		MinecraftForgeClient.registerItemRenderer(ModuleCore.ItemManager.Pot_Holder.item(), new ItemCampfireRenderer("pot_holder"));
		MinecraftForgeClient.registerItemRenderer(ModuleCoal.BlockManager.Multiblock_Charcoal_Kiln.item(), new ItemCharcoalKiln());
	}

	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass) {
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}
}
