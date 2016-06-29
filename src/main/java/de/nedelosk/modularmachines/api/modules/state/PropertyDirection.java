package de.nedelosk.modularmachines.api.modules.state;

import net.minecraft.util.EnumFacing;

public class PropertyDirection extends PropertyEnum<EnumFacing>{
	public PropertyDirection(String name){
		super(name, EnumFacing.class, null);
	}
}