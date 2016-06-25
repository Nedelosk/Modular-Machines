package de.nedelosk.modularmachines.client.render.modules;

import static de.nedelosk.modularmachines.client.render.modules.ModularRenderer.getTextureFromManager;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularTileEntity;
import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BatteryRenderer extends AdvancedRenderer {

	public ModelBase model = new ModelBase() {
	};
	public ModelRenderer Battery_Base;
	public ModelRenderer Battery_Top;
	public ModelRenderer Battery_Down;
	public ModelRenderer Battery_Right;
	public ModelRenderer Battery_Left;
	public final IModuleContainer container;
	public ResourceLocation baseTexture;
	public ResourceLocation topTexture;
	public ResourceLocation downTexture;
	public ResourceLocation rightTexture;
	public ResourceLocation leftTexture;

	public BatteryRenderer(IModuleContainer container, IModular modular) {
		this.container = container;
		this.Battery_Left = new ModelRenderer(model, 0, 0);
		this.Battery_Left.setRotationPoint(-8.0F, 12.5F, -6.0F);
		this.Battery_Left.addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, 0.0F);
		this.Battery_Right = new ModelRenderer(model, 0, 0);
		this.Battery_Right.setRotationPoint(-8.0F, 12.5F, 4.0F);
		this.Battery_Right.addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, 0.0F);
		this.Battery_Down = new ModelRenderer(model, 0, 0);
		this.Battery_Down.setRotationPoint(-8.0F, 20.5F, -6.0F);
		this.Battery_Down.addBox(0.0F, 0.0F, 0.0F, 1, 2, 12, 0.0F);
		this.Battery_Top = new ModelRenderer(model, 0, 0);
		this.Battery_Top.setRotationPoint(-8.0F, 10.5F, -6.0F);
		this.Battery_Top.addBox(0.0F, 0.0F, 0.0F, 1, 2, 12, 0.0F);
		this.Battery_Base = new ModelRenderer(model, 0, 0);
		this.Battery_Base.setRotationPoint(-7.5F, 12.5F, -4.0F);
		this.Battery_Base.addBox(0.0F, 0.0F, 0.0F, 1, 8, 8, 0.0F);
		int energy;
		if (modular.getEnergyHandler() != null) {
			energy = (modular.getEnergyHandler().getEnergyStored(ForgeDirection.UNKNOWN)
					/ (modular.getEnergyHandler().getMaxEnergyStored(ForgeDirection.UNKNOWN) / 8));
		} else {
			energy = 0;
		}
		baseTexture = getTextureFromManager("iron", container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "battery/", "_base_" + energy + ".png");
		topTexture = getTextureFromManager("iron", container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "battery/", "_top.png");
		downTexture = getTextureFromManager("iron", container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "battery/", "_down.png");
		leftTexture = getTextureFromManager("iron", container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "battery/", "_left.png");
		rightTexture = getTextureFromManager("iron", container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "battery/", "_right.png");
	}

	@Override
	public void renderItem(IRenderState state) {
		Tessellator t = Tessellator.instance;
		TextureManager manager = Minecraft.getMinecraft().getTextureManager();
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.5F, 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotated(90, 0F, 1F, 0F);
		GL11.glPushMatrix();
		manager.bindTexture(baseTexture);
		Battery_Base.render(0.0625F);
		manager.bindTexture(topTexture);
		Battery_Top.render(0.0625F);
		manager.bindTexture(downTexture);
		Battery_Down.render(0.0625F);
		manager.bindTexture(leftTexture);
		Battery_Left.render(0.0625F);
		manager.bindTexture(rightTexture);
		Battery_Right.render(0.0625F);
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
		manager.bindTexture(baseTexture);
		Battery_Base.render(0.0625F);
		manager.bindTexture(topTexture);
		Battery_Top.render(0.0625F);
		manager.bindTexture(downTexture);
		Battery_Down.render(0.0625F);
		manager.bindTexture(leftTexture);
		Battery_Left.render(0.0625F);
		manager.bindTexture(rightTexture);
		Battery_Right.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}