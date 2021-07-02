package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.Meanie1Model;
import mod.azure.hwg.item.weapons.Meanie1Item;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

public class Meanie1Render extends GeoItemRenderer<Meanie1Item> {
	public Meanie1Render() {
		super(new Meanie1Model());
	}
}