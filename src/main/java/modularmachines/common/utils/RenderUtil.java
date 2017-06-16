package modularmachines.common.utils;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.client.config.GuiUtils;

public class RenderUtil {

	public static void renderTooltip(int x, int y, List<String> tooltipData) {
		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
		GuiUtils.drawHoveringText(tooltipData, x, y, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), -1, Minecraft.getMinecraft().fontRendererObj);
	}

	public static TextureManager engine() {
		return Minecraft.getMinecraft().renderEngine;
	}

	public static void texture(ResourceLocation tex) {
		engine().bindTexture(tex);
	}

	public static void bindBlockTexture() {
		texture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	}
	
}
