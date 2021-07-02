package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.FlareGunModel;
import mod.azure.hwg.item.weapons.FlareGunItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class FlareGunRender extends GeoItemRenderer<FlareGunItem> {
	public FlareGunRender() {
		super(new FlareGunModel());
	}
}