package de.nedelosk.modularmachines.api.modules.position;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class StoragePositions<K> {

	private final ImmutableMap<K, IStoragePosition> positionMap;
	private final Class<? extends K> keyClass;

	public StoragePositions(ImmutableMap<K, IStoragePosition> positionMap) {
		this((Class<? extends K>) positionMap.keySet().iterator().next().getClass(), positionMap);
	}

	public StoragePositions(Class<? extends K> keyClass, ImmutableMap<K, IStoragePosition> positionMap) {
		this.keyClass = keyClass;
		this.positionMap = positionMap;
	}

	@Nonnull
	public ImmutableMap<K, IStoragePosition> asMap() {
		return positionMap;
	}

	@Nonnull
	public Class<? extends K> getKeyClass() {
		return keyClass;
	}

	@Nonnull
	public ImmutableList<IStoragePosition> asList() {
		return positionMap.values().asList();
	}
}
