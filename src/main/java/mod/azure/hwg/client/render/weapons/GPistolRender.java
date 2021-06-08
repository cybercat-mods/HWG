package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.GPistolModel;
import mod.azure.hwg.item.weapons.GPistolItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GPistolRender extends GeoItemRenderer<GPistolItem> {
	public GPistolRender() {
		super(new GPistolModel());
	}
}