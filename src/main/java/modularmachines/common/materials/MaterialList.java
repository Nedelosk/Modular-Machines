package modularmachines.common.materials;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaterialList implements Iterable<EnumMaterial> {

	private EnumMaterial[] materials;

	public MaterialList(EnumMaterial... materials) {
		this.materials = materials;
	}

	public EnumMaterial[] getMaterials() {
		return materials;
	}

	public EnumMaterial get(int index) {
		if (index >= materials.length) {
			return null;
		}
		return materials[index];
	}

	public int getIndex(EnumMaterial material) {
		if (material == null) {
			return -1;
		}
		int index = 0;
		for (EnumMaterial otherMaterial : materials) {
			if (otherMaterial.equals(material)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public EnumMaterial getFromOre(String oreDict) {
		for (EnumMaterial material : materials) {
			String[] oreDicts = material.getOreDicts();
			if (oreDicts != null && oreDicts.length > 0) {
				for (String otherOreDict : oreDicts) {
					if (otherOreDict.equals(oreDict)) {
						return material;
					}
				}
			}
		}
		return null;
	}

	public String getName(int index) {
		if (index >= materials.length) {
			return null;
		}
		EnumMaterial material = materials[index];
		if (material == null) {
			return null;
		}
		return material.getName();
	}

	public int getColor(int index) {
		if (index >= materials.length) {
			return -1;
		}
		return materials[index].getColor();
	}

	public int size() {
		return materials.length;
	}

	@Override
	public Iterator<EnumMaterial> iterator() {
		return new Iterator<EnumMaterial>() {

			int index = 0;

			@Override
			public boolean hasNext() {
				return (index < materials.length);
			}

			@Override
			public EnumMaterial next() throws NoSuchElementException {
				if (index >= materials.length) {
					throw new NoSuchElementException("Array index: " + index);
				}
				index++;
				return materials[index - 1];
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
