package nedelosk.modularmachines.api.modular.module.basic;

import java.util.ArrayList;

import akka.japi.Pair;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.tier.Tiers;
import nedelosk.modularmachines.api.modular.tier.Tiers.Tier;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModule extends INBTTagable {
	
	String getName(ModuleStack stack);
	
	String getModuleName();
	
	void update(IModular modular, ModuleStack stack);
	
	void readFromNBT(NBTTagCompound nbt, IModular modular);
	
	String getModifier(ModuleStack stack);
	
	String getTierModifier(ModuleStack stack);
	
	@SideOnly(Side.CLIENT)
	IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack);
	
	@SideOnly(Side.CLIENT)
	IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile);
	
	ArrayList<Tier> getTiers();
	
	void addTier(Tier tier);
}
