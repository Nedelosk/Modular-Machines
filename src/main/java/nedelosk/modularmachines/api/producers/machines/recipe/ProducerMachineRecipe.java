package nedelosk.modularmachines.api.producers.machines.recipe;

import java.util.List;

import codechicken.nei.recipe.GuiCraftingRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.gui.WidgetProgressBar;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.engine.IProducerEngine;
import nedelosk.modularmachines.api.producers.fluids.ITankData;
import nedelosk.modularmachines.api.producers.fluids.ITankData.TankMode;
import nedelosk.modularmachines.api.producers.inventory.IProducerInventory;
import nedelosk.modularmachines.api.producers.machines.ProducerMachine;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.recipes.RecipeRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.api.utils.ModuleUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(modid = "NotEnoughItems", iface = "codechicken.nei.recipe.GuiCraftingRecipe")
public abstract class ProducerMachineRecipe extends ProducerMachine implements IProducerMachineRecipe {
	
	public int itemOutputs;
	public int itemInputs;
	public int fluidOutputs;
	public int fluidInputs;
	
	protected int speed;

	public ProducerMachineRecipe(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	public ProducerMachineRecipe(String modifier, int itemInputs, int itemOutputs, int speed) {
		super(modifier);
		this.itemInputs = itemInputs;
		this.itemOutputs = itemOutputs;
		this.speed = speed;
		this.timerTotal = 50;
	}
	
	public ProducerMachineRecipe(String modifier, int itemInputs, int itemOutputs, int fluidInputs, int fluidOutputs, int speed) {
		super(modifier);
		this.itemInputs = itemInputs;
		this.itemOutputs = itemOutputs;
		this.fluidInputs = fluidInputs;
		this.fluidOutputs = fluidOutputs;
		this.speed = speed;
		this.timerTotal = 50;
	}

	public RecipeInput[] getInputItems(IModular modular, ModuleStack moduleStack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		RecipeInput[] inputs = new RecipeInput[getItemInputs(moduleStack)];
		for (int i = 0; i < getItemInputs(moduleStack); i++) {
			ItemStack stack = tile.getModular().getInventoryManager().getStackInSlot(moduleStack.getModule().getName(moduleStack, false), i);
			inputs[i] = new RecipeInput(i, stack);
		}
		return inputs;
	}

	public RecipeInput[] getInputFluids(IModular modular, ModuleStack moduleStack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		List<ITankData> datas = modular.getTankManeger().getProducer().getDatas(modular, moduleStack, TankMode.INPUT);
		RecipeInput[] fluidInputs = new RecipeInput[getFluidInputs(moduleStack)];
		int inputs = fluidInputs.length;
		if(datas.size() < fluidInputs.length)
			inputs = datas.size();
		for(int ID = 0;ID < inputs;ID++){
			ITankData data = datas.get(ID);
			if(data != null && data.getTank() != null && !data.getTank().isEmpty()){
				fluidInputs[ID] = new RecipeInput(ID, data.getTank().getFluid().copy());
			}
		}
		return fluidInputs;
	}

	@Override
	public boolean removeInput(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(stack), getInputs(modular, stack), getCraftingModifiers(modular, stack));
		for (int i = 0; i < getInputs(modular, stack).length; i++) {
			RecipeInput input = getInputs(modular, stack)[i];
			RecipeItem[] inputs = recipe.getInputs();
			if (input != null) {
				if (!inputs[i].isFluid()) {
					if (inputs[i].isOre())
						tile.getModular().getInventoryManager().decrStackSize(stack.getModule().getName(stack, false),
								input.slotIndex, inputs[i].ore.stackSize);
					else
						tile.getModular().getInventoryManager().decrStackSize(stack.getModule().getName(stack, false),
								input.slotIndex, inputs[i].item.stackSize);
					continue;
				} else {
					tile.getModular().getTankManeger().getProducer().drain(ForgeDirection.UNKNOWN, input.fluid, true, stack, modular);
					continue;
				}
			} else
				return false;
		}
		return true;
	}
	
