package net.rose.rip_and_tear.common.init;

public class ModConfiguration {
    // Warper
    public static final float THROWN_WARPER_RECALL_DAMAGE = 8f;
    public static final float THROWN_WARPER_RECALL_DAMAGE_RADIUS = 0.5f;
    public static final float WARPER_PROJECTILE_HIT_DAMAGE = 8f;

    // Soul Glutton
    public static final int SOUL_GLUTTON_INTERACTION_DISTANCE = 7;
    public static final int SOUL_GLUTTON_USE_COOLDOWN = 60;
    public static final int SOUL_STATE_SOUL_INTEGRITY_MAXIMUM = 100;
    public static final int SOUL_STATE_SOUL_INTEGRITY_REGAIN_COOLDOWN = 20;

    // Fate Crusher
    public static final int FATE_CRUSHER_ENTITY_LIFETIME = 8;
    public static final int FATE_CRUSHER_ENTITY_DAMAGE_START_AGE = 4;
    public static final float FATE_CRUSHER_ENTITY_DAMAGE_PER_TICK = 5F;
    public static final float FATE_CRUSHER_ENTITY_AOD_BOX_SIZE = 2F;
    public static final int FATE_CRUSHER_ITEM_USE_COOLDOWN = 8 * 20; // 8 seconds * 20 ticks/second

    // region Look & Feel

    public static final int SOUL_STATE_SOUL_INTEGRITY_BAR_WIDTH = 182;
    public static final int SOUL_STATE_SOUL_INTEGRITY_BAR_HEIGHT = 5;

    public static final float WARPER_HIT_PARTICLES_SPREAD = 0.35f;
    public static final float THROWN_WARPER_RECALL_TRAIL_INTERVAL = 1f;
    public static final float THROWN_WARPER_RECALL_STEP_SIZE = 0.5f;
    public static final float THROWN_WARPER_RECALL_PARTICLE_SPREAD = 0.1f;
    public static final float WARPER_PROJECTILE_TELEPORT_SOUND_PITCH_MIN = 0.75f;
    public static final float WARPER_PROJECTILE_TELEPORT_SOUND_PITCH_MAX = 1.25f;
    public static final float WARPER_SWING_SOUND_VOLUME = 0.35F;

    public static final float CHARTER_SKULL_PARTICLE_HORIZONTAL_VELOCITY = 0.001f;
    public static final float CHARTER_SKULL_PARTICLE_VERTICAL_VELOCITY_MIN = 0.0075f;
    public static final float CHARTER_SKULL_PARTICLE_VERTICAL_VELOCITY_MAX = 0.01f;
    public static final float CHARTER_SKULL_PARTICLE_VELOCITY_DAMPEN = 0.875f;
    public static final float CHARTER_SKULL_PARTICLE_VELOCITY_MULTIPLIER = 0.15f;
    public static final float CHARTER_SKULL_PARTICLE_SCALE_MIN = 0.75f;
    public static final float CHARTER_SKULL_PARTICLE_SCALE_MAX = 1.1f;
    public static final int CHARTER_SKULL_PARTICLE_AGE_MIN = 12;
    public static final int CHARTER_SKULL_PARTICLE_AGE_MAX = 16;

    // endregion
}
