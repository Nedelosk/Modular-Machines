package de.nedelosk.forestmods.client.render.modules;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ModularRenderer implements ISimpleRenderer {

	public ModularRenderer() {
	}

	@Override
	public void render(IRenderState state) {
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
		IModular modular = state.getModular();
		for(ModuleStack stack : modular.getModuleStacks()) {
			if (stack != null) {
				ISimpleRenderer renderer = stack.getModule().getRenderer(stack, state);
				if (renderer != null) {
					renderer.render(state);
				}
			}
		}
	}

	public static ResourceLocation getTextureFromManager(String defaultName, String name, String befor, String after) {
		try {
			SimpleReloadableResourceManager manager = (SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
			if (manager.getResource(new ResourceLocation("forestmods", "textures/models/modules/" + befor + name + after)) != null) {
				return new ResourceLocation("forestmods", "textures/models/modules/" + befor + name + after);
			}
		} catch (Exception e) {
			return new ResourceLocation("forestmods", "textures/models/modules/" + befor + defaultName + after);
		}
		return null;
	}
}
