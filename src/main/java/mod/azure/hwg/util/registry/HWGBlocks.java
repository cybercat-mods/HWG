package mod.azure.hwg.util.registry;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.blocks.FuelTankBlock;
import mod.azure.hwg.blocks.GunTableBlock;
import mod.azure.hwg.blocks.TickingLightBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HWGBlocks {

	public static final Block FUEL_TANK = block(new FuelTankBlock(), "fuel_tank");
	public static final GunTableBlock GUN_TABLE = block(new GunTableBlock(), "gun_table");
	public static final TickingLightBlock TICKING_LIGHT_BLOCK = block(new TickingLightBlock(), "lightblock");

	static <T extends Block> T block(T c, String id) {
		Registry.register(Registries.BLOCK, new Identifier(HWGMod.MODID, id), c);
		return c;
	}
}
