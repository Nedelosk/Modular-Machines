package modularmachines.api.modules.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.energy.IHeatSource;
import modularmachines.api.energy.IKineticSource;
import modularmachines.api.integration.IWailaState;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modules.EnumWallType;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.controller.IModuleController;
import modularmachines.api.modules.controller.ModuleControlled;
import modularmachines.api.modules.energy.IModuleKinetic;
import modularmachines.api.modules.handlers.IAdvancedModuleContentHandler;
import modularmachines.api.modules.handlers.IModuleContentHandler;
import modularmachines.api.modules.integration.IModuleWaila;
import modularmachines.api.modules.models.IModelHandler;
import modularmachines.api.modules.models.ModelHandlerStatus;
import modularmachines.api.modules.models.ModuleModelLoader;
import modularmachines.api.modules.position.EnumModulePositions;
import modularmachines.api.modules.position.IModulePositioned;
import modularmachines.api.modules.position.IModulePostion;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.storage.module.IModuleHandler;
import modularmachines.api.modules.tools.properties.IModuleMachineProperties;
import modularmachines.api.property.PropertyDouble;
import modularmachines.api.property.PropertyInteger;
import modularmachines.api.property.PropertyRecipe;
import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.Recipe;
import modularmachines.api.recipes.RecipeItem;
import modularmachines.api.recipes.RecipeRegistry;

public abstract class ModuleMachine extends ModuleControlled implements IModuleMachine, IModuleWaila, IModulePositioned {

	public static final PropertyInteger WORKTIME = new PropertyInteger("workTime", 0);
	public static final PropertyInteger WORKTIMETOTAL = new PropertyInteger("workTimeTotal", 0);
	public static final PropertyInteger CHANCE = new PropertyInteger("chance", 0);
	public static final PropertyDouble SPEED = new PropertyDouble("speed", 0);
	public static final PropertyDouble HEATTOREMOVE = new PropertyDouble("heatToRemove", 0);
	public static final PropertyDouble HEATREQUIRED = new PropertyDouble("requiredHeat", 0F);
	public static final PropertyRecipe RECIPE = new PropertyRecipe("currentRecipe");

	public ModuleMachine(String name) {
		super(name);
	}

	@Override
	public int getWorkTimeModifier(IModuleState state) {
		IModuleProperties properties = state.getModuleProperties();
		if (properties instanceof IModuleMachineProperties) {
			return ((IModuleMachineProperties) properties).getWorkTimeModifier(state);
		}
		return 0;
	}

	@Override
	public double getMaxSpeed(IModuleState state) {
		IModuleProperties properties = state.getModuleProperties();
		if (properties instanceof IModuleMachineProperties) {
			return ((IModuleMachineProperties) properties).getMaxSpeed(state);
		}
		return 0;
	}

	/* RECIPE */
	protected boolean removeInput(IModuleState state) {
		int chance = getChance(state);
		IModular modular = state.getModular();
		IModularHandler tile = modular.getHandler();
		IRecipe recipe = getCurrentRecipe(state);
		List<RecipeItem> outputs = new ArrayList();
		for (RecipeItem item : getCurrentRecipe(state).getOutputs()) {
			if (item != null) {
				if (item.chance == -1 || item.chance >= chance) {
					outputs.add(item.copy());
				}
			}
		}
		List<IAdvancedModuleContentHandler> advancedHandlers = new ArrayList<>();
		for (IModuleContentHandler handler : getHandlers(state)) {
			if (handler instanceof IAdvancedModuleContentHandler) {
				advancedHandlers.add((IAdvancedModuleContentHandler) handler);
			}
		}
		for (IAdvancedModuleContentHandler handler : advancedHandlers) {
			if (!handler.canAddRecipeOutputs(chance, outputs.toArray(new RecipeItem[outputs.size()]))) {
				return false;
			}
		}
		for (IAdvancedModuleContentHandler handler : advancedHandlers) {
			if (!handler.canRemoveRecipeInputs(chance, recipe.getInputs())) {
				return false;
			}
		}
		for (IAdvancedModuleContentHandler handler : advancedHandlers) {
			handler.removeRecipeInputs(chance, recipe.getInputs());
		}
		return true;
	}

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

	protected List<IModuleContentHandler> getHandlers(IModuleState state) {
		return state.getPage(getMainPageClass()).getContentHandlers();
	}

