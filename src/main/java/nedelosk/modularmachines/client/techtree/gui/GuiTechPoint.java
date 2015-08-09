package nedelosk.modularmachines.client.techtree.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.techtree.TechPointTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GuiTechPoint extends Gui {
	
    private static final ResourceLocation achievement = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    private TechPointTypes[] type = new TechPointTypes[5];
    private int[] points = new int[5];
    private long timer;
    private RenderItem itemRender = new RenderItem();
    private Minecraft mc = Minecraft.getMinecraft();
    
    public void addPoints(TechPointTypes[] type, int[] points)
    {
    	this.type  = type;
    	this.points = points;
    	timer = 10 * 20;
    }
    
    public void addPoints(TechPointTypes type, int points)
    {
    	int index = 0;
    	for(int i = 0;i < 5;i++)
    		if(this.type[i] == null)
    		{
    			index = i;
    			break;
    		}
    	this.type[index]  = type;
    	this.points[index] = points;
    	timer = 10 * 20;
    }
    
    public void drawTechPointTab(int x, int y)
    {
    	if(type[0] != null &&  timer != 0)
    	{
            timer -= 1;
    		GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
    		if(timer > 160 || timer < 40)
    		{
            float var38 = (float)Math.sin(Minecraft.getSystemTime() % 600L / 600.0D * 3.141592653589793D * 2.0D) * 0.25F + 0.75F;
            GL11.glColor4f(var38, var38, var38, var38);
    		}
    		else
    		{
    			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    		}
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            mc.renderEngine.bindTexture(achievement);
        	this.drawTexturedModalRect(x , y, 96, 202, 160, 32);
        	int a = 0;
    		String name = "";
        	for(int i = 0;i < type.length;i++)
        	{
        		if(type[i] != null)
        		{
        		name += StatCollector.translateToLocal("mm.techpoint." + type[i].name()) + " " + points[i] + ((type.length >= i + 1 && type[i] != null) ? "," : "");
        		}
        	}
        	this.mc.fontRenderer.drawString(name, x + 5 + a * 2, y + 7, -1);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
    		GL11.glPopMatrix();
    	}
    	else
    	{
    		type = new TechPointTypes[5];
    		points = new int[5];
    	}
    }

}
