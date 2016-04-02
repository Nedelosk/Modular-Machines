package de.nedelosk.techtree.client.proxy;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import de.nedelosk.techtree.client.gui.GuiTechPoint;
import de.nedelosk.techtree.common.config.TechTreeConfigs;
import de.nedelosk.techtree.common.proxy.CommonProxy;
import de.nedelosk.techtree.utils.language.LanguageManager;
import net.minecraft.client.settings.KeyBinding;

public class ClientProxy extends CommonProxy {

	public static KeyBinding techTree = new KeyBinding("forest.mm.techtree.key", Keyboard.KEY_U, "forest.mm.techtree");
	public static KeyBinding techTreeEditor = new KeyBinding("forest.mm.techtree.editor.key", Keyboard.KEY_I, "forest.mm.techtree.editor");
	public static final GuiTechPoint techPointGui = new GuiTechPoint();

	@Override
	public void init() {
		ClientRegistry.registerKeyBinding(techTree);
		ClientRegistry.registerKeyBinding(techTreeEditor);
		TechTreeConfigs.init();
		LanguageManager.init();
		LanguageManager.getInstance().writeLanguageData();
	}

	@Override
	public void postInit() {
		LanguageManager.getInstance().updateLanguage();
	}
}
