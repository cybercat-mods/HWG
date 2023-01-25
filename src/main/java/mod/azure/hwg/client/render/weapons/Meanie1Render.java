package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.Meanie1Model;
import mod.azure.hwg.item.weapons.Meanie1Item;

public class Meanie1Render extends GeoItemRenderer<Meanie1Item> {
	public Meanie1Render() {
		super(new Meanie1Model());
	}
}