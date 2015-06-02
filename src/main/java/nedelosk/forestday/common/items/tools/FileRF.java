package nedelosk.forestday.common.items.tools;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FileRF extends FileEnergy implements IEnergyContainerItem{

	public FileRF(int capacity) {
		super("file.energy.rf", capacity);
		this.capacity = capacity;
		this.setTextureName("forestday:file/file_energy_rf");
	}
	
    protected int capacity;
    protected int maxReceive = 80000;
    protected int maxExtract = 120;
    
    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
    	ItemStack fileEnergy = new ItemStack(id, 1, 0);
    	ItemStack file = new ItemStack(id, 1, 0);
    	file.setTagCompound(new NBTTagCompound());
    	fileEnergy.setTagCompound(new NBTTagCompound());
    	fileEnergy.getTagCompound().setInteger("Energy", capacity);
    	file.getTagCompound().setInteger("Energy", 0);
    	list.add(file);
    	list.add(fileEnergy);
    }
    
    @Override
    protected int removeEnergy(ItemStack stack)
    {
    	return extractEnergy(stack, maxExtract, false);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        NBTTagCompound tags = stack.getTagCompound();
        if (tags.hasKey("Energy"))
        {
            String color = "";
            int RF = tags.getInteger("Energy");

            if (RF != 0)
            {
                if (RF <= this.getMaxEnergyStored(stack) / 3)
                    color = "\u00a74";
                else if (RF > this.getMaxEnergyStored(stack) * 2 / 3)
                    color = "\u00a72";
                else
                    color = "\u00a76";
            }

            String energy = new StringBuilder().append(color).append(tags.getInteger("Energy")).append("/").append(getMaxEnergyStored(stack)).append(" RF").toString();
            list.add(energy);
        }
    }
    
    @Override
    public int getMaxEnergyExtract() 
    {
    	return maxExtract;
    }

    @Override
    public int receiveEnergy (ItemStack container, int maxReceive, boolean simulate)
    {
        NBTTagCompound tags = container.getTagCompound();
        if (tags == null || !tags.hasKey("Energy"))
            return 0;
        int energy = tags.getInteger("Energy");
        int energyReceived = tags.hasKey("EnergyReceiveRate") ? tags.getInteger("EnergyReceiveRate") : this.maxReceive;
        int maxEnergy = tags.hasKey("EnergyMax") ? tags.getInteger("EnergyMax") : this.capacity;

        energyReceived = Math.min(maxEnergy - energy, Math.min(energyReceived, maxReceive));
        if (!simulate)
        {
            energy += energyReceived;
            tags.setInteger("Energy", energy);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy (ItemStack container, int maxExtract, boolean simulate)
    {
        NBTTagCompound tags = container.getTagCompound();
        if (tags == null || !tags.hasKey("Energy"))
        {
            return 0;
        }
        int energy = tags.getInteger("Energy");
        int energyExtracted = tags.hasKey("EnergyExtractionRate") ? tags.getInteger("EnergyExtractionRate") : this.maxExtract; // backup value

        energyExtracted = Math.min(energy, Math.min(energyExtracted, maxExtract));
        if (!simulate)
        {
            energy -= energyExtracted;
            tags.setInteger("Energy", energy);
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored (ItemStack container)
    {
        NBTTagCompound tags = container.getTagCompound();
        if (tags == null || !tags.hasKey("Energy"))
        {
            return 0;
        }
        return tags.getInteger("Energy");
    }

    @Override
    public int getMaxEnergyStored (ItemStack container)
    {
        NBTTagCompound tags = container.getTagCompound();
        if (tags == null || !tags.hasKey("Energy"))
            return 0;

        if (tags.hasKey("EnergyMax"))
            return tags.getInteger("EnergyMax");
        return capacity;
    }


}
