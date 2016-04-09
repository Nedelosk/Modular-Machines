package de.nedelosk.forestmods.common.modules;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleAdvanced;
import de.nedelosk.forestmods.api.modules.IRecipeManager;
import de.nedelosk.forestmods.api.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.forestmods.api.modules.special.IModuleController;
import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.IRecipeJsonSerializer;
import de.nedelosk.forestmods.api.recipes.IRecipeNBTSerializer;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.producers.recipe.ModuleRecipeManager;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public abstract class ModuleAdvanced extends Module implements IModuleAdvanced, IModuleController, IRecipeJsonSerializer, IRecipeNBTSerializer {

	protected IRecipeManager recipeManager;

	public ModuleAdvanced(String name) {
		super(name);
		RecipeRegistry.registerRecipeHandler(getRecipeCategory(), new ModuleRecipeHandler(this));
	}

	/* RECIPE */
	@Override
	public boolean removeInput() {
		IModularTileEntity tile = modular.getTile();
		IRecipe recipe = RecipeRegistry.getRecipe(getRecipeCategory(), getInputs(), getRecipeModifiers());
		List<IModuleContentHandler> handlers = Lists.newArrayList();
		getContentHandlers(handlers);
		for(IModuleContentHandler handler : handlers) {
			if (!handler.canRemoveRecipeInputs(recipe.getInputs())) {
				return false;
			}
		}
		for(IModuleContentHandler handler : handlers) {
			handler.removeRecipeInputs(recipe.getInputs());
		}
		return true;
	}

	public abstract RecipeItem[] getInputs();

	@Override
	public boolean addOutput() {
		IModularTileEntity tile = modular.getTile();
		List<IModuleContentHandler> handlers = Lists.newArrayList();
		getContentHandlers(handlers);
		for(IModuleContentHandler handler : handlers) {
			if (!handler.canAddRecipeOutputs(getRecipeManager().getOutputs())) {
				return false;
			}
		}
		for(IModuleContentHandler handler : handlers) {
			handler.addRecipeOutputs(getRecipeManager().getOutputs());
		}
		return true;
	}

	@Override
	public void updateServer() {
		if (!modular.isAssembled()) {
			return;
		}
		IModularTileEntity tile = modular.getTile();
		ModuleStack<IModuleEngine> engineStack = ModularUtils.getEngine(modular);
		if (engineStack != null && tile.getEnergyStored(null) > 0) {
			IModuleEngine engine = engineStack.getModule();
			int burnTime = engine.getBurnTime();
			int burnTimeTotal = engine.getBurnTimeTotal();
			IRecipeManager manager = getRecipeManager();
			if (burnTime >= burnTimeTotal || burnTimeTotal == 0) {
				IRecipe recipe = RecipeRegistry.getRecipe(getRecipeCategory(), getInputs(), getRecipeModifiers());
				if (manager != null) {
					if (addOutput()) {
						setRecipeManager(null);
						engine.setBurnTimeTotal(0);
						engine.setBurnTime(0);
						engine.setIsWorking(false);
						PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) tile, engineStack));
					}
				} else if (recipe != null) {
					int newBurnTime = engineStack.getModule().getBurnTimeTotal(modular, recipe.getRequiredSpeedModifier(), moduleStack)
							/ moduleStack.getMaterial().getTier();
					setRecipeManager(new ModuleRecipeManager(modular, recipe.getRecipeCategory(), recipe.getRequiredMaterial() / newBurnTime,
							recipe.getInputs().clone(), getRecipeModifiers()));
					if (!removeInput()) {
						setRecipeManager(null);
						return;
					}
					engine.setIsWorking(true);
					engine.setBurnTimeTotal(newBurnTime);
				}
			}
		}
	}

	@Override
	public void updateClient() {
	}

	@Override
	public Object[] getRecipeModifiers() {
		return null;
	}

	public abstract Class<? extends IRecipe> getRecipeClass();

	/* SERIALIZERS */
	@Override
	public JsonObject serializeJson(Object[] objects) {
		return null;
	}

	@Override
	public void serializeNBT(NBTTagCompound nbt, Object[] craftingModifiers) {
	}

	@Override
	public Object[] deserializeJson(JsonObject object) {
		return null;
	}

	@Override
	public Object[] deserializeNBT(NBTTagCompound nbt) {
		return null;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		super.writeToNBT(nbt, modular);
		if (recipeManager != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			recipeManager.writeToNBT(nbtTag, modular);
			nbt.setTag("RecipeManager", nbtTag);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt, modular);
		if (nbt.hasKey("RecipeManager")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("RecipeManager");
			recipeManager = new ModuleRecipeManager();
			recipeManager.readFromNBT(nbtTag, modular);
		}
	}

	@Override
	public IRecipeManager getRecipeManager() {
		return recipeManager;
	}

	@Override
	public void setRecipeManager(IRecipeManager manager) {
		this.recipeManager = manager;
	}

	@Override
	public boolean transferInput(IModularTileEntity tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem) {
		if (RecipeRegistry.isRecipeInput(getRecipeCategory(), new RecipeItem(slotID, stackItem))) {
			if (moduleStack.getModule().getInventory().mergeItemStack(stackItem, 36 + slotID, 37 + slotID, false, container)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void canAssembleModular() throws ModularException {
		List<Class<? extends IModule>> requiredModules = getRequiredModules();
		requiredModules.add(IModuleCasing.class);
		for(ModuleStack stack : modular.getModuleStacks()) {
			stack.getModule().addRequiredModules(requiredModules);
		}
		RM: for(Class<? extends IModule> moduleClass : requiredModules) {
			for(ModuleStack stack : modular.getModuleStacks()) {
				if (moduleClass.isAssignableFrom(stack.getModule().getClass())) {
					continue RM;
				}
			}
			throw new ModularException(
					StatCollector.translateToLocal("modular.ex.require.module") + " " + StatCollector.translateToLocal("module." + moduleClass));
		}
	}
}
