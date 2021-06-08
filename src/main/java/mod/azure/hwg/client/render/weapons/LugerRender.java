package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.LugerModel;
import mod.azure.hwg.item.weapons.LugerItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class LugerRender extends GeoItemRenderer<LugerItem> {
	public LugerRender() {
		super(new LugerModel());
	}
}