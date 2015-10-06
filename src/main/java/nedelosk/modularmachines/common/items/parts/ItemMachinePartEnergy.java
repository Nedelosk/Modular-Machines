package nedelosk.modularmachines.common.items.parts;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.Optional;
import nedelosk.modularmachines.api.parts.PartType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@Optional.Interface(modid = "CoFHAPI|energy", iface = "cofh.api.energy.IEnergyContainerItem")
public abstract class ItemMachinePartEnergy extends ItemMachinePart implements IEnergyContainerItem {

    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;
	
	public ItemMachinePartEnergy(PartType[] requiredComponents, String name) {
		super(requiredComponents, name);
	}
	
    @Override
    public int getMaxDamage (ItemStack stack)
    {
        return capacity;
    }

    @Override
    public int getDamage(ItemStack stack) {
        NBTTagCompound tags = stack.getTagCompound();
        if (tags == null)
        {
            return 0;
        }
        if (tags.getCompoundTag(getTagKey()).hasKey("Energy"))
        {
            int energy = tags.getCompoundTag(getTagKey()).getInteger("Energy");
            int max = getMaxEnergyStored(stack);
            if(energy > 0) {
                int damage = ((max - energy) * 100) / max;
                if(damage == 0 && max-energy > 0)
                    damage = 1;
                return damage;
            }
        }
        return 0;
    }

    @Override
    public int getDisplayDamage(ItemStack stack) {
        return getDamage(stack);
    }

    /* IEnergyContainerItem */
    @Override
    @Optional.Method(modid = "CoFHAPI|energy")
    public int receiveEnergy (ItemStack container, int maxReceive, boolean simulate)
    {
        NBTTagCompound tags = container.getTagCompound();
        if (tags == null || !tags.getCompoundTag(getTagKey()).hasKey("Energy"))
            return 0;
        int energy = tags.getCompoundTag(getTagKey()).getInteger("Energy");
        int energyReceived = tags.hasKey("EnergyReceiveRate") ? tags.getInteger("EnergyReceiveRate") : this.maxReceive;
        int maxEnergy = this.capacity; // backup value

        // calculate how much we can receive
        energyReceived = Math.min(maxEnergy - energy, Math.min(energyReceived, maxReceive));
        if (!simulate)
        {
            energy += energyReceived;
            tags.getCompoundTag(getTagKey()).setInteger("Energy", energy);
            //container.setItemDamage(1 + (getMaxEnergyStored(container) - energy) * (container.getMaxDamage() - 2) / getMaxEnergyStored(container));
        }
        return energyReceived;
    }

    @Override
    @Optional.Method(modid = "CoFHAPI|energy")
    public int extractEnergy (ItemStack container, int maxExtract, boolean simulate)
    {
        NBTTagCompound tags = container.getTagCompound();
        if (tags == null || !tags.getCompoundTag(getTagKey()).hasKey("Energy"))
        {
            return 0;
        }
        int energy = tags.getCompoundTag(getTagKey()).getInteger("Energy");
        int energyExtracted = tags.hasKey("EnergyExtractionRate") ? tags.getInteger("EnergyExtractionRate") : this.maxExtract; // backup value

        // calculate how much we can extract
        energyExtracted = Math.min(energy, Math.min(energyExtracted, maxExtract));
        if (!simulate)
        {
            energy -= energyExtracted;
            tags.getCompoundTag(getTagKey()).setInteger("Energy", energy);
            //container.setItemDamage(1 + (getMaxEnergyStored(container) - energy) * (container.getMaxDamage() - 1) / getMaxEnergyStored(container));
        }
        return energyExtracted;
    }

    @Override
    @Optional.Method(modid = "CoFHAPI|energy")
    public int getEnergyStored (ItemStack container)
    {
        NBTTagCompound tags = container.getTagCompound();
        if (tags == null || !tags.getCompoundTag(getTagKey()).hasKey("Energy"))
        {
            return 0;
        }
        
        return tags.getCompoundTag(getTagKey()).getInteger("Energy");
    }

    @Override
    @Optional.Method(modid = "CoFHAPI|energy")
    public int getMaxEnergyStored (ItemStack container)
    {
        NBTTagCompound tags = container.getTagCompound();
        if (tags == null || !tags.getCompoundTag(getTagKey()).hasKey("Energy"))
            return 0;

        if (tags.hasKey("EnergyMax"))
            return tags.getCompoundTag(getTagKey()).getInteger("EnergyMax");
        // backup
        return capacity;
    }

}
