package de.nedelosk.modularmachines.common.modules.storaged.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.energy.IKineticSource;
import de.nedelosk.modularmachines.api.integration.IWailaState;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.energy.IModuleKinetic;
import de.nedelosk.modularmachines.api.modules.handlers.IAdvancedModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.integration.IModuleWaila;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleController;
import de.nedelosk.modularmachines.api.modules.storaged.tools.EnumToolType;
import de.nedelosk.modularmachines.api.modules.storaged.tools.IModuleMachine;
import de.nedelosk.modularmachines.api.modules.storaged.tools.IModuleMachineProperties;
import de.nedelosk.modularmachines.api.property.PropertyDouble;
import de.nedelosk.modularmachines.api.property.PropertyFloat;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import de.nedelosk.modularmachines.api.property.PropertyRecipe;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.Recipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import de.nedelosk.modularmachines.client.modules.ModelHandler;
import de.nedelosk.modularmachines.client.modules.ModelHandlerStatus;
import de.nedelosk.modularmachines.common.modules.Module;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncHeatBuffer;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleMachine extends Module implements IModuleMachine, IModuleWaila {

	public static final PropertyInteger WORKTIME = new PropertyInteger("workTime", 0);
	public static final PropertyInteger WORKTIMETOTAL = new PropertyInteger("workTimeTotal", 0);
	public static final PropertyInteger CHANCE = new PropertyInteger("chance", 0);
	public static final PropertyFloat SPEED = new PropertyFloat("speed", 0);
	public static final PropertyDouble HEATTOREMOVE = new PropertyDouble("heatToRemove", 0);
	public static final PropertyDouble HEATREQUIRED = new PropertyDouble("requiredHeat", 0F);
	public static final PropertyRecipe RECIPE = new PropertyRecipe("currentRecipe");

	public ModuleMachine(String name) {
		super(name);
	}

	@Override
	public int getWorkTimeModifier(IModuleState state) {
		IModuleProperties properties = state.getModuleProperties();
		if(properties instanceof IModuleMachineProperties){
			return ((IModuleMachineProperties) properties).getWorkTimeModifier(state);
		}
		return 0;
	}

	@Override
	public float getMaxSpeed(IModuleState state) {
		IModuleProperties properties = state.getModuleProperties();
		if(properties instanceof IModuleMachineProperties){
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
		List<IAdvancedModuleContentHandler> handlers = state.getContentHandlers();
		List<RecipeItem> outputs = new ArrayList();
		for(RecipeItem item : getCurrentRecipe(state).getOutputs()){
			if(item != null){
				if(item.chance == -1 || item.chance >= chance){
					outputs.add(item.copy());
				}
			}
		}
		for(IAdvancedModuleContentHandler handler : handlers) {
			if (!handler.canAddRecipeOutputs(chance, outputs.toArray(new RecipeItem[outputs.size()]))) {
				return false;
			}
		}
		for(IAdvancedModuleContentHandler handler : handlers) {
			if (!handler.canRemoveRecipeInputs(chance, recipe.getInputs())) {
				return false;
			}
		}
		for(IAdvancedModuleContentHandler handler : handlers) {
			handler.removeRecipeInputs(chance, recipe.getInputs());
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		ResourceLocation[] locs = new ResourceLocation[]{
				ModelHandler.getModelLocation(state.getContainer(), getModelFolder(state.getContainer()), true),
				ModelHandler.getModelLocation(state.getContainer(), getModelFolder(state.getContainer()), false)
		};
		return new ModelHandlerStatus(getModelFolder(state.getContainer()), state.getContainer(), locs);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List handlers = new ArrayList<>();
		ResourceLocation[] locs = new ResourceLocation[]{
				ModelHandler.getModelLocation(container, getModelFolder(container), true),
				ModelHandler.getModelLocation(container, getModelFolder(container), false)
		};
		handlers.add(new ModelHandlerStatus(getModelFolder(container), container, locs));
		return handlers;
	}

	protected abstract String getModelFolder(IModuleContainer container);

	protected boolean addOutput(IModuleState state) {
		int chance = getChance(state);
		IModular modular = state.getModular();
		IModularHandler tile = modular.getHandler();
		List<IAdvancedModuleContentHandler> handlers = state.getContentHandlers();
		List<RecipeItem> outputs = new ArrayList();
		for(RecipeItem item : getCurrentRecipe(state).getOutputs()){
			if(item != null){
				if(item.chance == -1 || item.chance >= chance){
					outputs.add(item.copy());
				}
			}
		}
		for(IAdvancedModuleContentHandler handler : handlers) {
			handler.addRecipeOutputs(chance, outputs.toArray(new RecipeItem[outputs.size()]));
		}
		return true;
	}

	public abstract RecipeItem[] getInputs(IModuleState state);

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		IModular modular = state.getModular();
		Random rand = modular.getHandler().getWorld().rand;
		EnumToolType type = getType(state);
		boolean needUpdate = false;
		IModuleState<IModuleController> controller = modular.getModule(IModuleController.class);

		if(controller == null || controller.getModule() == null || controller.getModule().canWork(controller, state)){
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
						setWorkTimeTotal(state, createWorkTimeTotal(state, validRecipe.getSpeed()) / state.getContainer().getMaterial().getTier());
						state.set(CHANCE, rand.nextInt(100));
						if(type == EnumToolType.HEAT){
							state.set(HEATTOREMOVE, validRecipe.get(Recipe.HEATTOREMOVE) / getWorkTimeTotal(state));
							state.set(HEATREQUIRED, validRecipe.get(Recipe.HEAT));
						}
						needUpdate = true;
					}
				}else{
					int workTime = 0;
					if(type == EnumToolType.KINETIC){
						for(IModuleState<IModuleKinetic> otherState : modular.getModules(IModuleKinetic.class)){
							IKineticSource source = otherState.getModule().getKineticSource(otherState);
							if(source.getKineticEnergyStored() > 0){
								source.extractKineticEnergy(1, false);
								if(state.get(SPEED) < getMaxSpeed(state)){
									state.set(SPEED, state.get(SPEED)+0.01F);
								}else if(state.get(SPEED) > getMaxSpeed(state)){
									state.set(SPEED, getMaxSpeed(state));
								}
							}else{
								if(state.get(SPEED) > 0){
									state.set(SPEED, state.get(SPEED)-0.05F);
								}else if(0 > state.get(SPEED)){
									state.set(SPEED, 0F);
								}
							}
						}
						if(state.get(SPEED) > 0){
							workTime+=Math.round(state.get(SPEED));
						}
					}else if(type == EnumToolType.HEAT){
						IHeatSource heatBuffer = modular.getHeatSource();
						if(heatBuffer.getHeatStored() >= state.get(HEATREQUIRED)){
							heatBuffer.extractHeat(state.get(HEATTOREMOVE), false);
							workTime = 1;
							PacketHandler.INSTANCE.sendToAll(new PacketSyncHeatBuffer(modular.getHandler()));
						}
					}

					if(workTime > 0){
						needUpdate = true;
						addWorkTime(state, workTime);
					}
				}
				if(needUpdate){
					PacketHandler.INSTANCE.sendToAll(new PacketSyncModule(modular.getHandler(), state));
				}
			}
		}
	}

	protected boolean isRecipeValid(IRecipe recipe, IModuleState state) {
		EnumToolType type = getType(state);
		if(type == EnumToolType.HEAT){
			if(recipe.get(Recipe.HEAT) > state.getModular().getHeatSource().getHeatStored()){
				return false;
			}
		}
		return true;
	}

	public boolean canWork(IModuleState state){
		EnumToolType type = getType(state);
		if(type == EnumToolType.HEAT){
			return state.getModular().getHeatSource().getHeatStored() > 0;
		}else if(type == EnumToolType.KINETIC){
			IModular modular = state.getModular();
			for(IModuleState<IModuleKinetic> otherState : modular.getModules(IModuleKinetic.class)){
				IKineticSource source = otherState.getModule().getKineticSource(otherState);
				if(source.getKineticEnergyStored() > 0){
					return true;
				}
			}
		}
		return true;
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
	public void updateClient(IModuleState state, int tickCount) {
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		IModuleState state = super.createState(modular, container).register(WORKTIME).register(WORKTIMETOTAL).register(CHANCE).register(RECIPE);
		if(getType(state) == EnumToolType.HEAT){
			state = state.register(HEATTOREMOVE).register(HEATREQUIRED);
		}else if(getType(state) == EnumToolType.KINETIC){
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
				}else if(RecipeRegistry.itemEqualsItem(recipeInput, machineInput, true)){
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
		if(RecipeRegistry.getRecipeHandler(getRecipeCategory(state)) == null){
			return Collections.emptyList();
		}
		return RecipeRegistry.getRecipeHandler(getRecipeCategory(state)).getRecipes();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.NONE;
	}

	@Override
	public EnumPosition getPosition(IModuleContainer container) {
		return EnumPosition.LEFT;
	}

}