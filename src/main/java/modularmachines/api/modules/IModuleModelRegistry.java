package modularmachines.api.modules;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.model.IModuleKeyGenerator;
import modularmachines.api.modules.model.IModuleModelBakery;

@SideOnly(Side.CLIENT)
public interface IModuleModelRegistry {
	void registerModel(IModuleData moduleData, IModuleModelBakery bakery);
	
	void registerModel(IModuleData moduleData, IModuleModelBakery bakery, IModuleKeyGenerator generator);
	
	IModuleKeyGenerator getGenerator(IModuleData moduleData);
	
	@Nullable
	IModuleModelBakery getModel(IModuleData moduleData);
}
