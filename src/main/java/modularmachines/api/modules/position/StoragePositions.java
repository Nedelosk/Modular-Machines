package modularmachines.api.modules.position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class StoragePositions<K> {

	private final ImmutableMap<K, IStoragePosition> positionMap;
	private final ImmutableList<IStoragePosition> positionList;
	private final Class<? extends K> keyClass;

	public StoragePositions(ImmutableMap<K, IStoragePosition> positionMap) {
		this((Class<? extends K>) positionMap.keySet().iterator().next().getClass(), positionMap);
	}

	public StoragePositions(Class<? extends K> keyClass, ImmutableMap<K, IStoragePosition> positionMap) {
		this.keyClass = keyClass;
		this.positionMap = positionMap;
		List<IStoragePosition> positions = new ArrayList<>(positionMap.values());
		Collections.sort(positions, StoragePositionComperator.INSTANCE);
		this.positionList = ImmutableList.copyOf(positions);
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
		return positionList;
	}

	private enum StoragePositionComperator implements Comparator<IStoragePosition> {
		INSTANCE;

		@Override
		public int compare(IStoragePosition arg0, IStoragePosition arg1) {
			return arg0.getProperty(arg1);
		}
	}
}
