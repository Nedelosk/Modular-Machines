package nedelosk.modularmachines.api.parts;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public interface IMachinePart extends IMachine, IMachineComponent {

	String getTagKey();
	
	ItemStack buildItemFromStacks(ItemStack[] stacks);
	
	boolean validComponent(int slot, ItemStack stack);
	
	ItemStack getMachine(ItemStack stack);
	
	PartType[] getMachineComponents();
	
	String getPartName();
	
	@SideOnly(Side.CLIENT)
	IItemRenderer getPartRenderer();
	
	Material[] getMaterials(ItemStack stack);
	
	ModuleStack buildModule(ItemStack stack);
	
}
