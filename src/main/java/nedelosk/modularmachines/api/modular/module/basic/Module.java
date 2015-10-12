package nedelosk.modularmachines.api.modular.module.basic;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.core.MMRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Module implements IModule{
	
	public String modifier;
	protected Material material;
	
	public Module() {
	}
	
	public Module(String modifier) {
		this.modifier = modifier;
	}
	
	public Module(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("Modifier"))
			this.modifier = nbt.getString("Modifier");
		if(nbt.hasKey("Material")){
			for(Material mat : MMRegistry.materials){
				if(mat.identifier.equals(nbt.getString("Material")))
					material = mat;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		readFromNBT(nbt);
	}

	@Override
	public void update(IModular modular) {
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if(modifier != null)
			nbt.setString("Modifier", modifier);
		if(material != null)
			nbt.setString("Material", material.identifier);
	}
	
	@Override
	public String getModifier()
	{
		return modifier;
	}

	@Override
	public String getName() {
		return "module" + getModuleName() + ((modifier != null) ? modifier : "");
	}
	
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return null;
	}
	
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return null;
	}
	
	@Override
	public Material getMaterial() {
		return material;
	}
	
	@Override
	public void setMaterial(Material material) {
		this.material = material;
	}

}
