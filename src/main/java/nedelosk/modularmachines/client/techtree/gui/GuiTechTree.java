package nedelosk.modularmachines.client.techtree.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.basic.techtree.TechPointTypes;
import nedelosk.modularmachines.api.basic.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.basic.techtree.TechTreeCategoryList;
import nedelosk.modularmachines.api.basic.techtree.TechTreePlayerData;
import nedelosk.modularmachines.api.basic.techtree.TechTreeEntry;
import nedelosk.modularmachines.api.basic.techtree.TechTreeManager;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.techtree.PacketEntryComplete;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GuiTechTree
  extends GuiScreen
{
	
	public static ResourceLocation techtree = new ResourceLocation("modularmachines", "textures/gui/gui_techtree.png");
  private static int guiMapTop;
  private static int guiMapLeft;
  private static int guiMapBottom;
  private static int guiMapRight;
  protected int paneWidth = 256;
  protected int paneHeight = 230;
  protected int mouseX = 0;
  protected int mouseY = 0;
  protected double field_74117_m;
  protected double field_74115_n;
  protected double guiMapX;
  protected double guiMapY;
  protected double field_74124_q;
  protected double field_74123_r;
  private int isMouseButtonDown = 0;
  public static int lastX = -5;
  public static int lastY = -6;
  private GuiButton button;
  private LinkedList<TechTreeEntry> entrys = new LinkedList();
  public static HashMap<String, ArrayList<String>> completedEntrys = new HashMap();
  public static ArrayList<String> highlightedItem = new ArrayList();
  private static String selectedCategory = null;
  private TechTreeEntry currentHighlight = null;
  private String playerName = "";
  private EntityPlayer player;
  long popuptime = 0L;
  String popupmessage = "";
  
  public GuiTechTree()
  {
	this.player = Minecraft.getMinecraft().thePlayer;
    short var2 = 141;
    short var3 = 141;
    this.field_74117_m = (this.guiMapX = this.field_74124_q = lastX * 24 - var2 / 2 - 12);
    this.field_74115_n = (this.guiMapY = this.field_74123_r = lastY * 24 - var3 / 2);
    updateEntrys();
    this.playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
  }
  
  public GuiTechTree(double x, double y)
  {
    this.field_74117_m = (this.guiMapX = this.field_74124_q = x);
    this.field_74115_n = (this.guiMapY = this.field_74123_r = y);
    updateEntrys();
    this.player = Minecraft.getMinecraft().thePlayer;
    this.playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
  }
  
  public void updateEntrys()
  {
    if (this.mc == null) {
      this.mc = Minecraft.getMinecraft();
    }
    this.entrys.clear();
    if (selectedCategory == null)
    {
      Collection cats = TechTreeCategories.entryCategories.keySet();
      selectedCategory = (String)cats.iterator().next();
    }
    Collection col = TechTreeCategories.getEntryList(selectedCategory).entrys.values();
    for (Object res : col) {
      this.entrys.add((TechTreeEntry)res);
    }
    guiMapTop = TechTreeCategories.getEntryList(selectedCategory).minDisplayColumn * 24 - 85;
    guiMapLeft = TechTreeCategories.getEntryList(selectedCategory).minDisplayRow * 24 - 112;
    guiMapBottom = TechTreeCategories.getEntryList(selectedCategory).maxDisplayColumn * 24 - 112;
    guiMapRight = TechTreeCategories.getEntryList(selectedCategory).maxDisplayRow * 24 - 61;
  }
  
  @Override
public void onGuiClosed()
  {
    short var2 = 141;
    short var3 = 141;
    lastX = (int)((this.guiMapX + var2 / 2 + 12.0D) / 24.0D);
    lastY = (int)((this.guiMapY + var3 / 2) / 24.0D);
    


    super.onGuiClosed();
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
    if (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
    {
      highlightedItem.clear();
      this.mc.displayGuiScreen((GuiScreen)null);
      this.mc.setIngameFocus();
    }
    else
    {
      if (par2 == 1) {
        highlightedItem.clear();
      }
      super.keyTyped(par1, par2);
    }
  }
  
  @Override
  public void drawScreen(int mx, int my, float par3)
  {
    int var4 = (this.width - this.paneWidth) / 2;
    int var5 = (this.height - this.paneHeight) / 2;
    if (Mouse.isButtonDown(0))
    {
      int var6 = var4 + 8;
      int var7 = var5 + 17;
      if (((this.isMouseButtonDown == 0) || (this.isMouseButtonDown == 1)) && (mx >= var6) && (mx < var6 + 224) && (my >= var7) && (my < var7 + 196))
      {
        if (this.isMouseButtonDown == 0)
        {
          this.isMouseButtonDown = 1;
        }
        else
        {
          this.guiMapX -= mx - this.mouseX;
          this.guiMapY -= my - this.mouseY;
          this.field_74124_q = (this.field_74117_m = this.guiMapX);
          this.field_74123_r = (this.field_74115_n = this.guiMapY);
        }
        this.mouseX = mx;
        this.mouseY = my;
      }
      if (this.field_74124_q < guiMapTop) {
        this.field_74124_q = guiMapTop;
      }
      if (this.field_74123_r < guiMapLeft) {
        this.field_74123_r = guiMapLeft;
      }
      if (this.field_74124_q >= guiMapBottom) {
        this.field_74124_q = (guiMapBottom - 1);
      }
      if (this.field_74123_r >= guiMapRight) {
        this.field_74123_r = (guiMapRight - 1);
      }
    }
    else
    {
      this.isMouseButtonDown = 0;
    }
    drawDefaultBackground();
    genTreeBackground(mx, my, par3);
    if (this.popuptime > System.currentTimeMillis())
    {
      int xq = var4 + 128;
      int yq = var5 + 128;
      int var41 = this.fontRendererObj.splitStringWidth(this.popupmessage, 150) / 2;
      drawGradientRect(xq - 78, yq - var41 - 3, xq + 78, yq + var41 + 3, -1073741824, -1073741824);
      this.fontRendererObj.drawSplitString(this.popupmessage, xq - 75, yq - var41, 150, -7302913);
    }
    Collection cats = TechTreeCategories.entryCategories.keySet();
    int count = 0;
    boolean swop = false;
    for (Object obj : cats)
    {
      if (count == 9)
      {
        count = 0;
        swop = true;
      }
      TechTreeCategoryList rcl = TechTreeCategories.getEntryList((String)obj);
      int mposx = mx - (var4 - 24 + (swop ? 280 : 0));
      int mposy = my - (var5 + count * 24);
      if ((mposx >= 0) && (mposx < 24) && (mposy >= 0) && (mposy < 24)) {
    	  this.fontRendererObj.drawStringWithShadow(TechTreeCategories.getCategoryName((String)obj), mx, my - 8, 16777215);
      }
        count++;
    }
  }
  
  @Override
  public void updateScreen()
  {
    this.field_74117_m = this.guiMapX;
    this.field_74115_n = this.guiMapY;
    double var1 = this.field_74124_q - this.guiMapX;
    double var3 = this.field_74123_r - this.guiMapY;
    if (var1 * var1 + var3 * var3 < 4.0D)
    {
      this.guiMapX += var1;
      this.guiMapY += var3;
    }
    else
    {
      this.guiMapX += var1 * 0.85D;
      this.guiMapY += var3 * 0.85D;
    }
  }
  
  protected void genTreeBackground(int mx, int my, float par3)
  {
	  
    long t = System.nanoTime() / 50000000L;
    
    int var4 = MathHelper.floor_double(this.field_74117_m + (this.guiMapX - this.field_74117_m) * par3);
    int var5 = MathHelper.floor_double(this.field_74115_n + (this.guiMapY - this.field_74115_n) * par3);
    if (var4 < guiMapTop) {
      var4 = guiMapTop;
    }
    if (var5 < guiMapLeft) {
      var5 = guiMapLeft;
    }
    if (var4 >= guiMapBottom) {
      var4 = guiMapBottom - 1;
    }
    if (var5 >= guiMapRight) {
      var5 = guiMapRight - 1;
    }
    int var8 = (this.width - this.paneWidth) / 2;
    int var9 = (this.height - this.paneHeight) / 2;
    int var10 = var8 + 16;
    int var11 = var9 + 17;
    this.zLevel = 0.0F;
    GL11.glDepthFunc(518);
    GL11.glPushMatrix();
    GL11.glTranslatef(0.0F, 0.0F, -200.0F);
    GL11.glEnable(3553);
    RenderHelper.enableGUIStandardItemLighting();
    GL11.glDisable(2896);
    GL11.glEnable(32826);
    GL11.glEnable(2903);
    
    GL11.glPushMatrix();
    //GL11.glScalef(2.0F, 2.0F, 1.0F);
    int vx = (int)((var4 - guiMapTop) / Math.abs(guiMapTop - guiMapBottom) * 288.0F);
    int vy = (int)((var5 - guiMapLeft) / Math.abs(guiMapLeft - guiMapRight) * 316.0F);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    RenderUtils.bindTexture(TechTreeCategories.getEntryList(selectedCategory).background);
    
    drawTexturedModalRect(var10, var11, /*vx / 2*/0, /*vy / 2*/ 0, 224, 196);
    //GL11.glScalef(0.5F, 0.5F, 1.0F);
    GL11.glPopMatrix();
    GL11.glEnable(2929);
    GL11.glDepthFunc(515);
    if (completedEntrys.get(this.playerName) != null) {
      for (int var22 = 0; var22 < this.entrys.size(); var22++)
      {
        TechTreeEntry var33 = this.entrys.get(var22);
        if ((var33.parents != null) && (var33.parents.length > 0)) {
          for (int a = 0; a < var33.parents.length; a++) {
        	  try{
            if ((var33.parents[a] != null) && (TechTreeCategories.getEntry(var33.parents[a]).category.equals(selectedCategory)))
            {
            	TechTreeEntry parent = TechTreeCategories.getEntry(var33.parents[a]);
                int var24 = var33.displayColumn * 24 - var4 + 11 + var10;
                int var25 = var33.displayRow * 24 - var5 + 11 + var11;
                int var26 = parent.displayColumn * 24 - var4 + 11 + var10;
                int var27 = parent.displayRow * 24 - var5 + 11 + var11;
                
                boolean var28 = ((ArrayList)completedEntrys.get(this.playerName)).contains(var33.key);
                boolean var29 = ((ArrayList)completedEntrys.get(this.playerName)).contains(parent.key);
                
                int var30 = Math.sin(Minecraft.getSystemTime() % 600L / 600.0D * 3.141592653589793D * 2.0D) > 0.6D ? 255 : 130;
                if (var28) {
                  drawLine(var24, var25, var26, var27, 0.1F, 0.1F, 0.1F, par3);
                } //else if (canUnlockResearch(var33)) {
                  if (var29) {
                    drawLine(var24, var25, var26, var27, 0.0F, 1.0F, 0.0F, par3);
                  } else if (canUnlockResearch(parent)) {
                    drawLine(var24, var25, var26, var27, 0.0F, 0.0F, 1.0F, par3);
                  }
                //}
            }
          }catch(Exception e){
        	  e.getMessage();
          }
          }
        }
        if ((var33.siblings != null) && (var33.siblings.length > 0)) {
          for (int a = 0; a < var33.siblings.length; a++) {
            if ((var33.siblings[a] != null) && (TechTreeCategories.getEntry(var33.siblings[a]).category.equals(selectedCategory)))
            {
              TechTreeEntry sibling = TechTreeCategories.getEntry(var33.siblings[a]);
              if ((sibling.parents == null) || ((sibling.parents != null) && (!Arrays.asList(sibling.parents).contains(var33.key))))
              {
                int var24 = var33.displayColumn * 24 - var4 + 11 + var10;
                int var25 = var33.displayRow * 24 - var5 + 11 + var11;
                int var26 = sibling.displayColumn * 24 - var4 + 11 + var10;
                int var27 = sibling.displayRow * 24 - var5 + 11 + var11;
                
                boolean var28 = ((ArrayList)completedEntrys.get(this.playerName)).contains(var33.key);
                boolean var29 = ((ArrayList)completedEntrys.get(this.playerName)).contains(sibling.key);
                if (var28) {
                  drawLine(var24, var25, var26, var27, 0.1F, 0.1F, 0.2F, par3);
                } else if (canUnlockResearch(var33)) {
                  if (var29) {
                    drawLine(var24, var25, var26, var27, 0.0F, 1.0F, 0.0F, par3);
                  } else if (canUnlockResearch(sibling)) {
                    drawLine(var24, var25, var26, var27, 0.0F, 0.0F, 1.0F, par3);
                  }
                }
              }
            }
          }
        }
      }
    }
    this.currentHighlight = null;
    RenderItem itemRenderer = new RenderItem();
    
    GL11.glEnable(32826);
    GL11.glEnable(2903);
    if (completedEntrys.get(this.playerName) != null) {
      for (int var24 = 0; var24 < this.entrys.size(); var24++)
      {
    	  TechTreeEntry var35 = this.entrys.get(var24);
        int var26 = var35.displayColumn * 24 - var4;
        int var27 = var35.displayRow * 24 - var5;
        if ((var26 >= -24) && (var27 >= -24) && (var26 <= 224) && (var27 <= 196))
        {
          int var42 = var10 + var26;
          int var41 = var11 + var27;
          if (((ArrayList)completedEntrys.get(this.playerName)).contains(var35.key))
          {
            float var38 = 1.0F;
            GL11.glColor4f(var38, var38, var38, 1.0F);
          }
          else
          {
            if (var35.isConcealed() && !canUnlockResearch(var35)) {
              continue;
            }
            if (canUnlockResearch(var35))
            {
              float var38 = (float)Math.sin(Minecraft.getSystemTime() % 600L / 600.0D * 3.141592653589793D * 2.0D) * 0.25F + 0.75F;
              GL11.glColor4f(var38, var38, var38, var38);
            }
            else
            {
              float var38 = 0.3F;
              GL11.glColor4f(var38, var38, var38, 1.0F);
            }
          }
          RenderUtils.bindTexture(techtree);
          
          GL11.glEnable(2884);
          GL11.glEnable(3042);
          GL11.glBlendFunc(770, 771);
          
          drawTexturedModalRect(var42 - 2, var41 - 2, 0, 230, 26, 26);
          if (!canUnlockResearch(var35))
          {
            float var40 = 0.1F;
            GL11.glColor4f(var40, var40, var40, 1.0F);
            itemRenderer.renderWithColor = false;
          }
          GL11.glDisable(3042);
          /*if (highlightedItem.contains(var35.key))
          {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture(ParticleEngine.particleTexture);
            int px = (int)(t % 16L) * 16;
            GL11.glTranslatef(var42 - 5, var41 - 5, 0.0F);
            
            UtilsFX.drawTexturedQuad(0, 0, px, 80, 16, 16, 0.0D);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
          }*/
          if (var35.icon_item != null)
          {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glEnable(32826);
            GL11.glEnable(2903);
            GL11.glEnable(2896);
            itemRenderer.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, var35.icon_item, var42 + 3, var41 + 3);
            
            GL11.glDisable(2896);
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
          }
          else if (var35.icon_resource != null)
          {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            this.mc.renderEngine.bindTexture(var35.icon_resource);
            if (!itemRenderer.renderWithColor) {
              GL11.glColor4f(0.2F, 0.2F, 0.2F, 1.0F);
            }
            RenderUtils.drawTexturedQuadFull(var42 + 3, var41 + 3, this.zLevel);
            GL11.glPopMatrix();
          }
          if (!canUnlockResearch(var35)) {
            itemRenderer.renderWithColor = true;
          }
          if ((mx >= var10) && (my >= var11) && (mx < var10 + 224) && (my < var11 + 196) && (mx >= var42) && (mx <= var42 + 22) && (my >= var41) && (my <= var41 + 22)) {
            this.currentHighlight = var35;
          }
          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
      }
    }
    GL11.glDisable(2929);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    
    Collection cats = TechTreeCategories.entryCategories.keySet();
    int count = 0;
    boolean swop = false;
    for (Object obj : cats)
    {
    	TechTreeCategoryList rcl = TechTreeCategories.getEntryList((String)obj);
        GL11.glPushMatrix();
        if (count == 9)
        {
          count = 0;
          swop = true;
        }
        int s0 = !swop ? 0 : 264;
        int s1 = 0;
        int s2 = swop ? 14 : 0;
        if (!selectedCategory.equals(obj))
        {
          s1 = 24;
          s2 = swop ? 6 : 8;
        }
        RenderUtils.bindTexture(techtree);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (swop) {
          drawTexturedModalRectReversed(var8 + s0 - 8, var9 + count * 24, 176 + s1, 232, 24, 24);
        } else {
          drawTexturedModalRect(var8 - 24 + s0, var9 + count * 24, 152 + s1, 232, 24, 24);
        }
       /* if (highlightedItem.contains((String)obj))
        {
          GL11.glPushMatrix();
          
          this.mc.renderEngine.bindTexture(ParticleEngine.particleTexture);
          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
          int px = (int)(16L * (t % 16L));
          UtilsFX.drawTexturedQuad(var8 - 27 + s2 + s0, var9 - 4 + count * 24, px, 80, 16, 16, -90.0D);
          GL11.glPopMatrix();
        }*/
        GL11.glPushMatrix();
        this.mc.renderEngine.bindTexture(rcl.icon);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        RenderUtils.drawTexturedQuadFull(var8 - 19 + s2 + s0, var9 + 4 + count * 24, -80.0D);
        GL11.glPopMatrix();
        if (!selectedCategory.equals(obj))
        {
          RenderUtils.bindTexture(techtree);
          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
          if (swop) {
            drawTexturedModalRectReversed(var8 + s0 - 8, var9 + count * 24, 224, 232, 24, 24);
          } else {
            drawTexturedModalRect(var8 - 24 + s0, var9 + count * 24, 200, 232, 24, 24);
          }
        }
        GL11.glPopMatrix();
        count++;
    }
    RenderUtils.bindTexture(techtree);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    drawTexturedModalRect(var8, var9, 0, 0, this.paneWidth, this.paneHeight);
    
    GL11.glPopMatrix();
    
    this.zLevel = 0.0F;
    GL11.glDepthFunc(515);
    GL11.glDisable(2929);
    GL11.glEnable(3553);
    super.drawScreen(mx, my, par3);
    if ((completedEntrys.get(this.playerName) != null) && 
      (this.currentHighlight != null))
    {
      String var34 = this.currentHighlight.getNameTranslated();
      int var26 = mx + 6;
      int var27 = my - 4;
      int var99 = 0;
      FontRenderer fr = this.fontRendererObj;
      //if (canUnlockResearch(this.currentHighlight))
      //{
        boolean primary = (!((ArrayList)completedEntrys.get(this.playerName)).contains(this.currentHighlight.key));
        String requiredText = StatCollector.translateToLocal("mm.techtree.required") + " " + Integer.toString(currentHighlight.getTechPoints()) + " " + StatCollector.translateToLocal("mm.techtree.point.text") + " " + StatCollector.translateToLocal("mm.techtree.point." + currentHighlight.getTechPointType().name());
        int var42 = (int)Math.max(fr.getStringWidth(var34), fr.getStringWidth(this.currentHighlight.getTextTranslated()) / 1.9F);
        if(!completedEntrys.get(playerName).contains(currentHighlight.key))
        	var42 = Math.max(var42, fr.getStringWidth(requiredText));
        int var41 = fr.splitStringWidth(var34, var42) + 5;
        
        if (primary)
        {
          var99 += 9;
          var42 = (int)Math.max(var42, fr.getStringWidth(StatCollector.translateToLocal("tc.research.shortprim")) / 1.9F);
        }
        drawGradientRect(var26 - 3, var27 - 3, var26 + var42 + 3, var27 + var41 + 6 + var99, -1073741824, -1073741824);
        
        GL11.glPushMatrix();
        GL11.glTranslatef(var26, var27 + var41 - 1, 0.0F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.fontRendererObj.drawStringWithShadow(this.currentHighlight.getText(), 0, 0, -7302913);
        if(!completedEntrys.get(playerName).contains(currentHighlight.key))
        	this.fontRendererObj.drawStringWithShadow(requiredText , 0, 14, currentHighlight.getTechPointType() == TechPointTypes.VERY_EASY ? 0x4BDE3B : currentHighlight.getTechPointType() == TechPointTypes.EASY ? 0x249717 : currentHighlight.getTechPointType() == TechPointTypes.NORMAL ? -8355712 : currentHighlight.getTechPointType() == TechPointTypes.HARD ? 0xBA1F1F : 0x790606);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        if (primary)
        {
          GL11.glPushMatrix();
          GL11.glTranslatef(var26, var27 + var41 + 8, 0.0F);
          GL11.glScalef(0.5F, 0.5F, 0.5F);
         /* if (ResearchManager.getResearchSlot(this.mc.thePlayer, this.currentHighlight.key) >= 0) {
            this.fontRendererObj.drawStringWithShadow(StatCollector.translateToLocal("tc.research.hasnote"), 0, 0, 16753920);
          } else if (this.hasScribestuff) {
            this.fontRendererObj.drawStringWithShadow(StatCollector.translateToLocal("tc.research.getprim"), 0, 0, 8900331);
          } else {
            this.fontRendererObj.drawStringWithShadow(StatCollector.translateToLocal("tc.research.shortprim"), 0, 0, 14423100);
          }*/
          GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
      /*}
      else
      {
        GL11.glPushMatrix();
        
        int var42 = (int)Math.max(fr.getStringWidth(var34), fr.getStringWidth(StatCollector.translateToLocal("tc.researchmissing")) / 1.5F);
        String var39 = StatCollector.translateToLocal("tc.researchmissing");
        int var30 = fr.splitStringWidth(var39, var42 * 2);
        drawGradientRect(var26 - 3, var27 - 3, var26 + var42 + 3, var27 + var30 + 10, -1073741824, -1073741824);
        GL11.glTranslatef(var26, var27 + 12, 0.0F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.fontRendererObj.drawSplitString(var39, 0, 0, var42 * 2, -9416624);
        GL11.glPopMatrix();
      }*/
      fr.drawStringWithShadow(var34, var26, var27, -1);
    }
    GL11.glEnable(2929);
    GL11.glEnable(2896);
    RenderHelper.disableStandardItemLighting();
    
  }
  
  @Override
protected void mouseClicked(int par1, int par2, int par3)
  {
    int var4;
    int var5;
    int count;
    boolean swop;
    if (currentHighlight != null && (!((ArrayList)completedEntrys.get(this.playerName)).contains(this.currentHighlight.key)) && (canUnlockResearch(this.currentHighlight)))
    {
      //if (TechTreeManager.getTechPoints(player)[currentHighlight.getTechPointType().ordinal()] >= currentHighlight.getTechPoints())
      //{
        PacketHandler.INSTANCE.sendToServer(new PacketEntryComplete(this.currentHighlight.key, TechTreeManager.getTechPoints(player)[currentHighlight.getTechPointType().ordinal()] - currentHighlight.getTechPoints(), currentHighlight.getTechPointType()));
        
        //this.popuptime = (System.currentTimeMillis() + 3000L);
        //this.popupmessage = new ChatComponentTranslation(StatCollector.translateToLocal("tc.research.popup"), new Object[] { "" + this.currentHighlight.getName() }).getUnformattedText();
      //}
    }
    else if ((this.currentHighlight != null) && (((ArrayList)completedEntrys.get(this.playerName)).contains(this.currentHighlight.key)))
    {
      this.mc.displayGuiScreen(new GuiTechTreePage(this.currentHighlight, 0, this.guiMapX, this.guiMapY));
    }
    else
    {
      var4 = (this.width - this.paneWidth) / 2;
      var5 = (this.height - this.paneHeight) / 2;
      
      Collection cats = TechTreeCategories.entryCategories.keySet();
      count = 0;
      swop = false;
      for (Object obj : cats)
      {
        TechTreeCategoryList rcl = TechTreeCategories.getEntryList((String)obj);
          if (count == 9)
          {
            count = 0;
            swop = true;
          }
          int mposx = par1 - (var4 - 24 + (swop ? 280 : 0));
          int mposy = par2 - (var5 + count * 24);
          if ((mposx >= 0) && (mposx < 24) && (mposy >= 0) && (mposy < 24))
          {
            selectedCategory = (String)obj;
            updateEntrys();
            break;
          }
          count++;
      }
    }
    super.mouseClicked(par1, par2, par3);
  }
  
  public void drawTexturedModalRectReversed(int par1, int par2, int par3, int par4, int par5, int par6)
  {
    float f = 0.0039063F;
    float f1 = 0.0039063F;
    Tessellator tessellator = Tessellator.instance;
    tessellator.startDrawingQuads();
    tessellator.addVertexWithUV(par1 + 0, par2 + par6, this.zLevel, (par3 + 0) * f, (par4 + par6) * f1);
    tessellator.addVertexWithUV(par1 + par5, par2 + par6, this.zLevel, (par3 - par5) * f, (par4 + par6) * f1);
    tessellator.addVertexWithUV(par1 + par5, par2 + 0, this.zLevel, (par3 - par5) * f, (par4 + 0) * f1);
    tessellator.addVertexWithUV(par1 + 0, par2 + 0, this.zLevel, (par3 + 0) * f, (par4 + 0) * f1);
    tessellator.draw();
  }
  
  private boolean canUnlockResearch(TechTreeEntry res)
  {
    if ((res.parents != null) && (res.parents.length > 0)) {
      for (String pt : res.parents)
      {
        TechTreeEntry parent = TechTreeCategories.getEntry(pt);
        if ((parent != null) && (!(((TechTreePlayerData)player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techEntrys.contains(parent.key)))) {
          return false;
        }
      }
    }
    return true;
  }
  
  @Override
public boolean doesGuiPauseGame()
  {
    return false;
  }
  
  private void drawLine(int x, int y, int x2, int y2, float r, float g, float b, float te)
  {
    float count = FMLClientHandler.instance().getClient().thePlayer.ticksExisted + te;
    
    Tessellator var12 = Tessellator.instance;
    
    GL11.glPushMatrix();
    GL11.glAlphaFunc(516, 0.003921569F);
    GL11.glDisable(3553);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    


    double d3 = x - x2;
    double d4 = y - y2;
    float dist = MathHelper.sqrt_double(d3 * d3 + d4 * d4);
    int inc = (int)(dist / 2.0F);
    float dx = (float)(d3 / inc);
    float dy = (float)(d4 / inc);
    if (Math.abs(d3) > Math.abs(d4)) {
      dx *= 2.0F;
    } else {
      dy *= 2.0F;
    }
    GL11.glLineWidth(3.0F);
    GL11.glEnable(2848);
    GL11.glHint(3154, 4354);
    


    var12.startDrawing(3);
    for (int a = 0; a <= inc; a++)
    {
      float r2 = r;
      float g2 = g;
      float b2 = b;
      float mx = 0.0F;
      float my = 0.0F;
      float op = 0.6F;
      var12.setColorRGBA_F(01.F, 1F, 1F, 0.6F);
      
      var12.addVertex(x - dx * a + mx, y - dy * a + my, 0.0D);
      if (Math.abs(d3) > Math.abs(d4)) {
        dx *= (1.0F - 1.0F / (inc * 3.0F / 2.0F));
      } else {
        dy *= (1.0F - 1.0F / (inc * 3.0F / 2.0F));
      }
    }
    var12.draw();
    
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(2848);
    GL11.glDisable(3042);
    GL11.glDisable(32826);
    GL11.glEnable(3553);
    GL11.glAlphaFunc(516, 0.1F);
    GL11.glPopMatrix();
  }
}
