package de.nedelosk.modularmachines.api.modules.state;

import net.minecraft.nbt.NBTBase;

public abstract class PropertyBase<V, N extends NBTBase> implements IProperty<V, N>{
	private final Class<? extends V> valueClass;
	private final String name;
	private final V defaultValue;

	protected PropertyBase(String name, Class<? extends V> valueClass, V defaultValue){
		this.valueClass = valueClass;
		this.name = name;
		this.defaultValue = defaultValue;
	}

	@Override
	public V getDefaultValue() {
		return defaultValue;
	}

	@Override
	public String getName(){
		return this.name;
	}

	@Override
	public Class<? extends V> getValueClass(){
		return this.valueClass;
	}

	@Override
	public boolean equals(Object p_equals_1_){
		if (this == p_equals_1_){
			return true;
		} else if (!(p_equals_1_ instanceof PropertyBase)){
			return false;
		}else {
			PropertyBase<?, N> propertyhelper = (PropertyBase)p_equals_1_;
			return this.valueClass.equals(propertyhelper.valueClass) && this.name.equals(propertyhelper.name);
		}
	}

	@Override
	public int hashCode() {
		return 31 * this.valueClass.hashCode() + this.name.hashCode();
	}
}