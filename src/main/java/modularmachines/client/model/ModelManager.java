package modularmachines.client.model;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.model.IModelState;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.common.core.Constants;
import modularmachines.common.utils.ModelUtil;
import modularmachines.common.utils.content.IClientContentHandler;
import modularmachines.common.utils.content.IColoredBlock;
import modularmachines.common.utils.content.IColoredItem;
import modularmachines.common.utils.content.IItemModelRegister;

@SideOnly(Side.CLIENT)
public class ModelManager {

	private static final ModelManager instance = new ModelManager();
	private final List<IItemModelRegister> itemModelRegisters = new ArrayList<>();
	private final List<IClientContentHandler> clientContentHandlers = new ArrayList<>();
	private final List<IColoredBlock> blockColorList = new ArrayList<>();
	private final List<IColoredItem> itemColorList = new ArrayList<>();
	/* DEFAULT ITEM AND BLOCK MODEL STATES*/
	private IModelState defaultBlockState;
	private IModelState defaultItemState;

	public ModelManager() {
	}

	public static ModelManager getInstance() {
		return instance;
	}

	public void registerItemModel(Item item, int meta, String identifier) {
		ModelLoader.setCustomModelResourceLocation(item, meta, createModelLocation(identifier));
	}

	public void registerItemModel(Item item, int meta, String modID, String identifier) {
		ModelLoader.setCustomModelResourceLocation(item, meta, createModelLocation(modID, identifier));
	}

	public void registerItemModel(Item item, int meta) {
		ModelLoader.setCustomModelResourceLocation(item, meta, createModelLocation(item));
	}

	public void registerItemModel(Item item, ItemMeshDefinition definition) {
		ModelLoader.setCustomMeshDefinition(item, definition);
	}

	public ModelResourceLocation createModelLocation(Item item) {
		String itemName = ForgeRegistries.ITEMS.getKey(item).getResourcePath();
		return createModelLocation(itemName);
	}

	public ModelResourceLocation createModelLocation(String identifier) {
		return createModelLocation(Constants.MOD_ID, identifier);
	}

	public ModelResourceLocation createModelLocation(String modID, String identifier) {
		return new ModelResourceLocation(modID + ":" + identifier, "inventory");
	}
	
	public IModelState getDefaultBlockState() {
		return defaultBlockState;
	}
	
	public IModelState getDefaultItemState() {
		return defaultItemState;
	}
	
	public void onBakeModels(ModelBakeEvent event) {
		//load default item and block model states
		defaultItemState = ModelUtil.loadModelState(new ResourceLocation("minecraft:models/item/generated"));
		defaultBlockState = ModelUtil.loadModelState(new ResourceLocation("minecraft:models/block/block"));
	}

	public void registerBlockClient(Block block) {
		if (block instanceof IItemModelRegister) {
			itemModelRegisters.add((IItemModelRegister) block);
		}
		if (block instanceof IClientContentHandler) {
			clientContentHandlers.add((IClientContentHandler) block);
		}
		if (block instanceof IColoredBlock) {
			blockColorList.add((IColoredBlock) block);
		}
	}

	public void registerItemClient(Item item) {
		if (item instanceof IItemModelRegister) {
			itemModelRegisters.add((IItemModelRegister) item);
		}
		if (item instanceof IClientContentHandler) {
			clientContentHandlers.add((IClientContentHandler) item);
		}
		if (item instanceof IColoredItem) {
			itemColorList.add((IColoredItem) item);
		}
	}

	public void registerModels() {
		for (IItemModelRegister itemModelRegister : itemModelRegisters) {
			Item item = null;
			if (itemModelRegister instanceof Block) {
				item = Item.getItemFromBlock((Block) itemModelRegister);
			} else if (itemModelRegister instanceof Item) {
				item = (Item) itemModelRegister;
			}
			if (item != null) {
				itemModelRegister.registerItemModels(item, this);
			}
		}
		for (IClientContentHandler clientContentHandler : clientContentHandlers) {
			clientContentHandler.handleClientContent();
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerItemAndBlockColors() {
		Minecraft minecraft = Minecraft.getMinecraft();
		BlockColors blockColors = minecraft.getBlockColors();
		for (IColoredBlock blockColor : blockColorList) {
			if (blockColor instanceof Block) {
				blockColors.registerBlockColorHandler(BlockColorHandler.INSTANCE, (Block) blockColor);
			}
		}
		ItemColors itemColors = minecraft.getItemColors();
		for (IColoredItem itemColor : itemColorList) {
			if (itemColor instanceof Item) {
				itemColors.registerItemColorHandler(ItemColorHandler.INSTANCE, (Item) itemColor);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private static class ItemColorHandler implements IItemColor {

		public static final ItemColorHandler INSTANCE = new ItemColorHandler();

		private ItemColorHandler() {
		}
		
		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			Item item = stack.getItem();
			if (item instanceof IColoredItem) {
				return ((IColoredItem) item).getColorFromItemstack(stack, tintIndex);
			}
			return 0xffffff;
		}
	}

	@SideOnly(Side.CLIENT)
	private static class BlockColorHandler implements IBlockColor {

		public static final BlockColorHandler INSTANCE = new BlockColorHandler();

		private BlockColorHandler() {
		}

		@Override
		public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
			Block block = state.getBlock();
			if (block instanceof IColoredBlock) {
				return ((IColoredBlock) block).colorMultiplier(state, world, pos, tintIndex);
			}
			return 0xffffff;
		}
	}
}
