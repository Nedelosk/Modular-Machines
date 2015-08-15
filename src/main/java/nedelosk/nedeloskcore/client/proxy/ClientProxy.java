package nedelosk.nedeloskcore.client.proxy;

import nedelosk.nedeloskcore.client.renderer.item.ItemPlanRenderer;
import nedelosk.nedeloskcore.client.renderer.tile.BlockMultiblockValveRenderer;
import nedelosk.nedeloskcore.client.renderer.tile.TilePlanRenderer;
import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;
import nedelosk.nedeloskcore.common.book.BookDatas;
import nedelosk.nedeloskcore.common.core.registry.NCBlocks;
import nedelosk.nedeloskcore.common.proxy.CommonProxy;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void init()
	{
		BookDatas.readManuals();
	}
	
	@Override
	public void registerRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TilePlan.class, new TilePlanRenderer());
		MinecraftForgeClient.registerItemRenderer(NCBlocks.Plan_Block.item(), new ItemPlanRenderer(new TilePlanRenderer()));
		BlockMultiblockValveRenderer multiblockRenderer = new BlockMultiblockValveRenderer();
		RenderingRegistry.registerBlockHandler(multiblockRenderer);
		MinecraftForgeClient.registerItemRenderer(NCBlocks.Multiblock_Valve.item(), multiblockRenderer);
	}
	
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
