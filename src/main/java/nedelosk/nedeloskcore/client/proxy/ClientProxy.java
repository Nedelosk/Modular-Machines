package nedelosk.nedeloskcore.client.proxy;

import nedelosk.nedeloskcore.client.renderer.item.ItemPlanRenderer;
import nedelosk.nedeloskcore.client.renderer.tile.TilePlanRenderer;
import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;
import nedelosk.nedeloskcore.common.book.BookDatas;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import nedelosk.nedeloskcore.common.proxy.CommonProxy;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void init()
	{
		BookDatas.readManuals();
	}
	
	@Override
	public void registerRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TilePlan.class, new TilePlanRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NRegistry.planBlock), new ItemPlanRenderer(new TilePlanRenderer()));
	}
	
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
