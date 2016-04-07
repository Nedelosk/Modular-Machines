package de.nedelosk.forestmods.common.items;

public class ItemFile extends ItemToolCrafting {

	public ItemFile(int damagemax, int damage, String uln, String nameTexture, int tier, Material material) {
		super(uln, damagemax, tier, material, nameTexture, damage);
		this.setNoRepair();
	}
}
