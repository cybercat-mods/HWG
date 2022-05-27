package mod.azure.hwg.config;

import java.util.Arrays;
import java.util.List;

public class HWGConfig extends CustomMidnightConfig {

	@Entry
	public static List<String> merc_biomes = Arrays.asList("#forest", "#plains", "#icy", "#taiga", "#jungle", "#desert",
			"#mesa");
	@Entry
	public static int merc_spawn_weight = 5;
	@Entry
	public static int merc_min_group = 1;
	@Entry
	public static int merc_max_group = 2;

	@Entry
	public static List<String> spy_biomes = Arrays.asList("#taiga", "#jungle", "#mesa");
	@Entry
	public static int spy_spawn_weight = 5;
	@Entry
	public static int spy_min_group = 1;
	@Entry
	public static int spy_max_group = 2;

	@Entry
	public static List<String> lesser_biomes = Arrays.asList("#nether");
	@Entry
	public static int lesser_spawn_weight = 2;
	@Entry
	public static int lesser_min_group = 1;
	@Entry
	public static int lesser_max_group = 2;

	@Entry
	public static List<String> greater_biomes = Arrays.asList("#nether");
	@Entry
	public static int greater_spawn_weight = 1;
	@Entry
	public static int greater_min_group = 1;
	@Entry
	public static int greater_max_group = 1;

	@Entry
	public static double merc_health = 20;
	@Entry
	public static int merc_exp = 4;
	@Entry
	public static double spy_health = 20;
	@Entry
	public static int spy_exp = 4;
	@Entry
	public static double lesser_health = 48;
	@Entry
	public static int lesser_exp = 8;
	@Entry
	public static double greater_health = 100;
	@Entry
	public static int greater_exp = 16;

	@Entry
	public static boolean rocket_breaks = true;
	@Entry
	public static boolean balrog_breaks = false;
	@Entry
	public static boolean grenades_breaks = false;
	@Entry
	public static boolean bullets_breakdripstone = false;
	@Entry
	public static float pistol_damage = 5F;
	@Entry
	public static float golden_pistol_damage = 6F;
	@Entry
	public static float silenced_pistol_damage = 4F;
	@Entry
	public static float luger_damage = 5.5F;
	@Entry
	public static float ak47_damage = 6F;
	@Entry
	public static float sniper_damage = 25F;
	@Entry
	public static float sniper_scoped_damage = 30F;
	@Entry
	public static float minigun_damage = 4F;
	@Entry
	public static float smg_damage = 3.5F;
	@Entry
	public static float hellhorse_damage = 5F;
	@Entry
	public static float tommy_damage = 3F;
	@Entry
	public static float shotgun_damage = 5F;
	@Entry
	public static float meanie_damage = 6F;
	@Entry
	public static float balrog_damage = 8F;
	@Entry
	public static float brimstone_damage = 3F;
}