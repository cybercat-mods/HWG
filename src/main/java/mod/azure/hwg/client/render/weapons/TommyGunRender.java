package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.TommyGunModel;
import mod.azure.hwg.item.weapons.Assasult2Item;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

public class TommyGunRender extends GeoItemRenderer<Assasult2Item> {
	public TommyGunRender() {
		super(new TommyGunModel());
	}
}