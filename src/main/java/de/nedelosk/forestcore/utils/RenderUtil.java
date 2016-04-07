package de.nedelosk.forestcore.utils;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public final class RenderUtil {

	@SideOnly(Side.CLIENT)
	public static void renderText(int x, int y, int width, int height, String unlocalizedText) {
		renderText(x, y, width, height, 10, unlocalizedText);
	}

	@SideOnly(Side.CLIENT)
	public static void renderText(int x, int y, int width, int height, int paragraphSize, String unlocalizedText) {
		x += 2;
		y += 10;
		width -= 4;
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		boolean unicode = font.getUnicodeFlag();
		font.setUnicodeFlag(true);
		String text = StatCollector.translateToLocal(unlocalizedText).replaceAll("&", "\u00a7");
		String[] textEntries = text.split("<br>");
		List<List<String>> lines = new ArrayList();
		String controlCodes = "";
		for(String s : textEntries) {
			List<String> words = new ArrayList();
			String lineStr = "";
			String[] tokens = s.split(" ");
			for(String token : tokens) {
				String prev = lineStr;
				String spaced = token + " ";
				lineStr += spaced;
				controlCodes = toControlCodes(getControlCodes(prev));
				if (font.getStringWidth(lineStr) > width) {
					lines.add(words);
					lineStr = controlCodes + spaced;
					words = new ArrayList();
				}
				words.add(controlCodes + token);
			}
			if (!lineStr.isEmpty()) {
				lines.add(words);
			}
			lines.add(new ArrayList());
		}
		int i = 0;
		for(List<String> words : lines) {
			words.size();
			int xi = x;
			int spacing = 4;
			int wcount = words.size();
			int compensationSpaces = 0;
			/*
			 * boolean justify = ConfigHandler.lexiconJustifiedText && wcount >
			 * 0 && lines.size() > i && !lines.get(i + 1).isEmpty(); if(justify)
			 * { String s = Joiner.on("").join(words); int swidth =
			 * font.getStringWidth(s); int space = width - swidth; spacing =
			 * wcount == 1 ? 0 : space / (wcount - 1); compensationSpaces =
			 * wcount == 1 ? 0 : space % (wcount - 1); }
			 */
			for(String s : words) {
				int extra = 0;
				if (compensationSpaces > 0) {
					compensationSpaces--;
					extra++;
				}
				font.drawString(s, xi, y, 0);
				xi += font.getStringWidth(s) + spacing + extra;
			}
			y += words.isEmpty() ? paragraphSize : 10;
			i++;
		}
		font.setUnicodeFlag(unicode);
	}

	public static String getControlCodes(String s) {
		String controls = s.replaceAll("(?<!\u00a7)(.)", "");
		String wiped = controls.replaceAll(".*r", "r");
		return wiped;
	}

	public static String toControlCodes(String s) {
		return s.replaceAll(".", "\u00a7$0");
	}

	public static void renderTooltip(int x, int y, List<String> tooltipData) {
		int color = 0x505000ff;
		int color2 = 0xf0100010;
		renderTooltip(x, y, tooltipData, color, color2);
	}

	public static void renderTooltip(int x, int y, List<String> tooltipData, int color, int color2) {
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if (lighting) {
			net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		}
		if (tooltipData != null && !tooltipData.isEmpty()) {
			int var5 = 0;
			int var6;
			int var7;
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			for(var6 = 0; var6 < tooltipData.size(); ++var6) {
				var7 = fontRenderer.getStringWidth(tooltipData.get(var6));
				if (var7 > var5) {
					var5 = var7;
				}
			}
			var6 = x + 12;
			var7 = y - 12;
			int var9 = 8;
			if (tooltipData.size() > 1) {
				var9 += 2 + (tooltipData.size() - 1) * 10;
			}
			float z = 300F;
			drawGradientRect(var6 - 3, var7 - 4, z, var6 + var5 + 3, var7 - 3, color2, color2);
			drawGradientRect(var6 - 3, var7 + var9 + 3, z, var6 + var5 + 3, var7 + var9 + 4, color2, color2);
			drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 + var9 + 3, color2, color2);
			drawGradientRect(var6 - 4, var7 - 3, z, var6 - 3, var7 + var9 + 3, color2, color2);
			drawGradientRect(var6 + var5 + 3, var7 - 3, z, var6 + var5 + 4, var7 + var9 + 3, color2, color2);
			int var12 = (color & 0xFFFFFF) >> 1 | color & -16777216;
			drawGradientRect(var6 - 3, var7 - 3 + 1, z, var6 - 3 + 1, var7 + var9 + 3 - 1, color, var12);
			drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, z, var6 + var5 + 3, var7 + var9 + 3 - 1, color, var12);
			drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 - 3 + 1, color, color);
			drawGradientRect(var6 - 3, var7 + var9 + 2, z, var6 + var5 + 3, var7 + var9 + 3, var12, var12);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			for(int var13 = 0; var13 < tooltipData.size(); ++var13) {
				String var14 = tooltipData.get(var13);
				fontRenderer.drawStringWithShadow(var14, var6, var7, -1);
				if (var13 == 0) {
					var7 += 2;
				}
				var7 += 10;
			}
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			if (!lighting) {
				net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
			}
		}
		GL11.glColor4f(1F, 1F, 1F, 1F);
	}

	public static void renderTooltip(int x, int y, List<String> tooltipData, int color) {
		if (tooltipData != null && !tooltipData.isEmpty()) {
			boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
			if (lighting) {
				net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
			}
			int var5 = 0;
			int var6;
			int var7;
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			for(var6 = 0; var6 < tooltipData.size(); ++var6) {
				var7 = fontRenderer.getStringWidth(tooltipData.get(var6));
				if (var7 > var5) {
					var5 = var7;
				}
			}
			var6 = x + 12;
			var7 = y - 12;
			int var9 = 8;
			if (tooltipData.size() > 1) {
				var9 += 2 + (tooltipData.size() - 1) * 10;
			}
			float z = 300F;
			int color2 = -267386864;
			drawGradientRect(var6 - 3, var7 - 4, z, var6 + var5 + 3, var7 - 3, color2, color2);
			drawGradientRect(var6 - 3, var7 + var9 + 3, z, var6 + var5 + 3, var7 + var9 + 4, color2, color2);
			drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 + var9 + 3, color2, color2);
			drawGradientRect(var6 - 4, var7 - 3, z, var6 - 3, var7 + var9 + 3, color2, color2);
			drawGradientRect(var6 + var5 + 3, var7 - 3, z, var6 + var5 + 4, var7 + var9 + 3, color2, color2);
			int var12 = (color & 0xFFFFFF) >> 1 | color & -16777216;
			drawGradientRect(var6 - 3, var7 - 3 + 1, z, var6 - 3 + 1, var7 + var9 + 3 - 1, color, var12);
			drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, z, var6 + var5 + 3, var7 + var9 + 3 - 1, color, var12);
			drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 - 3 + 1, color, color);
			drawGradientRect(var6 - 3, var7 + var9 + 2, z, var6 + var5 + 3, var7 + var9 + 3, var12, var12);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			for(int var13 = 0; var13 < tooltipData.size(); ++var13) {
				String var14 = tooltipData.get(var13);
				fontRenderer.drawStringWithShadow(var14, var6, var7, -1);
				if (var13 == 0) {
					var7 += 2;
				}
				var7 += 10;
			}
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			if (!lighting) {
				net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
			}
		}
		GL11.glColor4f(1F, 1F, 1F, 1F);
	}

	public static void drawGradientRect(int par1, int par2, float z, int par3, int par4, int par5, int par6) {
		float var7 = (par5 >> 24 & 255) / 255F;
		float var8 = (par5 >> 16 & 255) / 255F;
		float var9 = (par5 >> 8 & 255) / 255F;
		float var10 = (par5 & 255) / 255F;
		float var11 = (par6 >> 24 & 255) / 255F;
		float var12 = (par6 >> 16 & 255) / 255F;
		float var13 = (par6 >> 8 & 255) / 255F;
		float var14 = (par6 & 255) / 255F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator var15 = Tessellator.instance;
		var15.startDrawingQuads();
		var15.setColorRGBA_F(var8, var9, var10, var7);
		var15.addVertex(par3, par2, z);
		var15.addVertex(par1, par2, z);
		var15.setColorRGBA_F(var12, var13, var14, var11);
		var15.addVertex(par1, par4, z);
		var15.addVertex(par3, par4, z);
		var15.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static final ResourceLocation BLOCK_TEX = TextureMap.locationBlocksTexture;
	public static final ResourceLocation ITEM_TEX = TextureMap.locationItemsTexture;

	public static TextureManager engine() {
		return Minecraft.getMinecraft().renderEngine;
	}

	public static void bindTexture(ResourceLocation tex) {
		engine().bindTexture(tex);
	}

	public static void bindTexture(String modID, String path) {
		engine().bindTexture(new ResourceLocation(modID, path));
	}

	public static void bindBlockTexture() {
		engine().bindTexture(BLOCK_TEX);
	}

	public static void bindItemTexture() {
		engine().bindTexture(ITEM_TEX);
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

	public static void drawTexturedModalRect(int par1, int par2, float z, int par3, int par4, int par5, int par6) {
		drawTexturedModalRect(par1, par2, z, par3, par4, par5, par6, 0.00390625F, 0.00390625F);
	}

	public static void drawTexturedModalRect(int par1, int par2, float z, int par3, int par4, int par5, int par6, float f, float f1) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(par1 + 0, par2 + par6, z, (par3 + 0) * f, (par4 + par6) * f1);
		tessellator.addVertexWithUV(par1 + par5, par2 + par6, z, (par3 + par5) * f, (par4 + par6) * f1);
		tessellator.addVertexWithUV(par1 + par5, par2 + 0, z, (par3 + par5) * f, (par4 + 0) * f1);
		tessellator.addVertexWithUV(par1 + 0, par2 + 0, z, (par3 + 0) * f, (par4 + 0) * f1);
		tessellator.draw();
	}

	public static void drawTexturedModelRectFromIcon(int p_94065_1_, int p_94065_2_, float z, IIcon p_94065_3_, int p_94065_4_, int p_94065_5_) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(p_94065_1_ + 0, p_94065_2_ + p_94065_5_, z, p_94065_3_.getMinU(), p_94065_3_.getMaxV());
		tessellator.addVertexWithUV(p_94065_1_ + p_94065_4_, p_94065_2_ + p_94065_5_, z, p_94065_3_.getMaxU(), p_94065_3_.getMaxV());
		tessellator.addVertexWithUV(p_94065_1_ + p_94065_4_, p_94065_2_ + 0, z, p_94065_3_.getMaxU(), p_94065_3_.getMinV());
		tessellator.addVertexWithUV(p_94065_1_ + 0, p_94065_2_ + 0, z, p_94065_3_.getMinU(), p_94065_3_.getMinV());
		tessellator.draw();
	}

	public static void drawTexturedQuadFull(int par1, int par2, double zLevel) {
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(par1 + 0, par2 + 16, zLevel, 0.0D, 1.0D);
		var9.addVertexWithUV(par1 + 16, par2 + 16, zLevel, 1.0D, 1.0D);
		var9.addVertexWithUV(par1 + 16, par2 + 0, zLevel, 1.0D, 0.0D);
		var9.addVertexWithUV(par1 + 0, par2 + 0, zLevel, 0.0D, 0.0D);
		var9.draw();
	}
}
