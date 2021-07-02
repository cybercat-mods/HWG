package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.MinigunModel;
import mod.azure.hwg.item.weapons.AnimatedItem;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

public class MinigunRender extends GeoItemRenderer<AnimatedItem> {
	public MinigunRender() {
		super(new MinigunModel());
	}
}