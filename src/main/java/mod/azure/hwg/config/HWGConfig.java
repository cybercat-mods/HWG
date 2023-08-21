package mod.azure.hwg.config;

import mod.azure.azurelib.config.Config;
import mod.azure.azurelib.config.Configurable;
import mod.azure.hwg.HWGMod;

@Config(id = HWGMod.MODID)
public class HWGConfig {

	@Configurable
	public int merc_spawn_weight = 5;
	@Configurable
	public int merc_min_group = 1;
	@Configurable
	public int merc_max_group = 2;

	@Configurable
	public int spy_spawn_weight = 5;
	@Configurable
	public int spy_min_group = 1;
	@Configurable
	public int spy_max_group = 2;

	@Configurable
	public int lesser_spawn_weight = 2;
	@Configurable
	public int lesser_min_group = 1;
	@Configurable
	public int lesser_max_group = 2;

	@Configurable
	public int greater_spawn_weight = 1;
	@Configurable
	public int greater_min_group = 1;
	@Configurable
	public int greater_max_group = 1;

	@Configurable
	public double merc_health = 20;
	@Configurable
	public int merc_exp = 4;
	@Configurable
	public double spy_health = 20;
	@Configurable
	public int spy_exp = 4;
	@Configurable
	public double lesser_health = 48;
	@Configurable
	public int lesser_exp = 8;
	@Configurable
	public double greater_health = 100;
	@Configurable
	public int greater_exp = 16;

	@Configurable
	public boolean rocket_breaks = true;
	@Configurable
	public boolean balrog_breaks = false;
	@Configurable
	public boolean grenades_breaks = false;
	@Configurable
	public boolean bullets_breakdripstone = false;
	@Configurable
	public boolean bullets_disable_iframes_on_players = false;
	@Configurable
	public float pistol_damage = 5F;
	@Configurable
	public float golden_pistol_damage = 6F;
	@Configurable
	public float silenced_pistol_damage = 4F;
	@Configurable
	public float luger_damage = 5.5F;
	@Configurable
	public float ak47_damage = 6F;
	@Configurable
	public float sniper_damage = 25F;
	@Configurable
	public float sniper_scoped_damage = 30F;
	@Configurable
	public float minigun_damage = 4F;
	@Configurable
	public float smg_damage = 3.5F;
	@Configurable
	public float hellhorse_damage = 5F;
	@Configurable
	public float tommy_damage = 3F;
	@Configurable
	public float shotgun_damage = 5F;
	@Configurable
	public float meanie_damage = 6F;
	@Configurable
	public float balrog_damage = 8F;
	@Configurable
	public float brimstone_damage = 3F;
}