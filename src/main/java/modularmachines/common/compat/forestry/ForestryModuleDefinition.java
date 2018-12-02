package modularmachines.common.compat.forestry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleDataBuilder;
import modularmachines.api.modules.IModuleDefinition;
import modularmachines.api.modules.IModuleModelRegistry;
import modularmachines.api.modules.IModuleRegistry;
import modularmachines.api.modules.components.IModuleComponentFactory;
import modularmachines.api.modules.positions.RackPosition;
import modularmachines.client.model.module.BakeryBase;
import modularmachines.common.modules.components.SteamConsumerComponent;
import modularmachines.common.modules.components.block.BoundingBoxComponent;
import modularmachines.common.modules.data.ModuleData;
import modularmachines.common.utils.Mod;
import modularmachines.registry.ModItems;

public enum ForestryModuleDefinition implements IModuleDefinition {
	PEAT_ENGINE("engine_peat", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder builder) {
			builder.setPositions(RackPosition.values());
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryBase("module/engines/peat", "module/engines/window"));
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(data, new ItemStack(ModItems.itemEngineSteam));
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			module.addComponent(new BoundingBoxComponent(new AxisAlignedBB(3.0F / 16.0F, 9.0F / 16.0F, 15.0F / 16F, 7.0F / 16.0F, 13.0F / 16.0F, 1.0F)));
			module.addComponent(new SteamConsumerComponent());
			factory.addEnergyHandler(module, 10000);
		}
	};
	protected final IModuleData data;
	
	ForestryModuleDefinition(String registryName, int complexity) {
		IModuleDataBuilder dataBuilder = new ModuleData.Builder()
				.setRegistryName(registryName)
				.setComplexity(complexity)
				.setTranslationKey(registryName)
				.setDefinition(this);
		setProperties(dataBuilder);
		this.data = dataBuilder.build();
	}
	
	protected void setProperties(IModuleDataBuilder data) {
	}
	
	protected ItemStack getItem(String registryName) {
		return new ItemStack(Mod.FORESTRY.getItem(registryName));
	}
	
	public static void preInit() {
		if (Mod.FORESTRY.active()) {
			MinecraftForge.EVENT_BUS.register(ForestryModuleDefinition.class);
		}
	}
	
	@SubscribeEvent
	public static void onDataRegister(RegistryEvent.Register<IModuleData> event) {
		for (ForestryModuleDefinition definition : values()) {
			event.getRegistry().register(definition.data);
		}
	}
}
