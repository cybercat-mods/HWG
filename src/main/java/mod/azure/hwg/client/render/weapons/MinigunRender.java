package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.MinigunModel;
import mod.azure.hwg.item.weapons.AnimatedItem;

public class MinigunRender extends GeoItemRenderer<AnimatedItem> {
	public MinigunRender() {
		super(new MinigunModel());
	}
}