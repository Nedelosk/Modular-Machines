package de.nedelosk.forestmods.common.config;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.common.config.Config.ConfigGroup;
import de.nedelosk.forestmods.common.core.Constants;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

@SuppressWarnings({ "rawtypes" })
@SideOnly(Side.CLIENT)
public class GuiConfigFactory extends GuiConfig {

	public GuiConfigFactory(GuiScreen parentScreen) {
		super(parentScreen, getConfigElements(parentScreen), Constants.MODID, false, false, StatCollector.translateToLocal("mm.config.title"));
	}

	private static List<IConfigElement> getConfigElements(GuiScreen parent) {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		String prefix = "mm.config.";
		for ( ConfigGroup group : Config.groups ) {
			list.add(new ConfigElement<ConfigCategory>(
					Config.config.getCategory(group.lc()).setLanguageKey(prefix + group.lang).setRequiresMcRestart(group.reloadMC)));
		}
		return list;
	}
}
