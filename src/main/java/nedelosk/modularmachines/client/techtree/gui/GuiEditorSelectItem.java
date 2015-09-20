package nedelosk.modularmachines.client.techtree.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GuiEditorSelectItem extends GuiEditorBase {

	public final GuiItemSearchField search;
	public final String modifier;
	public ItemStack selected;
	
	public GuiEditorSelectItem(GuiTechTreeEditor parent, ItemStack selected, int left, int top, String modifier) {
		super(parent, left, top);
		
		IInventory inventory = mc.thePlayer.inventory;
		for(int i = 0;i < inventory.getSizeInventory();i++){
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack != null){
				stack = stack.copy();
				stack.stackSize = 1;
				inventoryItems.add(stack);
			}
		}
		search = new GuiItemSearchField(0, 0, 125, 12);
		this.selected = selected;
		this.modifier = modifier;
	}

	public ArrayList<ItemStack> inventoryItems = new ArrayList<ItemStack>();
	public ArrayList<ItemStack> availableItems = new ArrayList<ItemStack>();
	
	@Override
	public void drawScreen(int mX, int mY, float p_73863_3_) {
		drawList(parent, left + 5, top + 22, inventoryItems, mX, mY);
		drawList(parent, left + 135, top + 22, availableItems, mX, mY);
		search.xPosition = left + 65;
		search.yPosition = top + 5;
		search.drawTextBox();
		parent.drawItemStack(selected, left + 5, top +5);
	    if(selected != null && parent.inBounds(left + 5, top + 5, 18, 18, mX, mY))
	    	RenderUtils.renderTooltip(mX, mY, selected.getTooltip(mc.thePlayer, false));
	}
	
	@Override
	public void initGui() {
		super.initGui();
		addButton(new GuiButton(0, left + 25, top + 142, StatCollector.translateToLocal("techtree.editor.done")));
		addButtons();
	}
	
	private void drawList(GuiTechTreeEditor gui, int x, int y, List<ItemStack> items, int mX, int mY)
	{
	  for (int i = 0; i < items.size(); i++)
	  {
		ItemStack stack = items.get(i);
	    int xI = i % 6;
	    int yI = i / 6;
	    GL11.glPushMatrix();
	    gui.drawItemStack(stack, x + xI * 20, y + yI * 20);
	    
	    if(gui.inBounds(x + xI * 20, y + yI * 20, 18, 18, mX, mY))
	    	RenderUtils.renderTooltip(mX, mY, stack.getTooltip(mc.thePlayer, false));
	    GL11.glPopMatrix();
	  }
	}
	
	public class GuiItemSearchField extends GuiTextField{

		public GuiItemSearchField(int p_i1032_2_, int p_i1032_3_, int p_i1032_4_, int p_i1032_5_) {
			super(mc.fontRenderer, p_i1032_2_, p_i1032_3_, p_i1032_4_, p_i1032_5_);
		}
		
		@Override
		public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_) {
			super.mouseClicked(p_146192_1_, p_146192_2_, p_146192_3_);
			
			if(!isFocused()){
				availableItems.clear();
				String search = getText();
				if(search != ""){
				Iterator<Item> iterator = Item.itemRegistry.iterator();
				Item item;
				while(iterator.hasNext()){
					item = iterator.next();
					if(item != null && item.getCreativeTab() != null)
						item.getSubItems(item, null, availableItems);
				}
				List<String> description;
				Iterator<ItemStack> itemIterator = GuiEditorSelectItem.this.availableItems.iterator();
				int kept = 0;
				while (itemIterator.hasNext())
		        {
					ItemStack itemStack = itemIterator.next();
					if(kept >= 36){
						itemIterator.remove();
					}else{
						try
			            {
			                description = itemStack.getTooltip(mc.thePlayer, false);
			            }
			            catch (Throwable ex)
			            {
			               itemIterator.remove();
			               continue;
			            }
						Iterator<String> descriptionIterator = description.iterator();
						boolean foundSequence = false;
			            while (descriptionIterator.hasNext())
			              {
			                String line = descriptionIterator.next().toLowerCase();
			                if (line.contains(search))
			                {
			                  foundSequence = true;
			                  break;
			                }
			              }
			              if (!foundSequence) {
			                itemIterator.remove();
			              } else {
			                kept++;
			              }
					}
		        }
				}
			}
		}
		
	}
	@Override
	public void keyTyped(char key, int p_73869_2_) {
		search.textboxKeyTyped(key, p_73869_2_);
	}
	
	@Override
	public void mouseClicked(int mX, int mY, int p_73864_3_) {
		search.mouseClicked(mX, mY, p_73864_3_);
		clickList(parent, left + 5, top + 22, inventoryItems, mX, mY);
		clickList(parent, left + 135, top + 22, availableItems, mX, mY);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.id == 0){
			if(selected != null)
				parent.setItem(selected, modifier);
			onGuiClose();
		}
	}
	
	@Override
	public void onGuiClose() {
		parent.getButtonList().removeAll(buttons);
		parent.editorBase = null;
		parent.utils.switchMode(parent.utils.mode);
	}
	
	private void clickList(GuiTechTreeEditor gui, int x, int y, List<ItemStack> items, int mX, int mY)
	{
	  for (int i = 0; i < items.size(); i++)
	  {
	    ItemStack stack = items.get(i);
	    int xI = i % 6;
	    int yI = i / 6;
	    if (gui.inBounds(x + xI * 20, y + yI * 20, 18, 18, mX, mY))
	    {
	      if (stack == null) {
	        break;
	      }
	      this.selected = stack.copy();
	      this.selected.stackSize = 1;
	      break;
	    }
	  }
	}
	
}
