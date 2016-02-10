package de.nedelosk.forestmods.client.render.modules;

import static de.nedelosk.forestmods.client.render.modules.ModularRenderer.loadTexture;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularRenderer;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class CasingRenderer implements IModularRenderer {

	public final ModuleStack stack;
	public ModelBase model = new ModelBase() {
	};
	public ModelRenderer Base_Casing_Left;
	public ModelRenderer Base_Casing_Right;
	public ModelRenderer Front_Casing;
	public ModelRenderer Back_Casing;
	public ModelRenderer Top_Right_Casing;
	public ModelRenderer Down_Right_Casing;
	public ResourceLocation baseTextureLeft;
	public ResourceLocation baseTextureRight;
	public ResourceLocation frontTexture;
	public ResourceLocation backTexture;
	public ResourceLocation topTexture;
	public ResourceLocation downTexture;

	public CasingRenderer(ModuleStack stack) {
		this.stack = stack;
		this.Top_Right_Casing = new ModelRenderer(model, 0, 0);
		this.Top_Right_Casing.setRotationPoint(0.0F, 9.0F, -5.0F);
		this.Top_Right_Casing.addBox(0.0F, 0.0F, 0.0F, 7, 2, 10, 0.0F);
		this.Front_Casing = new ModelRenderer(model, 0, 0);
		this.Front_Casing.setRotationPoint(-7.0F, 9.0F, -7.0F);
		this.Front_Casing.addBox(0.0F, 0.0F, 0.0F, 14, 15, 2, 0.0F);
		this.Back_Casing = new ModelRenderer(model, 0, 0);
		this.Back_Casing.setRotationPoint(-7.0F, 9.0F, 5.0F);
		this.Back_Casing.addBox(0.0F, 0.0F, 0.0F, 14, 15, 2, 0.0F);
		this.Base_Casing_Left = new ModelRenderer(model, 0, 0);
		this.Base_Casing_Left.setRotationPoint(-7.0F, 9.0F, -5.0F);
		this.Base_Casing_Left.addBox(0.0F, 0.0F, 0.0F, 7, 15, 10, 0.0F);
		this.Base_Casing_Right = new ModelRenderer(model, 0, 0);
		this.Base_Casing_Right.setRotationPoint(0.0F, 9.0F, -5.0F);
		this.Base_Casing_Right.addBox(0.0F, 0.0F, 0.0F, 7, 15, 10, 0.0F);
		this.Down_Right_Casing = new ModelRenderer(model, 0, 0);
		this.Down_Right_Casing.setRotationPoint(0.0F, 22.0F, -5.0F);
		this.Down_Right_Casing.addBox(0.0F, 0.0F, 0.0F, 7, 2, 10, 0.0F);
		baseTextureLeft = loadTexture("iron", stack.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/", "_base_left.png");
		baseTextureRight = loadTexture("iron", stack.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/", "_base_right.png");
		frontTexture = loadTexture("iron", stack.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/", "_front.png");
		backTexture = loadTexture("iron", stack.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/", "_back.png");
		topTexture = loadTexture("iron", stack.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/", "_top.png");
		downTexture = loadTexture("iron", stack.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/", "_down.png");
	}

	@Override
	public void renderMachineItemStack(IModular machine, ItemStack stack) {
		Tessellator t = Tessellator.instance;
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.5F, 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotated(90, 0F, 1F, 0F);
		GL11.glPushMatrix();
		manager.bindTexture(baseTextureLeft);
		Base_Casing_Left.render(0.0625F);
		if (ModularUtils.getEngine(machine) == null) {
			manager.bindTexture(baseTextureRight);
			Base_Casing_Right.render(0.0625F);
		} else {
			manager.bindTexture(topTexture);
			Top_Right_Casing.render(0.0625F);
			manager.bindTexture(downTexture);
			Down_Right_Casing.render(0.0625F);
		}
		manager.bindTexture(frontTexture);
		Front_Casing.render(0.0625F);
		manager.bindTexture(backTexture);
		Back_Casing.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	@Override
	public void renderMachine(IModularTileEntity entity, double x, double y, double z) {
		Tessellator t = Tessellator.instance;
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
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
		manager.bindTexture(baseTextureLeft);
		Base_Casing_Left.render(0.0625F);
		if (ModularUtils.getEngine(entity.getModular()) == null) {
			manager.bindTexture(baseTextureRight);
			Base_Casing_Right.render(0.0625F);
		} else {
			manager.bindTexture(topTexture);
			Top_Right_Casing.render(0.0625F);
			manager.bindTexture(downTexture);
			Down_Right_Casing.render(0.0625F);
		}
		manager.bindTexture(frontTexture);
		Front_Casing.render(0.0625F);
		manager.bindTexture(backTexture);
		Back_Casing.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
