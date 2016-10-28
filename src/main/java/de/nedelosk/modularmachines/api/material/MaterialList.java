package de.nedelosk.modularmachines.api.material;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaterialList<M extends IMaterial> implements Iterable<M> {

	private M[] materials;

	public MaterialList(M... materials) {
		this.materials = materials;
	}

	public M[] getMaterials() {
		return materials;
	}

	public M get(int index) {
		if (index >= materials.length) {
			return null;
		}
		return materials[index];
	}

	public int getIndex(M material) {
		if (material == null) {
			return -1;
		}
		int index = 0;
		for(M otherMaterial : materials) {
			if (otherMaterial.equals(material)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public M getFromOre(String oreDict) {
		for(M otherMaterial : materials) {
			if (otherMaterial instanceof IMetalMaterial) {
				IMetalMaterial metalMaterial = (IMetalMaterial) otherMaterial;
				String[] oreDicts = metalMaterial.getOreDicts();
				if (oreDicts != null && oreDicts.length > 0) {
					for(String otherOreDict : oreDicts) {
						if (otherOreDict.equals(oreDict)) {
							return otherMaterial;
						}
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
		M material = materials[index];
		if (material == null) {
			return null;
		}
		return material.getName();
	}

	public int getColor(int index) {
		if (index >= materials.length) {
			return -1;
		}
		M material = materials[index];
		if (material instanceof IColoredMaterial) {
			return ((IColoredMaterial) material).getColor();
		}
		return -1;
	}

	public int size() {
		return materials.length;
	}

	@Override
	public Iterator<M> iterator() {
		return new Iterator<M>() {

			int index = 0;

			@Override
			public boolean hasNext() {
				return (index < materials.length);
			}

			@Override
			public M next() throws NoSuchElementException {
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
