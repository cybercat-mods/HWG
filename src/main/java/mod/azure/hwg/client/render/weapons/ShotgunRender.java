package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.ShotgunModel;
import mod.azure.hwg.item.weapons.ShotgunItem;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

public class ShotgunRender extends GeoItemRenderer<ShotgunItem> {
	public ShotgunRender() {
		super(new ShotgunModel());
	}
}