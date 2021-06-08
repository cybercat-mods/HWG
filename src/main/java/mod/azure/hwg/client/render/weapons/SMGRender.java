package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.SMGModel;
import mod.azure.hwg.item.weapons.Assasult1Item;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SMGRender extends GeoItemRenderer<Assasult1Item> {
	public SMGRender() {
		super(new SMGModel());
	}
}