package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.SniperModel;
import mod.azure.hwg.item.weapons.SniperItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SniperRender extends GeoItemRenderer<SniperItem> {
	public SniperRender() {
		super(new SniperModel());
	}
}