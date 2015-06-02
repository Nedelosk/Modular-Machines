package nedelosk.forestday.structure.base.blocks.tile;


public class TileStructureMetal extends TileStructure{

	public TileStructureMetal(int maxHeat, String uid) {
		super(maxHeat, "metal" + uid);
	}
	
	public TileStructureMetal() {
		super(2000, "metal");
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		
	}

}
