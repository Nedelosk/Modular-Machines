package nedelosk.forestday.structure.base.blocks.tile;

public class TileStructureBrick extends TileStructure {

	public TileStructureBrick(int maxHeat, String uid) {
		super(maxHeat, "brick" + uid);
	}
	
	public TileStructureBrick() {
		super(1000, "brick");
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		
	}

}
