package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IRecipeManager;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.integration.IWailaProvider;
import de.nedelosk.modularmachines.api.modules.integration.IWailaState;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.ModuleState;
import de.nedelosk.modularmachines.api.modules.state.PropertyInteger;
import de.nedelosk.modularmachines.api.modules.state.PropertyRecipeManager;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import de.nedelosk.modularmachines.common.modular.assembler.AssemblerGroup;
import de.nedelosk.modularmachines.common.modular.assembler.AssemblerSlot;
import de.nedelosk.modularmachines.common.modules.tools.RecipeManager;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;

public abstract class ModuleTool extends Module implements IModuleTool, IWailaProvider {

	public static final PropertyRecipeManager RECIPEMANAGER = new PropertyRecipeManager("recipeManager");
	public static final PropertyInteger WORKTIME = new PropertyInteger("workTime");
	public static final PropertyInteger WORKTIMETOTAL = new PropertyInteger("workTimeTotal");
	public static final PropertyInteger CHANCE = new PropertyInteger("chance");

	protected final int speedModifier;
	protected final int size;

	public ModuleTool(int speedModifier, int size) {
		this.speedModifier = speedModifier;
		this.size = size;
	}

	/* RECIPE */
	@Override
	public boolean removeInput(IModuleState state) {
		int chance = getChance(state);
		IModular modular = state.getModular();
		IModularHandler tile = modular.getHandler();
		IRecipe recipe = RecipeRegistry.getRecipe(getRecipeCategory(state), getInputs(state), getRecipeModifiers(state));
		List<IModuleContentHandler> handlers = 		state.getContentHandlers();
		List<RecipeItem> outputs = new ArrayList();
		for(RecipeItem item : getRecipeManager(state).getOutputs()){
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
		for(RecipeItem item : getRecipeManager(state).getOutputs()){
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

	@Override
	public void updateServer(IModuleState state) {
		IModular modular = state.getModular();
		Random rand = modular.getHandler().getWorld().rand;

		if (canWork(state)) {
			IRecipeManager manager = getRecipeManager(state);
			if (getWorkTime(state) >= getWorkTimeTotal(state) || manager == null) {
				IRecipe recipe = RecipeRegistry.getRecipe(getRecipeCategory(state), getInputs(state), getRecipeModifiers(state));
				if (manager != null) {
					if (addOutput(state)) {
						setRecipeManager(state, null);
						setBurnTimeTotal(state, 0);
						setWorkTime(state, 0);
						state.add(CHANCE, rand.nextInt(100));
						PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularHandler) modular.getHandler(), state));
					}
				} else if (recipe != null) {
					setBurnTimeTotal(state, createBurnTimeTotal(state, recipe.getRequiredSpeedModifier()) / state.getContainer().getMaterial().getTier());
					setRecipeManager(state, new RecipeManager(recipe.getRecipeCategory(), (recipe.getRequiredMaterial() * speedModifier) / getWorkTimeTotal(state),
							recipe.getInputs().clone(), getRecipeModifiers(state)));
					if (!removeInput(state)) {
						setRecipeManager(state, null);
						return;
					}
					state.add(CHANCE, rand.nextInt(100));
					PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularHandler) modular.getHandler(), state));
				}
			}
		}
	}

	public abstract boolean canWork(IModuleState state);

	@Override
	public int createBurnTimeTotal(IModuleState state, int recipeSpeed) {
		int startTime = recipeSpeed * speedModifier;
		return startTime;
	}

	@Override
	public int getWorkTime(IModuleState state) {
		return (int) state.get(WORKTIME);
	}

	@Override
	public void setWorkTime(IModuleState state, int burnTime) {
		state.add(WORKTIME, burnTime);
	}

	@Override
	public void addWorkTime(IModuleState state, int burnTime) {
		state.add(WORKTIME, (int)state.get(WORKTIME) + burnTime);
	}

	@Override
	public int getWorkTimeTotal(IModuleState state) {
		return (int) state.get(WORKTIMETOTAL);
	}

	@Override
	public void setBurnTimeTotal(IModuleState state, int burnTimeTotal) {
		state.add(WORKTIMETOTAL, burnTimeTotal);
	}

	@Override
	public void updateClient(IModuleState state) {
	}

	@Override
	public Object[] getRecipeModifiers(IModuleState state) {
		return null;
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return new ModuleState(modular, this, container).add(WORKTIME, 0).add(WORKTIMETOTAL, 0).add(CHANCE, 0);
	}

	@Override
	public IRecipeManager getRecipeManager(IModuleState state) {
		return (IRecipeManager) state.get(RECIPEMANAGER);
	}

	@Override
	public void setRecipeManager(IModuleState state, IRecipeManager manager) {
		state.add(RECIPEMANAGER, manager);
	}

	@Override
	public boolean transferInput(IModularHandler tile, IModuleState state, EntityPlayer player, int slotID, Container container, ItemStack stackItem) {
		if (RecipeRegistry.isRecipeInput(getRecipeCategory(state), new RecipeItem(slotID, stackItem))) {
			IModuleInventory inventory = (IModuleInventory) state.getContentHandler(ItemStack.class);
			if (inventory.mergeItemStack(stackItem, 36 + slotID, 37 + slotID, false, container)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IAssemblerGroup createGroup(IAssembler assembler, ItemStack stack, int groupID) {
		IAssemblerGroup group = new AssemblerGroup(assembler, groupID);
		IAssemblerSlot controllerSlot = new AssemblerSlot(group, 4, 4, assembler.getNextIndex(), "controller", ModuleTool.class);
		group.setControllerSlot(controllerSlot);
		return group;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IModuleState state, IWailaState data) {
		currenttip.add(Translator.translateToLocal(state.getContainer().getUnlocalizedName()));
		currenttip.add(TextFormatting.ITALIC + (getWorkTime(state) + " / " + getWorkTimeTotal(state)));
		return currenttip;
	}

	@Override
	public int getChance(IModuleState state) {
		return (int) state.get(CHANCE);
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
	public int getSpeedModifier(IModuleState state) {
		return speedModifier;
	}

	@Override
	public int getSize() {
		return size;
	}
}
