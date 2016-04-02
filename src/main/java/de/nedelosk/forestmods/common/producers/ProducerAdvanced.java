package de.nedelosk.forestmods.common.producers;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.producers.IRecipeManager;
import de.nedelosk.forestmods.api.producers.IModuleAdvanced;
import de.nedelosk.forestmods.api.producers.handlers.IModuleContentHandler;
import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.IRecipeJsonSerializer;
import de.nedelosk.forestmods.api.recipes.IRecipeNBTSerializer;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.producers.recipe.ModuleRecipeManager;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class ProducerAdvanced extends Producer implements IModuleAdvanced, IRecipeJsonSerializer, IRecipeNBTSerializer {

	protected IRecipeManager recipeManager;

	public ProducerAdvanced() {
		super();
		RecipeRegistry.registerRecipeHandler(getRecipeCategory(), new ProducerRecipeHandler(this));
	}

	/* RECIPE */
	@Override
	public boolean removeInput() {
		IModularTileEntity tile = modular.getTile();
		IRecipe recipe = RecipeRegistry.getRecipe(getRecipeCategory(), getInputs(), getRecipeModifiers());
		List<IModuleContentHandler> handlers = Lists.newArrayList();
		getHandlers(handlers);
		for ( IModuleContentHandler handler : handlers ) {
			if (!handler.canRemoveRecipeInputs(recipe.getInputs())) {
				return false;
			}
		}
		for ( IModuleContentHandler handler : handlers ) {
			handler.removeRecipeInputs(recipe.getInputs());
		}
		return true;
	}

	public abstract RecipeItem[] getInputs();

	@Override
	public boolean addOutput() {
		IModularTileEntity tile = modular.getTile();
		List<IModuleContentHandler> handlers = Lists.newArrayList();
		getHandlers(handlers);
		for ( IModuleContentHandler handler : handlers ) {
			if (!handler.canAddRecipeOutputs(getRecipeManager().getOutputs())) {
				return false;
			}
		}
		for ( IModuleContentHandler handler : handlers ) {
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
						PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) tile, engineStack, true));
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
	public IRecipeManager getRecipeManager() {
		return recipeManager;
	}

	@Override
	public void setRecipeManager(IRecipeManager manager) {
		this.recipeManager = manager;
	}
}
