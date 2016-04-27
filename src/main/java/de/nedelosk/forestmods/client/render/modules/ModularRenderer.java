package de.nedelosk.forestmods.client.render.modules;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.library.modules.IModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ModularRenderer implements ISimpleRenderer {

	private static List<ResourceLocation> missingTestures = new ArrayList();

	public ModularRenderer() {
	}

	@Override
	public void render(IRenderState state) {
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
		IModular modular = state.getModular();
		for(IModule module : modular.getModules()) {
			if (module != null) {
				ISimpleRenderer renderer = module.getRenderer(state);
				if (renderer != null) {
					renderer.render(state);
				}
			}
		}
	}

	public static ResourceLocation getTextureFromManager(String defaultName, String name, String befor, String after) {
		ResourceLocation location = new ResourceLocation("forestmods", "textures/models/modules/" + befor + name + after);
		try {
			SimpleReloadableResourceManager manager = (SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
			if (manager.getResource(location) != null) {
				return location;
			}
		} catch (Exception e) {
			if(!missingTestures.contains(location)){
				missingTestures.add(location);
				e.printStackTrace();
			}
			return new ResourceLocation("forestmods", "textures/models/modules/" + befor + defaultName + after);
		}
		return null;
	}
}
