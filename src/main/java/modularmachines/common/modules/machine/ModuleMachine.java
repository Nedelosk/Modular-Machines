package modularmachines.common.modules.machine;

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
import modularmachines.common.modules.IModuleWorking;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncModule;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;

public abstract class ModuleMachine<R extends IRecipe> extends Module implements ITickable, IModuleWorking {

	public static final Random RANDOM = new Random();
	
	protected final Random rand;
	protected final int workTimeModifier;
	protected int workTime = 0;
	protected int workTimeTotal = 0;
	protected float chance = 0.0F;
	protected R recipe;

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
	
	@Override
	public void sendModuleUpdate() {
		ILocatable locatable = logic.getLocatable();
		if (locatable != null) {
			PacketHandler.sendToNetwork(new PacketSyncModule(this),locatable.getCoordinates(), (WorldServer) locatable.getWorldObj());
		}
	}

	public abstract RecipeItem[] getInputs();

	protected boolean isRecipeValid(R recipe) {
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

	public R getValidRecipe() {
		List<R> recipes = getRecipes();
		RecipeItem[] inputs = getInputs();
		if (recipes == null) {
			return null;
		}
		testRecipes: for (R recipe : recipes) {
			RecipeItem[] recipeInputs = recipe.getInputItems();
			for (int i = 0; i < recipeInputs.length; i++) {
				RecipeItem recipeInput = recipeInputs[i];
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

	public boolean isRecipeInput(int index, RecipeItem item) {
		List<R> recipes = getRecipes();
		if (recipes == null || item == null) {
			return false;
		}
		for (R recipe : recipes) {
			RecipeItem[] items = recipe.getInputItems();
			if(index >= items.length){
				continue;
			}
			if (RecipeRegistry.itemEqualsItem(items[index], item, false)) {
				return true;
			}
		}
		return false;
	}

	protected abstract String getRecipeCategory();
	
	@Override
	public int getWorkTime() {
		return workTime;
	}
	
	@Override
	public int getWorkTimeTotal() {
		return workTimeTotal;
	}
	
	@Override
	public boolean isWorking() {
		return workTime > 0;
	}
	
	public float getChance() {
		return chance;
	}
	
	public R getRecipe() {
		return recipe;
	}
	
	public void setRecipe(R recipe) {
		this.recipe = recipe;
	}

	public List<R> getRecipes() {
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