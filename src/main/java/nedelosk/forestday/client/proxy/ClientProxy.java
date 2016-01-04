package nedelosk.forestday.client.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import nedelosk.forestcore.library.multiblock.MultiblockClientTickHandler;
import nedelosk.forestday.client.renderer.item.ItemCampfireRenderer;
import nedelosk.forestday.client.renderer.item.ItemMachineWoodBase;
import nedelosk.forestday.client.renderer.tile.TileCampfireRenderer;
import nedelosk.forestday.client.renderer.tile.TileCharcoalAshRenderer;
import nedelosk.forestday.client.renderer.tile.TileCharcoalKilnRenderer;
import nedelosk.forestday.client.renderer.tile.TileWorkbenchRenderer;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalAsh;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalKiln;
import nedelosk.forestday.common.blocks.tiles.TileWorkbench;
import nedelosk.forestday.common.proxy.CommonProxy;
import nedelosk.forestday.modules.ModuleCore;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	public static int[][] sideAndFacingToSpriteOffset = new int[][] {

			{ 3, 2, 0, 0, 0, 0 }, { 2, 3, 1, 1, 1, 1 }, { 1, 1, 3, 2, 5, 4 }, { 0, 0, 2, 3, 4, 5 },
			{ 4, 5, 4, 5, 3, 2 }, { 5, 4, 5, 4, 2, 3 } };

	@Override
	public void registerTickHandlers() {
		FMLCommonHandler.instance().bus().register(new MultiblockClientTickHandler());
	}

	@Override
	public void registerRenderers() {
		;
		ClientRegistry.bindTileEntitySpecialRenderer(TileCampfire.class, new TileCampfireRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileWorkbench.class, new TileWorkbenchRenderer());
		MinecraftForgeClient.registerItemRenderer(ModuleCore.BlockManager.Machine.item(), new ItemMachineWoodBase());

		ClientRegistry.bindTileEntitySpecialRenderer(TileCharcoalKiln.class, new TileCharcoalKilnRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCharcoalAsh.class, new TileCharcoalAshRenderer());

		MinecraftForgeClient.registerItemRenderer(ModuleCore.ItemManager.Curb.item(), new ItemCampfireRenderer("curb"));
		MinecraftForgeClient.registerItemRenderer(ModuleCore.ItemManager.Pot.item(), new ItemCampfireRenderer("pot"));
		MinecraftForgeClient.registerItemRenderer(ModuleCore.ItemManager.Pot_Holder.item(),
				new ItemCampfireRenderer("pot_holder"));

		/*
		 * BlockMultiblockValveRenderer multiblockValveRenderer = new
		 * BlockMultiblockValveRenderer();
		 * RenderingRegistry.registerBlockHandler(multiblockValveRenderer);
		 * MinecraftForgeClient.registerItemRenderer(FBlockManager.
		 * Multiblock_Valve.item(), multiblockValveRenderer);
		 * BlockMultiblockRenderer multiblockRenderer = new
		 * BlockMultiblockRenderer();
		 * RenderingRegistry.registerBlockHandler(multiblockRenderer);
		 * MinecraftForgeClient.registerItemRenderer(FBlockManager.Multiblock.
		 * item(), multiblockRenderer);
		 * ClientRegistry.bindTileEntitySpecialRenderer(TileMultiblockBase.
		 * class, new TileMultiblockRenderer());
		 */
	}

	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass) {
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers
				.get(tileEntityClass);
	}

}
