package nedelosk.modularmachines.client.gui.assembler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.ModuleItem;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssenbler;
import nedelosk.modularmachines.common.items.ModularItem;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;

public class GuiButtonModularAssemblerBookmark extends GuiButton {

	protected ResourceLocation guiTextureOverlay = RenderUtils.getResourceLocation("modularmachines", "modular_assembler_overlay", "gui");
	public RenderItem itemRender = RenderItem.getInstance();
	public ItemStack itemToRender;
	
	public String page;
	
	public GuiButtonModularAssemblerBookmark(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, String page) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_,28 , 21, null);
		this.page = page;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		boolean inside = mx >= xPosition && my >= yPosition && mx < xPosition + width && my < yPosition + height;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GuiModularAssembler assembler = (GuiModularAssembler) mc.currentScreen;
		RenderUtils.bindTexture(guiTextureOverlay);
		RenderUtils.drawTexturedModalRect(xPosition, yPosition, 1, ((TileModularAssenbler)assembler.getTile()).page.equals(page) ? 0 : 28,235 ,28 , 21);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		Random r = FMLClientHandler.instance().getClient().theWorld.rand;
		ArrayList<ItemStack> item = ModularMachinesApi.bookmark.get(page);
		
		if(itemToRender == null)
			if(item == null)
				itemToRender = new ItemStack(Items.arrow);
			else
				itemToRender = item.get(r.nextInt(item.size()));
		drawItemStack(itemToRender , xPosition + 6, yPosition + 3);
		RenderHelper.disableStandardItemLighting();
		GL11.glEnable(GL11.GL_BLEND);
		
		if(inside)
			RenderUtils.renderTooltip(mx, my, Arrays.asList(StatCollector.translateToLocal("mm.modularassembler.bookmark." + page)));
		GL11.glPopMatrix();
		
	}
	
    private void drawItemStack(ItemStack stack, int x, int y)
    {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (stack != null) font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = Minecraft.getMinecraft().fontRenderer;
        itemRender.renderItemAndEffectIntoGUI(font, Minecraft.getMinecraft().getTextureManager(), stack, x, y);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }

}
