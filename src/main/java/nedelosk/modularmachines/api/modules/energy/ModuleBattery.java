package nedelosk.modularmachines.api.modules.energy;

import java.util.ArrayList;
import java.util.List;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.client.renderer.ModularMachineRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.handlers.EnergyHandler;
import nedelosk.modularmachines.api.modular.inventory.SlotModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.Module;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.api.utils.ModuleUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleBattery extends Module implements IModuleBattery {

	public EnergyStorage storage;
	private int batteryCapacity;
	private int speedModifier;
	private int energyModifier;

	public ModuleBattery(String modifier, EnergyStorage storage) {
		super(modifier);
		this.storage = storage;
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		if (batteryCapacity == 0) {
			batteryCapacity = getStorage(stack).getMaxEnergyStored();
		}
		int energyModifier = 0;
		int speedModifier = 0;
		if (ModuleUtils.getCapacitors(modular) != null && ModuleUtils.getCapacitors(modular).size() > 0 && (ModuleUtils.getCapacitors(modular).get(0) != null
				|| ModuleUtils.getCapacitors(modular).get(1) != null || ModuleUtils.getCapacitors(modular).get(2) != null)) {
			for ( ModuleStack<IModule> module : ModuleUtils.getCapacitors(modular).getStacks() ) {
				if (module != null && module.getModule() != null && module.getModule() instanceof IModuleCapacitor) {
					IModuleCapacitor capacitor = (IModuleCapacitor) module.getModule();
					if (capacitor.canWork(modular, module)) {
						energyModifier += capacitor.getEnergyModifier();
						speedModifier += capacitor.getSpeedModifier();
					}
				}
			}
			this.speedModifier = speedModifier;
			this.energyModifier = energyModifier;
			int capacity = batteryCapacity + ((batteryCapacity * (energyModifier / 10)) / 10);
			if (((EnergyHandler) modular.getManager().getEnergyHandler()).getStorage().getMaxEnergyStored() != capacity) {
				((EnergyHandler) modular.getManager().getEnergyHandler()).getStorage().setCapacity(capacity);
			}
		} else {
			if (((EnergyHandler) modular.getManager().getEnergyHandler()).getStorage().getMaxEnergyStored() > batteryCapacity) {
				if (((EnergyHandler) modular.getManager().getEnergyHandler()).getStorage().getEnergyStored() > batteryCapacity) {
					((EnergyHandler) modular.getManager().getEnergyHandler()).getStorage().setEnergyStored(batteryCapacity);
				}
				((EnergyHandler) modular.getManager().getEnergyHandler()).getStorage().setCapacity(batteryCapacity);
			}
		}
	}

	// Inventory
	@Override
	public List<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotCapacitor(modular.getMachine(), 0, 143, 17, stack));
		list.add(new SlotCapacitor(modular.getMachine(), 1, 143, 35, stack));
		list.add(new SlotCapacitor(modular.getMachine(), 2, 143, 53, stack));
		return list;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 3;
	}

	// NBT
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);
		if (storage != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			storage.writeToNBT(nbtTag);
			nbtTag.setInteger("Capacity", storage.getMaxEnergyStored());
			nbtTag.setInteger("MaxReceive", storage.getMaxReceive());
			nbtTag.setInteger("MaxExtract", storage.getMaxExtract());
			nbt.setTag("Storage", nbtTag);
		}
		nbt.setInteger("BatteryCapacity", batteryCapacity);
		nbt.setInteger("speedModifier", speedModifier);
		nbt.setInteger("energyModifier", energyModifier);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);
		if (nbt.hasKey("Storage")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("Storage");
			storage = new EnergyStorage(nbtTag.getInteger("Capacity"), nbtTag.getInteger("MaxReceive"), nbtTag.getInteger("MaxExtract"));
			storage.readFromNBT(nbtTag);
		}
		batteryCapacity = nbt.getInteger("BatteryCapacity");
		speedModifier = nbt.getInteger("speedModifier");
		energyModifier = nbt.getInteger("energyModifier");
	}

	@Override
	public EnergyStorage getStorage(ModuleStack stack) {
		return storage;
	}

	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}

	public static class SlotCapacitor extends SlotModular {

		public SlotCapacitor(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, ModuleStack stack) {
			super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_, stack);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			if (ModuleRegistry.getProducer(stack) != null && ModuleRegistry.getProducer(stack).getModule() != null
					&& ModuleRegistry.getProducer(stack).getModule() instanceof IModuleCapacitor) {
				return true;
			}
			return false;
		}

		@Override
		public void onSlotChanged() {
			super.onSlotChanged();
			if (((IModularTileEntity) inventory).getModular().getModuleContainers().get("Capacitor") == null
					|| ((IModularTileEntity) inventory).getModular().getModuleContainers().get("Capacitor").size() < 3) {
				List<ModuleStack> modules = new ArrayList<ModuleStack>();
				for ( int i = 0; i < 3; i++ ) {
					modules.add(null);
				}
				((IModularTileEntity) inventory).getModular().getModuleContainers().put("Capacitor", modules);
			}
			List<ModuleStack> modules = ((IModularTileEntity) inventory).getModular().getModuleContainers().get("Capacitor");
			if (getStack() == null) {
				if (modules.get(getSlotIndex()) != null) {
					modules.set(getSlotIndex(), null);
				}
			} else {
				if (ModuleRegistry.getProducer(getStack()) != null && ModuleRegistry.getProducer(getStack()).getModule() != null
						&& ModuleRegistry.getProducer(getStack()).getModule() instanceof IModuleCapacitor) {
					((IModularTileEntity) inventory).getModular().getModuleContainers().get("Capacitor").set(getSlotIndex(),
							ModuleRegistry.getProducer(getStack()));
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.BatteryRenderer(moduleStack, modular);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModularMachineRenderer.BatteryRenderer(moduleStack, modular);
	}

	@Override
	public boolean onBuildModular(IModular modular, ModuleStack stack, List<String> moduleNames) {
		modular.getManager().setEnergyHandler(new EnergyHandler(((IModuleBattery) stack.getModule()).getStorage(stack)));
		return super.onBuildModular(modular, stack, moduleNames);
	}

	@Override
	public boolean transferInput(ModuleStack<IModuleInventory> stackModule, IModularTileEntity tile, EntityPlayer player, int slotID, Container container,
			ItemStack stackItem) {
		ModuleStack<IModule> stack = ModuleRegistry.getProducer(stackItem);
		if (stack != null && stack.getModule() == Modules.CAPACITOR && stack.getModule() != null && stack.getModule() instanceof IModuleCapacitor) {
			if (mergeItemStack(stackItem, 36, 39, false, container)) {
				return true;
			}
		}
		return false;
	}
}
