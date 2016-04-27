package de.nedelosk.forestmods.library.material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class MaterialRegistry {

	private static ArrayList<IMaterial> materials = Lists.newArrayList();

	public static void registerMaterial(IMaterial material) {
		materials.add(material);
	}

	public static List<IMaterial> getMaterials() {
		return Collections.unmodifiableList(materials);
	}

	public static IMaterial getMaterial(String name) {
		for(IMaterial material : materials) {
			if (material.getName().equals(name)) {
				return material;
			}
		}
		return null;
	}
}
