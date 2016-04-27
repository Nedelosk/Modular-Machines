package de.nedelosk.forestmods.client.render.modules;

import static de.nedelosk.forestmods.client.render.modules.ModularRenderer.getTextureFromManager;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modular.ModularHelper;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class CasingRenderer extends AdvancedRenderer {

	public final IModuleContainer container;
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

	public CasingRenderer(IModuleContainer container) {
		this.container = container;
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
		baseTextureLeft = getTextureFromManager("iron", container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/", "_base_left.png");
		baseTextureRight = getTextureFromManager("iron", container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/", "_base_right.png");
		frontTexture = getTextureFromManager("iron", container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/", "_front.png");
		backTexture = getTextureFromManager("iron", container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/", "_back.png");
		topTexture = getTextureFromManager("iron", container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/", "_top.png");
		downTexture = getTextureFromManager("iron", container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "casing/", "_down.png");
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
		manager.bindTexture(baseTextureLeft);
		Base_Casing_Left.render(0.0625F);
		if (ModularHelper.getEngine(state.getModular()) == null) {
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
	public void render(IRenderState state) {
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
		manager.bindTexture(baseTextureLeft);
		Base_Casing_Left.render(0.0625F);
		if (ModularHelper.getEngine(state.getModular()) == null) {
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