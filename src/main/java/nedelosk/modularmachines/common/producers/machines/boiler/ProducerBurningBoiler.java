package nedelosk.modularmachines.common.producers.machines.boiler;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.api.gui.IGuiBase;
import nedelosk.forestcore.api.gui.Widget;
import nedelosk.forestcore.api.inventory.IContainerBase;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.inventory.SlotModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.client.gui.widget.WidgetBurningBar;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ProducerBurningBoiler extends ProducerBoiler {
	
	public ProducerBurningBoiler() {
		this(25, 100, 1000);
	}
	
	public ProducerBurningBoiler(int speed, int energy, int water) {
		super("Burning", 1, 0, 1, 1, speed, energy, water);
	}

	public ProducerBurningBoiler(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(new SlotModular(modular.getMachine(), 0, 80, 34, stack));
		return slots;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		gui.getWidgetManager().add(new WidgetBurningBar( 80, 54, fuel, fuelTotal));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack stack) {
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		for (Widget widget : widgets) {
			if (widget instanceof WidgetBurningBar) {
				ProducerBurningBoiler generator = (ProducerBurningBoiler) stack.getProducer();
				if (generator != null)	{
					int fuel = generator.fuel;
					int fuelTotal = generator.fuelTotal;
					((WidgetBurningBar) widget).fuel = fuel;
					((WidgetBurningBar) widget).fuelTotal = fuelTotal;
				}
			}
		}
	}

	@Override
	public List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		return null;
	}

	@Override
	public int getColor() {
		return 0xAA681C;
	}
	
	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		if(modular.getManager().getFluidHandler() != null){
			if(fuel > 0){
				fuel--;
				modular.getManager().getFluidHandler().fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.getFluid("steam"), steam), true);
			}else{
				RecipeInput[] inputs = getInputs(modular, stack);
				if(inputs != null){
					if(inputs[1].isItem() && TileEntityFurnace.getItemBurnTime(inputs[1].item) > 0){
						int burnTime = TileEntityFurnace.getItemBurnTime(inputs[1].item);
						if(!removeInputs(modular, stack, 1))
							return;
						fuel = burnTime;
						fuelTotal = burnTime;
					}
				}
			}
			if (timer > timerTotal) {
				modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(), modular.getMachine().getZCoord());
				timer = 0;
			} else {
					timer++;
			}
		}
	}
	
	public boolean removeInputs(IModular modular, ModuleStack stack, int size) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		for (int i = 0; i < getInputs(modular, stack).length; i++) {
			RecipeInput input = getInputs(modular, stack)[i];
			if (input != null) {
				if (!input.isFluid()) {
					if (input.isOre())
						if(tile.getModular().getInventoryManager().decrStackSize(stack.getModule().getName(stack, false), input.slotIndex, size) == null)
							return false;
					else
						if(tile.getModular().getInventoryManager().decrStackSize(stack.getModule().getName(stack, false), input.slotIndex, size) == null)
							return false;
					continue;
				} else if(input.isFluid()) {
					if(tile.getModular().getManager().getFluidHandler().drain(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.WATER, water), true) == null)
						return false;
					continue;
				}
			} else
				return false;
		}
		return true;
	}
	
	@Override
	public String getRecipeName(ModuleStack stack) {
		return null;
	}

	@Override
	public RecipeInput[] getInputs(IModular modular, ModuleStack stack) {
		RecipeInput[] fluids = getInputFluids(modular, stack);
		RecipeInput[] items = getInputItems(modular, stack);
		if(fluids != null && fluids.length > 0 && items != null && items.length > 0)
			return new RecipeInput[]{fluids[0], items[0]};
		return null;
	}

	@Override
	public int getSpeedModifier() {
		return 10;
	}
	
	@Override
	public List<String> getRequiredModules() {
		ArrayList<String> modules = new ArrayList();
		modules.add("TankManager");
		modules.add("Casing");
		return modules;
	}

}
