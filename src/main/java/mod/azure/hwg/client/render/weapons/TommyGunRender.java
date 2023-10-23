package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.TommyGunModel;
import mod.azure.hwg.item.weapons.Assasult2Item;

public class TommyGunRender extends GeoItemRenderer<Assasult2Item> {
    public TommyGunRender() {
        super(new TommyGunModel());
    }
}