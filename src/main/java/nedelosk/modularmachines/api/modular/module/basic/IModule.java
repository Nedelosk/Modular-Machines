package nedelosk.modularmachines.api.modular.module.basic;

import java.util.ArrayList;
import java.util.HashMap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.type.Types.Type;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public interface IModule {
	
	String getName(ModuleStack stack);
	
	String getRegistryName();
	
	String getModuleName();
	
	String getModifier(ModuleStack stack);
	
	String getTypeModifier(ModuleStack stack);
	
	@SideOnly(Side.CLIENT)
	IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack);
	
	@SideOnly(Side.CLIENT)
	IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile);
	
	ArrayList<Type> getTypes();
	
	void addType(Type tier, String modifier, IProducer producer);
	
	void addType(Type tier);
	
	HashMap<Type, IProducer> getProducer();
}
