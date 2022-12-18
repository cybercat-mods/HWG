package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.Meanie2Model;
import mod.azure.hwg.item.weapons.Meanie2Item;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Meanie2Render extends GeoItemRenderer<Meanie2Item> {
	public Meanie2Render() {
		super(new Meanie2Model());
	}
}