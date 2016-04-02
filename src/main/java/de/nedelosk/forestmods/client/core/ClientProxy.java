package de.nedelosk.forestmods.client.core;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import de.nedelosk.forestcore.multiblock.MultiblockClientTickHandler;
import de.nedelosk.forestmods.api.transport.TransportClientTickHandler;
import de.nedelosk.forestmods.client.render.item.ItemCampfireRenderer;
import de.nedelosk.forestmods.client.render.item.ItemCharcoalKiln;
import de.nedelosk.forestmods.client.render.item.ItemMachineWoodBase;
import de.nedelosk.forestmods.client.render.item.ItemModularAssembler;
import de.nedelosk.forestmods.client.render.item.ItemTransport;
import de.nedelosk.forestmods.client.render.tile.TileCampfireRenderer;
import de.nedelosk.forestmods.client.render.tile.TileCharcoalKilnRenderer;
import de.nedelosk.forestmods.client.render.tile.TileModularAssemblerRenderer;
import de.nedelosk.forestmods.client.render.tile.TileModularMachineRenderer;
import de.nedelosk.forestmods.client.render.tile.TileTransportNodeRenderer;
import de.nedelosk.forestmods.client.render.tile.TileTransportRenderer;
import de.nedelosk.forestmods.common.blocks.tile.TileCampfire;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.blocks.tile.TileModularMachine;
import de.nedelosk.forestmods.common.core.BlockManager;
import de.nedelosk.forestmods.common.core.CommonProxy;
import de.nedelosk.forestmods.common.core.ItemManager;
import de.nedelosk.forestmods.common.transport.TileEntityTransport;
import de.nedelosk.forestmods.common.transport.node.TileEntityTransportNode;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerTickHandlers() {
		super.registerTickHandlers();
		FMLCommonHandler.instance().bus().register(new MultiblockClientTickHandler());
		FMLCommonHandler.instance().bus().register(new TransportClientTickHandler());
	}

	@Override
	public void registerRenderers() {
		/* Machine Base */
		ClientRegistry.bindTileEntitySpecialRenderer(TileCampfire.class, new TileCampfireRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockMachines), new ItemMachineWoodBase());
		MinecraftForgeClient.registerItemRenderer(ItemManager.itemCampfireCurb, new ItemCampfireRenderer("curb"));
		MinecraftForgeClient.registerItemRenderer(ItemManager.itemCampfirePot, new ItemCampfireRenderer("pot"));
		MinecraftForgeClient.registerItemRenderer(ItemManager.itemCampfirePotHolder, new ItemCampfireRenderer("pot_holder"));
		/* Modular */
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularMachine.class, new TileModularMachineRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularAssembler.class, new TileModularAssemblerRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockModularMachines), new ItemModularAssembler());
		/* Charcoal Kiln */
		ClientRegistry.bindTileEntitySpecialRenderer(TileCharcoalKiln.class, new TileCharcoalKilnRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockCharcoalKiln), new ItemCharcoalKiln());
		/* Transport */
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransport.class, new TileTransportRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransportNode.class, new TileTransportNodeRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockTransport), new ItemTransport());
	}

	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass) {
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}
}