	protected boolean addOutput(IModuleState state) {
		int chance = getChance(state);
		IModular modular = state.getModular();
		IModularHandler tile = modular.getHandler();
		List<IModuleContentHandler> handlers = getHandlers(state);
		List<RecipeItem> outputs = new ArrayList();
		for (RecipeItem item : getCurrentRecipe(state).getOutputs()) {
			if (item != null) {
				if (item.chance == -1 || item.chance >= chance) {
					outputs.add(item.copy());
				}
			}
		}
		for (IModuleContentHandler handler : handlers) {
			if (handler instanceof IAdvancedModuleContentHandler) {
				((IAdvancedModuleContentHandler) handler).addRecipeOutputs(chance, outputs.toArray(new RecipeItem[outputs.size()]));
			}
		}
		return true;
	}

	public abstract RecipeItem[] getInputs(IModuleState state);

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		IModular modular = state.getModular();
		IModuleHandler handler = state.getModuleHandler();
		Random rand = modular.getHandler().getWorld().rand;
		EnumToolType type = getType(state);
		boolean needUpdate = false;
		IModuleState<IModuleController> controller = modular.getModule(IModuleController.class);
		if ((controller == null || controller.getModule() == null || controller.getModule().canWork(controller, state))) {
			if (canWork(state)) {
				IRecipe currentRecipe = getCurrentRecipe(state);
				if (getWorkTime(state) >= getWorkTimeTotal(state) || currentRecipe == null) {
					IRecipe validRecipe = getValidRecipe(state);
					if (currentRecipe != null) {
						if (addOutput(state)) {
							setCurrentRecipe(state, null);
							setWorkTimeTotal(state, 0);
							setWorkTime(state, 0);
							state.set(CHANCE, rand.nextInt(100));
							needUpdate = true;
						}
					} else if (validRecipe != null) {
						setCurrentRecipe(state, validRecipe);
						if (!removeInput(state)) {
							setCurrentRecipe(state, null);
							return;
						}
						setWorkTimeTotal(state, createWorkTimeTotal(state, validRecipe.getSpeed()) / state.getContainer().getItemContainer().getMaterial().getTier());
						state.set(CHANCE, rand.nextInt(100));
						if (type == EnumToolType.HEAT) {
							state.set(HEATTOREMOVE, validRecipe.get(Recipe.HEATTOREMOVE) / getWorkTimeTotal(state));
							state.set(HEATREQUIRED, validRecipe.get(Recipe.HEAT));
						}
						needUpdate = true;
					}
				} else {
					int workTime = 0;
					if (type == EnumToolType.KINETIC) {
						for (IModuleState<IModuleKinetic> otherState : handler.getModules(IModuleKinetic.class)) {
							IKineticSource source = otherState.getModule().getKineticSource(otherState);
							double kinetic = source.getStored() / source.getCapacity();
							if (source.getStored() > 0F) {
								source.extractKineticEnergy(kinetic, false);
								if (state.get(SPEED) < getMaxSpeed(state)) {
									state.set(SPEED, state.get(SPEED) + kinetic / 10F);
								} else if (state.get(SPEED) > getMaxSpeed(state)) {
									state.set(SPEED, getMaxSpeed(state));
								}
							} else {
								if (state.get(SPEED) > 0F) {
									state.set(SPEED, state.get(SPEED) - kinetic / 5F);
								} else if (0 > state.get(SPEED)) {
									state.set(SPEED, 0F);
								}
							}
						}
						if (state.get(SPEED) > 0) {
							workTime += Math.round(state.get(SPEED));
						}
					} else if (type == EnumToolType.HEAT) {
						IHeatSource heatBuffer = modular.getHeatSource();
						if (heatBuffer.getHeatStored() >= state.get(HEATREQUIRED)) {
							heatBuffer.extractHeat(state.get(HEATTOREMOVE), false);
							workTime = 1;
						}
					}
					if (workTime > 0) {
						needUpdate = true;
						addWorkTime(state, workTime);
					}
				}
				if (needUpdate) {
					sendModuleUpdate(state);
				}
			}
		}
	}

	protected boolean isRecipeValid(IRecipe recipe, IModuleState state) {
		EnumToolType type = getType(state);
		if (type == EnumToolType.HEAT) {
			if (recipe.get(Recipe.HEAT) > state.getModular().getHeatSource().getHeatStored()) {
				return false;
			}
		}
		return true;
	}

	public boolean canWork(IModuleState state) {
		EnumToolType type = getType(state);
		if (type == EnumToolType.HEAT) {
			return state.getModular().getHeatSource().getHeatStored() > 0;
		} else if (type == EnumToolType.KINETIC) {
			IModular modular = state.getModular();
			for (IModuleState<IModuleKinetic> otherState : state.getModuleHandler().getModules(IModuleKinetic.class)) {
				IKineticSource source = otherState.getModule().getKineticSource(otherState);
				if (source.getStored() > 0) {
					return true;
				}
			}
		}
		return true;
	}

	@Override
	public List<IModuleState> getUsedModules(IModuleState state) {
		return new ArrayList(state.getModular().getModules(IModuleKinetic.class));
	}

	protected int createWorkTimeTotal(IModuleState state, int recipeSpeed) {
		return recipeSpeed * getWorkTimeModifier(state);
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
	public void setWorkTimeTotal(IModuleState state, int burnTimeTotal) {
		state.set(WORKTIMETOTAL, burnTimeTotal);
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return state.get(WORKTIME) > 0;
	}

	@Override
	public void updateClient(IModuleState state, int tickCount) {
	}

	@Override
	public IModuleState createState(IModuleProvider provider, IModuleContainer container) {
		IModuleState state = super.createState(provider, container).register(WORKTIME).register(WORKTIMETOTAL).register(CHANCE).register(RECIPE);
		if (getType(state) == EnumToolType.HEAT) {
			state = state.register(HEATTOREMOVE).register(HEATREQUIRED);
		} else if (getType(state) == EnumToolType.KINETIC) {
			state = state.register(SPEED);
		}
		return state;
	}

	@Override
	public IRecipe getValidRecipe(IModuleState state) {
		List<IRecipe> recipes = getRecipes(state);
		RecipeItem[] inputs = getInputs(state);
		if (recipes == null) {
			return null;
		}
		testRecipes: for (IRecipe recipe : recipes) {
			ArrayList<RecipeItem> recipeInputs = new ArrayList<>();
			for (RecipeItem recipeInput : recipe.getInputs().clone()) {
				recipeInputs.add(recipeInput);
			}
			for (int i = 0; i < recipeInputs.size(); i++) {
				RecipeItem recipeInput = recipeInputs.get(i);
				RecipeItem machineInput = inputs[i];
				if (recipeInput == null || machineInput == null) {
					continue testRecipes;
				}
				if (machineInput.isNull()) {
					if (!recipeInput.isNull()) {
						continue testRecipes;
					}
					continue;
				} else if (recipeInput.isNull()) {
					if (!machineInput.isNull()) {
						continue testRecipes;
					}
					continue;
				} else if (RecipeRegistry.itemEqualsItem(recipeInput, machineInput, true)) {
					continue;
				}
				continue testRecipes;
			}
			if (isRecipeValid(recipe, state)) {
				return recipe;
			}
		}
		return null;
	}

	@Override
	public boolean isRecipeInput(IModuleState state, RecipeItem item) {
		List<IRecipe> recipes = getRecipes(state);
		if (recipes == null || item == null) {
			return false;
		}
		for (IRecipe recipe : recipes) {
			ArrayList<RecipeItem> recipeInputs = new ArrayList<>();
			for (RecipeItem recipeInput : recipe.getInputs().clone()) {
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

	protected abstract String getRecipeCategory(IModuleState state);

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		IModuleState state = data.getState();
		currenttip.add(state.getContainer().getDisplayName());
		currenttip.add(TextFormatting.ITALIC + (getWorkTime(state) + " / " + getWorkTimeTotal(state)));
		return currenttip;
	}

	@Override
	public int getChance(IModuleState state) {
		return state.get(CHANCE);
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		return null;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		return null;
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
		if (RecipeRegistry.getRecipeHandler(getRecipeCategory(state)) == null) {
			return Collections.emptyList();
		}
		return RecipeRegistry.getRecipeHandler(getRecipeCategory(state)).getRecipes();
	}

	protected abstract Class<? extends IModulePage> getMainPageClass();

	@SideOnly(Side.CLIENT)
	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.NONE;
	}

	@Override
	public IModulePostion[] getValidPositions(IModuleContainer container) {
		return new IModulePostion[] { EnumModulePositions.SIDE };
	}
}