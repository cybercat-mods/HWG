package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.ShotgunModel;
import mod.azure.hwg.item.weapons.ShotgunItem;

public class ShotgunRender extends GeoItemRenderer<ShotgunItem> {
    public ShotgunRender() {
        super(new ShotgunModel());
    }
}