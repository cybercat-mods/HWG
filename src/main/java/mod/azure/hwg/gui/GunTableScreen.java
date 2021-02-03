package mod.azure.hwg.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class GunTableScreen extends CottonInventoryScreen<GunTableDescription> {
    public GunTableScreen(GunTableDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}