package de.nedelosk.forestmods.client.render.modules;

import static de.nedelosk.forestmods.client.render.modules.ModularRenderer.loadTexture;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularRenderer;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBattery;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBatterySaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class BatteryRenderer implements IModularRenderer {

	public ModelBase model = new ModelBase() {
	};
	public ModelRenderer Battery_Base;
	public ModelRenderer Battery_Top;
	public ModelRenderer Battery_Down;
	public ModelRenderer Battery_Right;
	public ModelRenderer Battery_Left;
	public final ModuleStack<IModuleBattery, IModuleBatterySaver> stack;
	public ResourceLocation baseTexture;
	public ResourceLocation topTexture;
	public ResourceLocation downTexture;
	public ResourceLocation rightTexture;
	public ResourceLocation leftTexture;

	public BatteryRenderer(ModuleStack<IModuleBattery, IModuleBatterySaver> stack, IModular modular) {
		this.stack = stack;
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
		if (modular.isAssembled()) {
			if (modular.getUtilsManager().getEnergyHandler() != null) {
				energy = (modular.getUtilsManager().getEnergyHandler().getEnergyStored(ForgeDirection.UNKNOWN)
						/ (modular.getUtilsManager().getEnergyHandler().getMaxEnergyStored(ForgeDirection.UNKNOWN) / 8));
			} else {
				energy = 0;
			}
		} else {
			energy = 0;
		}
		baseTexture = loadTexture("iron", stack.getMaterial().getName().toLowerCase(Locale.ENGLISH), "battery/", "_base_" + energy + ".png");
		topTexture = loadTexture("iron", stack.getMaterial().getName().toLowerCase(Locale.ENGLISH), "battery/", "_top.png");
		downTexture = loadTexture("iron", stack.getMaterial().getName().toLowerCase(Locale.ENGLISH), "battery/", "_down.png");
		leftTexture = loadTexture("iron", stack.getMaterial().getName().toLowerCase(Locale.ENGLISH), "battery/", "_left.png");
		rightTexture = loadTexture("iron", stack.getMaterial().getName().toLowerCase(Locale.ENGLISH), "battery/", "_right.png");
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