package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.FlareGunModel;
import mod.azure.hwg.item.weapons.FlareGunItem;

public class FlareGunRender extends GeoItemRenderer<FlareGunItem> {
    public FlareGunRender() {
        super(new FlareGunModel());
    }
}