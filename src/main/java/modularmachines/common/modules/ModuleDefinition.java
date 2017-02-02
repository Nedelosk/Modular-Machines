package modularmachines.common.modules;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.IModuleFactory;
import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.common.modules.storages.modules.ModuleCasing;
import modularmachines.common.modules.storages.modules.ModuleDataCasing;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum ModuleDefinition implements IModuleFactory {
	CASING_WOOD(new ModuleDataCasing(EnumModuleSizes.LARGEST), "casing.wood", 0, EnumModuleSizes.LARGE){

		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleCasing(storage);
		}
		
	};

	private final ModuleData data;
	
	private ModuleDefinition(ModuleData data, String registry, int complexity, EnumModuleSizes size) {
		this.data = data;
		data.setRegistryName(registry);
		data.setComplexity(complexity);
		data.setSize(size);
		data.setUnlocalizedName(registry);
		data.setFactory(this);
		GameRegistry.register(data);
	}
	
	private ModuleDefinition() {
		this.data = createData();
	}
	
	protected ModuleData createData(){
		return new ModuleData();
	}
	
	public ModuleData data(){
		return data;
	}

}
