package nedelosk.modularmachines.common.blocks.tile;

import nedelosk.modularmachines.api.modular.material.Material;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.nedeloskcore.common.blocks.tile.TileBase;
import net.minecraft.nbt.NBTTagCompound;

public class TileMaterial extends TileBase {
	
	public Material material;
	
	public TileMaterial() {
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setString("Material", material.identifier);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(Material material : MMRegistry.materials){
			if(material.identifier.equals(nbt.getString("Material")))
				this.material = material;
		}
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		
	}
	
}
