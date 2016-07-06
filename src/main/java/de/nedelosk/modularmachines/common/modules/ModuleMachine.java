package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.integration.IWailaProvider;
import de.nedelosk.modularmachines.api.modules.integration.IWailaState;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.ModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleMachine;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import de.nedelosk.modularmachines.api.property.PropertyRecipe;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public abstract class ModuleMachine extends Module implements IModuleMachine, IWailaProvider {

	public static final PropertyInteger WORKTIME = new PropertyInteger("workTime", 0);
	public static final PropertyInteger WORKTIMETOTAL = new PropertyInteger("workTimeTotal", 0);
	public static final PropertyInteger CHANCE = new PropertyInteger("chance", 0);
	public static final PropertyRecipe RECIPE = new PropertyRecipe("currentRecipe");

	protected final int speedModifier;
	protected final int size;

	public ModuleMachine(int speedModifier, int size) {
		this.speedModifier = speedModifier;
		this.size = size;
	}

	/* RECIPE */
	@Override
	public boolean removeInput(IModuleState state) {
		int chance = getChance(state);
		IModular modular = state.getModular();
		IModularHandler tile = modular.getHandler();
		IRecipe recipe = getCurrentRecipe(state);
		List<IModuleContentHandler> handlers = 		state.getContentHandlers();
		List<RecipeItem> outputs = new ArrayList();
		for(RecipeItem item : getCurrentRecipe(state).getOutputs()){
			if(item != null){
				if(item.chance == -1 || item.chance >= chance){
					outputs.add(item.copy());
				}
			}
		}
		for(IModuleContentHandler handler : handlers) {
			if (!handler.canAddRecipeOutputs(chance, outputs.toArray(new RecipeItem[outputs.size()]))) {
				return false;
			}
		}
		for(IModuleContentHandler handler : handlers) {
			if (!handler.canRemoveRecipeInputs(chance, recipe.getInputs())) {
				return false;
			}
		}
		for(IModuleContentHandler handler : handlers) {
			handler.removeRecipeInputs(chance, recipe.getInputs());
		}
		return true;
	}

	public abstract RecipeItem[] getInputs(IModuleState state);

	@Override
	public boolean addOutput(IModuleState state) {
		int chance = getChance(state);
		IModular modular = state.getModular();
		IModularHandler tile = modular.getHandler();
		List<IModuleContentHandler> handlers = state.getContentHandlers();
		List<RecipeItem> outputs = new ArrayList();
		for(RecipeItem item : getCurrentRecipe(state).getOutputs()){
			if(item != null){
				if(item.chance == -1 || item.chance >= chance){
					outputs.add(item.copy());
				}
			}
		}
		for(IModuleContentHandler handler : handlers) {
			handler.addRecipeOutputs(chance, outputs.toArray(new RecipeItem[outputs.size()]));
		}
		return true;
	}

	public abstract boolean canWork(IModuleState state);

	@Override
	public int createBurnTimeTotal(IModuleState state, int recipeSpeed) {
		int startTime = recipeSpeed * speedModifier;
		return startTime;
	}

	@Override
	public int getWorkTime(IModuleState state) {
		return state.get(WORKTIME);
	}

	@Override
	public void setWorkTime(IModuleState state, int burnTime) {
		state.set(WORKTIME, burnTime);
	}

	@Override
	public void addWorkTime(IModuleState state, int burnTime) {
		state.set(WORKTIME, state.get(WORKTIME) + burnTime);
	}

	@Override
	public int getWorkTimeTotal(IModuleState state) {
		return state.get(WORKTIMETOTAL);
	}

	@Override
	public void setBurnTimeTotal(IModuleState state, int burnTimeTotal) {
		state.set(WORKTIMETOTAL, burnTimeTotal);
	}

	@Override
	public void updateClient(IModuleState state, int tickCount) {
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return new ModuleState(modular, this, container).register(WORKTIME).register(WORKTIMETOTAL).register(CHANCE).register(RECIPE);
	}

	@Override
	public IRecipe getValidRecipe(IModuleState state) {
		List<IRecipe> recipes = getRecipes(state);
		RecipeItem[] inputs = getInputs(state);
		if (recipes == null) {
			return null;
		}
		testRecipes: for(IRecipe recipe : recipes) {
			ArrayList<RecipeItem> recipeInputs = new ArrayList<RecipeItem>();
			for(RecipeItem recipeInput : recipe.getInputs().clone()) {
				recipeInputs.add(recipeInput);
			}
			for(int i = 0; i < recipeInputs.size(); i++) {
				RecipeItem recipeInput = recipeInputs.get(i);
				RecipeItem machineInput = inputs[i];
				if(recipeInput == null || machineInput == null){
					continue testRecipes;
				}
				if (machineInput.isNull()) {
					if (!recipeInput.isNull()) {
						continue testRecipes;
					}
					continue;
				}else if (recipeInput.isNull()) {
					if (!machineInput.isNull()) {
						continue testRecipes;
					}
					continue;
				}else if(RecipeRegistry.itemEqualsItem(recipeInput, machineInput, false)){
					continue;
				}
				continue testRecipes;
			}
			if(isRecipeValid(recipe, state)){
				return recipe;
			}
		}
		return null;
	}

	protected boolean isRecipeValid(IRecipe recipe, IModuleState state){
		return true;
	}

	@Override
	public boolean isRecipeInput(IModuleState state, RecipeItem item) {
		List<IRecipe> recipes = getRecipes(state);
		if (recipes == null || item == null) {
			return false;
		}
		for(IRecipe recipe : recipes) {
			ArrayList<RecipeItem> recipeInputs = new ArrayList<RecipeItem>();
			for(RecipeItem recipeInput : recipe.getInputs().clone()) {
				recipeInputs.add(recipeInput);
			}
			if (recipeInputs.isEmpty()) {
				return false;
			}
			for(int i = 0; i < recipeInputs.size(); i++) {
				RecipeItem recipeInput = recipeInputs.get(i);
				if (recipeInput == null) {
					continue;
				}
				if (recipeInput.index != item.index) {
					continue;
				}
				if(RecipeRegistry.itemEqualsItem(recipeInput, item, false)){
					return true;
				}
			}
		}
		return false;
	}

	protected abstract String getRecipeCategory(IModuleState state);

	@Override
	public boolean transferInput(IModularHandler tile, IModuleState state, EntityPlayer player, int slotID, Container container, ItemStack stackItem) {
		if (isRecipeInput(state, new RecipeItem(slotID, stackItem))) {
			IModuleInventory inventory = (IModuleInventory) state.getContentHandler(ItemStack.class);
			if (inventory.mergeItemStack(stackItem, 36 + slotID, 37 + slotID, false, container)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IModuleState state, IWailaState data) {
		currenttip.add(Translator.translateToLocal(state.getContainer().getUnlocalizedName()));
		currenttip.add(TextFormatting.ITALIC + (getWorkTime(state) + " / " + getWorkTimeTotal(state)));
		return currenttip;
	}

	@Override
	public int getChance(IModuleState state) {
		return state.get(CHANCE);
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IModuleState state, IWailaState data) {
		return null;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IModuleState state, IWailaState data) {
		return null;
	}

	@Override
	public int getSpeed(IModuleState state) {
		return speedModifier;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public IRecipe getCurrentRecipe(IModuleState state) {
		return state.get(RECIPE);
	}

	@Override
	public void setCurrentRecipe(IModuleState state, IRecipe recipe) {
		state.set(RECIPE, recipe);
	}

	@Override
	public List<IRecipe> getRecipes(IModuleState state) {
		return RecipeRegistry.getRecipeHandler(getRecipeCategory(state)).getRecipes();
	}

	@Override
	public boolean renderWall() {
		return false;
	}

}
