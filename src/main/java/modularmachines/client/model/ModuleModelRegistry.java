package modularmachines.client.model;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleModelRegistry;
import modularmachines.api.modules.model.IModuleKeyGenerator;
import modularmachines.api.modules.model.IModuleModelBakery;
import modularmachines.common.modules.ModuleKeyGenerators;

@SideOnly(Side.CLIENT)
public enum ModuleModelRegistry implements IModuleModelRegistry {
	INSTANCE;
	
	private final Map<IModuleData, ModelPair> moduleModels = new HashMap<>();
	
	@Override
	public void registerModel(IModuleData moduleData, IModuleModelBakery bakery) {
		registerModel(moduleData, bakery, ModuleKeyGenerators.DEFAULT_GENERATOR);
	}
	
	@Override
	public void registerModel(IModuleData moduleData, IModuleModelBakery bakery, IModuleKeyGenerator generator) {
		moduleModels.put(moduleData, new ModelPair(bakery, generator));
	}
	
	@Override
	public IModuleKeyGenerator getGenerator(IModuleData moduleData) {
		ModelPair pair = moduleModels.get(moduleData);
		if (pair != null) {
			return pair.generator;
		}
		return ModuleKeyGenerators.DEFAULT_GENERATOR;
	}
	
	@Nullable
	@Override
	public IModuleModelBakery getModel(IModuleData moduleData) {
		return moduleModels.getOrDefault(moduleData, ModelPair.EMPTY).bakery;
	}
	
	private static class ModelPair {
		public static final ModelPair EMPTY = new ModelPair(null, ModuleKeyGenerators.DEFAULT_GENERATOR);
		
		@Nullable
		private final IModuleModelBakery bakery;
		private final IModuleKeyGenerator generator;
		
		private ModelPair(@Nullable IModuleModelBakery bakery, IModuleKeyGenerator generator) {
			this.bakery = bakery;
			this.generator = generator;
		}
	}
	
}
