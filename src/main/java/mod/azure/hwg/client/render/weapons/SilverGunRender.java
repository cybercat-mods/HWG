package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.SilverGunModel;
import mod.azure.hwg.item.weapons.SilverGunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SilverGunRender extends GeoItemRenderer<SilverGunItem> {
	public SilverGunRender() {
		super(new SilverGunModel());
	}
}