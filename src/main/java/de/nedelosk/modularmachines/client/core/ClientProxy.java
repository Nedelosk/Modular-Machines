package de.nedelosk.modularmachines.client.core;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.client.render.item.ItemModularAssemblerRenderer;
import de.nedelosk.modularmachines.client.render.item.ItemModularRenderer;
import de.nedelosk.modularmachines.client.render.item.ItemTransportRenderer;
import de.nedelosk.modularmachines.client.render.tile.TileModularAssemblerRenderer;
import de.nedelosk.modularmachines.client.render.tile.TileModularMachineRenderer;
import de.nedelosk.modularmachines.client.render.tile.TileTransportNodeRenderer;
import de.nedelosk.modularmachines.client.render.tile.TileTransportRenderer;
import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.core.CommonProxy;
import de.nedelosk.modularmachines.common.core.ItemManager;
import de.nedelosk.modularmachines.common.transport.TileEntityTransport;
import de.nedelosk.modularmachines.common.transport.TransportClientTickHandler;
import de.nedelosk.modularmachines.common.transport.node.TileEntityTransportNode;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerTickHandlers() {
		super.registerTickHandlers();
		MinecraftForge.EVENT_BUS.register(new TransportClientTickHandler());
	}

	@Override
	public void registerRenderers() {
		/* Modular */
		ClientRegistry.bindTileEntitySpecialRenderer(TileModular.class, new TileModularMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockModular), new ItemModularRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularAssembler.class, new TileModularAssemblerRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockAssembler), new ItemModularAssemblerRenderer());
		/* Charcoal Kiln */

		/* Transport */
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransport.class, new TileTransportRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransportNode.class, new TileTransportNodeRenderer());
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlockManager.blockTransport), 0, TileEntityTransport.class);
		for(int i = 1; i < ModularMachinesApi.getNodeTypes().size(); i++) {
			ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlockManager.blockTransport), i, TileEntityTransportNode.class);
		}
	}

	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass) {
		return TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}
	
	@Override
	public void registerBlock(Block block){
		ModelManager.getInstance().registerBlockClient(block);
	}
	
	@Override
	public void registerItem(Item item){
		ModelManager.getInstance().registerItemClient(item);
	}
}
