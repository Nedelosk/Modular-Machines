package nedelosk.modularmachines.plugins;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.plugins.basic.Plugin;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.Modules;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.fluids.IProducerTank;
import nedelosk.modularmachines.api.modular.type.Types;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerBattery;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerCapacitor;
import nedelosk.modularmachines.common.modular.module.tool.producer.fluids.ProducerTank;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;

public class PluginEnderIO extends Plugin {

	public ItemStack chassis = GameRegistry.findItemStack(getRequiredMod(), "itemMachinePart", 1);
	public Item capacitorBank = GameRegistry.findItem(getRequiredMod(), "blockCapBank");
	public Item capacitor = GameRegistry.findItem(getRequiredMod(), "itemBasicCapacitor");
	public Item tanks = GameRegistry.findItem(getRequiredMod(), "blockTank");

	@Override
	public void preInit() {
		ModuleRegistry.addModuleItem(chassis, Modules.CASING, Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(capacitorBank, 1, 1), Modules.BATTERY, new ProducerBattery("CapacitorBankBasic", new EnergyStorage(1000000, 1000, 1000)), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(capacitorBank, 1, 2), Modules.BATTERY, new ProducerBattery("CapacitorBank", new EnergyStorage(5000000, 5000, 5000)), Types.BRONZE);
		ModuleRegistry.addModuleItem(new ItemStack(capacitorBank, 1, 3), Modules.BATTERY, new ProducerBattery("CapacitorBankVibrant", new EnergyStorage(25000000, 25000, 25000)), Types.STEEL);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 0), Modules.CAPACITOR, new ProducerCapacitor("CapacitorBasic", 10, 20), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 1), Modules.CAPACITOR, new ProducerCapacitor("CapacitorDoubleLayer", 20, 40), Types.BRONZE);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 2), Modules.CAPACITOR, new ProducerCapacitor("CapacitorVibrant", 40, 60), Types.STEEL);
		ModuleRegistry.addModuleItem(new ItemStack(tanks), Modules.TANK, new ProducerTankEnderIO("TankFluid", 16000), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(tanks, 1, 1), Modules.TANK, new ProducerTankEnderIO("TankFluidPressurized", 32000), Types.BRONZE);
	}

	@Override
	public String getRequiredMod() {
		return "EnderIO";
	}

	@Override
	public boolean getConfigOption() {
		return ModularConfig.pluginEnderIO;
	}

	private static class ProducerTankEnderIO extends ProducerTank {

		public ProducerTankEnderIO(String modifier, int capacity) {
			super(modifier, capacity);
		}

		public ProducerTankEnderIO(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
			super(nbt, modular, stack);
		}

		@Override
		public void setStorageFluid(FluidStack stack, ModuleStack<IModule, IProducerTank> moduleStack, ItemStack itemStack) {
			if(stack != null){
				NBTTagCompound nbtTag = new NBTTagCompound();
				NBTTagCompound fluidTag = new NBTTagCompound();
				stack.writeToNBT(fluidTag);
				nbtTag.setTag("tankContents", fluidTag);
				nbtTag.setInteger("tankType", itemStack.getItemDamage());
				nbtTag.setInteger("voidMode", 2);
				nbtTag.setByte("eio.abstractMachine", (byte) 1);
				NBTTagCompound displayTag = new NBTTagCompound();
				displayTag.setString("Name", itemStack.getItemDamage() == 0 ? "Fluid Tank (Configured)" : "Pressurized Fluid Tank (Configured)");
				nbtTag.setTag("display", displayTag);
				nbtTag.setTag("Items", new NBTTagList());
				nbtTag.setInteger("slotLayoutVersion", 1);
				nbtTag.setInteger("redstoneControlMode", 0);
				itemStack.setTagCompound(nbtTag);
			}
		}
		
		@Override
		public FluidStack getStorageFluid(ModuleStack<IModule, IProducerTank> moduleStack, ItemStack itemStack) {
			if(!itemStack.hasTagCompound())
				return null;
			if(!itemStack.getTagCompound().hasKey("tankContents"))
				return null;
			NBTTagCompound tag = itemStack.getTagCompound();
			NBTTagCompound fluidTag = tag.getCompoundTag("tankContents");
			return FluidStack.loadFluidStackFromNBT(fluidTag);
		}

	}

}
