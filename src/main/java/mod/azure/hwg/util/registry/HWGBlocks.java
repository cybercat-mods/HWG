package mod.azure.hwg.util.registry;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.blocks.FuelTankBlock;
import mod.azure.hwg.blocks.GunTableBlock;
import mod.azure.hwg.blocks.TickingLightBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class HWGBlocks {

    public static final Block FUEL_TANK = block(new FuelTankBlock(), "fuel_tank");
    public static final GunTableBlock GUN_TABLE = block(new GunTableBlock(), "gun_table");
    public static final TickingLightBlock TICKING_LIGHT_BLOCK = block(new TickingLightBlock(), "lightblock");

    static <T extends Block> T block(T c, String id) {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(HWGMod.MODID, id), c);
        return c;
    }
}
