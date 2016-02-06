package de.nedelosk.forestmods.client.core;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import de.nedelosk.forestcore.multiblock.MultiblockClientTickHandler;
import de.nedelosk.forestmods.client.render.item.ItemCampfireRenderer;
import de.nedelosk.forestmods.client.render.item.ItemCharcoalKiln;
import de.nedelosk.forestmods.client.render.item.ItemMachineWoodBase;
import de.nedelosk.forestmods.client.render.item.ItemRendererModular;
import de.nedelosk.forestmods.client.render.tile.TileCampfireRenderer;
import de.nedelosk.forestmods.client.render.tile.TileCharcoalKilnRenderer;
import de.nedelosk.forestmods.client.render.tile.TileModularMachineRenderer;
import de.nedelosk.forestmods.client.render.tile.TileWorkbenchRenderer;
import de.nedelosk.forestmods.common.blocks.tile.TileCampfire;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.blocks.tile.TileModularMachine;
import de.nedelosk.forestmods.common.blocks.tile.TileWorkbench;
import de.nedelosk.forestmods.common.core.BlockManager;
import de.nedelosk.forestmods.common.core.CommonProxy;
import de.nedelosk.forestmods.common.core.ItemManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileCharcoalKiln.class, new TileCharcoalKilnRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularMachine.class, new TileModularMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemManager.itemCampfireCurb, new ItemCampfireRenderer("curb"));
		MinecraftForgeClient.registerItemRenderer(ItemManager.itemCampfirePot, new ItemCampfireRenderer("pot"));
		MinecraftForgeClient.registerItemRenderer(ItemManager.itemCampfirePotHolder, new ItemCampfireRenderer("pot_holder"));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockCharcoalKiln), new ItemCharcoalKiln());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockModularMachines), new ItemRendererModular());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockManager.blockMachines), new ItemMachineWoodBase());
	}

	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass) {
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}
}
