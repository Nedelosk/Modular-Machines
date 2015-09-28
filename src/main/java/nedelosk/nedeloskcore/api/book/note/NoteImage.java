package nedelosk.nedeloskcore.api.book.note;

import nedelosk.nedeloskcore.api.book.BookData;
import nedelosk.nedeloskcore.api.book.IGuiBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NoteImage extends Note {

	ResourceLocation resource;

	public NoteImage(String unlocalizedName, int id, ResourceLocation resource, BookData data) {
		super(unlocalizedName, id, data);
		this.resource = resource;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderScreen(IGuiBook gui, int mx, int my) {
		TextureManager render = Minecraft.getMinecraft().renderEngine;
		render.bindTexture(resource);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		((GuiScreen) gui).drawTexturedModalRect(gui.getLeft(), gui.getTop(), 0, 0, gui.getGuiWidth(), gui.getGuiHeight());
		GL11.glDisable(GL11.GL_BLEND);

		int width = gui.getGuiWidth() - 30;
		int height = gui.getGuiHeight();
		int x = gui.getLeft() + 16;
		int y = gui.getTop() + height - 40;
		NoteText.renderText(x, y, width, height, getUnlocalizedName());
	}

}
