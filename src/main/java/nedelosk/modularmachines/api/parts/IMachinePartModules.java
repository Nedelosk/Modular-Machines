package nedelosk.modularmachines.api.parts;

import java.util.HashMap;
import java.util.List;

import nedelosk.modularmachines.api.materials.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IMachinePartModules extends IMachinePart {

	PartType[][] getProducerComponents();
	
	HashMap<Integer, String> getModules();
	
	int getModulesIDs();
	
	ItemStack buildItem(int id, List<Material> materials);
	
	NBTTagCompound buildItemNBT(int id, List<Material> materials);
	
	boolean validComponent(int id, int slot, ItemStack stack);
	
	Material[] getModuleMaterials(ItemStack stack);
	
	Material[] getPartMaterials(ItemStack stack);
	
	int getModuleID(ItemStack stack);
	
	void updateComponents(int ID);
	
}
