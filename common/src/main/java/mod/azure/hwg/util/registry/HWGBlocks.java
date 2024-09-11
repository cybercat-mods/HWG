package mod.azure.hwg.util.registry;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.blocks.FuelTankBlock;
import mod.azure.hwg.blocks.GunTableBlock;
import mod.azure.hwg.util.registry.interfaces.CommonBlockRegistryInterface;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public record HWGBlocks() {

    public static final Supplier<Block> FUEL_TANK = CommonBlockRegistryInterface.registerBlock(CommonMod.MOD_ID,"fuel_tank", () -> new FuelTankBlock(
            BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion()));
    public static final Supplier<Block> GUN_TABLE = CommonBlockRegistryInterface.registerBlock(CommonMod.MOD_ID,"gun_table", () -> new GunTableBlock(
            BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));

    public static void init() {
    }
}
