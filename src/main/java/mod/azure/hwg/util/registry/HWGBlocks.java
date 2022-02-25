package mod.azure.hwg.util.registry;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.blocks.FuelTankBlock;
import mod.azure.hwg.blocks.GunTableBlock;
import mod.azure.hwg.blocks.TickingLightBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HWGBlocks {

	public static final Block FUEL_TANK = block(new FuelTankBlock(), "fuel_tank");
	public static final GunTableBlock GUN_TABLE = block(new GunTableBlock(), "gun_table");
	public static final TickingLightBlock TICKING_LIGHT_BLOCK = block(new TickingLightBlock(), "lightblock");

	static <T extends Block> T block(T c, String id) {
		Registry.register(Registry.BLOCK, new Identifier(HWGMod.MODID, id), c);
		return c;
	}
}
