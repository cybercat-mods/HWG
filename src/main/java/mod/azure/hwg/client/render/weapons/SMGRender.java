package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.SMGModel;
import mod.azure.hwg.item.weapons.Assasult1Item;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SMGRender extends GeoItemRenderer<Assasult1Item> {
	public SMGRender() {
		super(new SMGModel());
	}
}