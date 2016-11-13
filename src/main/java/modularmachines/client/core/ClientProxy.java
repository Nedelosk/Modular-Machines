package modularmachines.client.core;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import modularmachines.api.gui.GuiManager;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.client.gui.GuiHelper;
import modularmachines.client.model.ModelManager;
import modularmachines.common.core.CommonProxy;
import modularmachines.common.core.Constants;
import modularmachines.common.utils.Translator;

public class ClientProxy extends CommonProxy {

	public static KeyBinding MODULE_INFO = new KeyBinding("key.mm.showModuleInfo", KeyConflictContext.GUI, Keyboard.KEY_M, Constants.NAME);

	@Override
	public void preInit() {
		GuiManager.helper = new GuiHelper();
	}

	@Override
	public void init() {
		ClientRegistry.registerKeyBinding(MODULE_INFO);
	}

	@Override
	public List<String> addModuleInfo(ItemStack itemStack) {
		List<String> tooltip = new ArrayList<>();
		IModuleItemContainer container = ModuleManager.getContainerFromItem(itemStack);
		if (container != null) {
			if (Keyboard.isKeyDown(MODULE_INFO.getKeyCode())) {
				List<String> moduleTooltip = new ArrayList<>();
				tooltip.add(TextFormatting.DARK_GREEN + "" + TextFormatting.ITALIC + Translator.translateToLocal("mm.tooltip.moduleInfo"));
				container.addTooltip(moduleTooltip, itemStack);
				for (String s : moduleTooltip) {
					tooltip.add(TextFormatting.DARK_GREEN + s);
				}
			} else {
				tooltip.add(TextFormatting.DARK_GREEN + Translator.translateToLocalFormatted("mm.tooltip.hold.moduleInfo", Keyboard.getKeyName(MODULE_INFO.getKeyCode())));
			}
		}
		return tooltip;
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

	public static class BlockModeStateMapper extends StateMapperBase {

		private final ModelResourceLocation location;

		public BlockModeStateMapper(ModelResourceLocation location) {
			this.location = location;
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
			return location;
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

	@Override
	public void registerBlock(Block block) {
		ModelManager.getInstance().registerBlockClient(block);
	}

	@Override
	public void registerItem(Item item) {
		ModelManager.getInstance().registerItemClient(item);
	}
}
