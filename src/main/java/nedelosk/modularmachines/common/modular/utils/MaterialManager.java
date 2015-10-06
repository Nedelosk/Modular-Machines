package nedelosk.modularmachines.common.modular.utils;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.common.core.MMRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MaterialManager {

	public static Material getMaterial(ItemStack stack){
		NBTTagCompound nbtTag = stack.getTagCompound();
		if(nbtTag == null)
			return null;
		NBTTagCompound nbtPart = nbtTag.getCompoundTag("Part");
		if(!nbtPart.hasKey("Material"))
			return null;
		for(Material material : MMRegistry.materials){
			if(material.identifier.equals(nbtPart.getString("Material")))
				return material;
		}
		return null;
	}
	
	public static void setMaterial(ItemStack stack, Material material){
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
