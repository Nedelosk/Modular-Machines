package de.nedelosk.modularmachines.common.utils;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

public final class RenderUtil {

	public static void renderTooltip(int x, int y, List<String> tooltipData) {
		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
		GuiUtils.drawHoveringText(tooltipData, x, y, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), -1, Minecraft.getMinecraft().fontRendererObj);
	}

	public static TextureManager engine() {
		return Minecraft.getMinecraft().renderEngine;
	}

	public static void bindTexture(ResourceLocation tex) {
		engine().bindTexture(tex);
	}

	public static void bindBlockTexture() {
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	}

	public static int glDrawScaledString(FontRenderer fontRenderer, String text, int x, int y, float size, int color) {
		GL11.glPushMatrix();
		GL11.glScaled(size, size, size);
		x = (int) ((x * 1F) / size);
		y = (int) ((y * 1F) / size);
		fontRenderer.drawString(text, x, y, color);
		GL11.glPopMatrix();
		return (int) (10 * size);
	}

	public static ResourceLocation getResourceLocation(String modName, String fileName, String... subPackages) {
		String filePath = "textures/";
		for(String subPackage : subPackages) {
			filePath += subPackage + "/";
		}
		filePath += fileName + ".png";
		return new ResourceLocation(modName, filePath);
	}
}
