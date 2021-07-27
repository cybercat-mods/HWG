package mod.azure.hwg.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
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

		public int lesser_spawn_weight = 2;
		public int lesser_min_group = 1;
		public int lesser_max_group = 2;

		public int greater_spawn_weight = 1;
		public int greater_min_group = 1;
		public int greater_max_group = 1;
	}

	public static class MobStats {
		public double merc_health = 20;
		public double spy_health = 20;
		public double lesser_health = 48;
		public double greater_health = 100;
	}

	public static class Weapons {
		public boolean rocket_breaks = true;
		public float pistol_damage = 5F;
		public float golden_pistol_damage = 6F;
		public float silenced_pistol_damage = 4F;
		public float luger_damage = 5.5F;
		public float ak47_damage = 6F;
		public float sniper_damage = 25F;
		public float sniper_scoped_damage = 30F;
		public float minigun_damage = 4F;
		public float smg_damage = 3.5F;
		public float hellhorse_damage = 5F;
		public float tommy_damage = 3F;
		public float shotgun_damage = 5F;
		public float meanie_damage = 6F;
		public float balrog_damage = 8F;
		public float brimstone_damage = 3F;
	}
}