package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.SniperModel;
import mod.azure.hwg.item.weapons.SniperItem;

public class SniperRender extends GeoItemRenderer<SniperItem> {
    public SniperRender() {
        super(new SniperModel());
    }
}