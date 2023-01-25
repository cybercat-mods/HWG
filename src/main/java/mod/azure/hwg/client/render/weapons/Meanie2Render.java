package mod.azure.hwg.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.hwg.client.models.weapons.Meanie2Model;
import mod.azure.hwg.item.weapons.Meanie2Item;

public class Meanie2Render extends GeoItemRenderer<Meanie2Item> {
	public Meanie2Render() {
		super(new Meanie2Model());
	}
}