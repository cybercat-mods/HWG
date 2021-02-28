package mod.azure.hwg.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import mod.azure.hwg.HWGMod;

@Config(name = HWGMod.MODID)
public class HWGConfig implements ConfigData {

	@ConfigEntry.Gui.CollapsibleObject
	public Spawning spawn = new Spawning();

	@ConfigEntry.Gui.CollapsibleObject
	public MobStats stats = new MobStats();

	@ConfigEntry.Gui.CollapsibleObject
	public Weapons weapons = new Weapons();

	public static class Spawning {
		public int merc_spawn_weight = 5;
		public int merc_min_group = 1;
		public int merc_max_group = 2;

		public int spy_spawn_weight = 5;
		public int spy_min_group = 1;
		public int spy_max_group = 2;

		public int lesser_spawn_weight = 10;
		public int lesser_min_group = 1;
		public int lesser_max_group = 2;

		public int greater_spawn_weight = 10;
		public int greater_min_group = 1;
		public int greater_max_group = 2;
	}

	public static class MobStats {
		public double merc_health = 20;
		public double spy_health = 20;
		public double lesser_health = 48;
		public double greater_health = 100;
	}

	public static class Weapons {
		public boolean rocket_breaks = true;
	}
}