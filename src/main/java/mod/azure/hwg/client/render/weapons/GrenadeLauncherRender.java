package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.client.models.weapons.GrenadeLauncherModel;
import mod.azure.hwg.item.weapons.GrenadeLauncherItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GrenadeLauncherRender extends GeoItemRenderer<GrenadeLauncherItem> {
	public GrenadeLauncherRender() {
		super(new GrenadeLauncherModel());
	}
}