package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.HellhorseRevolverModel;
import mod.azure.hwg.item.weapons.HellhorseRevolverItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class HellRender extends GeoItemRenderer<HellhorseRevolverItem> {
	public HellRender() {
		super(new HellhorseRevolverModel());
	}
}