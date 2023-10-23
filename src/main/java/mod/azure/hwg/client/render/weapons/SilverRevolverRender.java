package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.SilverRevolverModel;
import mod.azure.hwg.item.weapons.SilverRevolverItem;

public class SilverRevolverRender extends GeoItemRenderer<SilverRevolverItem> {
    public SilverRevolverRender() {
        super(new SilverRevolverModel());
    }
}