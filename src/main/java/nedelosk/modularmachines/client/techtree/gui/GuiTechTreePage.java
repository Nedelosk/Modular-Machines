package nedelosk.modularmachines.client.techtree.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.crafting.IModularCraftingRecipe;
import nedelosk.modularmachines.api.modular.crafting.ShapedModularCraftingRecipe;
import nedelosk.modularmachines.api.modular.crafting.ShapelessModularCraftingRecipe;
import nedelosk.modularmachines.api.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.techtree.TechTreeEntry;
import nedelosk.modularmachines.api.techtree.TechTreeManager;
import nedelosk.modularmachines.api.techtree.TechTreePage;
import nedelosk.modularmachines.api.techtree.TechTreePage.PageType;
import nedelosk.nedeloskcore.common.book.note.NoteText;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@SideOnly(Side.CLIENT)
public class GuiTechTreePage
  extends GuiScreen
{
  protected static RenderItem itemRenderer = new RenderItem();
  public static LinkedList<Object[]> history = new LinkedList();
  protected int paneWidth = 256;
  protected int paneHeight = 181;
  protected double guiMapX;
  protected double guiMapY;
  protected int mouseX = 0;
  protected int mouseY = 0;
  private GuiButton button;
  private TechTreeEntry entry;
  private TechTreePage[] pages = null;
  private int page = 0;
  private int maxPages = 0;
  
  public GuiTechTreePage(TechTreeEntry research, int page, double x, double y)
  {
    this.entry = research;
    this.guiMapX = x;
    this.guiMapY = y;
    
    this.mc = Minecraft.getMinecraft();
    
    this.pages = research.getPages();
    
    List<TechTreePage> p1 = Arrays.asList(this.pages);
    ArrayList<TechTreePage> p2 = new ArrayList();
    for (TechTreePage pp : p1) {
      if ((pp == null) || (pp.type != PageType.TEXT_CONCEALED) || (TechTreeManager.isEntryComplete(this.mc.thePlayer, pp.entry))) {
        p2.add(pp);
      }
    }
    this.pages = (p2.toArray(new TechTreePage[0]));
    this.maxPages = this.pages.length;
    
    if (page % 2 == 1) {
      page--;
    }
    this.page = page;
  }
  
  @Override
public void initGui() {}
  
  @Override
protected void actionPerformed(GuiButton par1GuiButton)
  {
    super.actionPerformed(par1GuiButton);
  }
  
  @Override
protected void keyTyped(char par1, int par2)
  {
    if ((par2 == this.mc.gameSettings.keyBindInventory.getKeyCode()) || (par2 == 1))
    {
      history.clear();
      this.mc.displayGuiScreen(new GuiTechTree(this.guiMapX, this.guiMapY));
    }
    else
    {
      super.keyTyped(par1, par2);
    }
  }
  
  @Override
public void onGuiClosed()
  {
    super.onGuiClosed();
  }
  
  @Override
public void drawScreen(int par1, int par2, float par3)
  {
    drawDefaultBackground();
    genPageBackground(par1, par2, par3);
    


    int sw = (this.width - this.paneWidth) / 2;
    int sh = (this.height - this.paneHeight) / 2;
    if (!history.isEmpty())
    {
      int mx = par1 - (sw + 118);
      int my = par2 - (sh + 189);
      if ((mx >= 0) && (my >= 0) && (mx < 20) && (my < 12)) {
        this.fontRendererObj.drawStringWithShadow(StatCollector.translateToLocal("recipe.return"), par1, par2, 16777215);
      }
    }
  }
  
  String tex1 = "modularmachines:textures/gui/gui_techtreetablet.png";
  String tex2 = "thaumcraft:textures/gui/gui_researchbook_overlay.png";
  
  protected void genPageBackground(int par1, int par2, float par3)
  {
    int sw = (this.width - this.paneWidth) / 2;
    int sh = (this.height - this.paneHeight) / 2;
    
    float var10 = (this.width - this.paneWidth * 1.3F) / 2.0F;
    float var11 = (this.height - this.paneHeight * 1.3F) / 2.0F;
    
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    RenderUtils.bindTexture(new ResourceLocation(tex1));
    
    GL11.glPushMatrix();
    GL11.glTranslatef(var10, var11, 0.0F);
    GL11.glEnable(3042);
    GL11.glScalef(1.3F, 1.3F, 1.0F);
    drawTexturedModalRect(0, 0, 0, 0, this.paneWidth, this.paneHeight);
    GL11.glPopMatrix();
    
    this.reference.clear();
    this.tooltip = null;
    int current = 0;
    for (int a = 0; a < this.pages.length; a++)
    {
      if (((current == this.page) || (current == this.page + 1)) && (current < this.maxPages)) {
        drawPage(this.pages[a], current % 2, sw, sh, par1, par2);
      }
      current++;
      if (current > this.page + 1) {
        break;
      }
    }
    if (this.tooltip != null) {
    	RenderUtils.renderTooltip(((Integer)this.tooltip[1]).intValue(), ((Integer)this.tooltip[2]).intValue(), (List)this.tooltip[0], ((Integer)this.tooltip[3]).intValue());
      //UtilsFX.drawCustomTooltip(this, itemRenderer, this.fontRendererObj, (List)this.tooltip[0], ((Integer)this.tooltip[1]).intValue(), ((Integer)this.tooltip[2]).intValue(), ((Integer)this.tooltip[3]).intValue());
    }
    RenderUtils.bindTexture(new ResourceLocation(tex1));
    int mx1 = par1 - (sw + 261);
    int my1 = par2 - (sh + 189);
    int mx2 = par1 - (sw - 17);
    int my2 = par2 - (sh + 189);
    
    float bob = MathHelper.sin(this.mc.thePlayer.ticksExisted / 3.0F) * 0.2F + 0.1F;
    if (!history.isEmpty())
    {
      GL11.glEnable(3042);
      drawTexturedModalRectScaled(sw + 118, sh + 189, 38, 202, 20, 12, bob);
    }
    if (this.page > 0)
    {
      GL11.glEnable(3042);
      drawTexturedModalRectScaled(sw - 16, sh + 190, 0, 184, 12, 8, bob);
    }
    if (this.page < this.maxPages - 2)
    {
      GL11.glEnable(3042);
      drawTexturedModalRectScaled(sw + 262, sh + 190, 12, 184, 12, 8, bob);
    }
  }
  
  private Object[] tooltip = null;
  
  public void drawCustomTooltip(GuiScreen gui, RenderItem itemRenderer, FontRenderer fr, List var4, int par2, int par3, int subTipColor)
  {
    this.tooltip = new Object[] { var4, Integer.valueOf(par2), Integer.valueOf(par3), Integer.valueOf(subTipColor) };
  }
  
  private long lastCycle = 0L;
  
  private void drawPage(TechTreePage pageParm, int side, int x, int y, int mx, int my)
  {
    GL11.glPushAttrib(1048575);
    if (this.lastCycle < System.currentTimeMillis())
    {
      this.cycle += 1;
      this.lastCycle = (System.currentTimeMillis() + 1000L);
    }
    if ((this.page == 0) && (side == 0))
    {
      drawTexturedModalRect(x + 4, y - 13, 24, 184, 96, 4);
      drawTexturedModalRect(x + 4, y + 4, 24, 184, 96, 4);
      int offset = this.fontRendererObj.getStringWidth(this.entry.getName());
      if (offset <= 130)
      {
        this.fontRendererObj.drawString(this.entry.getName(), x + 52 - offset / 2, y - 6, 3158064);
      }
      else
      {
        float vv = 130.0F / offset;
        GL11.glPushMatrix();
        GL11.glTranslatef(x + 52 - offset / 2 * vv, y - 6.0F * vv, 0.0F);
        GL11.glScalef(vv, vv, vv);
        this.fontRendererObj.drawString(this.entry.getName(), 0, 0, 3158064);
        GL11.glPopMatrix();
      }
      y += 25;
    }
    GL11.glAlphaFunc(516, 0.003921569F);
    if ((pageParm.type == TechTreePage.PageType.TEXT)) {
      drawTextPage(side, x, y - 10, pageParm.getTranslatedText());
    } else if (pageParm.type == TechTreePage.PageType.NORMAL_CRAFTING) {
      drawCraftingPage(1, x - 4, y - 8, mx, my, pageParm);
    } else if (pageParm.type == TechTreePage.PageType.SMELTING) {
      drawSmeltingPage(side, x - 4, y - 8, mx, my, pageParm);
    }
    else if (pageParm.type == TechTreePage.PageType.MODULAR_CRAFTING) {
        drawModularCraftingPage(side, x - 4, y - 8, mx, my, pageParm);
      }
    GL11.glAlphaFunc(516, 0.1F);
    GL11.glPopAttrib();
  }
  
  private void drawModularCraftingPage(int side, int x, int y, int mx, int my, TechTreePage pageParm)
  {
    IModularCraftingRecipe recipe = null;
    Object tr = null;
    if ((pageParm.recipe instanceof Object[])) {
      try
      {
        tr = ((Object[])pageParm.recipe)[this.cycle];
      }
      catch (Exception e)
      {
        this.cycle = 0;
        tr = ((Object[])pageParm.recipe)[this.cycle];
      }
    } else {
      tr = pageParm.recipe;
    }
    if ((tr instanceof ShapedModularCraftingRecipe)) {
      recipe = (ShapedModularCraftingRecipe)tr;
    } else if ((tr instanceof ShapelessModularCraftingRecipe)) {
      recipe = (ShapelessModularCraftingRecipe)tr;
    }
    if (recipe == null) {
      return;
    }
    GL11.glPushMatrix();
    int start = side * 152;
    
    RenderUtils.bindTexture(new ResourceLocation(tex2));
    

    GL11.glPushMatrix();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glEnable(3042);
    GL11.glTranslatef(x + start, y, 0.0F);
    GL11.glScalef(2.0F, 2.0F, 1.0F);
    drawTexturedModalRect(2, 32, 60, 15, 52, 52);
    drawTexturedModalRect(20, 12, 20, 3, 16, 16);
    GL11.glPopMatrix();
    
    int mposx = mx;
    int mposy = my;
    
    GL11.glPushMatrix();
    GL11.glTranslated(0.0D, 0.0D, 100.0D);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    RenderHelper.enableGUIStandardItemLighting();
    GL11.glEnable(2884);
    itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, recipe.getRecipeOutput(), x + 48 + start, y + 32);
    itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, recipe.getRecipeOutput(), x + 48 + start, y + 32);
    RenderHelper.disableStandardItemLighting();
    GL11.glEnable(2896);
    GL11.glPopMatrix();
    if ((mposx >= x + 48 + start) && (mposy >= y + 32) && (mposx < x + 48 + start + 16) && (mposy < y + 32 + 16)) {
      drawCustomTooltip(this, itemRenderer, this.fontRendererObj, recipe.getRecipeOutput().getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips), mx, my, 11);
    }
    if ((recipe != null) && (((recipe instanceof ShapedModularCraftingRecipe))))
    {
      String text = StatCollector.translateToLocal("recipe.type.modular.workbench");
      int offset = this.fontRendererObj.getStringWidth(text);
      this.fontRendererObj.drawString(text, x + start + 56 - offset / 2, y, 5263440);
      
      int rw = 0;
      int rh = 0;
      Object[] items = null;
      if ((recipe instanceof ShapedModularCraftingRecipe))
      {
        rw = ((ShapedModularCraftingRecipe)recipe).width;
        rh = ((ShapedModularCraftingRecipe)recipe).height;
        items = ((ShapedModularCraftingRecipe)recipe).input;
      }
      for (int i = 0; (i < rw) && (i < 3); i++) {
        for (int j = 0; (j < rh) && (j < 3); j++) {
          if (items[(i + j * rw)] != null)
          {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(2884);
            GL11.glTranslated(0.0D, 0.0D, 100.0D);
            itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, (ItemStack)items[(i + j * rw)], x + start + 16 + i * 32, y + 76 + j * 32);
            itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, ((ItemStack)items[(i + j * rw)]).copy().splitStack(1), x + start + 16 + i * 32, y + 76 + j * 32);
            RenderHelper.disableStandardItemLighting();
            GL11.glEnable(2896);
            GL11.glPopMatrix();
          }
        }
      }
      for (int i = 0; (i < rw) && (i < 3); i++) {
        for (int j = 0; (j < rh) && (j < 3); j++) {
          if (items[(i + j * rw)] != null) {
            if ((mposx >= x + 16 + start + i * 32) && (mposy >= y + 76 + j * 32) && (mposx < x + 16 + start + i * 32 + 16) && (mposy < y + 76 + j * 32 + 16))
            {
              List addtext = ((ItemStack)items[(i + j * rw)]).getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
              Object[] ref = findRecipeReference(((ItemStack)items[(i + j * rw)]));
              if ((ref != null) && (!((String)ref[0]).equals(this.entry.key)))
              {
                addtext.add("§8§o" + StatCollector.translateToLocal("recipe.clickthrough"));
                this.reference.add(Arrays.asList(new Serializable[] { Integer.valueOf(mx), Integer.valueOf(my), (String)ref[0], (Integer)ref[1] }));
              }
              drawCustomTooltip(this, itemRenderer, this.fontRendererObj, addtext, mx, my, 11);
            }
          }
        }
      }
    }
    if ((recipe != null) && (((recipe instanceof ShapelessModularCraftingRecipe))))
    {
      String text = StatCollector.translateToLocal("recipe.type.modular.workbench.shapeless");
      int offset = this.fontRendererObj.getStringWidth(text);
      this.fontRendererObj.drawString(text, x + start + 56 - offset / 2, y, 5263440);
      
      List<Object> items = null;
      if ((recipe instanceof ShapelessModularCraftingRecipe)) {
        items = ((ShapelessModularCraftingRecipe)recipe).getInput();
      }
      for (int i = 0; (i < items.size()) && (i < 9); i++) {
        if (items.get(i) != null)
        {
          GL11.glPushMatrix();
          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
          RenderHelper.enableGUIStandardItemLighting();
          GL11.glEnable(2884);
          GL11.glTranslated(0.0D, 0.0D, 100.0D);
          itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, (ItemStack)items.get(i), x + start + 16 + i % 3 * 32, y + 76 + i / 3 * 32);
          itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, ((ItemStack)items.get(i)).copy().splitStack(1), x + start + 16 + i % 3 * 32, y + 76 + i / 3 * 32);
          RenderHelper.disableStandardItemLighting();
          GL11.glEnable(2896);
          GL11.glPopMatrix();
        }
      }
      for (int i = 0; (i < items.size()) && (i < 9); i++) {
        if (items.get(i) != null) {
          if ((mposx >= x + 16 + start + i % 3 * 32) && (mposy >= y + 76 + i / 3 * 32) && (mposx < x + 16 + start + i % 3 * 32 + 16) && (mposy < y + 76 + i / 3 * 32 + 16))
          {
            List addtext = ((ItemStack)items.get(i)).getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
            Object[] ref = findRecipeReference(((ItemStack)items.get(i)));
            if ((ref != null) && (!((String)ref[0]).equals(this.entry.key)))
            {
              addtext.add("§8§o" + StatCollector.translateToLocal("recipe.clickthrough"));
              this.reference.add(Arrays.asList(new Serializable[] { Integer.valueOf(mx), Integer.valueOf(my), (String)ref[0], (Integer)ref[1] }));
            }
            drawCustomTooltip(this, itemRenderer, this.fontRendererObj, addtext, mx, my, 11);
          }
        }
      }
    }
    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    GL11.glPopMatrix();
  }
  
  private void drawCraftingPage(int side, int x, int y, int mx, int my, TechTreePage pageParm)
  {
    IRecipe recipe = null;
    Object tr = null;
    if ((pageParm.recipe instanceof Object[])) {
      try
      {
        tr = ((Object[])pageParm.recipe)[this.cycle];
      }
      catch (Exception e)
      {
        this.cycle = 0;
        tr = ((Object[])pageParm.recipe)[this.cycle];
      }
    } else {
      tr = pageParm.recipe;
    }
    if ((tr instanceof ShapedRecipes)) {
      recipe = (ShapedRecipes)tr;
    } else if ((tr instanceof ShapelessRecipes)) {
      recipe = (ShapelessRecipes)tr;
    } else if ((tr instanceof ShapedOreRecipe)) {
      recipe = (ShapedOreRecipe)tr;
    } else if ((tr instanceof ShapelessOreRecipe)) {
      recipe = (ShapelessOreRecipe)tr;
    }
    if (recipe == null) {
      return;
    }
    GL11.glPushMatrix();
    int start = side * 152;
    
    RenderUtils.bindTexture(new ResourceLocation(tex2));
    

    GL11.glPushMatrix();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glEnable(3042);
    GL11.glTranslatef(x + start, y, 0.0F);
    GL11.glScalef(2.0F, 2.0F, 1.0F);
    drawTexturedModalRect(2, 32, 60, 15, 52, 52);
    drawTexturedModalRect(20, 12, 20, 3, 16, 16);
    GL11.glPopMatrix();
    
    int mposx = mx;
    int mposy = my;
    
    GL11.glPushMatrix();
    GL11.glTranslated(0.0D, 0.0D, 100.0D);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    RenderHelper.enableGUIStandardItemLighting();
    GL11.glEnable(2884);
    itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, recipe.getRecipeOutput(), x + 48 + start, y + 32);
    itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, recipe.getRecipeOutput(), x + 48 + start, y + 32);
    RenderHelper.disableStandardItemLighting();
    GL11.glEnable(2896);
    GL11.glPopMatrix();
    if ((mposx >= x + 48 + start) && (mposy >= y + 32) && (mposx < x + 48 + start + 16) && (mposy < y + 32 + 16)) {
      drawCustomTooltip(this, itemRenderer, this.fontRendererObj, recipe.getRecipeOutput().getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips), mx, my, 11);
    }
    if ((recipe != null) && (((recipe instanceof ShapedRecipes)) || ((recipe instanceof ShapedOreRecipe))))
    {
      String text = StatCollector.translateToLocal("recipe.type.workbench");
      int offset = this.fontRendererObj.getStringWidth(text);
      this.fontRendererObj.drawString(text, x + start + 56 - offset / 2, y, 5263440);
      
      int rw = 0;
      int rh = 0;
      Object[] items = null;
      if ((recipe instanceof ShapedRecipes))
      {
        rw = ((ShapedRecipes)recipe).recipeWidth;
        rh = ((ShapedRecipes)recipe).recipeHeight;
        items = ((ShapedRecipes)recipe).recipeItems;
      }
      else
      {
        rw = ((Integer)ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipe.class, (ShapedOreRecipe)recipe, new String[] { "width" })).intValue();
        rh = ((Integer)ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipe.class, (ShapedOreRecipe)recipe, new String[] { "height" })).intValue();
        items = ((ShapedOreRecipe)recipe).getInput();
      }
      for (int i = 0; (i < rw) && (i < 3); i++) {
        for (int j = 0; (j < rh) && (j < 3); j++) {
          if (items[(i + j * rw)] != null)
          {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(2884);
            GL11.glTranslated(0.0D, 0.0D, 100.0D);
            itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, (ItemStack)items[(i + j * rw)], x + start + 16 + i * 32, y + 76 + j * 32);
            itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, ((ItemStack)items[(i + j * rw)]).copy().splitStack(1), x + start + 16 + i * 32, y + 76 + j * 32);
            RenderHelper.disableStandardItemLighting();
            GL11.glEnable(2896);
            GL11.glPopMatrix();
          }
        }
      }
      for (int i = 0; (i < rw) && (i < 3); i++) {
        for (int j = 0; (j < rh) && (j < 3); j++) {
          if (items[(i + j * rw)] != null) {
            if ((mposx >= x + 16 + start + i * 32) && (mposy >= y + 76 + j * 32) && (mposx < x + 16 + start + i * 32 + 16) && (mposy < y + 76 + j * 32 + 16))
            {
              List addtext = ((ItemStack)items[(i + j * rw)]).getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
              Object[] ref = findRecipeReference(((ItemStack)items[(i + j * rw)]));
              if ((ref != null) && (!((String)ref[0]).equals(this.entry.key)))
              {
                addtext.add("§8§o" + StatCollector.translateToLocal("recipe.clickthrough"));
                this.reference.add(Arrays.asList(new Serializable[] { Integer.valueOf(mx), Integer.valueOf(my), (String)ref[0], (Integer)ref[1] }));
              }
              drawCustomTooltip(this, itemRenderer, this.fontRendererObj, addtext, mx, my, 11);
            }
          }
        }
      }
    }
    if ((recipe != null) && (((recipe instanceof ShapelessRecipes)) || ((recipe instanceof ShapelessOreRecipe))))
    {
      String text = StatCollector.translateToLocal("recipe.type.workbenchshapeless");
      int offset = this.fontRendererObj.getStringWidth(text);
      this.fontRendererObj.drawString(text, x + start + 56 - offset / 2, y, 5263440);
      
      List<Object> items = null;
      if ((recipe instanceof ShapelessRecipes)) {
        items = ((ShapelessRecipes)recipe).recipeItems;
      } else {
        items = ((ShapelessOreRecipe)recipe).getInput();
      }
      for (int i = 0; (i < items.size()) && (i < 9); i++) {
        if (items.get(i) != null)
        {
          GL11.glPushMatrix();
          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
          RenderHelper.enableGUIStandardItemLighting();
          GL11.glEnable(2884);
          GL11.glTranslated(0.0D, 0.0D, 100.0D);
          itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, (ItemStack)items.get(i), x + start + 16 + i % 3 * 32, y + 76 + i / 3 * 32);
          itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, ((ItemStack)items.get(i)).copy().splitStack(1), x + start + 16 + i % 3 * 32, y + 76 + i / 3 * 32);
          RenderHelper.disableStandardItemLighting();
          GL11.glEnable(2896);
          GL11.glPopMatrix();
        }
      }
      for (int i = 0; (i < items.size()) && (i < 9); i++) {
        if (items.get(i) != null) {
          if ((mposx >= x + 16 + start + i % 3 * 32) && (mposy >= y + 76 + i / 3 * 32) && (mposx < x + 16 + start + i % 3 * 32 + 16) && (mposy < y + 76 + i / 3 * 32 + 16))
          {
            List addtext = ((ItemStack)items.get(i)).getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
            Object[] ref = findRecipeReference(((ItemStack)items.get(i)));
            if ((ref != null) && (!((String)ref[0]).equals(this.entry.key)))
            {
              addtext.add("§8§o" + StatCollector.translateToLocal("recipe.clickthrough"));
              this.reference.add(Arrays.asList(new Serializable[] { Integer.valueOf(mx), Integer.valueOf(my), (String)ref[0], (Integer)ref[1] }));
            }
            drawCustomTooltip(this, itemRenderer, this.fontRendererObj, addtext, mx, my, 11);
          }
        }
      }
    }
    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    GL11.glPopMatrix();
  }
  
  private void drawSmeltingPage(int side, int x, int y, int mx, int my, TechTreePage pageParm)
  {
    ItemStack in = (ItemStack)pageParm.recipe;
    ItemStack out = null;
    if (in != null) {
      out = FurnaceRecipes.smelting().getSmeltingResult(in);
    }
    if ((in != null) && (out != null))
    {
      GL11.glPushMatrix();
      int start = side * 152;
      
      String text = StatCollector.translateToLocal("recipe.type.smelting");
      int offset = this.fontRendererObj.getStringWidth(text);
      this.fontRendererObj.drawString(text, x + start + 56 - offset / 2, y, 5263440);
      
      RenderUtils.bindTexture(new ResourceLocation(tex2));
      

      GL11.glPushMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3042);
      GL11.glTranslatef(x + start, y + 28, 0.0F);
      GL11.glScalef(2.0F, 2.0F, 1.0F);
      drawTexturedModalRect(0, 0, 0, 192, 56, 64);
      GL11.glPopMatrix();
      
      int mposx = mx;
      int mposy = my;
      
      GL11.glPushMatrix();
      GL11.glTranslated(0.0D, 0.0D, 100.0D);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderHelper.enableGUIStandardItemLighting();
      GL11.glEnable(2884);
      itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, in, x + 48 + start, y + 64);
      


      itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, in, x + 48 + start, y + 64);
      


      RenderHelper.disableStandardItemLighting();
      GL11.glEnable(2896);
      GL11.glPopMatrix();
      
      GL11.glPushMatrix();
      GL11.glTranslated(0.0D, 0.0D, 100.0D);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderHelper.enableGUIStandardItemLighting();
      GL11.glEnable(2884);
      itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, out, x + 48 + start, y + 144);
      


      itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, out, x + 48 + start, y + 144);
      


      RenderHelper.disableStandardItemLighting();
      GL11.glEnable(2896);
      GL11.glPopMatrix();
      if ((mposx >= x + 48 + start) && (mposy >= y + 64) && (mposx < x + 48 + start + 16) && (mposy < y + 64 + 16))
      {
        List addtext = in.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
        Object[] ref = findRecipeReference(in);
        if ((ref != null) && (!((String)ref[0]).equals(this.entry.key)))
        {
          addtext.add("§8§o" + StatCollector.translateToLocal("recipe.clickthrough"));
          this.reference.add(Arrays.asList(new Serializable[] { Integer.valueOf(mx), Integer.valueOf(my), (String)ref[0], (Integer)ref[1] }));
        }
        drawCustomTooltip(this, itemRenderer, this.fontRendererObj, addtext, mx, my, 11);
      }
      if ((mposx >= x + 48 + start) && (mposy >= y + 144) && (mposx < x + 48 + start + 16) && (mposy < y + 144 + 16)) {
        drawCustomTooltip(this, itemRenderer, this.fontRendererObj, out.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips), mx, my, 11);
      }
      GL11.glPopMatrix();
    }
  }
  
  private void drawTextPage(int side, int x, int y, String text)
  {
	int start = side * 152;
    GL11.glPushMatrix();
    RenderHelper.enableGUIStandardItemLighting();
    
    GL11.glEnable(3042);
    NoteText.renderText(x - 15 + start, y - 10, 152, this.height, text);
    //TechTree.fr.drawSplitString(text, x - 15 + side * 152, y, 139, 0, this);
    GL11.glPopMatrix();
  }
  
  ArrayList<List> reference = new ArrayList();
  
  @Override
