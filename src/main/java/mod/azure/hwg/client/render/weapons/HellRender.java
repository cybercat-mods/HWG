package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.HellhorseRevolverModel;
import mod.azure.hwg.item.weapons.HellhorseRevolverItem;

public class HellRender extends GeoItemRenderer<HellhorseRevolverItem> {
	public HellRender() {
		super(new HellhorseRevolverModel());
	}
}