package nedelosk.nedeloskcore.client.gui;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.GL11;

import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.api.plan.PlanRecipe;
import nedelosk.nedeloskcore.client.gui.button.GuiButtonBlueprint;
import nedelosk.nedeloskcore.client.gui.button.GuiButtonBlueprintBack;
import nedelosk.nedeloskcore.client.gui.button.GuiButtonBlueprintPage;
import nedelosk.nedeloskcore.common.core.registry.NCItems;
import nedelosk.nedeloskcore.common.network.handler.PacketHandler;
import nedelosk.nedeloskcore.common.network.packets.PacketBlueprint;
import nedelosk.nedeloskcore.common.plan.PlanRecipeManager;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBlueprint extends GuiScreen {
	
	private List listRecipes;
	protected int page;
	protected int pages;
	protected GuiBlueprint parent;
	protected int left;
	protected int top;
    protected int xSize = 190;
    protected int ySize = 104;
    protected EntityPlayer player;
    
    protected GuiButton pageLeft, pageRight, pageBack;
	
	public GuiBlueprint(List listRecipes, EntityPlayer player) {
		this.pages = 1 + (listRecipes.size() / 32);
		this.listRecipes = listRecipes;
		this.player = player;
	}
	
	public GuiBlueprint(List listRecipes, EntityPlayer player, GuiBlueprint parent) {
		this.listRecipes = listRecipes;
		this.parent = parent;
		this.pages = 1 + (listRecipes.size() / 32);
		this.player = player;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
        this.left = (this.width - this.xSize) / 2;
        this.top = (this.height - this.ySize) / 2;
		
        int id = 0;
        
		for(int i = 0 ;i < 4;i++)
		{
			for(int r = 0;r < 8;r++)
			{
				buttonList.add(new GuiButtonBlueprint(id, left + 16 + r * 18, top + 22 + i * 18, this));
				id++;
			}
		}
		
		buttonList.add(pageLeft = new GuiButtonBlueprintPage(32, left, top + ySize - 7, false));
		buttonList.add(pageRight = new GuiButtonBlueprintPage(33, left + xSize - 18, top + ySize - 7, true));
		buttonList.add(pageBack = new GuiButtonBlueprintBack(34, left + xSize / 2 - 8, top + ySize + 2));
		
		updateIndex();
		updateButton();
		
	}
	
	@Override
	protected void keyTyped(char c, int p_73869_2_) {
		super.keyTyped(c, p_73869_2_);
		for(int i= 0;i < 10;i++)
		{
		if(c == Integer.toString(i).toCharArray()[0])
		{
			actionPerformed((GuiButton)buttonList.get(i));
		}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(button instanceof GuiButtonBlueprint)
		{
			if(((GuiButtonBlueprint)button).object instanceof Block)
			{
				mc.displayGuiScreen(new GuiBlueprint(PlanRecipeManager.getRecipe((Block)((GuiButtonBlueprint)button).object), player, this));
			}
			else if(((GuiButtonBlueprint)button).object instanceof PlanRecipe)
			{
				player.inventory.mainInventory[player.inventory.currentItem] = NCoreApi.setItemPlan(NCItems.Plan.item(), (PlanRecipe)((GuiButtonBlueprint)button).object);
				closeGui();
				PacketHandler.INSTANCE.sendToServer(new PacketBlueprint(player.inventory.mainInventory[player.inventory.currentItem]));
			}
		}
		if(button.id == 32)
		{
		page--;
		updateButton();
		}
		if(button.id == 33)
		{
		page++;
		updateButton();
		}
		if(button.id == 34)
		{
		mc.displayGuiScreen(parent);
		}
	}
	
	public void updateIndex()
	{
		
		for(int i = page * 32; i < (page + 1) * 32; i++) {
			GuiButtonBlueprint button = (GuiButtonBlueprint) buttonList.get(i - page * 32);
			Object object = i >= listRecipes.size() ? null : listRecipes.get(i);
			if(object != null) {
				button.object = object;
			} 

		}
	}
	
	
	public void updateButton()
	{
		pageLeft.enabled = page != 0;
		pageRight.enabled = page + 1  != pages;
		pageBack.enabled = parent != null;
	}
	
	public void closeGui()
	{
        this.mc.displayGuiScreen((GuiScreen)null);
        this.mc.setIngameFocus();
	}
	
	@Override
	public void drawScreen(int arg0, int arg1, float arg2) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("nedeloskcore", "textures/gui/blueprint.png"));
        this.drawTexturedModalRect(left, top, 0, 0, 190, 104);
        
        
        String display = "Blueprint";
        
        RenderUtils.glDrawScaledString(fontRendererObj, display, left + 75, top + 10, 1, Color.WHITE.getRGB());
        
		super.drawScreen(arg0, arg1, arg2);
	}
	
}
