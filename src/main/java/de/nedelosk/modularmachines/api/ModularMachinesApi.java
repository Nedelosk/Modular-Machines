package de.nedelosk.modularmachines.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.nedelosk.modularmachines.api.transport.node.INodeType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

public class ModularMachinesApi {

	private static ArrayList<INodeType> nodeTypes = Lists.newArrayList();

	public static ArrayList<INodeType> getNodeTypes() {
		return nodeTypes;
	}

	public static void addNodeTypes(INodeType type) {
		nodeTypes.add(type);
	}

	public static INodeType getNodeType(int index) {
		return nodeTypes.get(index);
	}
}