protected void mouseClicked(int par1, int par2, int par3)
  {
    int var4 = (this.width - this.paneWidth) / 2;
    int var5 = (this.height - this.paneHeight) / 2;
    

    int mx = par1 - (var4 + 261);
    int my = par2 - (var5 + 189);
    if ((this.page < this.maxPages - 2) && (mx >= 0) && (my >= 0) && (mx < 14) && (my < 10))
    {
      this.page += 2;
      this.lastCycle = 0L;
      this.cycle = -1;
      Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, "thaumcraft:page", 0.66F, 1.0F, false);
    }
    mx = par1 - (var4 - 17);
    my = par2 - (var5 + 189);
    if ((this.page >= 2) && (mx >= 0) && (my >= 0) && (mx < 14) && (my < 10))
    {
      this.page -= 2;
      this.lastCycle = 0L;
      this.cycle = -1;
      Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, "thaumcraft:page", 0.66F, 1.0F, false);
    }
    if (!history.isEmpty())
    {
      mx = par1 - (var4 + 118);
      my = par2 - (var5 + 189);
      if ((mx >= 0) && (my >= 0) && (mx < 20) && (my < 12))
      {
        Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, "thaumcraft:page", 0.66F, 1.0F, false);
        

        Object[] o = history.pop();
        this.mc.displayGuiScreen(new GuiTechTreePage(TechTreeCategories.getEntry((String)o[0]), ((Integer)o[1]).intValue(), this.guiMapX, this.guiMapY));
      }
    }
    if (this.reference.size() > 0) {
      for (List coords : this.reference) {
        if ((par1 >= ((Integer)coords.get(0)).intValue()) && (par2 >= ((Integer)coords.get(1)).intValue()) && (par1 < ((Integer)coords.get(0)).intValue() + 16) && (par2 < ((Integer)coords.get(1)).intValue() + 16))
        {
          Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, "thaumcraft:page", 0.66F, 1.0F, false);
          

          history.push(new Object[] { this.entry.key, Integer.valueOf(this.page) });
          this.mc.displayGuiScreen(new GuiTechTreePage(TechTreeCategories.getEntry((String)coords.get(2)), ((Integer)coords.get(3)).intValue(), this.guiMapX, this.guiMapY));
        }
      }
    }
    super.mouseClicked(par1, par2, par3);
  }
  
  private int cycle = -1;
  
  @Override
