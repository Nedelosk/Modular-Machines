package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.energy.IKineticSource;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.IModuleIndexStorage;
import de.nedelosk.modularmachines.api.modular.ModularUtils;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleKinetic;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandlerAdvanced;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.integration.IWailaProvider;
import de.nedelosk.modularmachines.api.modules.integration.IWailaState;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleDrive;
import de.nedelosk.modularmachines.api.modules.storaged.tools.EnumToolType;
import de.nedelosk.modularmachines.api.modules.storaged.tools.IModuleMachine;
import de.nedelosk.modularmachines.api.property.PropertyDouble;
import de.nedelosk.modularmachines.api.property.PropertyFloat;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import de.nedelosk.modularmachines.api.property.PropertyIntegerArray;
import de.nedelosk.modularmachines.api.property.PropertyRecipe;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.Recipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import de.nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import de.nedelosk.modularmachines.common.modules.storaged.ModuleStoraged;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public abstract class ModuleMachine extends ModuleStoraged implements IModuleMachine, IWailaProvider {

	public static final PropertyInteger WORKTIME = new PropertyInteger("workTime", 0);
	public static final PropertyInteger WORKTIMETOTAL = new PropertyInteger("workTimeTotal", 0);
	public static final PropertyInteger CHANCE = new PropertyInteger("chance", 0);
	public static final PropertyDouble HEATTOREMOVE = new PropertyDouble("heatToRemove", 0);
	public static final PropertyFloat HEATREQUIRED = new PropertyFloat("requiredHeat", 0);
	public static final PropertyRecipe RECIPE = new PropertyRecipe("currentRecipe");
	public static final PropertyIntegerArray DRIVEINDEXES = new PropertyIntegerArray("driveIndexs");

	protected final int speedModifier;
	protected final EnumModuleSize size;

	public ModuleMachine(String name, int complexity, int speedModifier, EnumModuleSize size) {
		super(name, complexity);
		this.speedModifier = speedModifier;
		this.size = size;
	}

	/* RECIPE */
	protected boolean removeInput(IModuleState state) {
		int chance = getChance(state);
		IModular modular = state.getModular();
		IModularHandler tile = modular.getHandler();
		IRecipe recipe = getCurrentRecipe(state);
		List<IModuleContentHandlerAdvanced> handlers = state.getContentHandlers();
		List<RecipeItem> outputs = new ArrayList();
		for(RecipeItem item : getCurrentRecipe(state).getOutputs()){
			if(item != null){
				if(item.chance == -1 || item.chance >= chance){
					outputs.add(item.copy());
				}
			}
		}
		for(IModuleContentHandlerAdvanced handler : handlers) {
			if (!handler.canAddRecipeOutputs(chance, outputs.toArray(new RecipeItem[outputs.size()]))) {
				return false;
			}
		}
		for(IModuleContentHandlerAdvanced handler : handlers) {
			if (!handler.canRemoveRecipeInputs(chance, recipe.getInputs())) {
				return false;
			}
		}
		for(IModuleContentHandlerAdvanced handler : handlers) {
			handler.removeRecipeInputs(chance, recipe.getInputs());
		}
		return true;
	}

	protected boolean addOutput(IModuleState state) {
		int chance = getChance(state);
		IModular modular = state.getModular();
		IModularHandler tile = modular.getHandler();
		List<IModuleContentHandlerAdvanced> handlers = state.getContentHandlers();
		List<RecipeItem> outputs = new ArrayList();
		for(RecipeItem item : getCurrentRecipe(state).getOutputs()){
			if(item != null){
				if(item.chance == -1 || item.chance >= chance){
					outputs.add(item.copy());
				}
			}
		}
		for(IModuleContentHandlerAdvanced handler : handlers) {
			handler.addRecipeOutputs(chance, outputs.toArray(new RecipeItem[outputs.size()]));
		}
		return true;
	}

	public abstract RecipeItem[] getInputs(IModuleState state);

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		IModular modular = state.getModular();
		Random rand = modular.getHandler().getWorld().rand;
		List<IModuleState<IModuleDrive>> drives = getDrives(state);
		boolean needUpdate = false;

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
					state.set(HEATTOREMOVE, validRecipe.get(Recipe.HEATTOREMOVE) / getWorkTimeTotal(state));
					state.set(HEATREQUIRED, validRecipe.get(Recipe.HEAT));
					needUpdate = true;
				}
			}else{
				EnumToolType type = getType(state);
				int workTime = 0;
				if(type == EnumToolType.KINETIC){
					for(IModuleState<IModuleDrive> driveState : drives){
						if(driveState.getModule() instanceof IModuleKinetic){
							IKineticSource source = ((IModuleKinetic)driveState.getModule()).getKineticSource(driveState);
							double kineticEnergy = (source.getKineticEnergyStored() * 100 / source.getCapacity() * 100);
							if(kineticEnergy > 0){
								int kineticModifier = (int) (kineticEnergy / 15);
								source.extractKineticEnergy(kineticModifier, false);
								workTime+=kineticModifier;
							}
						}
					}
				}else if(type == EnumToolType.HEAT){
					IModuleState<IModuleCasing> casingState = ModularUtils.getCasing(modular);
					IHeatSource heatBuffer = casingState.getModule().getHeatSource(casingState);
					if(heatBuffer.getHeatStored() >= state.get(HEATREQUIRED)){
						heatBuffer.extractHeat(state.get(HEATTOREMOVE), false);
						workTime = Math.max(1, state.get(HEATTOREMOVE).intValue());	
					}
				}

				if(workTime > 0){
					addWorkTime(state, workTime);
				}
			}
			if(needUpdate){
				PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
			}
		}
	}

	protected boolean isRecipeValid(IRecipe recipe, IModuleState state) {
		EnumToolType type = getType(state);
		if(type == EnumToolType.HEAT){
			IModuleState<IModuleCasing> casingState = ModularUtils.getCasing(state.getModular());
			IHeatSource heatBuffer = casingState.getModule().getHeatSource(casingState);
			if(recipe.get(Recipe.HEAT) > heatBuffer.getHeatStored()){
				return false;
			}
		}
		return true;
	}

	public boolean canWork(IModuleState state){
		EnumToolType type = getType(state);
		if(type == EnumToolType.HEAT){
			IModuleState<IModuleCasing> casingState = ModularUtils.getCasing(state.getModular());
			IHeatSource heatBuffer = casingState.getModule().getHeatSource(casingState);
			if(casingState == null || casingState.getModule() == null){
				return false;
			}
			return heatBuffer.getHeatStored() > 0;
		}else if(type == EnumToolType.KINETIC){
			IModular modular = state.getModular();
			List<IModuleState<IModuleDrive>> drives = getDrives(state);
			for(IModuleState<IModuleDrive> driveState : drives){
				if(driveState.getModule() instanceof IModuleKinetic){
					IKineticSource source = ((IModuleKinetic)driveState.getModule()).getKineticSource(driveState);
					if(source.getKineticEnergyStored() > 0){
						return true;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean assembleModule(IItemHandler itemHandler, IModular modular, IModuleState state, IModuleIndexStorage storage) {
		List<IModuleState<IModuleDrive>> drives = getDrives(state, storage.getStateToSlotIndex().get(state), storage);
		if(drives.isEmpty()){
			return false;
		}
		int[] driveIndexes = new int[drives.size()];
		for(int i = 0;i < drives.size();i++){
			driveIndexes[i]  = drives.get(i).getIndex();
		}
		state.set(DRIVEINDEXES, driveIndexes);
		return true;
	}

	//public abstract boolean canStartWork(IModuleState state);

	public static final List<IModuleState<IModuleDrive>> getDrives(IModuleState state){
		List<IModuleState<IModuleDrive>> drives = new ArrayList<>();
		int[] driveIndexes = state.get(DRIVEINDEXES);
		IModular modular = state.getModular();
		for(int index : driveIndexes){
			drives.add(modular.getModule(index));
		}
		return drives;
	}

	private List<IModuleState<IModuleDrive>> getDrives(IModuleState state, int index, IModuleIndexStorage storage){
		List<IModuleState<IModuleDrive>> drives = new ArrayList<>();
		EnumModuleSize size = getSize();
		if(size == EnumModuleSize.LARGE){
			for(int slotIndex : ContainerModularAssembler.driveSlots){
				IModuleState driveState = storage.getSlotIndexToState().get(Integer.valueOf(slotIndex));
				if(driveState != null){
					drives.add(driveState);
				}
			}
		}else if(size == EnumModuleSize.MIDDLE){
			int otherTool = -1;
			for(int i = 0;i < ContainerModularAssembler.toolSlots.length;i++){
				int slotIndex = ContainerModularAssembler.toolSlots[i];
				if(slotIndex != index){
					if(storage.getSlotIndexToState().containsKey(Integer.valueOf(slotIndex))){
						otherTool = slotIndex;
					}
				}
			}
			if(otherTool != -1){
				for(int slotIndex : ContainerModularAssembler.driveSlots){
					if(slotIndex != ContainerModularAssembler.driveSlots[otherTool]){
						IModuleState driveState = storage.getSlotIndexToState().get(Integer.valueOf(slotIndex));
						if(driveState != null){
							drives.add(driveState);
						}
					}
				}
			}else{
				for(int slotIndex : ContainerModularAssembler.driveSlots){
					IModuleState driveState = storage.getSlotIndexToState().get(Integer.valueOf(slotIndex));
					if(driveState != null){
						drives.add(driveState);
					}
				}
			}
		}else{
			for(int i = 0;i < ContainerModularAssembler.toolSlots.length;i++){
				int otherIndex = ContainerModularAssembler.toolSlots[i];
				if(otherIndex == index){
					IModuleState driveState = storage.getSlotIndexToState().get(Integer.valueOf(ContainerModularAssembler.driveSlots[otherIndex]));
					if(driveState != null){
						drives.add(driveState);
					}
				}
			}
		}
		return drives;
	}

	protected int createWorkTimeTotal(IModuleState state, int recipeSpeed) {
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
	public void setWorkTimeTotal(IModuleState state, int burnTimeTotal) {
		state.set(WORKTIMETOTAL, burnTimeTotal);
	}

	@Override
	public void updateClient(IModuleState state, int tickCount) {
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		IModuleState state = super.createState(modular, container).register(WORKTIME).register(WORKTIMETOTAL).register(CHANCE).register(RECIPE).register(DRIVEINDEXES);
		if(getType(state) == EnumToolType.HEAT){
			state.register(HEATTOREMOVE).register(HEATREQUIRED);
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
	public boolean transferInput(IModularHandler tile, IModuleState state, EntityPlayer player, int slotID, Container container, ItemStack stackItem) {
		if (isRecipeInput(state, new RecipeItem(slotID, stackItem))) {
			IModuleInventory inventory = (IModuleInventory) state.getContentHandler(IModuleInventory.class);
			if (inventory.mergeItemStack(stackItem, 36 + slotID, 37 + slotID, false, container)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IModuleState state, IWailaState data) {
		currenttip.add(state.getContainer().getDisplayName());
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
	public EnumModuleSize getSize() {
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

}