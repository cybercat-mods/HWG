package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.SMGModel;
import mod.azure.hwg.item.weapons.AssasultItem;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

public class SMGRender extends GeoItemRenderer<AssasultItem> {
	public SMGRender() {
		super(new SMGModel());
	}
}