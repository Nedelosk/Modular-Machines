package nedelosk.modularmachines.common.machines;

import nedelosk.modularmachines.api.basic.machine.part.MachineMaterial;
import nedelosk.modularmachines.common.core.MMRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MachineManager {

	public static MachineMaterial getMaterial(ItemStack stack){
		NBTTagCompound nbtTag = stack.getTagCompound();
		if(nbtTag == null)
			return null;
		NBTTagCompound nbtPart = nbtTag.getCompoundTag("Part");
		if(!nbtPart.hasKey("Material"))
			return null;
		for(MachineMaterial material : MMRegistry.toolMaterials){
			if(material.identifier.equals(nbtPart.getString("Material")))
				return material;
		}
		return null;
	}
	
	public static void setMaterial(ItemStack stack, MachineMaterial material){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey("Part")){
			stack.getTagCompound().setTag("Part", new NBTTagCompound());;
		}
		NBTTagCompound nbtPart = stack.getTagCompound().getCompoundTag("Part");
		if(!nbtPart.hasKey("Material"))
			stack.getTagCompound().getCompoundTag("Part").setString("Material", material.identifier);
	}
	
}
