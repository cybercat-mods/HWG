package mod.azure.hwg.config;

import mod.azure.azurelib.config.Config;
import mod.azure.azurelib.config.Configurable;
import mod.azure.hwg.HWGMod;

@Config(id = HWGMod.MODID)
public class HWGConfig {

    @Configurable
    public MercConfigs mercconfigs = new MercConfigs();

    public static class MercConfigs {
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int merc_spawn_weight = 5;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int merc_min_group = 1;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int merc_max_group = 2;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.DecimalRange(min = 1)
    	public double merc_health = 20;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int merc_exp = 4;
    }

    @Configurable
    public SpyConfigs spyconfigs = new SpyConfigs();

    public static class SpyConfigs {
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int spy_spawn_weight = 5;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int spy_min_group = 1;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int spy_max_group = 2;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.DecimalRange(min = 1)
    	public double spy_health = 20;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int spy_exp = 4;
    }

    @Configurable
    public LesserConfigs lesserconfigs = new LesserConfigs();

    public static class LesserConfigs {
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int lesser_spawn_weight = 2;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int lesser_min_group = 1;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int lesser_max_group = 2;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.DecimalRange(min = 1)
    	public double lesser_health = 48;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int lesser_exp = 8;
    }

    @Configurable
    public GreatConfigs greatconfigs = new GreatConfigs();

    public static class GreatConfigs {
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int greater_spawn_weight = 1;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int greater_min_group = 1;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int greater_max_group = 1;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.DecimalRange(min = 1)
    	public double greater_health = 100;
    	
    	@Configurable
    	@Configurable.Synchronized
    	@Configurable.Range(min = 1)
    	public int greater_exp = 16;
    }

	@Configurable
	@Configurable.Synchronized
	public boolean rocket_breaks = true;
	@Configurable
	@Configurable.Synchronized
	public boolean balrog_breaks = false;
	@Configurable
	@Configurable.Synchronized
	public boolean grenades_breaks = false;
	@Configurable
	@Configurable.Synchronized
	public boolean bullets_breakdripstone = false;
	@Configurable
	@Configurable.Synchronized
	public boolean bullets_disable_iframes_on_players = false;

	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float pistol_damage = 5F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float golden_pistol_damage = 6F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float silenced_pistol_damage = 4F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float luger_damage = 5.5F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float ak47_damage = 6F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float sniper_damage = 25F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float sniper_scoped_damage = 30F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float minigun_damage = 4F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float smg_damage = 3.5F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float hellhorse_damage = 5F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float tommy_damage = 3F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float shotgun_damage = 5F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float meanie_damage = 6F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float balrog_damage = 8F;
	
	@Configurable
	@Configurable.Synchronized
	@Configurable.DecimalRange(min = 1)
	public float brimstone_damage = 3F;
}