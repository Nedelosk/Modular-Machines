package nedelosk.modularmachines.client.techtree.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import nedelosk.modularmachines.api.basic.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.basic.techtree.TechTreeEntry;
import nedelosk.modularmachines.api.basic.techtree.TechTreePage;
import nedelosk.modularmachines.api.basic.techtree.TechTreePage.PageType;
import nedelosk.modularmachines.client.techtree.gui.gadgets.GuiButtonSlot;
import nedelosk.modularmachines.client.techtree.gui.gadgets.GuiTab;
import nedelosk.modularmachines.client.techtree.gui.gadgets.GuiTextBox;
import nedelosk.modularmachines.client.techtree.utils.TechTreeUtils.Reader;
import nedelosk.modularmachines.client.techtree.utils.TechTreeUtils.Writer;
import nedelosk.modularmachines.client.techtree.utils.language.LanguageManager;
import nedelosk.modularmachines.client.techtree.utils.language.LanguageManager.TechTreeEntryLanguage;
import nedelosk.modularmachines.client.techtree.utils.language.TechTreeEntryLanguageData;
import nedelosk.modularmachines.client.techtree.utils.language.editor.TechTreeEntryEditorLanguageData;
import nedelosk.modularmachines.client.techtree.utils.TechTreeEditorData;
import nedelosk.modularmachines.client.techtree.utils.TechTreeEditorUtils.TechTreeEditorLanguage;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.config.TechTreeConfigs;
import nedelosk.modularmachines.common.inventory.InventoryCraftingEditor;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.techtree.editor.PacketTechTreeEditor;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiTechTreeEditor extends GuiScreen {
	
	protected enum Mode{
		DEFAULT, PAGES, CATEGORY
	}
	
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(TechTreeEntryEditorLanguageData.class,new TechTreeEntryLanguage.Serializer()).registerTypeAdapter(TechTreeEntryEditorLanguageData.class, new TechTreeEditorLanguage.Deserializer()).create();
	protected GuiEditorBase editorBase;
	private int guiWidth = 256;
	private int guiHeight = 166;
	private int left;
	private int top;
	private ArrayList<TechTreePage> entryPages = new ArrayList<TechTreePage>();
	private int page = 0;
	
	public TechTreeEditorData data = new TechTreeEditorData();
	public EditorUtils utils = new EditorUtils();
	public Editor editor = new Editor();
	
	@Override
	public void initGui() {
		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;
		
		utils.initGui();
		checkButtons();
		
		if(editorBase != null){
			editorBase.left = left;
			editorBase.top = top;
			editorBase.initGui();
		}
		utils.switchMode(utils.mode);
	}
    
    @Override
    public void drawScreen(int mX, int mY, float p_73863_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		RenderUtils.bindTexture(new ResourceLocation("modularmachines", "textures/gui/gui_techtree_editor.png"));	    
	    drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
	    if(editorBase == null)
	    {	
	    	utils.drawScreen();
	    	if(utils.mode == Mode.PAGES){
				String type = StatCollector.translateToLocal("techtree.editor.page.type." + entryPages.get(page).type.name().toLowerCase(Locale.ENGLISH));
				fontRendererObj.drawString(type, left + 128 - (fontRendererObj.getStringWidth(type) / 2), top + 5, 0x404040);
		        for (int k = 0; k < utils.craftingSlots.size(); ++k)
		        {
		            utils.craftingSlots.get(k).drawButton(this.mc, mX, mY);
		        }
	    	}
	    }else{
			for(int i = 0;i < utils.craftingSlots.size();i++)
				utils.craftingSlots.get(i).visible = false;
	    	editorBase.drawScreen(mX, mY, p_73863_3_);
	    }
    	super.drawScreen(mX, mY, p_73863_3_);
    }
	
    @Override
	protected void keyTyped(char key, int p_73869_2_)
    {
    	if(editorBase != null){
    		editorBase.keyTyped(key, p_73869_2_);
    	}else{
    		utils.keyTyped(key, p_73869_2_);
    	}
    }

    @Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        if(editorBase != null){
        	editorBase.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        }else{
        	utils.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        }
        for (int l = 0; l < utils.craftingSlots.size(); ++l)
        {
            GuiButtonSlot guibutton = utils.craftingSlots.get(l);

            if (guibutton.mousePressed(this.mc, p_73864_1_, p_73864_2_))
            {
                guibutton.func_146113_a(this.mc.getSoundHandler());
                this.actionPerformedCrafting(guibutton);
            }
        }
        
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
    private void switchPage(){
    	
    	if(!(entryPages.size() > page) || entryPages.get(page) == null){
    		entryPages.add(new TechTreePage(page));
    	}
		if(data.languagData.get(data.languag) == null)
			data.languagData.put(data.languag, new TechTreeEntryLanguageData());
		if(data.languagData.get(data.languag).pages == null)
			data.languagData.get(data.languag).pages = new String[16];
		if(data.languagData.get(data.languag).pages[page] == null)
			data.languagData.get(data.languag).pages[page] = "";
    	if(entryPages.get(page).type == PageType.TEXT || entryPages.get(page).type == PageType.TEXT_CONCEALED || entryPages.get(page).type == PageType.IMAGE)
    		utils.fieldPageText.setText(data.languagData.get(data.languag) != null ? data.languagData.get(data.languag).pages[page] : "");
    	if(entryPages.get(page).type == PageType.TEXT_CONCEALED || entryPages.get(page).type == PageType.IMAGE)
    		utils.fieldPageOther.setText(entryPages.get(page).image == null ? entryPages.get(page).text = "" : entryPages.get(page).image.toString());
    	if(entryPages.get(page).type == PageType.TEXT){
			utils.fieldPageHeigh = 96;
			utils.updateField();
		}else if(entryPages.get(page).type == PageType.TEXT_CONCEALED || entryPages.get(page).type == PageType.IMAGE){
			utils.fieldPageHeigh = 68;
    		utils.updateField();
    	}else if(entryPages.get(page).type == PageType.NORMAL_CRAFTING){
    		ItemStack[] stackList = entryPages.get(page).recipeInputs;
    		utils.craftingSlots.get(0).stack = stackList[0];
    		utils.craftingSlots.get(1).stack = stackList[1];
    		utils.craftingSlots.get(2).stack = stackList[2];
    		utils.craftingSlots.get(3).stack = stackList[3];
    		utils.craftingSlots.get(4).stack = stackList[4];
    		utils.craftingSlots.get(5).stack = stackList[5];
    		utils.craftingSlots.get(6).stack = stackList[6];
    		utils.craftingSlots.get(7).stack = stackList[7];
    		utils.craftingSlots.get(8).stack = stackList[8];
    	}
    	else if(entryPages.get(page).type == PageType.SMELTING){
    		utils.craftingSlots.get(4).stack = (ItemStack) entryPages.get(page).recipe;
    	}
    }
    
    private void checkButtons(){
    	if(0 < page)
    		utils.buttonLeftPage.enabled = true;
    	else
    		utils.buttonLeftPage.enabled = false;
    	if(page < 16)
    		utils.buttonRightPage.enabled = true;
    	else
    		utils.buttonRightPage.enabled = false;
    }
    
    protected void actionPerformedCrafting(GuiButtonSlot button){
    	if(button.id == 0){
    		editorBase = new GuiEditorSelectItem(this, utils.craftingSlots.get(0).stack, left, top, "crafting_0");
    		editorBase.initGui();
    	}else if(button.id == 1){
    		editorBase = new GuiEditorSelectItem(this, utils.craftingSlots.get(1).stack, left, top, "crafting_1");
    		editorBase.initGui();
    	}else if(button.id == 2){
    		editorBase = new GuiEditorSelectItem(this, utils.craftingSlots.get(2).stack, left, top, "crafting_2");
    		editorBase.initGui();
    	}else if(button.id == 3){
    		editorBase = new GuiEditorSelectItem(this, utils.craftingSlots.get(3).stack, left, top, "crafting_3");
    		editorBase.initGui();
    	}else if(button.id == 4){
    		editorBase = new GuiEditorSelectItem(this, utils.craftingSlots.get(4).stack, left, top, "crafting_4");
    		editorBase.initGui();
    	}else if(button.id == 5){
    		editorBase = new GuiEditorSelectItem(this, utils.craftingSlots.get(5).stack, left, top, "crafting_5");
    		editorBase.initGui();
    	}else if(button.id == 6){
    		editorBase = new GuiEditorSelectItem(this, utils.craftingSlots.get(6).stack, left, top, "crafting_6");
    		editorBase.initGui();
    	}else if(button.id == 7){
    		editorBase = new GuiEditorSelectItem(this, utils.craftingSlots.get(7).stack, left, top, "crafting_7");
    		editorBase.initGui();
    	}else if(button.id == 8){
    		editorBase = new GuiEditorSelectItem(this, utils.craftingSlots.get(8).stack, left, top, "crafting_8");
    		editorBase.initGui();
    	}
    }
	
	@Override
	protected void actionPerformed(GuiButton button) {
		savePages();
		if(editorBase != null)
			editorBase.actionPerformed(button);
		else{
			if(button.id == 0){
	            this.mc.displayGuiScreen((GuiScreen)null);
	            this.mc.setIngameFocus();
			}else if(button.id == 1){
				editor.saveEntry();
			}else if(button.id == 3){
				editor.loadEntry();
			}else if(button.id == 4){
				button.enabled = false;
				utils.mode = Mode.DEFAULT;
				utils.switchMode(utils.mode);
				for(GuiButton buttonL : (ArrayList<GuiButton>)buttonList){
					if(buttonL.id == 5){
						buttonL.enabled = true;
						continue;
					}else if(buttonL.id == 6){
						buttonL.enabled = true;
						break;
					}
				}
			}else if(button.id == 5){
				button.enabled = false;
				utils.mode = Mode.PAGES;
				switchPage();
				utils.switchMode(utils.mode);
				for(GuiButton buttonL : (ArrayList<GuiButton>)buttonList){
					if(buttonL.id == 4){
						buttonL.enabled = true;
						continue;
					}else if(buttonL.id == 6){
						buttonL.enabled = true;
						break;
					}
				}
			}else if(button.id == 6){
				button.enabled = false;
				utils.mode = Mode.CATEGORY;
				utils.switchMode(utils.mode);
				for(GuiButton buttonL : (ArrayList<GuiButton>)buttonList){
					if(buttonL.id == 4){
						buttonL.enabled = true;
						continue;
					}else if(buttonL.id == 5){
						buttonL.enabled = true;
						break;
					}
				}
			}else if(button.id == 7){
				page--;
				switchPage();
				checkButtons();
			}else if(button.id == 8){
				page++;
				switchPage();
				checkButtons();
			}else if(button.id == 9){
				entryPages.get(page).type = entryPages.get(page).type.nextType();
		    	if(entryPages.get(page).type == PageType.TEXT){
		    		utils.fieldPageHeigh = 96;
					utils.updateField();
				}
		    	else if(entryPages.get(page).type == PageType.TEXT_CONCEALED || entryPages.get(page).type == PageType.IMAGE){
		    		utils.fieldPageHeigh = 68;
		    		utils.updateField();
		    	}
		    	utils.updatePageButtons();
		    	utils.switchMode(utils.mode);
			}else if(button.id == 10){
				data.pointType = data.pointType.nextType();
				utils.buttonTechPoint.displayString = StatCollector.translateToLocal("mm.techpoint." + data.pointType.name());
			}else if(button.id == 11){
				if(!data.isAutoUnlock){
					data.isAutoUnlock = true;
					utils.buttonAutoUnlock.displayString = StatCollector.translateToLocal("techtree.editor.autounlock");
				}
				else{
					data.isAutoUnlock = false;
					utils.buttonAutoUnlock.displayString = StatCollector.translateToLocal("techtree.editor.not.autounlock");
				}
			}else if(button.id == 12){
				if(!data.isConcealed){
					data.isConcealed = true;
					utils.buttonConcealed.displayString = StatCollector.translateToLocal("techtree.editor.concealed");
				}
				else{
					data.isConcealed = false;
					utils.buttonConcealed.displayString = StatCollector.translateToLocal("techtree.editor.not.concealed");
				}
			}else if(button.id == 13){
				editorBase = new GuiEditorSelectItem(this, data.icon_item, left, top, "icon");
				editorBase.initGui();
				utils.switchMode(utils.mode);
			}else if(button.id == 14){
				if(TechTreeConfigs.activeLanguages.size() > 0){
					ArrayList<String> languagList = new ArrayList<String>();
					for(Map.Entry<String, Boolean> entry : TechTreeConfigs.activeLanguages.entrySet())
						if(entry.getValue())
							languagList.add(entry.getKey());
					if(languagList.size() == languagList.indexOf(data.languag) + 1)
						data.languag = languagList.get(0);
					else
						data.languag = languagList.get(languagList.indexOf(data.languag) + 1);
					utils.buttonSwitchLanguage.displayString = data.languag;
					switchPage();
				}
			}
		}
	}
    
    public void savePages(){
    	if(utils.mode == Mode.PAGES){
			if(entryPages.get(page).type == PageType.TEXT || entryPages.get(page).type == PageType.TEXT_CONCEALED || entryPages.get(page).type == PageType.IMAGE || entryPages.get(page).type == PageType.NORMAL_CRAFTING || entryPages.get(page).type == PageType.SMELTING){
				if(data.languagData.get(data.languag) == null)
					data.languagData.put(data.languag, new TechTreeEntryLanguageData());
	    		if(data.languagData.get(data.languag).pages == null)
	    			data.languagData.get(data.languag).pages = new String[16];
	    		data.languagData.get(data.languag).pages[page] = utils.fieldPageText.getText();
				if(entryPages.get(page).type == PageType.IMAGE){
					entryPages.get(page).image = new ResourceLocation(utils.fieldPageOther.getText());
				}else if(entryPages.get(page).type == PageType.TEXT_CONCEALED){
					entryPages.get(page).entry = utils.fieldPageOther.getText();
				}else if(entryPages.get(page).type == PageType.NORMAL_CRAFTING){
					ItemStack[] stackList = new ItemStack[9];
					for (int k = 0; k < utils.craftingSlots.size(); ++k)
			        {
						stackList[k] = utils.craftingSlots.get(k).stack;
			        }
					InventoryCrafting crafting = new InventoryCraftingEditor(stackList);
			        IRecipe recipe = null;
			        for(IRecipe recipeT : (ArrayList<IRecipe>) CraftingManager.getInstance().getRecipeList()){
			        	if(recipeT.matches(crafting, mc.theWorld))
			        		recipe = recipeT;
			        }
			        entryPages.get(page).recipe = recipe;
			        if(recipe != null)
			        	entryPages.get(page).recipeOutput = recipe.getRecipeOutput();
			        entryPages.get(page).recipeInputs = stackList;
				}else if(entryPages.get(page).type == PageType.SMELTING){
					entryPages.get(page).recipe = utils.craftingSlots.get(4).stack;
				}
			}
			
		}
    }
    
    public void loadCatagory(){
    	
    }
    
    public void saveCatagory(){
    	
    }
    
    public List getButtonList(){
    	return buttonList;
    }
    
    public void setItem(ItemStack stack, String modifier){
    	if(modifier.equals("icon")){
    		data.icon_item = stack;
    	}else if(modifier.equals("crafting_0")){
    		utils.craftingSlots.get(0).stack = stack;
    	}else if(modifier.equals("crafting_1")){
    		utils.craftingSlots.get(1).stack = stack;
    	}else if(modifier.equals("crafting_2")){
    		utils.craftingSlots.get(2).stack = stack;
    	}else if(modifier.equals("crafting_3")){
    		utils.craftingSlots.get(3).stack = stack;
    	}else if(modifier.equals("crafting_4")){
    		utils.craftingSlots.get(4).stack = stack;
    	}else if(modifier.equals("crafting_5")){
    		utils.craftingSlots.get(5).stack = stack;
    	}else if(modifier.equals("crafting_6")){
    		utils.craftingSlots.get(6).stack = stack;
    	}else if(modifier.equals("crafting_7")){
    		utils.craftingSlots.get(7).stack = stack;
    	}else if(modifier.equals("crafting_8")){
    		utils.craftingSlots.get(8).stack = stack;
    	}
    }
    
    public boolean inBounds(int x, int y, int w, int h, int mX, int mY)
    {
      return (x <= mX) && (mX <= x + w) && (y <= mY) && (mY <= y + h);
    }
    
    public void drawItemStack(ItemStack stack, int x, int y)
    {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (stack != null) font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = Minecraft.getMinecraft().fontRenderer;
        itemRender.renderItemAndEffectIntoGUI(font, Minecraft.getMinecraft().getTextureManager(), stack, x, y);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
	    RenderHelper.disableStandardItemLighting();
	    GL11.glEnable(GL11.GL_BLEND);
    }
    
    @Override
	protected void drawGradientRect(int p_73733_1_, int p_73733_2_, int p_73733_3_, int p_73733_4_, int p_73733_5_, int p_73733_6_){
    	drawGradientRect(p_73733_1_, p_73733_2_, p_73733_3_, p_73733_4_, p_73733_5_, p_73733_6_);
    }
    
    public class EditorUtils{
    	public GuiTextField fieldKey, fieldCategory, fieldTechPoints, fieldParents, fieldSiblings, fieldColumn, fieldRow, fieldCIcon, fieldCBackground, fieldCKey, fieldCKeyLanguag, fieldPageOther = null;
    	private GuiButton buttonClose, buttonSave, buttonDelete, buttonLaodFile, buttonLeftPage, buttonRightPage, buttonSwitchType, buttonTechPoint, buttonAutoUnlock, buttonConcealed, buttonIcon, buttonSwitchLanguage;
    	public GuiTextBox fieldPageText = null;
    	public int fieldPageWidth = 244;
    	public int fieldPageHeigh = 68;
    	public Mode mode = Mode.DEFAULT;
    	public List<GuiButtonSlot> craftingSlots = new ArrayList<GuiButtonSlot>();
    	
    	public void drawScreen(){
    		if(mode == Mode.DEFAULT){
    			String key = StatCollector.translateToLocal("techtree.editor.key");
	    		fontRendererObj.drawString(key, left + 50 - (fontRendererObj.getStringWidth(key) / 2), top + 5, 0x404040);
	    		fieldKey.drawTextBox();
	    		String text = StatCollector.translateToLocal("techtree.editor.category");
	    		fontRendererObj.drawString(text, left + 50 - (fontRendererObj.getStringWidth(text) / 2), top + 35, 0x404040);
	    		fieldCategory.drawTextBox();
	    		String type = StatCollector.translateToLocal("techtree.editor.type");
	    		fontRendererObj.drawString(type, left + 50 - (fontRendererObj.getStringWidth(type) / 2), top + 65, 0x404040);
	    		fieldTechPoints.drawTextBox();
	    		String parents = StatCollector.translateToLocal("techtree.editor.parents");
	    		fontRendererObj.drawString(parents, left + 188 - (fontRendererObj.getStringWidth(parents) / 2), top + 5, 0x404040);
	    		fieldParents.drawTextBox();
	    		String siblings = StatCollector.translateToLocal("techtree.editor.siblings");
	    		fontRendererObj.drawString(siblings, left + 188 - (fontRendererObj.getStringWidth(siblings) / 2), top + 35, 0x404040);
	    		fieldSiblings.drawTextBox();
	    		String columnAndRow = StatCollector.translateToLocal("techtree.editor.culumnandrow");
	    		fontRendererObj.drawString(columnAndRow, left + 188 - (fontRendererObj.getStringWidth(columnAndRow) / 2), top + 65, 0x404040);
	    		fieldColumn.drawTextBox();
	    		fieldRow.drawTextBox();
    		}else if(mode == Mode.PAGES){
    	    	if(entryPages.get(page).type == PageType.TEXT || entryPages.get(page).type == PageType.TEXT_CONCEALED || entryPages.get(page).type == PageType.IMAGE){
    	    		fieldPageText.drawTextBox();
    	    	}
    	    	if(entryPages.get(page).type == PageType.TEXT_CONCEALED || entryPages.get(page).type == PageType.IMAGE){
    	    		String title = "";
    	    		if(entryPages.get(page).type == PageType.IMAGE)
    	    			title = StatCollector.translateToLocal("techtree.editor.page.image");
    	    		else if(entryPages.get(page).type == PageType.TEXT_CONCEALED)
    	    			title = StatCollector.translateToLocal("techtree.editor.page.text_concealed");
    	    		fontRendererObj.drawString(title, left + 128 - (fontRendererObj.getStringWidth(title) / 2), top + 86, 0x404040);
    	    		fieldPageOther.drawTextBox();
    	    	}
    		}
    	}
    	
    	public void initGui(){
    		String key = "";
    		if(fieldKey != null)
    			key = fieldKey.getText();
    		fieldKey = new GuiTextField(mc.fontRenderer, left + 6, top + 15, 90, 15){
    			@Override
    			public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_) {
    				super.mouseClicked(p_146192_1_, p_146192_2_, p_146192_3_);
    				
    				if(!isFocused()){
    					for(TechTreePage page : entryPages){
    						page.text = "mm.techtree_page." + fieldKey.getText() + "." + page.ID;
    					}
    				}
    			}
    		};
    		utils.fieldKey.setMaxStringLength(50);
    		fieldKey.setText(key);
    		String category = "";
    		if(fieldCategory != null)
    			category = fieldCategory.getText();
    		fieldCategory = new GuiTextField(mc.fontRenderer, left + 6, top + 45, 90, 15);
    		fieldCategory.setText(category);
    		String parents = "";
    		if(fieldParents != null)
    			parents = fieldParents.getText();
    		fieldParents = new GuiTextField(mc.fontRenderer, left + 123, top + 15, 125, 15);
    		fieldParents.setMaxStringLength(150);
    		fieldParents.setText(parents);
    		String siblings = "";
    		if(fieldSiblings != null)
    			siblings = fieldSiblings.getText();
    		fieldSiblings = new GuiTextField(mc.fontRenderer, left + 123, top + 45, 125, 15);
    		fieldSiblings.setMaxStringLength(150);
    		fieldSiblings.setText(siblings);
    		String column = "";
    		if(fieldColumn != null)
    			column = fieldColumn.getText();
    		fieldColumn = new GuiTextField(mc.fontRenderer, left + 143, top + 75, 30, 15);
    		fieldColumn.setText(column);
    		String row = "";
    		if(fieldRow != null)
    			row = fieldRow.getText();
    		fieldRow = new GuiTextField(mc.fontRenderer, left + 193, top + 75, 30, 15);
    		fieldRow.setText(row);
    		String techPoints = "";
    		if(fieldTechPoints != null)
    			techPoints = fieldTechPoints.getText();
    		fieldTechPoints = new GuiTextField(mc.fontRenderer, left + 6, top + 75, 90, 15);
    		fieldTechPoints.setText(techPoints);
    		String page = "";
    		if(fieldPageText != null)
    			page = fieldPageText.getText();
    		fieldPageText = new GuiTextBox(mc.fontRenderer, left + 6, top + 15, fieldPageWidth, fieldPageHeigh);
    		fieldPageText.setMaxStringLength(500);
    		fieldPageText.setText(page);
    		String other = "";
    		if(fieldPageOther != null)
    			other = fieldPageOther.getText();
    		fieldPageOther = new GuiTextField(mc.fontRenderer, left + 6, top + 96, 244, 15);
    		fieldPageOther.setMaxStringLength(100);
    		fieldPageOther.setText(other);
    		
    		String cKey = "";
    		if(fieldCKey != null)
    			cKey = fieldCKey.getText();
    		fieldCKey = new GuiTextField(mc.fontRenderer, left + 6, top + 96, 244, 15);
    		fieldCKey.setMaxStringLength(50);
    		fieldCKey.setText(cKey);
    		
    		String cIcon = "";
    		if(fieldCIcon != null)
    			cIcon = fieldCIcon.getText();
    		fieldCIcon = new GuiTextField(mc.fontRenderer, left + 6, top + 96, 244, 15);
    		fieldCIcon.setMaxStringLength(100);
    		fieldCIcon.setText(cIcon);
    		
    		String cBackground = "";
    		if(fieldCBackground != null)
    			cBackground = fieldCBackground.getText();
    		fieldCBackground = new GuiTextField(mc.fontRenderer, left + 6, top + 96, 244, 15);
    		fieldCBackground.setMaxStringLength(100);
    		fieldCBackground.setText(cBackground);
    		
    		buttonList.add(buttonClose = new GuiButton(0, left + 7, top + 141, 40, 20, StatCollector.translateToLocal("techtree.editor.close")));
    		buttonList.add(buttonSave = new GuiButton(1, left + 61, top + 141, 40, 20, StatCollector.translateToLocal("techtree.editor.save")));
    		buttonList.add(buttonDelete = new GuiButton(2, left + 114, top + 141, 54, 20, StatCollector.translateToLocal("techtree.editor.delete")));
    		buttonList.add(buttonLaodFile = new GuiButton(3, left + 184, top + 141, 54, 20, StatCollector.translateToLocal("techtree.editor.load")));
    		buttonList.add(new GuiTab(4, left + 4, top - 20, 60, 20, StatCollector.translateToLocal("techtree.editor.default"), !(mode == Mode.DEFAULT)));
    		buttonList.add(new GuiTab(5, left + 66, top - 20, 60, 20, StatCollector.translateToLocal("techtree.editor.pages"), !(mode == Mode.PAGES)));
    		buttonList.add(new GuiTab(6, left + 128, top - 20, 60, 20, StatCollector.translateToLocal("techtree.editor.category"), !(mode == Mode.CATEGORY)));
    		buttonList.add(buttonLeftPage = new GuiButton(7, left + 7, top + 115, 80, 20, StatCollector.translateToLocal("techtree.editor.prevPage")));
    		buttonList.add(buttonRightPage = new GuiButton(8, left + 189, top + 115, 58, 20, StatCollector.translateToLocal("techtree.editor.nextPage")));
    		buttonList.add(buttonSwitchType = new GuiButton(9, left + 92, top + 115, 92, 20, StatCollector.translateToLocal("techtree.editor.switchPageType")));
    		buttonList.add(buttonTechPoint = new GuiButton(10, left + 6, top + 95, 95, 20, StatCollector.translateToLocal("mm.techpoint." + data.pointType.name())));
    		buttonList.add(buttonAutoUnlock = new GuiButton(11, left + 144, top + 117, 95, 20, StatCollector.translateToLocal("techtree.editor.not.autounlock")));
    		buttonList.add(buttonConcealed = new GuiButton(12, left + 144, top + 95, 95, 20, StatCollector.translateToLocal("techtree.editor.not.concealed")));
    		buttonList.add(buttonIcon = new GuiButton(13, left + 6, top + 117, 95, 20, StatCollector.translateToLocal("techtree.editor.icon")));
    		buttonList.add(buttonSwitchLanguage = new GuiButton(14, left + 184, top + 141, 54, 20, data.languag));
    		
    		updateSlot(new GuiButtonSlot(0, left + 101, top + 44, GuiTechTreeEditor.this));
    		updateSlot(new GuiButtonSlot(1, left + 119, top + 44, GuiTechTreeEditor.this));
    		updateSlot(new GuiButtonSlot(2, left + 137, top + 44, GuiTechTreeEditor.this));
    		updateSlot(new GuiButtonSlot(3, left + 101, top + 62, GuiTechTreeEditor.this));
    		updateSlot(new GuiButtonSlot(4, left + 119, top + 62, GuiTechTreeEditor.this));
    		updateSlot(new GuiButtonSlot(5, left + 137, top + 62, GuiTechTreeEditor.this));
    		updateSlot(new GuiButtonSlot(6, left + 101, top + 80, GuiTechTreeEditor.this));
    		updateSlot(new GuiButtonSlot(7, left + 119, top + 80, GuiTechTreeEditor.this));
    		updateSlot(new GuiButtonSlot(8, left + 137, top + 80, GuiTechTreeEditor.this));
    	}
    	
    	public void updateSlot(GuiButtonSlot slot){
    		ItemStack crafting = null;
    		if(craftingSlots.size() > slot.id && craftingSlots.get(slot.id) != null){
    			crafting = craftingSlots.get(slot.id).stack;
    			craftingSlots.remove(slot.id);
    		}
    		craftingSlots.add(slot.id, slot);
    		craftingSlots.get(slot.id).stack = crafting;
    	}
    	
    	private void updateField(){
    		String page = "";
    		if(fieldPageText != null)
    			page = fieldPageText.getText();
    		fieldPageText = new GuiTextBox(mc.fontRenderer, left + 6, top + 15, fieldPageWidth, fieldPageHeigh);
    		fieldPageText.setText(page);
    	}
    	
    	private void updatePageButtons(){
    		fieldPageText = new GuiTextBox(mc.fontRenderer, left + 6, top + 15, fieldPageWidth, fieldPageHeigh);
    		fieldPageOther = new GuiTextField(mc.fontRenderer, left + 6, top + 96, 244, 15);
    		for(int i = 0;i < craftingSlots.size();i++){
    			GuiButtonSlot slot = craftingSlots.get(i);
    			craftingSlots.set(i, new GuiButtonSlot(slot.id, slot.xPosition, slot.yPosition, GuiTechTreeEditor.this));
    		}
    	}
    	
    	private void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
        {
      		if(mode == Mode.DEFAULT){
              	fieldKey.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
              	fieldCategory.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
              	fieldTechPoints.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
              	fieldParents.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
              	fieldSiblings.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
              	fieldColumn.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
              	fieldRow.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
            }else if(mode == Mode.PAGES){
              	fieldPageText.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
              	if(entryPages.get(page).type == PageType.TEXT_CONCEALED || entryPages.get(page).type == PageType.IMAGE)
              		fieldPageOther.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
            }
       }
      	
    	private void keyTyped(char key, int p_73869_2_)
        {
        	if(mode == Mode.DEFAULT){
        		fieldKey.textboxKeyTyped(key, p_73869_2_);
        		fieldCategory.textboxKeyTyped(key, p_73869_2_);
        		fieldTechPoints.textboxKeyTyped(key, p_73869_2_);
        		fieldParents.textboxKeyTyped(key, p_73869_2_);
        		fieldSiblings.textboxKeyTyped(key, p_73869_2_);
        		fieldColumn.textboxKeyTyped(key, p_73869_2_);
        		fieldRow.textboxKeyTyped(key, p_73869_2_);
        	}else if(mode == Mode.PAGES){
        		fieldPageText.textboxKeyTyped(key, p_73869_2_);
        		if(entryPages.get(page).type == PageType.TEXT_CONCEALED || entryPages.get(page).type == PageType.IMAGE)
        			fieldPageOther.textboxKeyTyped(key, p_73869_2_);
        	}
        }
    	
    	public void switchMode(Mode mode){
    	    if(editorBase == null){
    		    if(mode == Mode.DEFAULT){
    		    	buttonLeftPage.visible = false;
    				buttonRightPage.visible = false;
    				buttonSwitchType.visible = false;
    				buttonAutoUnlock.visible = true;
    				buttonConcealed.visible = true;
    				buttonTechPoint.visible = true;
    				buttonIcon.visible = true;
    				buttonClose.visible = true;
    				buttonSave.visible = true;
    				buttonDelete.visible = true;
    				buttonLaodFile.visible = true;
    				buttonSwitchLanguage.visible = false;
    				for(int i = 0;i < craftingSlots.size();i++)
    					craftingSlots.get(i).visible = false;
    		    }else if(mode == Mode.PAGES){
    		    	buttonLeftPage.visible = true;
    				buttonRightPage.visible = true;
    				buttonSwitchType.visible = true;
    				buttonTechPoint.visible = false;
    				buttonAutoUnlock.visible = false;
    				buttonConcealed.visible = false;
    				buttonIcon.visible = false;
    				buttonClose.visible = true;
    				buttonSave.visible = false;
    				buttonDelete.visible = false;
    				buttonLaodFile.visible = false;
    				buttonSwitchLanguage.visible = true;
    				if(entryPages.get(page).type == PageType.NORMAL_CRAFTING)
    					for(int i = 0;i < craftingSlots.size();i++)
    						craftingSlots.get(i).visible = true;
    				else if(entryPages.get(page).type == PageType.SMELTING){
    					for(int i = 0;i < craftingSlots.size();i++)
    						if(i == 4)
    							craftingSlots.get(4).visible = true;
    						else
    							craftingSlots.get(i).visible = false;
    				}else{
    					for(int i = 0;i < craftingSlots.size();i++)
    						craftingSlots.get(i).visible = false;
    				}
    		    }else if(mode == Mode.CATEGORY){
    		    	buttonLeftPage.visible = false;
    				buttonRightPage.visible = false;
    				buttonSwitchType.visible = false;
    				buttonAutoUnlock.visible = false;
    				buttonConcealed.visible = false;
    				buttonTechPoint.visible = false;
    				buttonIcon.visible = false;
    				buttonClose.visible = true;
    				buttonSwitchLanguage.visible = true;
    		    }
    	    }
    	    else
    	    {
    	    	buttonLeftPage.visible = false;
    			buttonRightPage.visible = false;
    			buttonSwitchType.visible = false;
    			buttonAutoUnlock.visible = false;
    			buttonConcealed.visible = false;
    			buttonTechPoint.visible = false;
    			buttonIcon.visible = false;
    			buttonClose.visible = false;
    			buttonSave.visible = false;
    			buttonDelete.visible = false;
    			buttonLaodFile.visible = false;
    			buttonSwitchLanguage.visible = false;
    			for(int i = 0;i < craftingSlots.size();i++)
    				craftingSlots.get(i).visible = false;
    		}
    	}
    }

	public class Editor{
		
	    public void saveEntry(){
			try{
			TechTreeEntry entry = new TechTreeEntry(utils.fieldKey.getText(), utils.fieldCategory.getText(), Integer.parseInt(utils.fieldTechPoints.getText()), data.pointType, Integer.parseInt(utils.fieldColumn.getText()), Integer.parseInt(utils.fieldRow.getText()), data.icon_item);
			if(!utils.fieldSiblings.getText().equals("")){
			String[] parents = utils.fieldParents.getText().split(",");
			entry.setParents(parents);
			}
			if(!utils.fieldSiblings.getText().equals("")){
				String[] siblings = utils.fieldSiblings.getText().split(",");
				entry.setSiblings(siblings);
			}
			if(entryPages.size() > 0)
				entry.setPages(entryPages.toArray(new TechTreePage[entryPages.size()]));
			if(data.isAutoUnlock)
				entry.setAutoUnlock();
			if(data.isConcealed)
				entry.setConcealed();
			File categoryFile = new File(ModularMachines.configFolder + "/techtree/categorys", utils.fieldCategory.getText());
			Writer.writeEntry(entry, categoryFile);
			TechTreeCategories.addEntry(entry);
			PacketHandler.INSTANCE.sendToServer(new PacketTechTreeEditor(entry));
			File entryFile = new File(categoryFile, entry.key.toLowerCase(Locale.ENGLISH) + "_language.json");
			int pagesSize = 0;
			for(TechTreePage page : entryPages)
				if(page != null)
					pagesSize++;
			String[] pages = new String[pagesSize];
			for(int i = 0; i< pagesSize;i++)
				pages[i] = "mm.techtree_page." + entry.key + "." + i;
			BufferedWriter writerEntry = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(entryFile)));
			writerEntry.write(GSON.toJson(new TechTreeEntryEditorLanguageData(new TechTreeEntryLanguageData("mm.techtree_entry_name." + entry.key, "mm.techtree_entry_text."+ entry.key, pages), data.languagData)));
			writerEntry.close();
			LanguageManager.getInstance().readEntryLanguageData(entry, categoryFile);
			}catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    public void loadEntry(){
			try {
				File entryLanguageFile = new File(ModularMachines.configFolder + "/techtree/categorys/" + utils.fieldCategory.getText(), utils.fieldKey.getText().toLowerCase() + "_language.json");
				JsonReader reader = new JsonReader(new FileReader(entryLanguageFile));
				TechTreeEntryEditorLanguageData data = GSON.fromJson(reader, TechTreeEntryEditorLanguageData.class);
				reader.close();
				GuiTechTreeEditor.this.data.languagData = data.languageData;
				File entryFile = new File(ModularMachines.configFolder + "/techtree/categorys/" + utils.fieldCategory.getText(), utils.fieldKey.getText().toLowerCase() + ".json");
				TechTreeEntry entry = Reader.readEntry(entryFile);
				if(entry != null){
					utils.fieldColumn.setText(Integer.toString(entry.displayColumn));
					utils.fieldRow.setText(Integer.toString(entry.displayRow));
					if(entry.parents != null){
						String[] parentsE = entry.parents;
						StringBuilder parents = new StringBuilder();
						for(int i = 0; i < parentsE.length;i++){
							String pE = parentsE[i];
							parents.append(pE + ((i + 1 == parentsE.length) ? "" : ", "));
						}
						utils.fieldParents.setText(parents.toString());
					}
					if(entry.siblings != null){
						String[] siblingsE = entry.siblings;
						StringBuilder siblings = new StringBuilder();
						for(String sE : siblingsE)
							siblings.append(sE);
						utils.fieldSiblings.setText(siblings.toString());
					}
					utils.fieldTechPoints.setText(Integer.toString(entry.getTechPoints()));
					GuiTechTreeEditor.this.data.pointType = entry.getTechPointType();
					utils.buttonTechPoint.displayString = StatCollector.translateToLocal("mm.techpoint." + GuiTechTreeEditor.this.data.pointType.name());
					GuiTechTreeEditor.this.data.isAutoUnlock = entry.isAutoUnlock();
					GuiTechTreeEditor.this.data.isConcealed = entry.isConcealed();
					if(GuiTechTreeEditor.this.data.isAutoUnlock)
						utils.buttonAutoUnlock.displayString = StatCollector.translateToLocal("techtree.editor.autounlock");
					else
						utils.buttonAutoUnlock.displayString = StatCollector.translateToLocal("techtree.editor.not.autounlock");
					if(GuiTechTreeEditor.this.data.isConcealed)
						utils.buttonAutoUnlock.displayString = StatCollector.translateToLocal("techtree.editor.concealed");
					else
						utils.buttonAutoUnlock.displayString = StatCollector.translateToLocal("techtree.editor.not.concealed");
					for(TechTreePage page : entry.getPages())
						entryPages.add(page.ID, page);
					if(entry.icon_item != null)
						GuiTechTreeEditor.this.data.icon_item = entry.icon_item;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
}
