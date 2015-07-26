package nedelosk.modularmachines.common.techtree;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class TechTree implements IExtendedEntityProperties {

	public EntityPlayer player;
	public int points;
	public static final String EXT_PROP_NAME = "TechTree";
	
	public TechTree(EntityPlayer player) {
		this.player = player;
	}
	
	public static void addTechPoints(EntityPlayer player, int points)
	{
		get(player).points = get(player).points + points;
	}
	
	public static int getTechPoints(EntityPlayer player)
	{
		return get(player).points;
	}
	
	public static void removeTechPoints(EntityPlayer player, int points)
	{
		if(get(player).points > points)
			get(player).points = get(player).points - points;
		else
			get(player).points = 0;
	}
	
	public static final TechTree get(EntityPlayer player)
	{
		return (TechTree) player.getExtendedProperties(EXT_PROP_NAME);
	}
	
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(EXT_PROP_NAME, new TechTree(player));
	}

	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		nbt.setInteger("TechPoints", points);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		points = nbt.getInteger("TechPoints");
	}

	@Override
	public void init(Entity entity, World world) {
		
	}
	
}
