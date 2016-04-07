package de.nedelosk.forestmods.api.utils;

public class ModuleUID {

	private final String categoryUID;
	private final String moduleUID;

	public ModuleUID(String categoryUID, String moduleUID) {
		if (categoryUID != null && !categoryUID.isEmpty()) {
			this.categoryUID = categoryUID;
		} else {
			this.categoryUID = "default";
		}
		if (moduleUID != null && !moduleUID.isEmpty()) {
			this.moduleUID = moduleUID;
		} else {
			this.moduleUID = "default";
		}
	}

	public ModuleUID(String UID) {
		String s1 = "default";
		String s2 = UID;
		int i = UID.indexOf(58);
		if (i >= 0) {
			s2 = UID.substring(i + 1, UID.length());
			if (i > 1) {
				s1 = UID.substring(0, i);
			}
		}
		this.categoryUID = s1.toLowerCase();
		this.moduleUID = s2;
	}

	public String getModuleUID() {
		return this.moduleUID;
	}

	public String getCategoryUID() {
		return this.categoryUID;
	}

	@Override
	public String toString() {
		return this.categoryUID + ":" + this.moduleUID;
	}

	@Override
	public boolean equals(Object UID) {
		if (this == UID) {
			return true;
		} else if (!(UID instanceof ModuleUID)) {
			return false;
		} else {
			ModuleUID resourcelocation = (ModuleUID) UID;
			return this.categoryUID.equals(resourcelocation.categoryUID) && this.moduleUID.equals(resourcelocation.moduleUID);
		}
	}

	@Override
	public int hashCode() {
		return 31 * this.categoryUID.hashCode() + this.moduleUID.hashCode();
	}
}