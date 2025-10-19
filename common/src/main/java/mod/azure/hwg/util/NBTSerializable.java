package mod.azure.hwg.util;

import net.minecraft.nbt.CompoundTag;

public interface NBTSerializable {

    void load(CompoundTag compoundTag);

    void save(CompoundTag compoundTag);
}
