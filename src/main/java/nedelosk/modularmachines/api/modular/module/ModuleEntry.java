package nedelosk.modularmachines.api.modular.module;

import nedelosk.modularmachines.api.IModularAssembler;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.RendererSides;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssenbler;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class ModuleEntry {

	protected ResourceLocation guiTextureOverlay = new ResourceLocation("modularmachines", "textures/gui/modular_assembler_overlay.png");
	
	public int x;
	public int y;
	public ModuleEntry parent;
	public String moduleName;
	public String moduleName2;
	public IModularAssembler assembler;
	public int ID;
	public String page;
	public RendererSides[] rendererSides;
	public int tier;
	public boolean isChain;
	public int chainTile;
	
	public ModuleEntry(int ID, int x, int y, String moduleName, String page, RendererSides... rendererSides) {
		this.x = x + 12;
		this.y = y + 12;
		this.ID = ID;
		this.moduleName = moduleName;
		this.page = page;
		this.rendererSides = rendererSides;
	}
	
	public ModuleEntry setTier(int tier) {
		this.tier = tier;
		return this;
	}
	
	public ModuleEntry() {
	}
	
	public ModuleEntry(int ID, int x, int y, String moduleName, ModuleEntry parent, String page, RendererSides... rendererSides) {
		this.x = x + 12;
		this.y = y + 12;
		this.parent = parent;
		this.ID = ID;
		this.moduleName = moduleName;
		this.page = page;
		this.rendererSides = rendererSides;
	}
	
	public ModuleEntry(int ID, int x, int y, String moduleName, ModuleEntry parent, String page, boolean isChain, int chainTile, RendererSides... rendererSides) {
		this.x = x + 12;
		this.y = y + 12;
		this.parent = parent;
		this.ID = ID;
		this.moduleName = moduleName;
		this.page = page;
		this.rendererSides = rendererSides;
		this.chainTile = chainTile;
		this.isChain = isChain;
	}
	
	public ModuleEntry(int ID, int x, int y, String moduleName, String moduleName2, String page, RendererSides... rendererSides) {
		this.x = x + 12;
		this.y = y + 12;
		this.ID = ID;
		this.moduleName = moduleName;
		this.moduleName2 = moduleName2;
		this.page = page;
		this.rendererSides = rendererSides;
	}
	
	public ModuleEntry(int ID, int x, int y, String moduleName, String moduleName2, ModuleEntry parent, String page, RendererSides... rendererSides) {
		this.x = x + 12;
		this.y = y + 12;
		this.parent = parent;
		this.ID = ID;
		this.moduleName = moduleName;
		this.moduleName2 = moduleName2;
		this.page = page;
		this.rendererSides = rendererSides;
	}
	
	public ModuleEntry(int ID, int x, int y, String moduleName, String moduleName2, ModuleEntry parent, String page, boolean isChain, int chainTile, RendererSides... rendererSides) {
		this.x = x + 12;
		this.y = y + 12;
		this.parent = parent;
		this.ID = ID;
		this.moduleName = moduleName;
		this.moduleName2 = moduleName2;
		this.page = page;
		this.rendererSides = rendererSides;
		this.chainTile = chainTile;
		this.isChain = isChain;
	}
	
	public void setAssembler(IModularAssembler assembler) {
		this.assembler = assembler;
	}
	
	public int getTier() {
		if(tier != 0)
			return tier;
		if(assembler.getStackInSlot(parent.page, parent.ID) == null)
			return 1;
		return (isChain && chainTile == 2) ? ModularMachinesApi.getModuleItem(parent.moduleName, assembler.getStackInSlot(parent.page, parent.ID)).getTier() - 1 : ModularMachinesApi.getModuleItem(parent.moduleName, assembler.getStackInSlot(parent.page, parent.ID)).getTier();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setInteger("chainTile", this.chainTile);
		nbtTag.setInteger("ID", this.ID);
		nbtTag.setString("page", this.page);
		nbtTag.setInteger("tier", this.tier);
		nbtTag.setInteger("x", this.x);
		nbtTag.setInteger("y", this.y);
		nbtTag.setBoolean("isChain", this.isChain);
		nbtTag.setString("moduleName", this.moduleName);
		int[] RendererSides = new int[rendererSides.length];
		int i = 0;
		for(RendererSides side : rendererSides)
		{
			RendererSides[i] = side.ordinal();
			i++;
		}
		nbt.setIntArray("RendererSides", RendererSides);
		nbtTag.setTag("Entry", nbtTag);
		
		NBTTagCompound nbtTagAssembler = new NBTTagCompound();
		assembler.writeToNBT(nbtTagAssembler);
		nbtTag.setTag("Assembler", nbtTagAssembler);
		
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		NBTTagCompound nbtTag = nbt.getCompoundTag("Entry");
		chainTile = nbtTag.getInteger("chainTile");
		ID = nbtTag.getInteger("ID");
		page = nbtTag.getString("page");
		tier = nbtTag.getInteger("tier");
		x = nbtTag.getInteger("x");
		y = nbtTag.getInteger("y");
		isChain = nbtTag.getBoolean("isChain");
		moduleName = nbtTag.getString("moduleName");
		int[] rendererSides = nbt.getIntArray("RendererSides");
		this.rendererSides = new RendererSides[rendererSides.length];
		for(int i = 0;i < rendererSides.length;i++)
		{
			this.rendererSides[i] = RendererSides.values()[rendererSides[i]];
		}
		
		NBTTagCompound nbtTagAssembler = nbt.getCompoundTag("Assembler");
		nbtTagAssembler.setString("ID", "tile.modular.assenbler");
		assembler = (IModularAssembler) TileEntity.createAndLoadEntity(nbtTagAssembler);
		assembler.readFromNBT(nbtTagAssembler);
	}
	
}
