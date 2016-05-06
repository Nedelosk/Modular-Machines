package de.nedelosk.forestmods.client.core;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import de.nedelosk.forestmods.client.render.blocks.BlockCharcoalKilnRenderer;
import de.nedelosk.forestmods.client.render.item.CampfireItemsRenderer;
import de.nedelosk.forestmods.client.render.item.ItemCampfireRenderer;
import de.nedelosk.forestmods.client.render.item.ItemCharcoalKilnRenderer;
import de.nedelosk.forestmods.client.render.item.ItemModularAssemblerRenderer;
import de.nedelosk.forestmods.client.render.item.ItemModularRenderer;
import de.nedelosk.forestmods.client.render.item.ItemTransportRenderer;
import de.nedelosk.forestmods.client.render.tile.TileBloomeryRenderer;
import de.nedelosk.forestmods.client.render.tile.TileCampfireRenderer;
import de.nedelosk.forestmods.client.render.tile.TileCharcoalKilnRenderer;
import de.nedelosk.forestmods.client.render.tile.TileModularAssemblerRenderer;
import de.nedelosk.forestmods.client.render.tile.TileModularMachineRenderer;
import de.nedelosk.forestmods.client.render.tile.TileTransportNodeRenderer;
import de.nedelosk.forestmods.client.render.tile.TileTransportRenderer;
import de.nedelosk.forestmods.common.blocks.tile.TileBloomery;
import de.nedelosk.forestmods.common.blocks.tile.TileCampfire;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.blocks.tile.TileModular;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.core.BlockManager;
import de.nedelosk.forestmods.common.core.CommonProxy;
import de.nedelosk.forestmods.common.core.ItemManager;
import de.nedelosk.forestmods.common.transport.TileEntityTransport;
import de.nedelosk.forestmods.common.transport.node.TileEntityTransportNode;
import de.nedelosk.forestmods.library.multiblock.MultiblockClientTickHandler;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	public static int charcoalKilnRenderID;

	@Override
	public void registerTickHandlers() {
		super.registerTickHandlers();
		FMLCommonHandler.instance().bus().register(new MultiblockClientTickHandler());
		// FMLCommonHandler.instance().bus().register(new
		// TransportClientTickHandler());
	}

	@Override
	public void registerRenderers() {
		/* Machine Base */
		charcoalKilnRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockCharcoalKilnRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCampfire.class, new TileCampfireRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockCampfire), new ItemCampfireRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemManager.itemCampfireCurb, new CampfireItemsRenderer("curb"));
		MinecraftForgeClient.registerItemRenderer(ItemManager.itemCampfirePot, new CampfireItemsRenderer("pot"));
		MinecraftForgeClient.registerItemRenderer(ItemManager.itemCampfirePotHolder, new CampfireItemsRenderer("pot_holder"));
		/* Modular */
		ClientRegistry.bindTileEntitySpecialRenderer(TileModular.class, new TileModularMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockModular), new ItemModularRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularAssembler.class, new TileModularAssemblerRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockAssembler), new ItemModularAssemblerRenderer());
		/* Charcoal Kiln */
		ClientRegistry.bindTileEntitySpecialRenderer(TileCharcoalKiln.class, new TileCharcoalKilnRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockCharcoalKiln), new ItemCharcoalKilnRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileBloomery.class, new TileBloomeryRenderer());

		/* Transport */
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransport.class, new TileTransportRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransportNode.class, new TileTransportNodeRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockTransport), new ItemTransportRenderer());
	}

	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass) {
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}
}
