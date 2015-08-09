package nedelosk.forestday.client.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import nedelosk.forestday.client.machines.base.renderer.item.ItemCampfireRenderer;
import nedelosk.forestday.client.machines.base.renderer.item.ItemMachineWoodBase;
import nedelosk.forestday.client.machines.base.renderer.tile.TileCampfireRenderer;
import nedelosk.forestday.client.machines.base.renderer.tile.TileKilnRenderer;
import nedelosk.forestday.client.machines.base.renderer.tile.TileWorkbenchRenderer;
import nedelosk.forestday.client.machines.multiblock.charcoalkiln.ItemCharcoalKilnRenderer;
import nedelosk.forestday.client.machines.multiblock.charcoalkiln.TileCharcoalAshRenderer;
import nedelosk.forestday.client.machines.multiblock.charcoalkiln.TileCharcoalKilnRenderer;
import nedelosk.forestday.common.machines.base.wood.campfire.TileCampfire;
import nedelosk.forestday.common.machines.base.wood.kiln.TileKiln;
import nedelosk.forestday.common.machines.base.wood.workbench.TileWorkbench;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.TileCharcoalAsh;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.TileCharcoalKiln;
import nedelosk.forestday.common.proxy.CommonProxy;
import nedelosk.forestday.common.registrys.FBlocks;
import nedelosk.forestday.common.registrys.FItems;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	
	  public static int[][] sideAndFacingToSpriteOffset = new int[][] {

	      { 3, 2, 0, 0, 0, 0 },
	      { 2, 3, 1, 1, 1, 1 },
	      { 1, 1, 3, 2, 5, 4 },
	      { 0, 0, 2, 3, 4, 5 },
	      { 4, 5, 4, 5, 3, 2 },
	      { 5, 4, 5, 4, 2, 3 } };
	
	@Override
	public void registerRenderers() {
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileKiln.class, new TileKilnRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCampfire.class, new TileCampfireRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileWorkbench.class, new TileWorkbenchRenderer());
		MinecraftForgeClient.registerItemRenderer(FBlocks.Machine_Wood_Base.item(), new ItemMachineWoodBase());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileCharcoalKiln.class, new TileCharcoalKilnRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCharcoalAsh.class, new TileCharcoalAshRenderer());
		MinecraftForgeClient.registerItemRenderer(FItems.charcoal_kiln.item(), new ItemCharcoalKilnRenderer());
		
		MinecraftForgeClient.registerItemRenderer(FItems.curb.item(), new ItemCampfireRenderer("curb"));
		MinecraftForgeClient.registerItemRenderer(FItems.pot.item(), new ItemCampfireRenderer("pot"));
		MinecraftForgeClient.registerItemRenderer(FItems.pot_holder.item(), new ItemCampfireRenderer("pot_holder"));
	}
	
	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass){
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}
	
	
	
	
}
