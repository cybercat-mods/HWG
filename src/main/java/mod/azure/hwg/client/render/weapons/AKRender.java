package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.AKModel;
import mod.azure.hwg.item.weapons.AssasultItem;

public class AKRender extends GeoItemRenderer<AssasultItem> {
    public AKRender() {
        super(new AKModel());
    }
}