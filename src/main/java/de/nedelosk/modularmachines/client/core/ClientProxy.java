package de.nedelosk.modularmachines.client.core;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.client.render.tile.TileModularAssemblerRenderer;
import de.nedelosk.modularmachines.client.render.tile.TileModularMachineRenderer;
import de.nedelosk.modularmachines.client.render.tile.TileTransportNodeRenderer;
import de.nedelosk.modularmachines.client.render.tile.TileTransportRenderer;
import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.core.CommonProxy;
import de.nedelosk.modularmachines.common.transport.TileEntityTransport;
import de.nedelosk.modularmachines.common.transport.TransportClientTickHandler;
import de.nedelosk.modularmachines.common.transport.node.TileEntityTransportNode;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerTickHandlers() {
		super.registerTickHandlers();
		MinecraftForge.EVENT_BUS.register(new TransportClientTickHandler());
	}

	@Override
	public void registerRenderers() {
		/* Modular */
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularAssembler.class, new TileModularAssemblerRenderer());
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlockManager.blockAssembler), 0, TileModularAssembler.class);

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
	public void registerFluidStateMapper(Block block, final Fluid fluid) {
		final ModelResourceLocation fluidLocation = new ModelResourceLocation("modularmachines:fluids", fluid.getName());
		StateMapperBase ignoreState = new FluidStateMapper(fluidLocation);
		registerStateMapper(block, ignoreState);
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), new FluidItemMeshDefinition(fluidLocation));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(block), fluidLocation);
	}

	@Override
	public void registerStateMapper(Block block, IStateMapper mapper) {
		ModelLoader.setCustomStateMapper(block, mapper);
	}

	private static class FluidStateMapper extends StateMapperBase {
		private final ModelResourceLocation fluidLocation;

		public FluidStateMapper(ModelResourceLocation fluidLocation) {
			this.fluidLocation = fluidLocation;
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
			return fluidLocation;
		}
	}

	private static class FluidItemMeshDefinition implements ItemMeshDefinition {
		private final ModelResourceLocation fluidLocation;

		public FluidItemMeshDefinition(ModelResourceLocation fluidLocation) {
			this.fluidLocation = fluidLocation;
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return fluidLocation;
		}
	}
	
    public static enum DefaultTextureGetter implements Function<ResourceLocation, TextureAtlasSprite>{
        INSTANCE;

        public TextureAtlasSprite apply(ResourceLocation location)
        {
            return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
        }
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
