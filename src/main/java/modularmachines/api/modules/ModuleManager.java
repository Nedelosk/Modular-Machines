package modularmachines.api.modules;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.components.IModuleComponentFactory;

public class ModuleManager {
	
	public static IModuleRegistry registry;
	
	public static IModuleFactory factory;
	
	public static IModuleComponentFactory componentFactory;
	
	@SideOnly(Side.CLIENT)
	public static IModuleModelRegistry modelRegistry;
}
