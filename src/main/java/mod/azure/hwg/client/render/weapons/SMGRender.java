package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.SMGModel;
import mod.azure.hwg.item.weapons.Assasult1Item;

public class SMGRender extends GeoItemRenderer<Assasult1Item> {
    public SMGRender() {
        super(new SMGModel());
    }
}