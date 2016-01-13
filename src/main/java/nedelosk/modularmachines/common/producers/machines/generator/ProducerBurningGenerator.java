package nedelosk.modularmachines.common.producers.machines.generator;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.inventory.SlotModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.client.gui.widget.WidgetBurningBar;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

public class ProducerBurningGenerator extends ProducerGenerator {

	public ProducerBurningGenerator() {
		this(15, 10);
	}

	public ProducerBurningGenerator(int speed, int energy) {
		super("Burning", 1, 0, speed, energy);
	}

	public ProducerBurningGenerator(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(new SlotModular(modular.getMachine(), 0, 80, 34, stack));
		return slots;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		gui.getWidgetManager().add(new WidgetBurningBar(80, 54, fuel, fuelTotal));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack stack) {
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		for ( Widget widget : widgets ) {
			if (widget instanceof WidgetBurningBar) {
				ProducerBurningGenerator generator = (ProducerBurningGenerator) stack.getProducer();
				if (generator != null) {
					int fuel = generator.fuel;
					int fuelTotal = generator.fuelTotal;
					((WidgetBurningBar) widget).fuel = fuel;
					((WidgetBurningBar) widget).fuelTotal = fuelTotal;
				}
			}
		}
	}

	@Override
	public int getColor() {
		return 0xC9DC59;
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		if (modular.getManager().getEnergyHandler() != null) {
			if (fuel > 0) {
				if (modular.getManager().getEnergyHandler().receiveEnergy(ForgeDirection.UNKNOWN, energy, false) >= energy) {
					fuel--;
				}
			} else {
				if (getInputs(modular, stack) != null) {
					if (getInputs(modular, stack)[0].isItem() && TileEntityFurnace.getItemBurnTime(getInputs(modular, stack)[0].item) > 0) {
						int burnTime = TileEntityFurnace.getItemBurnTime(getInputs(modular, stack)[0].item);
						if (removeInputs(modular, stack, 1)) {
							fuel = burnTime;
							fuelTotal = burnTime;
							if (tile.getModular().getInventoryManager().decrStackSize(stack.getModule().getName(stack, false), 0, 1) == null) {
								return;
							}
						}
					}
				}
			}
			if (timer > timerTotal) {
				modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(),
						modular.getMachine().getZCoord());
				timer = 0;
			} else {
				timer++;
			}
		}
	}

	public boolean removeInputs(IModular modular, ModuleStack stack, int size) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		for ( int i = 0; i < getInputs(modular, stack).length; i++ ) {
			RecipeInput input = getInputs(modular, stack)[i];
			if (input != null) {
				if (!input.isFluid()) {
					if (input.isOre()) {
						if (tile.getModular().getInventoryManager().decrStackSize(stack.getModule().getName(stack, false), input.slotIndex, size) == null) {
							return false;
						}
					} else {
						if (tile.getModular().getInventoryManager().decrStackSize(stack.getModule().getName(stack, false), input.slotIndex, size) == null) {
							return false;
						}
					}
					continue;
				} else {
					if (tile.getModular().getManager().getFluidHandler().drain(ForgeDirection.UNKNOWN, input.fluid, true) == null) {
						return false;
					}
					continue;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public String getRecipeName(ModuleStack stack) {
		return null;
	}

	@Override
	public RecipeInput[] getInputs(IModular modular, ModuleStack stack) {
		return getInputItems(modular, stack);
	}

	@Override
	public List<String> getRequiredModules() {
		ArrayList<String> modules = new ArrayList();
		modules.add("Battery");
		modules.add("Casing");
		return modules;
	}
}
