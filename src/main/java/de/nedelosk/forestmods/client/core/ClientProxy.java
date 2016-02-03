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
import de.nedelosk.forestmods.common.core.CommonProxy;
import de.nedelosk.forestmods.common.core.modules.ModuleForestDay;
import de.nedelosk.forestmods.common.core.modules.ModuleModularMachine;
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileCharcoalKiln.class, new TileCharcoalKilnRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularMachine.class, new TileModularMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(ModuleForestDay.ItemManager.Curb.item(), new ItemCampfireRenderer("curb"));
		MinecraftForgeClient.registerItemRenderer(ModuleForestDay.ItemManager.Pot.item(), new ItemCampfireRenderer("pot"));
		MinecraftForgeClient.registerItemRenderer(ModuleForestDay.ItemManager.Pot_Holder.item(), new ItemCampfireRenderer("pot_holder"));
		MinecraftForgeClient.registerItemRenderer(ModuleForestDay.BlockManager.Multiblock_Charcoal_Kiln.item(), new ItemCharcoalKiln());
		MinecraftForgeClient.registerItemRenderer(ModuleModularMachine.BlockManager.Modular_Machine.item(), new ItemRendererModular());
		MinecraftForgeClient.registerItemRenderer(ModuleForestDay.BlockManager.Machine.item(), new ItemMachineWoodBase());
	}

	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass) {
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}
}
