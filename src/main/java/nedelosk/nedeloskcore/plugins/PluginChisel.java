package nedelosk.nedeloskcore.plugins;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.nedeloskcore.api.Material;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.api.Material.MaterialType;
import nedelosk.nedeloskcore.plugins.basic.Plugin;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PluginChisel extends Plugin {

	public Block brickSandy = GameRegistry.findBlock(getRequiredMod(), "brick.sandy");
	public Block brickInfernal = GameRegistry.findBlock(getRequiredMod(), "brick.infernal");
	public Block brickQuarried = GameRegistry.findBlock(getRequiredMod(), "brick.quarried");
	public Block brickBleachedbone = GameRegistry.findBlock(getRequiredMod(), "brick.bleachedbone");
	public Block brickAbyssal = GameRegistry.findBlock(getRequiredMod(), "brick.abyssal");
	public Block brickFrostbound = GameRegistry.findBlock(getRequiredMod(), "brick.frostbound");
	public Block brickBloodstained = GameRegistry.findBlock(getRequiredMod(), "brick.bloodstained");
	
	@Override
	public void preInit() {
		NCoreApi.registerMaterial(new Material("sandyBrick", 2000, 5F, 1F, MaterialType.BRICK, "blockBrickSandy", new ItemStack(brickSandy, 1, 0)));
		NCoreApi.registerMaterial(new Material("infernalBrick", 2000, 5F, 1F, MaterialType.BRICK, "blockBrickInfernal", new ItemStack(brickInfernal, 1, 0)));
		NCoreApi.registerMaterial(new Material("quarriedBrick", 2000, 5F, 1F, MaterialType.BRICK, "blockBrickQuarried", new ItemStack(brickQuarried, 1, 0)));
		NCoreApi.registerMaterial(new Material("bleachedboneBrick", 2000, 5F, 1F, MaterialType.BRICK, "blockBrickBleachedbone", new ItemStack(brickBleachedbone, 1, 0)));
		NCoreApi.registerMaterial(new Material("abyssalBrick", 2000, 5F, 1F, MaterialType.BRICK, "blockBrickAbyssal", new ItemStack(brickAbyssal, 1, 0)));
		NCoreApi.registerMaterial(new Material("frostboundBrick", 2000, 5F, 1F, MaterialType.BRICK, "blockBrickFrostbound", new ItemStack(brickFrostbound, 1, 0)));
		NCoreApi.registerMaterial(new Material("bloodstainedBrick", 2000, 5F, 1F, MaterialType.BRICK, "blockBrickBloodstained", new ItemStack(brickBloodstained, 1, 0)));
	}
	
	@Override
	public void init() {
		OreDictionary.registerOre("blockBrickSandy", brickSandy);
		OreDictionary.registerOre("blockBrickInfernal", brickInfernal);
		OreDictionary.registerOre("blockBrickQuarried", brickQuarried);
		OreDictionary.registerOre("blockBrickBleachedbone", brickBleachedbone);
		OreDictionary.registerOre("blockBrickAbyssal", brickAbyssal);
		OreDictionary.registerOre("blockBrickFrostbound", brickFrostbound);
		OreDictionary.registerOre("blockBrickBloodstained", brickBloodstained);
	}
	
	@Override
	public String getRequiredMod() {
		return "Railcraft";
	}
	
}
