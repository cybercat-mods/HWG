package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.FlamethrowerModel;
import mod.azure.hwg.item.weapons.FlamethrowerItem;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

public class FlamethrowerRender extends GeoItemRenderer<FlamethrowerItem> {
	public FlamethrowerRender() {
		super(new FlamethrowerModel());
	}
}