package de.nedelosk.forestmods.client.render.modules;

import static de.nedelosk.forestmods.client.render.modules.ModularRenderer.loadTexture;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularRenderer;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachine;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MachineRenderer implements IModularRenderer {

	public ModuleStack stack;
	public ModelRenderer Machine_Front;
	public ResourceLocation textureMachine;
	public ModelBase model = new ModelBase() {
	};

	public MachineRenderer(ModuleStack<IModuleMachine, IModuleSaver> stack) {
		this.stack = stack;
		Machine_Front = new ModelRenderer(model, 0, 0);
		Machine_Front.setRotationPoint(-6.5F, 11.5F, -8.0F);
		Machine_Front.addBox(0.0F, 0.0F, 0.0F, 13, 10, 1, 0.0F);
		textureMachine = loadTexture("iron", stack.getMaterial().getName().toLowerCase(Locale.ENGLISH),
				"producer/" + stack.getModule().getFilePath(stack).toLowerCase(Locale.ENGLISH) + "/", ".png");
	}

	@Override
	public void renderMachineItemStack(IModular machine, ItemStack itemStack) {
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.5F, 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotated(90, 0F, 1F, 0F);
		GL11.glPushMatrix();
		manager.bindTexture(textureMachine);
		Machine_Front.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	@Override
	public void renderMachine(IModularTileEntity entity, double x, double y, double z) {
		IModular machine = entity.getModular();
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glPushMatrix();
		if (entity.getFacing() == 2) {
		} else if (entity.getFacing() == 3) {
			GL11.glRotated(180, 0F, 1F, 0F);
		} else if (entity.getFacing() == 4) {
			GL11.glRotated(270, 0F, 1F, 0F);
		} else if (entity.getFacing() == 5) {
			GL11.glRotated(90, 0F, 1F, 0F);
		}
		manager.bindTexture(textureMachine);
		Machine_Front.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
