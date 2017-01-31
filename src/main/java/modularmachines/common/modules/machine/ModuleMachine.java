package modularmachines.common.modules.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.IRecipeConsumer;
import modularmachines.api.recipes.RecipeItem;
import modularmachines.api.recipes.RecipeRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public abstract class ModuleMachine extends Module implements ITickable {

	public static final Random RANDOM = new Random();
	
	protected final Random rand;
	protected final int workTimeModifier;
	protected int workTime = 0;
	protected int workTimeTotal = 0;
	protected float chance = 0.0F;
	protected IRecipe recipe;

	public ModuleMachine(IModuleStorage storage, int workTimeModifier) {
		super(storage);
		this.workTimeModifier = workTimeModifier;
		ILocatable locatable = logic.getLocatable();
		if(locatable != null){
			rand = locatable.getWorldObj().rand;
		}else{
			rand = RANDOM; 
		}
			
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("WorkTime", workTime);
		compound.setInteger("WorkTimeTotal", workTimeTotal);
		compound.setFloat("Chance", chance);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		workTime = compound.getInteger("WorkTime");
		workTimeTotal = compound.getInteger("WorkTimeTotal");
		chance = compound.getFloat("Chance");
	}
	
	protected abstract IRecipeConsumer[] getConsumers();

	protected boolean addOutputs() {
		IRecipeConsumer[] consumers = getConsumers();
		for (IRecipeConsumer consumer : consumers) {
			consumer.insertOutputs(chance, recipe, false);
		}
		return true;
	}

	public abstract RecipeItem[] getInputs();

	protected boolean isRecipeValid(IRecipe recipe) {
		IRecipeConsumer[] consumers = getConsumers();
		for (IRecipeConsumer consumer : consumers) {
			if (!consumer.extractInputs(chance, recipe, true) || !consumer.insertOutputs(chance, recipe, true)) {
				return false;
			}
		}
		return true;
	}

	protected abstract boolean canWork();
	
	protected int createWorkTimeTotal( int recipeSpeed) {
		return recipeSpeed * workTimeModifier;
	}

	public IRecipe getValidRecipe() {
		List<IRecipe> recipes = getRecipes();
		RecipeItem[] inputs = getInputs();
		if (recipes == null) {
			return null;
		}
		testRecipes: for (IRecipe recipe : recipes) {
			ArrayList<RecipeItem> recipeInputs = new ArrayList<>();
			for (RecipeItem recipeInput : recipe.getInputItems().clone()) {
				recipeInputs.add(recipeInput);
			}
			for (int i = 0; i < recipeInputs.size(); i++) {
				RecipeItem recipeInput = recipeInputs.get(i);
				RecipeItem machineInput = inputs[i];
				if (recipeInput == null || machineInput == null) {
					continue testRecipes;
				}
				if (machineInput.isEmpty()) {
					if (!recipeInput.isEmpty()) {
						continue testRecipes;
					}
					continue;
				} else if (recipeInput.isEmpty()) {
					if (!machineInput.isEmpty()) {
						continue testRecipes;
					}
					continue;
				} else if (RecipeRegistry.itemEqualsItem(recipeInput, machineInput, true)) {
					continue;
				}
				continue testRecipes;
			}
			if (isRecipeValid(recipe)) {
				return recipe;
			}
		}
		return null;
	}

	public boolean isRecipeInput(RecipeItem item) {
		List<IRecipe> recipes = getRecipes();
		if (recipes == null || item == null) {
			return false;
		}
		for (IRecipe recipe : recipes) {
			ArrayList<RecipeItem> recipeInputs = new ArrayList<>();
			for (RecipeItem recipeInput : recipe.getInputItems().clone()) {
				recipeInputs.add(recipeInput);
			}
			if (recipeInputs.isEmpty()) {
				return false;
			}
			for (int i = 0; i < recipeInputs.size(); i++) {
				RecipeItem recipeInput = recipeInputs.get(i);
				if (recipeInput == null) {
					continue;
				}
				if (recipeInput.index != item.index) {
					continue;
				}
				if (RecipeRegistry.itemEqualsItem(recipeInput, item, false)) {
					return true;
				}
			}
		}
		return false;
	}

	protected abstract String getRecipeCategory();
	
	public float getChance() {
		return chance;
	}
	
	public IRecipe getRecipe() {
		return recipe;
	}
	
	public void setRecipe(IRecipe recipe) {
		this.recipe = recipe;
	}

	public List<IRecipe> getRecipes() {
		if (RecipeRegistry.getRecipeHandler(getRecipeCategory()) == null) {
			return Collections.emptyList();
		}
		return RecipeRegistry.getRecipeHandler(getRecipeCategory()).getRecipes();
	}
	
	//TODO: rendering
	/*
	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleItemContainer container = state.getContainer().getItemContainer();
		ResourceLocation[] locations = new ResourceLocation[2];
		locations[0] = ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), getModelFolder(container), container.getSize(), true);
		locations[1] = ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), getModelFolder(container), container.getSize(), false);
		return new ModelHandlerStatus(locations);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleItemContainer container) {
		Map<ResourceLocation, ResourceLocation> locations = new HashMap<>();
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), getModelFolder(container), container.getSize(), true),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", getModelFolder(container), container.getSize(), true));
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), getModelFolder(container), container.getSize(), false),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", getModelFolder(container), container.getSize(), false));
		return locations;
	}

	protected abstract String getModelFolder(IModuleItemContainer container);
	
	@SideOnly(Side.CLIENT)
	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.NONE;
	}*/
}
