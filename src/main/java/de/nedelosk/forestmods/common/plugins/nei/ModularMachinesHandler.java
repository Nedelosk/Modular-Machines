package de.nedelosk.forestmods.common.plugins.nei;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import de.nedelosk.forestmods.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.forestmods.library.gui.IButtonManager;
import de.nedelosk.forestmods.library.gui.IGuiBase;
import de.nedelosk.forestmods.library.gui.IWidgetManager;
import de.nedelosk.forestmods.library.gui.Widget;
import de.nedelosk.forestmods.library.gui.WidgetManager;
import de.nedelosk.forestmods.library.inventory.IGuiHandler;
import de.nedelosk.forestmods.library.modules.integration.INEIPage;
import de.nedelosk.forestmods.library.modules.integration.SlotNEI;
import de.nedelosk.forestmods.library.recipes.IRecipe;
import de.nedelosk.forestmods.library.recipes.RecipeItem;
import de.nedelosk.forestmods.library.recipes.RecipeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class ModularMachinesHandler extends TemplateRecipeHandler implements IGuiBase {

	public ResourceLocation nei_widgets = new ResourceLocation("forestmods:textures/gui/nei/nei_widgets.png");
	public String recipeName;
	public INEIPage page;
	public WidgetManager<ModularMachinesHandler> widgetManager = new WidgetManager<ModularMachinesHandler>(this);

	public ModularMachinesHandler(String recipeName, INEIPage page) {
		this.recipeName = recipeName;
		this.page = page;
		page.setGui(this);
		if (!NEIConfig.isAdded) {
			GuiCraftingRecipe.craftinghandlers.add(this);
			GuiUsageRecipe.usagehandlers.add(this);
		}
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal(recipeName + ".name");
	}

	@Override
	public String getGuiTexture() {
		return page.getGuiTexture().toString() + ".png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "ModularMachines" + recipeName;
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (result == null) {
			return;
		}
		List<IRecipe> recipes = RecipeRegistry.getRecipes().get(recipeName);
		if (recipes != null) {
			for(IRecipe recipe : recipes) {
				RecipeItem[] outputs = recipe.getOutputs();
				for(RecipeItem output : outputs) {
					if (result.getItem() == output.item.getItem() && result.getItemDamage() == output.item.getItemDamage()
							&& (!result.hasTagCompound() && !output.item.hasTagCompound() || result.getTagCompound().equals(output.item.getTagCompound()))) {
						ModularCachedRecipe res = new ModularCachedRecipe(recipe.getInputs(), outputs, recipe);
						arecipes.add(res);
					}
				}
			}
		}
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("ModularMachines" + recipeName) && getClass() == ModularMachinesHandler.class) {
			List<IRecipe> recipes = RecipeRegistry.getRecipes().get(recipeName);
			if (recipes != null) {
				for(IRecipe recipe : recipes) {
					ModularCachedRecipe res = new ModularCachedRecipe(recipe.getInputs(), recipe.getOutputs(), recipe);
					arecipes.add(res);
				}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		List<IRecipe> recipes = RecipeRegistry.getRecipes().get(recipeName);
		if (recipes != null) {
			for(IRecipe recipe : recipes) {
				ModularCachedRecipe res = new ModularCachedRecipe(recipe.getInputs(), recipe.getOutputs(), recipe);
				if (res.contains(res.input, ingredient)) {
					res.setIngredientPermutation(res.input, ingredient);
					arecipes.add(res);
				}
			}
		}
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		return new ModularMachinesHandler(recipeName, page);
	}

	@Override
	public void drawBackground(int recipeIndex) {
		page.setRecipe(((ModularCachedRecipe) arecipes.get(recipeIndex)).recipe);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 0, 0, page.getXSize(), page.getYSize());
		GuiDraw.changeTexture(new ResourceLocation("forestmods:textures/gui/nei/nei_widgets.png"));
		ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
		stacks.add(getResultStack(recipeIndex));
		for(PositionedStack stack : getIngredientStacks(recipeIndex)) {
			stacks.add(stack);
		}
		if (getOtherStacks(recipeIndex) != null) {
			for(PositionedStack stack : getOtherStacks(recipeIndex)) {
				if (stack != null) {
					stacks.add(stack);
				}
			}
		}
		for(PositionedStack stack : stacks) {
			GuiDraw.drawTexturedModalRect(stack.relx - 1, stack.rely - 1, 0, 0, 18, 18);
		}
		List<Widget> widgets = new ArrayList();
		page.addWidgets(widgets);
		widgetManager.addAll(widgets);
		widgetManager.drawWidgets();
		for(Widget widget : widgetManager.getWidgets()) {
			if (widget instanceof WidgetProgressBar) {
				if (((WidgetProgressBar) widget).burntimeTotal != 100) {
					((WidgetProgressBar) widget).burntimeTotal = 100;
				}
				if (((WidgetProgressBar) widget).burntime > ((WidgetProgressBar) widget).burntimeTotal) {
					((WidgetProgressBar) widget).burntime = 0;
				} else {
					((WidgetProgressBar) widget).burntime++;
				}
			}
		}
	}

	public class ModularCachedRecipe extends TemplateRecipeHandler.CachedRecipe {

		private ArrayList<PositionedStack> input;
		private PositionedStack output;
		private ArrayList<PositionedStack> outputs;
		public int energy;
		public IRecipe recipe;

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, input);
		}

		@Override
		public PositionedStack getResult() {
			return output;
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			return outputs;
		}

		public ArrayList<PositionedStack> getInput() {
			return input;
		}

		public ModularCachedRecipe(RecipeItem[] inputs, RecipeItem[] outputs, IRecipe recipe) {
			page.setRecipe(recipe);
			this.input = new ArrayList<PositionedStack>();
			this.outputs = new ArrayList<PositionedStack>();
			this.recipe = recipe;
			List<SlotNEI> slots = new ArrayList();
			page.createSlots(slots);
			int input = 0;
			int output = 0;
			for(SlotNEI stack : slots) {
				if (stack.isInput) {
					if (inputs.length != input) {
						if (inputs[input].isItem()) {
							this.input.add(new RecipePositionedStack(inputs[input].item, stack.x, stack.y, inputs[input]));
						} else if (inputs[input].isOre()) {
							ArrayList<ItemStack> listOre = OreDictionary.getOres(inputs[input].ore.oreDict);
							for(ItemStack stackOre : listOre) {
								stackOre.stackSize = inputs[input].ore.stackSize;
							}
							this.input.add(new RecipePositionedStack(listOre, stack.x, stack.y, inputs[input]));
						}
					}
					input++;
				} else {
					if (output == 0) {
						this.output = new RecipePositionedStack(outputs[output].item, stack.x, stack.y, outputs[output]);
					} else {
						if (outputs.length - 1 >= output) {
							this.outputs.add(new RecipePositionedStack(outputs[output].item, stack.x, stack.y, outputs[output]));
						}
					}
					output++;
				}
			}
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ModularCachedRecipe)) {
				return false;
			}
			ModularCachedRecipe recipe = (ModularCachedRecipe) obj;
			output.equals(recipe.output);
			for(int i = 0; i < outputs.size(); i++) {
				PositionedStack stack = outputs.get(i);
				if (!stack.equals(recipe.outputs.get(i))) {
					return false;
				}
			}
			for(int i = 0; i < input.size(); i++) {
				PositionedStack stack = input.get(i);
				if (!stack.equals(recipe.input.get(i))) {
					return false;
				}
			}
			return true;
		}
	}

	@Override
	public IButtonManager getButtonManager() {
		return null;
	}

	@Override
	public IWidgetManager getWidgetManager() {
		return widgetManager;
	}

	@Override
	public IGuiHandler getHandler() {
		return null;
	}

	@Override
	public void setZLevel(float zLevel) {
		GuiDraw.gui.setZLevel(zLevel);
	}

	@Override
	public float getZLevel() {
		return GuiDraw.gui.getZLevel();
	}

	@Override
	public int getGuiLeft() {
		return 0;
	}

	@Override
	public int getGuiTop() {
		return 0;
	}

	@Override
	public List<String> handleTooltip(GuiRecipe guiRecipe, List<String> currenttip, int recipe) {
		super.handleTooltip(guiRecipe, currenttip, recipe);
		if (GuiContainerManager.shouldShowTooltip(guiRecipe) && widgetManager != null) {
			Point mouse = GuiDraw.getMousePosition();
			Point offset = guiRecipe.getRecipePosition(recipe);
			Point relMouse = new Point(mouse.x - (guiRecipe.width - 176) / 2 - offset.x, mouse.y - (guiRecipe.height - 166) / 2 - offset.y);
			currenttip = provideTooltip(currenttip, relMouse);
		}
		return currenttip;
	}

	private List<String> provideTooltip(List<String> currenttip, Point relMouse) {
		for(Widget widget : widgetManager.getWidgets()) {
			if (widget != null) {
				if (widget.getPos().contains(relMouse)) {
					if (widget.getTooltip(this) != null && widget.showTooltip) {
						currenttip.addAll(widget.getTooltip(this));
					}
				}
			}
		}
		return currenttip;
	}

	@Override
	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public FontRenderer getFontRenderer() {
		return Minecraft.getMinecraft().fontRenderer;
	}

	@Override
	public Gui getGui() {
		return GuiDraw.gui;
	}

	@Override
	public List<String> handleItemTooltip(GuiRecipe guiRecipe, ItemStack itemStack, List<String> currenttip, int recipe) {
		super.handleItemTooltip(guiRecipe, itemStack, currenttip, recipe);
		ModularCachedRecipe crecipe = (ModularCachedRecipe) this.arecipes.get(recipe);
		Point mouse = GuiDraw.getMousePosition();
		Point offset = guiRecipe.getRecipePosition(recipe);
		Point relMouse = new Point(mouse.x - (guiRecipe.width - 176) / 2 - offset.x, mouse.y - (guiRecipe.height - 166) / 2 - offset.y);

		currenttip = this.provideItemTooltip(guiRecipe, itemStack, currenttip, crecipe, relMouse);
		return currenttip;
	}

	public List<String> provideItemTooltip(GuiRecipe guiRecipe, ItemStack itemStack, List<String> currenttip, ModularCachedRecipe crecipe, Point relMouse) {
		for (PositionedStack stack : crecipe.getIngredients()) {
			if (stack instanceof RecipePositionedStack && ((RecipePositionedStack) stack).getRect().contains(relMouse)) {
				currenttip = ((RecipePositionedStack) stack).handleTooltip(guiRecipe, currenttip);
			}
		}
		for (PositionedStack stack : crecipe.getOtherStacks()) {
			if (stack instanceof RecipePositionedStack && ((RecipePositionedStack) stack).getRect().contains(relMouse)) {
				currenttip = ((RecipePositionedStack) stack).handleTooltip(guiRecipe, currenttip);
			}
		}
		PositionedStack stack = crecipe.getResult();
		if (stack instanceof RecipePositionedStack && ((RecipePositionedStack) stack).getRect().contains(relMouse)) {
			currenttip = ((RecipePositionedStack) stack).handleTooltip(guiRecipe, currenttip);
		}
		return currenttip;
	}

	public static class RecipePositionedStack extends PositionedStack{

		private final List<String> tooltip = new ArrayList<>();
		public RecipeItem item;

		public RecipePositionedStack(Object object, int x, int y, RecipeItem item) {
			super(object, x, y);

			this.item = item;
			if(item.chance != -1){
				addToTooltip(EnumChatFormatting.GRAY + NEIClientUtils.translate("fm.chance")+ item.chance + '%');
			}
		}

		public List<String> handleTooltip(GuiRecipe guiRecipe, List<String> currenttip) {
			if (!this.tooltip.isEmpty()) {
				for (String tip : this.tooltip) {
					currenttip.add(tip);
				}
			}
			return currenttip;
		}

		public RecipePositionedStack addToTooltip(List<String> lines) {
			for (String tip : lines) {
				this.tooltip.add(tip);
			}
			return this;
		}

		public RecipePositionedStack addToTooltip(String line) {
			this.tooltip.add(line);
			return this;
		}

		public Rectangle getRect() {
			return new Rectangle(this.relx - 1, this.rely - 1, 18, 18);
		}

	}
}
