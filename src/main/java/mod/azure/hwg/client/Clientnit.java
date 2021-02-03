package mod.azure.hwg.client;

import org.lwjgl.glfw.GLFW;

import mod.azure.hwg.client.render.weapons.FlamethrowerRender;
import mod.azure.hwg.client.render.weapons.PistolRender;
import mod.azure.hwg.client.render.weapons.SPistolRender;
import mod.azure.hwg.util.HWGItems;
import mod.azure.hwg.util.packet.EntityPacket;
import mod.azure.hwg.util.packet.EntityPacketOnClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

@SuppressWarnings("deprecation")
public class Clientnit implements ClientModInitializer {

	public static KeyBinding reload = new KeyBinding("key.hwg.reload", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
			"category.hwg.binds");

	@Override
	public void onInitializeClient() {
		RenderRegistry.init();
//		ScreenRegistry.<GunTableDescription, GunTableScreen>register(HWGMod.SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new GunTableScreen(gui, inventory.player, title));
		GeoItemRenderer.registerItemRenderer(HWGItems.PISTOL, new PistolRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.SPISTOL, new SPistolRender());
		GeoItemRenderer.registerItemRenderer(HWGItems.FLAMETHROWER, new FlamethrowerRender());
		ClientSidePacketRegistry.INSTANCE.register(EntityPacket.ID, (ctx, buf) -> {
			EntityPacketOnClient.onPacket(ctx, buf);
		});
		KeyBindingHelper.registerKeyBinding(reload);
	}

}