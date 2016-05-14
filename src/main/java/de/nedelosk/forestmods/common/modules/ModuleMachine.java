package de.nedelosk.forestmods.common.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import de.nedelosk.forestmods.common.modular.assembler.AssemblerGroup;
import de.nedelosk.forestmods.common.modular.assembler.AssemblerSlot;
import de.nedelosk.forestmods.common.modules.producers.recipe.ModuleRecipeManager;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import de.nedelosk.forestmods.library.integration.IWailaProvider;
import de.nedelosk.forestmods.library.integration.IWailaState;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modular.ModularHelper;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerSlot;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.IModuleMachine;
import de.nedelosk.forestmods.library.modules.IRecipeManager;
import de.nedelosk.forestmods.library.modules.handlers.IModuleContentHandler;
import de.nedelosk.forestmods.library.recipes.IRecipe;
import de.nedelosk.forestmods.library.recipes.IRecipeJsonSerializer;
import de.nedelosk.forestmods.library.recipes.IRecipeNBTSerializer;
import de.nedelosk.forestmods.library.recipes.RecipeItem;
import de.nedelosk.forestmods.library.recipes.RecipeRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class ModuleMachine extends Module implements IModuleMachine, IRecipeJsonSerializer, IRecipeNBTSerializer, IWailaProvider {

	protected IRecipeManager recipeManager;
	protected int chance;
	protected final int speedModifier;
	protected int burnTime, burnTimeTotal;

	public ModuleMachine(IModular modular, IModuleContainer container, int speedModifier) {
		super(modular, container);
		this.speedModifier = speedModifier;
		RecipeRegistry.registerRecipeHandler(getRecipeCategory(), new ModuleRecipeHandler(this));
	}

	/* RECIPE */
	@Override
	public boolean removeInput() {
		IModularTileEntity tile = modular.getTile();
		IRecipe recipe = RecipeRegistry.getRecipe(getRecipeCategory(), getInputs(), getRecipeModifiers());
		List<IModuleContentHandler> handlers = Lists.newArrayList();
		getContentHandlers(handlers);
		List<RecipeItem> outputs = new ArrayList();
		for(RecipeItem item : getRecipeManager().getOutputs()){
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

	public abstract RecipeItem[] getInputs();

	@Override
	public boolean addOutput() {
		IModularTileEntity tile = modular.getTile();
		List<IModuleContentHandler> handlers = Lists.newArrayList();
		getContentHandlers(handlers);
		List<RecipeItem> outputs = new ArrayList();
		for(RecipeItem item : getRecipeManager().getOutputs()){
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
	public void updateServer() {
		Random rand = modular.getTile().getWorld().rand;

		if (canWork()) {
			IRecipeManager manager = getRecipeManager();
			if (burnTime >= burnTimeTotal || manager == null) {
				IRecipe recipe = RecipeRegistry.getRecipe(getRecipeCategory(), getInputs(), getRecipeModifiers());
				if (manager != null) {
					if (addOutput()) {
						setRecipeManager(null);
						burnTimeTotal = 0;
						burnTime = 0;
						chance = rand.nextInt(100);
						PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), this));
					}
				} else if (recipe != null) {
					burnTimeTotal = getBurnTimeTotal(modular, recipe.getRequiredSpeedModifier()) / container.getMaterial().getTier();
					setRecipeManager(new ModuleRecipeManager(modular, recipe.getRecipeCategory(), (recipe.getRequiredMaterial() * speedModifier) / burnTimeTotal,
							recipe.getInputs().clone(), getRecipeModifiers()));
					if (!removeInput()) {
						setRecipeManager(null);
						return;
					}
					chance = rand.nextInt();
					PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), this));
				}
			}
		}
	}

	public abstract boolean canWork();

	@Override
	public int getBurnTimeTotal(IModular modular, int recipeSpeed) {
		int startTime = recipeSpeed * speedModifier;
		return startTime + (startTime * ModularHelper.getBattery(modular).getSpeedModifier() / 100);
	}

	@Override
	public int getBurnTime() {
		return burnTime;
	}

	@Override
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	@Override
	public int getBurnTimeTotal() {
		return burnTimeTotal;
	}

	@Override
	public void setBurnTimeTotal(int burnTimeTotal) {
		this.burnTimeTotal = burnTimeTotal;
	}

	@Override
	public void addBurnTime(int burnTime) {
		this.burnTime += burnTime;
	}

	@Override
	public void updateClient() {
	}

	@Override
	public Object[] getRecipeModifiers() {
		return null;
	}

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

		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("burnTimeTotal", burnTimeTotal);
		nbt.setInteger("Chance", chance);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt, modular);
		if (nbt.hasKey("RecipeManager")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("RecipeManager");
			recipeManager = new ModuleRecipeManager();
			recipeManager.readFromNBT(nbtTag, modular);
		}

		burnTime = nbt.getInteger("burnTime");
		burnTimeTotal = nbt.getInteger("burnTimeTotal");
		chance = nbt.getInteger("Chance");
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
			if (inventory.mergeItemStack(stackItem, 36 + slotID, 37 + slotID, false, container)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IAssemblerGroup createGroup(IAssembler assembler, ItemStack stack, int groupID) {
		IAssemblerGroup group = new AssemblerGroup(assembler, groupID);
		IAssemblerSlot controllerSlot = new AssemblerSlot(group, 4, 4, assembler.getNextIndex(group), "controller", ModuleMachine.class);
		group.setControllerSlot(controllerSlot);
		return group;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		currenttip.add(burnTime + " / " + burnTimeTotal);
		return currenttip;
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
	public int getSpeedModifier() {
		return speedModifier;
	}
}
