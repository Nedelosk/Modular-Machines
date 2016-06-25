package de.nedelosk.modularmachines.api;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.transport.node.INodeType;

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