	@Override
	public Object[] getCraftingModifiers(IModular modular, ModuleStack stack) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	@Optional.Method(modid = "NotEnoughItems")
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack stack) {
		if (widget instanceof WidgetProgressBar) {
			if (Loader.isModLoaded("NotEnoughItems")) {
				GuiCraftingRecipe.openRecipeGui("ModularMachines" + getRecipeName(stack));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack stack) {
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		for (Widget widget : widgets) {
			if (widget instanceof WidgetProgressBar) {
				ModuleStack<IModule, IProducerEngine> engine = ModuleUtils.getModuleStackEngine(modular);
				if (engine != null)	{
					int burnTime = engine.getProducer().getBurnTime(engine);
					int burnTimeTotal = engine.getProducer().getBurnTimeTotal(engine);
					((WidgetProgressBar) widget).burntime = burnTime;
					((WidgetProgressBar) widget).burntimeTotal = burnTimeTotal;
				}
			}
		}
	}
	
	@Override
	public boolean transferInput(ModuleStack<IModule, IProducerInventory> stackModule, IModularTileEntity tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem){
		RecipeInput input = RecipeRegistry.isRecipeInput(getRecipeName(stackModule), new RecipeInput(slotID, stackItem));
		if(input != null){
			if(mergeItemStack(stackItem, 36 + input.slotIndex, 37 + input.slotIndex, false, container))
				return true;
		}
		return false;
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		ModuleStack<IModule, IProducerEngine> engineStack = ModuleUtils.getModuleStackEngine(modular);
		if (engineStack != null && tile.getEnergyStored(null) > 0) {
			IProducerEngine engine = engineStack.getProducer();
			int burnTime = engine.getBurnTime(engineStack);
			int burnTimeTotal = engine.getBurnTimeTotal(engineStack);
			IRecipeManager manager = engine.getManager(engineStack);
			if (burnTime >= burnTimeTotal || burnTimeTotal == 0) {
				IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(stack), getInputs(modular, stack), getCraftingModifiers(modular, stack));
				if (manager != null) {
					if (addOutput(modular, stack)) {
						engine.setManager(null);
						engine.setBurnTimeTotal(0);
						engine.setBurnTime(0);
						engine.setIsWorking(false);
					}
				} else if (getInputs(modular, stack) != null && recipe != null) {
					if (ModuleUtils.getModuleStackMachine(modular) == null)
						return;
					engine.setManager(engine.creatRecipeManager(modular, recipe.getRecipeName(), recipe.getRequiredMaterial() / engine.getBurnTimeTotal(modular, recipe.getRequiredSpeedModifier(), stack, engineStack), getInputs(modular, stack), getCraftingModifiers(modular, stack)));
					if (!removeInput(modular, stack)) {
						engine.setManager(null);
						return;
					}
					engine.setIsWorking(true);
					engine.setBurnTimeTotal(engine.getBurnTimeTotal(modular, recipe.getRequiredSpeedModifier(), stack, engineStack) / ModuleUtils.getModuleStackMachine(modular).getType().getTier());
				}
				if (timer > timerTotal) {
					if(timerTotal == 0)
						timerTotal = 50;
					modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(), modular.getMachine().getZCoord());
					timer = 0;
				} else {
					timer++;
				}
			} else {
				if (timer > timerTotal) {
					if(timerTotal == 0)
						timerTotal = 50;
					modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(), modular.getMachine().getZCoord());
					timer = 0;
				} else {
					timer++;
				}
			}
		}
	}

	@Override
	public boolean addOutput(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		ModuleStack<IModule, IProducerEngine> engineStack = ModuleUtils.getModuleStackEngine(modular);
		IProducerEngine engine = engineStack.getProducer();
		if (engine.getManager(engineStack).getOutputs() != null) {
			for (RecipeItem item : engine.getManager(engineStack).getOutputs()) {
				if (item != null) {
					if (!item.isFluid())
						if (tile.getModular().getInventoryManager().addToOutput(item.item.copy(), getItemInputs(stack), getItemInputs(stack) + getItemOutputs(stack), stack.getModule().getName(stack, false))) {
							item = null;
							continue;
						} else
							return false;
					else if (tile.getModular().getTankManeger().getProducer().fill(ForgeDirection.UNKNOWN, item.fluid.copy(), true, stack, modular) != 0) {
						item = null;
						continue;
					} else
						return false;
				}
			}
			return true;
		}
		engine.setManager(null);
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);

		nbt.setInteger("itemInputs", itemInputs);
		nbt.setInteger("itemOutputs", itemOutputs);
		
		nbt.setInteger("fluidInputs", fluidInputs);
		nbt.setInteger("fluidOutputs", fluidOutputs);
		nbt.setInteger("Speed", speed);
	}
	
	@Override
	public void writeCraftingModifiers(NBTTagCompound nbt, IModular modular, Object[] craftingModifiers) {
	}
	
	@Override
	public Object[] readCraftingModifiers(NBTTagCompound nbt, IModular modular) {
		return null;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);

		itemInputs = nbt.getInteger("itemInputs");
		itemOutputs = nbt.getInteger("itemOutputs");
		fluidInputs = nbt.getInteger("fluidInputs");
		fluidOutputs = nbt.getInteger("fluidOutputs");
		speed = nbt.getInteger("Speed");
	}

	@Override
	public int getSpeed(ModuleStack stack) {
		return speed;
	}
	
	@Override
	public int getItemInputs(ModuleStack<IModule, IProducer> stack) {
		return itemInputs;
	}
	
	@Override
	public int getItemOutputs(ModuleStack<IModule, IProducer> stack) {
		return itemOutputs;
	}
	
	@Override
	public int getFluidInputs(ModuleStack<IModule, IProducer> stack) {
		return fluidInputs;
	}
	
	@Override
	public int getFluidOutputs(ModuleStack<IModule, IProducer> stack) {
		return fluidOutputs;
	}
}