public boolean doesGuiPauseGame()
  {
    return false;
  }
  
  public Object[] findRecipeReference(ItemStack item)
  {
    return null;
  }
  
  public void drawTexturedModalRectScaled(int par1, int par2, int par3, int par4, int par5, int par6, float scale)
  {
    GL11.glPushMatrix();
    float var7 = 0.0039063F;
    float var8 = 0.0039063F;
    Tessellator var9 = Tessellator.instance;
    GL11.glTranslatef(par1 + par5 / 2.0F, par2 + par6 / 2.0F, 0.0F);
    GL11.glScalef(1.0F + scale, 1.0F + scale, 1.0F);
    var9.startDrawingQuads();
    var9.addVertexWithUV(-par5 / 2.0F, par6 / 2.0F, this.zLevel, (par3 + 0) * var7, (par4 + par6) * var8);
    var9.addVertexWithUV(par5 / 2.0F, par6 / 2.0F, this.zLevel, (par3 + par5) * var7, (par4 + par6) * var8);
    var9.addVertexWithUV(par5 / 2.0F, -par6 / 2.0F, this.zLevel, (par3 + par5) * var7, (par4 + 0) * var8);
    var9.addVertexWithUV(-par5 / 2.0F, -par6 / 2.0F, this.zLevel, (par3 + 0) * var7, (par4 + 0) * var8);
    var9.draw();
    GL11.glPopMatrix();
  }
}
