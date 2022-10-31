package xaidee.zombiefriends.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
import xaidee.zombiefriends.entity.ZombieVariant;


public class ZombieVariantRenderer extends HumanoidMobRenderer<Zombie, ZombieVariantModel<Zombie>> {

    private static final ResourceLocation ZOMBIE_LOCATION = new ResourceLocation("textures/entity/zombie/zombie.png");

    public static EntityRendererProvider<? super Zombie> OLD_ZOMBIE_RENDER_FACTORY = null;
    private EntityRenderer<? super Zombie> OLD_ZOMBIE_RENDER = null;

    private final EntityRendererProvider.Context context;

    public ZombieVariantRenderer(EntityRendererProvider.Context context) {
        this(context, false);
    }

    public ZombieVariantRenderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, createModel(context.getModelSet(), slim), 0.5F);
        this.context = context;
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR))));
        if (OLD_ZOMBIE_RENDER != null)
            OLD_ZOMBIE_RENDER = OLD_ZOMBIE_RENDER_FACTORY.create(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Zombie zombie) {
        ResourceLocation loc = ZombieVariant.getRandomTexture(zombie);
        //noinspection ConstantConditions
        if (loc != null) return loc;
        return ZOMBIE_LOCATION;
    }

    private static ZombieVariantModel<Zombie> createModel(EntityModelSet modelSet, boolean slim) {
        return new ZombieVariantModel<>(modelSet.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim);
    }

    @Override
    public void render(Zombie zombie, float p_116262_, float p_116263_, PoseStack p_116264_, MultiBufferSource p_116265_, int p_116266_) {
        this.model = createModel(this.context.getModelSet(), ZombieVariant.isSlim(zombie));
        super.render(zombie, p_116262_, p_116263_, p_116264_, p_116265_, p_116266_);
    }

    protected boolean isShaking(Zombie zombie) {
        return super.isShaking(zombie) || zombie.isUnderWaterConverting();
    }

}
