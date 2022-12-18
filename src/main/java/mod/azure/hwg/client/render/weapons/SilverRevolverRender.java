package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.SilverRevolverModel;
import mod.azure.hwg.item.weapons.SilverRevolverItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SilverRevolverRender extends GeoItemRenderer<SilverRevolverItem> {
	public SilverRevolverRender() {
		super(new SilverRevolverModel());
	}
}