package modularmachines.api.property;

import net.minecraft.nbt.NBTBase;

public abstract class PropertyBase<V, N extends NBTBase, P extends IPropertyProvider> implements IProperty<V, N, P> {

	private final Class<? extends V> valueClass;
	private final String name;
	private final V defaultValue;

	protected PropertyBase(String name, Class<? extends V> valueClass, V defaultValue) {
		this.valueClass = valueClass;
		this.name = name;
		this.defaultValue = defaultValue;
	}

	@Override
	public V getDefaultValue() {
		return defaultValue;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Class<? extends V> getValueClass() {
		return this.valueClass;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof PropertyBase)) {
			return false;
		} else {
			PropertyBase<?, N, P> propertyhelper = (PropertyBase) obj;
			return this.valueClass.equals(propertyhelper.valueClass) && this.name.equals(propertyhelper.name);
		}
	}

	@Override
	public int hashCode() {
		return 31 * this.valueClass.hashCode() + this.name.hashCode();
	}
}