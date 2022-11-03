package xaidee.zombiefriends.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import xaidee.zombiefriends.ZombieFriends;

import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;

public enum ZombieVariant {
    STEVE(0, "steve", false),
    ALEX(1, "alex", true),
    NOOR(1, "noor", true),
    SUNNY(1, "sunny", true),
    ARI(1, "ari", false),
    ZURI(1, "zuri", false),
    MAKENA(1, "makena", true),
    KAI(1, "kai", false),
    EFE(1, "efe", true);

    public static final ZombieVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(ZombieVariant::getId)).toArray(ZombieVariant[]::new);
    private final int id;
    private final String name;
    private final boolean slim;
    ZombieVariant(int id, String name, boolean slim) {
        this.id = id;
        this.name = name;
        this.slim = slim;
    }

    private static ResourceLocation getTexture(ZombieVariant variant) {
        return new ResourceLocation(ZombieFriends.MOD_ID, "textures/entity/zombie/" + variant.name + ".png");
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean isSlim() { return this.slim; }

    public static boolean isSlim(Entity entity) {
        if (entity.hasCustomName()) {
            for (ZombieVariant variant : BY_ID) {
                if (entity.getCustomName().getString().toLowerCase().equals(variant.getName()))
                    return variant.isSlim();
            }
        }
        UUID id = entity.getUUID();
        if (id == null) return BY_ID[0].isSlim();
        long most = id.getMostSignificantBits();
        int choice = Math.abs((int) (most % BY_ID.length));
        return BY_ID[choice].isSlim();
    }

    public static ResourceLocation getRandomTexture(Entity entity) {
        if (entity.hasCustomName()) {
            for (ZombieVariant variant : BY_ID) {
                if (entity.getCustomName().getString().toLowerCase().equals(variant.getName()))
                    return getTexture(variant);
            }
        }
        try {
            UUID id = entity.getUUID();
            long most = id.getMostSignificantBits();
            int choice = Math.abs((int) (most % BY_ID.length));
            return getTexture(BY_ID[choice]);
        } catch (Exception e) {
            ZombieFriends.LOGGER.warn("Failed to get Zombie's UUID! Defaulting to Steve variant", e);
            return getTexture(BY_ID[0]);
        }
    }
}
