package de.nedelosk.forestmods.client.render.modules;

import static de.nedelosk.forestmods.client.render.modules.ModularRenderer.getTextureFromManager;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class EngineRenderer extends AdvancedRenderer {

	public ModelBase model = new ModelBase() {
	};
	public ModelRenderer Base_Engine;
	public ModelRenderer Disc_Engine;
	public ModelRenderer Window_Engine_Top;
	public ModelRenderer Window_Engine_Down;
	public ModelRenderer Window_Engine_Left;
	public ModelRenderer Window_Engine_Right;
	public ModelRenderer Window_Engine_Glass;
	public final ModuleStack<IModuleEngine> stack;
	public ResourceLocation baseTexture;
	public ResourceLocation discTexture;
	public ResourceLocation windowTopTexture;
	public ResourceLocation windowDownTexture;
	public ResourceLocation windowLeftTexture;
	public ResourceLocation windowRightTexture;
	public ResourceLocation windowGlassTexture;

	public EngineRenderer(ModuleStack<IModuleEngine> stackEngine, ModuleStack stackCasing) {
		this.stack = stackEngine;
		Base_Engine = new ModelRenderer(model, 0, 0);
		Base_Engine.setRotationPoint(2.0F, 15.0F, -5.0F);
		Base_Engine.addBox(0.0F, 0.0F, 0.0F, 3, 3, 10, 0.0F);
		Disc_Engine = new ModelRenderer(model, 0, 0);
		Disc_Engine.setRotationPoint(1.0F, 14.0F, -5.0F);
		Disc_Engine.addBox(0.0F, 0.0F, 0.0F, 5, 5, 3, 0.0F);
		Window_Engine_Down = new ModelRenderer(model, 0, 0);
		Window_Engine_Down.setRotationPoint(7.0F, 21.5F, -6.0F);
		Window_Engine_Down.addBox(0.0F, 0.0F, 0.0F, 1, 1, 12, 0.0F);
		Window_Engine_Left = new ModelRenderer(model, 0, 0);
		Window_Engine_Left.setRotationPoint(7.0F, 11.5F, -6.0F);
		Window_Engine_Left.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
		Window_Engine_Right = new ModelRenderer(model, 0, 0);
		Window_Engine_Right.setRotationPoint(7.0F, 11.5F, 5.0F);
		Window_Engine_Right.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
		Window_Engine_Top = new ModelRenderer(model, 0, 0);
		Window_Engine_Top.setRotationPoint(7.0F, 10.5F, -6.0F);
		Window_Engine_Top.addBox(0.0F, 0.0F, 0.0F, 1, 1, 12, 0.0F);
		Window_Engine_Glass = new ModelRenderer(model, 0, 0);
		Window_Engine_Glass.setRotationPoint(7.0F, 11.5F, -5.0F);
		Window_Engine_Glass.addBox(0.0F, 0.0F, 0.0F, 1, 10, 10, 0.0F);
		baseTexture = getTextureFromManager("iron", stackEngine.getMaterial().getName().toLowerCase(Locale.ENGLISH), "engine/", "_base.png");
		discTexture = getTextureFromManager("iron", stackEngine.getMaterial().getName().toLowerCase(Locale.ENGLISH), "engine/", ".png");
		windowLeftTexture = getTextureFromManager("iron", stackCasing.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/window/",
				"_window_left.png");
		windowRightTexture = getTextureFromManager("iron", stackCasing.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/window/",
				"_window_right.png");
		windowTopTexture = getTextureFromManager("iron", stackCasing.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/window/", "_window_down.png");
		windowDownTexture = getTextureFromManager("iron", stackCasing.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/window/", "_window_top.png");
		windowGlassTexture = getTextureFromManager("iron", stackCasing.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/window/",
				"_window_glass.png");
	}

	@Override
	protected void renderItem(IRenderState state) {
		Tessellator t = Tessellator.instance;
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.5F, 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotated(90, 0F, 1F, 0F);
		GL11.glPushMatrix();
		manager.bindTexture(baseTexture);
		Base_Engine.render(0.0625F);
		manager.bindTexture(discTexture);
		Disc_Engine.render(0.0625F);
		manager.bindTexture(windowDownTexture);
		Window_Engine_Down.render(0.0625F);
		manager.bindTexture(windowLeftTexture);
		Window_Engine_Left.render(0.0625F);
		manager.bindTexture(windowTopTexture);
		Window_Engine_Top.render(0.0625F);
		manager.bindTexture(windowRightTexture);
		Window_Engine_Right.render(0.0625F);
		manager.bindTexture(windowGlassTexture);
		Window_Engine_Glass.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	@Override
	protected void renderBlock(IRenderState state) {
		IModularTileEntity entity = state.getModular().getTile();
		Tessellator t = Tessellator.instance;
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
		GL11.glPushMatrix();
		GL11.glTranslated((float) state.getX() + 0.5F, (float) state.getY() + 1.5F, (float) state.getZ() + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glPushMatrix();
		if (entity.getFacing() == ForgeDirection.NORTH) {
		} else if (entity.getFacing() == ForgeDirection.SOUTH) {
			GL11.glRotated(180, 0F, 1F, 0F);
		} else if (entity.getFacing() == ForgeDirection.WEST) {
			GL11.glRotated(270, 0F, 1F, 0F);
		} else if (entity.getFacing() == ForgeDirection.EAST) {
			GL11.glRotated(90, 0F, 1F, 0F);
		}
		float step;
		float progress = stack.getModule().getProgress();
		if (progress > 0.5) {
			step = 5.99F - (progress - 0.5F) * 2F * 5.99F;
		} else {
			step = progress * 2F * 5.99F;
		}
		float tfactor = step / 16;
		ForgeDirection direction = null;
		if (entity.getFacing() == ForgeDirection.NORTH) {
			direction = ForgeDirection.values()[entity.getFacing().ordinal() + 1];
		} else if (entity.getFacing() == ForgeDirection.SOUTH) {
			direction = ForgeDirection.values()[entity.getFacing().ordinal()];
		} else if (entity.getFacing() == ForgeDirection.WEST) {
			direction = ForgeDirection.values()[entity.getFacing().ordinal() - 1];
		} else if (entity.getFacing() == ForgeDirection.EAST) {
			direction = ForgeDirection.values()[entity.getFacing().ordinal() - 2];
		} else {
			direction = ForgeDirection.values()[entity.getFacing().ordinal()];
		}
		manager.bindTexture(discTexture);
		GL11.glTranslatef(direction.offsetX * tfactor, direction.offsetY * tfactor, direction.offsetZ * tfactor);
		Disc_Engine.render(0.0625F);
		GL11.glTranslatef(-direction.offsetX * tfactor, -direction.offsetY * tfactor, -direction.offsetZ * tfactor);
		manager.bindTexture(baseTexture);
		Base_Engine.render(0.0625F);
		manager.bindTexture(windowDownTexture);
		Window_Engine_Down.render(0.0625F);
		manager.bindTexture(windowLeftTexture);
		Window_Engine_Left.render(0.0625F);
		manager.bindTexture(windowTopTexture);
		Window_Engine_Top.render(0.0625F);
		manager.bindTexture(windowRightTexture);
		Window_Engine_Right.render(0.0625F);
		manager.bindTexture(windowGlassTexture);
		Window_Engine_Glass.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}