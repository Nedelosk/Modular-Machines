package modularmachines.client.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.lwjgl.input.Keyboard;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleDefinition;
import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.components.IModelComponent;
import modularmachines.client.model.BuiltInModelLoader;
import modularmachines.client.model.ModelManager;
import modularmachines.client.model.module.ModuleModelLoader;
import modularmachines.client.renderer.ModuleContainerTESR;
import modularmachines.common.blocks.tile.TileEntityModuleContainer;
import modularmachines.common.core.CommonProxy;
import modularmachines.common.core.Constants;
import modularmachines.common.modules.components.ModelComponent;
import modularmachines.common.utils.Translator;

public class ClientProxy extends CommonProxy {
	
	public static KeyBinding MODULE_INFO = new KeyBinding("key.mm.showModuleInfo", KeyConflictContext.GUI, Keyboard.KEY_M, Constants.NAME);
	
	@Override
	public void preInit() {
		super.preInit();
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		ModelLoaderRegistry.registerLoader(new BuiltInModelLoader(ModelManager.getInstance().getBuiltInModels()));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityModuleContainer.class, new ModuleContainerTESR());
	}
	
	@Override
	public void init() {
		ModelManager.getInstance().registerItemAndBlockColors();
		ClientRegistry.registerKeyBinding(MODULE_INFO);
	}
	
	@Override
	public void registerModuleModels() {
		for (IModuleData data : GameRegistry.findRegistry(IModuleData.class)) {
			IModuleDefinition definition = data.getDefinition();
			definition.registerModels();
		}
		ModuleModelLoader.INSTANCE.registerModels();
	}
	
	@Override
	public void playButtonClick() {
		Minecraft minecraft = Minecraft.getMinecraft();
		SoundHandler soundHandler = minecraft.getSoundHandler();
		soundHandler.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}
	
	@Override
	public List<String> addModuleInfo(ItemStack itemStack) {
		List<String> tooltip = new ArrayList<>();
		IModuleType type = ModuleManager.registry.getTypeFromItem(itemStack);
		if (type != null) {
			if (Keyboard.isKeyDown(MODULE_INFO.getKeyCode())) {
				IModuleData data = type.getData();
				List<String> moduleTooltip = new ArrayList<>();
				tooltip.add(TextFormatting.DARK_GREEN + "" + TextFormatting.ITALIC + Translator.translateToLocal("mm.tooltip.moduleInfo"));
				data.addTooltip(moduleTooltip, itemStack, type);
				tooltip.addAll(moduleTooltip);
				
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
	
	@Override
	public void addComponents(IModule module) {
		module.addComponent(new ModelComponent());
	}
	
	@Override
	public void markForModelUpdate(IModule module) {
		IModelComponent component = module.getComponent(IModelComponent.class);
		if (component != null) {
			component.setModelNeedReload(true);
		}
	}
}
