package modularmachines.common.utils.capabilitys;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import modularmachines.common.utils.content.IEnergyItem;

public class EnergyStorageItem implements IEnergyStorage, ICapabilityProvider {
	
	public static final String ENERGY_KEY = "Energy";
	protected final ItemStack container;
	protected final IEnergyItem energyItem;
	protected final int maxReceive;
	protected final int maxExtract;
	
	public EnergyStorageItem(ItemStack container, IEnergyItem energyItem) {
		this(container, energyItem, 1000, 1000);
	}
	
	public EnergyStorageItem(ItemStack container, IEnergyItem energyItem, int maxTransfer) {
		this(container, energyItem, maxTransfer, maxTransfer);
	}
	
	public EnergyStorageItem(ItemStack container, IEnergyItem energyItem, int maxReceive, int maxExtract) {
		this.container = container;
		this.energyItem = energyItem;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (!canReceive()) {
			return 0;
		}
		int energy = getEnergyStored();
		int energyReceived = Math.min(getMaxEnergyStored() - energy, Math.min(this.maxReceive, maxReceive));
		if (!simulate) {
			setEnergy(energy + energyReceived);
		}
		return energyReceived;
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!canExtract()) {
			return 0;
		}
		int energy = getEnergyStored();
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
		if (!simulate) {
			setEnergy(energy - energyExtracted);
		}
		return energyExtracted;
	}
	
	@Override
	public int getEnergyStored() {
		if (!container.hasTagCompound()) {
			return 0;
		}
		return container.getTagCompound().getInteger(ENERGY_KEY);
	}
	
	@Override
	public int getMaxEnergyStored() {
		if (!container.hasTagCompound()) {
			return 0;
		}
		return energyItem.getCapacity(container);
	}
	
	@Override
	public boolean canExtract() {
		return this.maxExtract > 0;
	}
	
	@Override
	public boolean canReceive() {
		return this.maxReceive > 0;
	}
	
	public void setEnergy(int energy) {
		if (!container.hasTagCompound()) {
			return;
		}
		container.getTagCompound().setInteger(ENERGY_KEY, energy);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY;
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY ? CapabilityEnergy.ENERGY.cast(this) : null;
	}
	
	public static ItemStack createItemStack(IEnergyItem item, int size, int damage, boolean empty) {
		ItemStack itemStack = new ItemStack(item.getItem(), size, damage);
		NBTTagCompound tagCombound = new NBTTagCompound();
		tagCombound.setInteger(EnergyStorageItem.ENERGY_KEY, empty ? 0 : item.getCapacity(itemStack));
		itemStack.setTagCompound(tagCombound);
		return itemStack;
	}
	
	public static ItemStack createItemStack(Item item, int size, int damage, int energy) {
		ItemStack itemStack = new ItemStack(item, size, damage);
		NBTTagCompound tagCombound = new NBTTagCompound();
		tagCombound.setInteger(EnergyStorageItem.ENERGY_KEY, energy);
		return itemStack;
	}
	
	public static int getCapacity(ItemStack stack) {
		if (stack.getItem() instanceof IEnergyItem) {
			return ((IEnergyItem) stack.getItem()).getCapacity(stack);
		}
		return 0;
	}
	
	public static int getEnergy(ItemStack stack) {
		return getEnergy(stack.getTagCompound());
	}
	
	public static int getEnergy(NBTTagCompound tagCombound) {
		if (!hasEnergy(tagCombound)) {
			return 0;
		}
		return tagCombound.getInteger(EnergyStorageItem.ENERGY_KEY);
	}
	
	public static boolean hasEnergy(ItemStack stack) {
		return hasEnergy(stack.getTagCompound());
	}
	
	public static boolean hasEnergy(@Nullable NBTTagCompound tagCombound) {
		return tagCombound != null && tagCombound.hasKey(EnergyStorageItem.ENERGY_KEY);
	}
}
