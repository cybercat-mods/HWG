package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.GPistolModel;
import mod.azure.hwg.item.weapons.GPistolItem;

public class GPistolRender extends GeoItemRenderer<GPistolItem> {
	public GPistolRender() {
		super(new GPistolModel());
	}
}