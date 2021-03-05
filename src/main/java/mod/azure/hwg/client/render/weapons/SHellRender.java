package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.SHellhorseRevolverModel;
import mod.azure.hwg.item.weapons.SilverRevolverItem;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

public class SHellRender extends GeoItemRenderer<SilverRevolverItem> {
	public SHellRender() {
		super(new SHellhorseRevolverModel());
	}
}