package nedelosk.forestbotany.client.core.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import nedelosk.forestbotany.client.renderers.items.ItemFruitRenderer;
import nedelosk.forestbotany.client.renderers.items.ItemInfuserBase;
import nedelosk.forestbotany.client.renderers.items.ItemSeedRenderer;
import nedelosk.forestbotany.client.renderers.tile.TileInfuserBaseRenderer;
import nedelosk.forestbotany.common.blocks.tile.TileInfuserBase;
import nedelosk.forestbotany.common.core.ForestBotany;
import nedelosk.forestbotany.common.core.proxy.CommonProxy;
import nedelosk.forestbotany.common.core.registrys.BlockRegistry;
import nedelosk.forestbotany.common.core.registrys.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers()
	{
		TileInfuserBaseRenderer rendererInfuser = new TileInfuserBaseRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileInfuserBase.class, rendererInfuser);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.infuser.block()), new ItemInfuserBase(rendererInfuser));
		
		MinecraftForgeClient.registerItemRenderer(ItemRegistry.seed.item(), new ItemSeedRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemRegistry.fruit.item(), new ItemFruitRenderer());
	}
	
}
