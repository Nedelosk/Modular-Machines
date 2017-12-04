package modularmachines.common.compat;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;

import modularmachines.common.compat.theoneprobe.TheOneProbePlugin;
import modularmachines.common.compat.thermalexpansion.TEPlugin;
import modularmachines.common.utils.Mod;

public class CompatManager {
	
	private static final List<ICompatPlugin> plugins = new ArrayList<>();
	
	public static void preInit() {
		register(TEPlugin.class, Mod.THERMAL_EXPANSION.modID());
		register(TheOneProbePlugin.class, "theoneprobe");
		
		for (ICompatPlugin plugin : plugins) {
			plugin.preInit();
		}
	}
	
	public static void init() {
		for (ICompatPlugin plugin : plugins) {
			plugin.init();
		}
	}
	
	public static void postInit() {
		for (ICompatPlugin plugin : plugins) {
			plugin.postInit();
		}
	}
	
	public static void register(Class<? extends ICompatPlugin> pluginClass, Object... args) {
		for (Object obj : args) {
			if (obj instanceof String) {
				String modID = (String) obj;
				if (modID.startsWith("@")) {
					if (modID.equals("@client")) {
						if (FMLCommonHandler.instance().getSide() != Side.CLIENT) {
							return;
						}
					}
				} else if (modID.startsWith("!")) {
					if (Loader.isModLoaded(modID.replaceAll("!", ""))) {
						return;
					}
				} else {
					if (!Loader.isModLoaded(modID)) {
						return;
					}
				}
			}
		}
		try {
			plugins.add(pluginClass.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
