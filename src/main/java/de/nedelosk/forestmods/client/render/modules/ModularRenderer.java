package de.nedelosk.forestmods.client.render.modules;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularRenderer;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.basic.IModuleWithRenderer;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ModularRenderer implements IModularRenderer {

	public ModularRenderer() {
	}

	@Override
	public void renderMachineItemStack(IModular machine, ItemStack itemStack) {
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
		for ( ModuleStack moduleStack : (List<ModuleStack>) machine.getModuleManager().getModuleStacks() ) {
			if (moduleStack != null && moduleStack.getModule() instanceof IModuleWithRenderer) {
				IModularRenderer renderer = ((IModuleWithRenderer) moduleStack.getModule()).getItemRenderer(machine, moduleStack, itemStack);
				if (renderer != null) {
					renderer.renderMachineItemStack(machine, itemStack);
				}
			}
		}
	}

	@Override
	public void renderMachine(IModularTileEntity entity, double x, double y, double z) {
		IModular machine = entity.getModular();
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
		for ( ModuleStack stack : (List<ModuleStack>) machine.getModuleManager().getModuleStacks() ) {
			if (stack != null && stack.getModule() instanceof IModuleWithRenderer) {
				IModularRenderer renderer = ((IModuleWithRenderer) stack.getModule()).getMachineRenderer(machine, stack, entity);
				if (renderer != null) {
					renderer.renderMachine(entity, x, y, z);
				}
			}
		}
	}

	public static ResourceLocation loadTexture(String defaultName, String name, String befor, String after) {
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
