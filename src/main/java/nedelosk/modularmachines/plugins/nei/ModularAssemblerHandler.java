package nedelosk.modularmachines.plugins.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import nedelosk.modularmachines.api.parts.IMachine;
import nedelosk.modularmachines.api.parts.IMachinePart;
import nedelosk.modularmachines.api.parts.IMachinePartProducer;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.modular.machines.assembler.AssemblerMachineInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class ModularAssemblerHandler extends TemplateRecipeHandler {
	
	public ResourceLocation nei_widgets = new ResourceLocation("modularmachines:textures/gui/nei/nei_widgets.png");
	
	public ModularAssemblerHandler() {
		if(!NEIConfig.isAdded)
		{
		      GuiCraftingRecipe.craftinghandlers.add(this);
		      GuiUsageRecipe.usagehandlers.add(this);
		}
	}
	
	@Override
	public void loadTransferRects() {
	    transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(93, 57, 23, 15), "ModularAssembler", new Object[0]));
	}
	
  @Override
  public String getRecipeName() {
	  return StatCollector.translateToLocal("nei.modularassembler");
  }
  
  @Override
  public int recipiesPerPage() {
	  return 1;
  }
  
  @Override
  public Class<? extends GuiContainer> getGuiClass() {	
	  return super.getGuiClass();
  }

  @Override
  public String getGuiTexture() {
	  return "modularmachines:textures/gui/nei/nei_assembler.png";
  }

  @Override
  public String getOverlayIdentifier() {
	  return "ModularAssembler";
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
	   if(result.getItem() instanceof IMachinePart){
		   AssemblerMachineInfo recipeInfo = MMRegistry.getAssemblerInfo((IMachinePart) result.getItem());
		   if(recipeInfo != null)
		   {
			   ArrayList[] inputs = new ArrayList[8];
			   IMachinePart part = (IMachinePart) result.getItem();
			   PartType[] types;
			   if(part instanceof IMachinePartProducer){
				   ((IMachinePartProducer) part).updateComponents(((IMachinePartProducer) part).getModuleID(result));
				   types = ((IMachinePartProducer) part).getProducerComponents()[((IMachinePartProducer) part).getModuleID(result)];
			   }else{
				   types = part.getMachineComponents();
			   }
			   for(int i = 0;i < types.length;i++){
				   PartType type = types[i];
				   if(type != null){
					   ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
					   Iterator<IMachine> iterator = type.neededPart.iterator();
					   Item partType = (Item) iterator.next();
				   	   partType.getSubItems(partType, null, stacks);
				   	   inputs[i] = stacks;
				   }
			   }
			   ModularAssemblerCachedRecipe res = new ModularAssemblerCachedRecipe(recipeInfo, inputs, result);
			   arecipes.add(res);	
		   }
	   }
  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
	  if(outputId.equals("ModularAssembler") && getClass() == ModularAssemblerHandler.class) {
		  HashMap<IMachinePart, AssemblerMachineInfo> mapInfo = MMRegistry.getAssemblerInfos();
		  for(Entry<IMachinePart, AssemblerMachineInfo> entry : mapInfo.entrySet()){
			  AssemblerMachineInfo info = entry.getValue();
			   ArrayList[] inputs = new ArrayList[8];
			   IMachinePart part = (IMachinePart) info.machine.getItem();
			   PartType[] types;
			   if(part instanceof IMachinePartProducer){
				   ((IMachinePartProducer) part).updateComponents(((IMachinePartProducer) part).getModuleID(info.machine));
				   types = ((IMachinePartProducer) part).getProducerComponents()[((IMachinePartProducer) part).getModuleID(info.machine)];
			   }else{
				   types = part.getMachineComponents();
			   }
			   for(int i = 0;i < types.length;i++){
				   PartType type = types[i];
				   if(type != null){
					   ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
					   Iterator<IMachine> iterator = type.neededPart.iterator();
					   Item partType = (Item) iterator.next();
				   	   partType.getSubItems(partType, null, stacks);
				   	   inputs[i] = stacks;
				   }
			   }
			   ModularAssemblerCachedRecipe res = new ModularAssemblerCachedRecipe(info, inputs, info.machine);
			   arecipes.add(res);
		  }
	  }else {
		  super.loadCraftingRecipes(outputId, results);
	  }
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
	  HashMap<IMachinePart, AssemblerMachineInfo> mapInfo = MMRegistry.getAssemblerInfos();
	  for(Entry<IMachinePart, AssemblerMachineInfo> entry : mapInfo.entrySet()){
		  AssemblerMachineInfo info = entry.getValue();
		   ArrayList[] inputs = new ArrayList[8];
		   IMachinePart part = (IMachinePart) info.machine.getItem();
		   PartType[] types;
		   if(part instanceof IMachinePartProducer){
			   ((IMachinePartProducer) part).updateComponents(((IMachinePartProducer) part).getModuleID(info.machine));
			   types = ((IMachinePartProducer) part).getProducerComponents()[((IMachinePartProducer) part).getModuleID(info.machine)];
		   }else{
			   types = part.getMachineComponents();
		   }
		   for(int i = 0;i < types.length;i++){
			   PartType type = types[i];
			   if(type != null){
				   ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
				   Iterator<IMachine> iterator = type.neededPart.iterator();
				   Item partType = (Item) iterator.next();
			   	   partType.getSubItems(partType, null, stacks);
			   	   inputs[i] = stacks;
			   }
		   }
		   ModularAssemblerCachedRecipe res = new ModularAssemblerCachedRecipe(info, inputs, info.machine);
		   if(res.contains(res.inputs, ingredient)) {
		    	res.setIngredientPermutation(res.inputs, ingredient);
		    	arecipes.add(res);	
		   }
	  }
  }

  @Override
  public void drawBackground(int recipeIndex) {
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GuiDraw.changeTexture(getGuiTexture());
	    GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 130);
	    
	    GuiDraw.changeTexture(nei_widgets);
	    for(PositionedStack stack : getIngredientStacks(recipeIndex))
	    	GuiDraw.drawTexturedModalRect(stack.relx - 1, stack.rely - 1, 0, 0, 18, 18);
  }
  
  public class ModularAssemblerCachedRecipe extends TemplateRecipeHandler.CachedRecipe {

	    private ArrayList<PositionedStack> inputs;
	    private PositionedStack output;

	    @Override
	    public PositionedStack getResult() {
	      return output;
	    }
	    
	    @Override
	    public List<PositionedStack> getIngredients() {
	    	return getCycledIngredients(cycleticks / 20, inputs);
	    }

	    public ModularAssemblerCachedRecipe(AssemblerMachineInfo info, ArrayList[] inputs, ItemStack output) {
	        this.inputs = new ArrayList<PositionedStack>();
	        for(int i = 0;i < inputs.length;i++){
	        	ArrayList<ItemStack> stacks = inputs[i];
	        	if(stacks != null){
	        		Point point = info.positions.get(i);
	        		this.inputs.add(new PositionedStack(stacks, point.getX(), point.getY() + 15));
	        	}
	        }
	        this.output = new PositionedStack(output, 127, 57);
	    }
  }

}
