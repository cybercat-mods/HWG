package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.LugerModel;
import mod.azure.hwg.item.weapons.LugerItem;

public class LugerRender extends GeoItemRenderer<LugerItem> {
	public LugerRender() {
		super(new LugerModel());
	}
}