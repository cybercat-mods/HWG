package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.GrenadeLauncherModel;
import mod.azure.hwg.item.weapons.GrenadeLauncherItem;

public class GrenadeLauncherRender extends GeoItemRenderer<GrenadeLauncherItem> {
    public GrenadeLauncherRender() {
        super(new GrenadeLauncherModel());
    }
}