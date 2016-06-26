package de.nedelosk.modularmachines.client.render.modules;

import static de.nedelosk.modularmachines.client.render.modules.ModularRenderer.getTextureFromManager;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class MachineRenderer extends AdvancedRenderer {

	public IModuleContainer container;
	public ModelRenderer Machine_Front;
	public ResourceLocation textureMachine;
	public ModelBase model = new ModelBase() {
	};

	public MachineRenderer(IModuleContainer container) {
		this.container = container;
		Machine_Front = new ModelRenderer(model, 0, 0);
		Machine_Front.setRotationPoint(-6.5F, 11.5F, -8.0F);
		Machine_Front.addBox(0.0F, 0.0F, 0.0F, 13, 10, 1, 0.0F);
		textureMachine = getTextureFromManager("iron", container.getMaterial().getName().toLowerCase(Locale.ENGLISH),
				"machine/" + container.getRegistryName().getResourcePath().replace(".", "/") + "/" + container.getRegistryName().getResourceDomain() + "/", ".png");
	}

	@Override
	protected void renderItem(IRenderState state) {
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
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
	protected void renderBlock(IRenderState state) {
		IModularHandler entity = state.getModular().getHandler();
		IModular machine = entity.getModular();
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
		GL11.glPushMatrix();
		GL11.glTranslated((float) state.getX() + 0.5F, (float) state.getY() + 1.5F, (float) state.getZ() + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glPushMatrix();
		if (entity.getFacing() == EnumFacing.NORTH) {
		} else if (entity.getFacing() == EnumFacing.SOUTH) {
			GL11.glRotated(180, 0F, 1F, 0F);
		} else if (entity.getFacing() == EnumFacing.WEST) {
			GL11.glRotated(270, 0F, 1F, 0F);
		} else if (entity.getFacing() == EnumFacing.EAST) {
			GL11.glRotated(90, 0F, 1F, 0F);
		}
		manager.bindTexture(textureMachine);
		Machine_Front.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
