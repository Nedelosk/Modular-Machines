package nedelosk.forestday.client.proxy;

import nedelosk.forestday.client.machines.iron.renderer.item.ItemSawRenderer;
import nedelosk.forestday.client.machines.iron.renderer.tile.TileSawRenderer;
import nedelosk.forestday.client.renderers.items.ItemWorkbanchRenderer;
import nedelosk.forestday.client.renderers.tile.TileWorkbanchRenderer;
import nedelosk.forestday.common.machines.brick.kiln.TileKiln;
import nedelosk.forestday.common.machines.iron.saw.TileSaw;
import nedelosk.forestday.common.machines.wood.workbench.TileWorkbench;
import nedelosk.forestday.common.proxy.CommonProxy;
import nedelosk.forestday.common.registrys.ForestdayBlockRegistry;
import nedelosk.forestday.machines.kiln.render.RendererKiln;
import nedelosk.forestday.machines.kiln.render.RendererKilnItem;
import nedelosk.forestday.structure.base.blocks.tile.TileCoilGrinding;
import nedelosk.forestday.structure.base.render.ItemRendererCoil;
import nedelosk.forestday.structure.base.render.RendererCoil;
import nedelosk.forestday.structure.base.render.SimpleBusRenderer;
import nedelosk.forestday.structure.base.render.SimpleBusRendererMetal;
import nedelosk.forestday.structure.kiln.charcoal.blocks.tile.TileCharcoalKiln;
import nedelosk.forestday.structure.kiln.charcoal.render.RendererCharcoalKiln;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
    public void registerRenderers() {
		
		TileEntitySpecialRenderer rendrerKilnResin = new RendererKiln();
		MinecraftForgeClient.registerItemRenderer(new ItemStack(ForestdayBlockRegistry.machineKiln, 1, 0).getItem(), new RendererKilnItem(rendrerKilnResin));
		ClientRegistry.bindTileEntitySpecialRenderer(TileKiln.class, rendrerKilnResin);
		
		TileEntitySpecialRenderer rendrerFile = new TileSawRenderer();
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ForestdayBlockRegistry.machineFile), new ItemSawRenderer(rendrerFile));
		ClientRegistry.bindTileEntitySpecialRenderer(TileSaw.class, rendrerFile);
		
		RenderingRegistry.registerBlockHandler(new SimpleBusRenderer());
		RenderingRegistry.registerBlockHandler(new SimpleBusRendererMetal());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileCoilGrinding.class, new RendererCoil());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ForestdayBlockRegistry.grindingCoil), new ItemRendererCoil(getRenderer(TileCoilGrinding.class)));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileCharcoalKiln.class, new RendererCharcoalKiln());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileWorkbench.class, new TileWorkbanchRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ForestdayBlockRegistry.workbench), new ItemWorkbanchRenderer((TileWorkbanchRenderer)getRenderer(TileWorkbench.class)));
}
	
	private TileEntitySpecialRenderer getRenderer(Class tileEntityClass){
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	
	
	
}
