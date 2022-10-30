package xaidee.zombiefriends.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class ZombieVariantModel<T extends Zombie> extends PlayerModel<T> {

    public final ModelPart rightSleeve;
    public final ModelPart leftSleeve;
    private final boolean slim;

    public ZombieVariantModel(ModelPart part, boolean slim) {
        super(part, slim);
        this.leftSleeve = part.getChild("left_sleeve");
        this.rightSleeve = part.getChild("right_sleeve");
        this.slim = slim;
    }

    @Override
    public void setupAnim(T zombie, float p_102002_, float p_102003_, float p_102004_, float p_102005_, float p_102006_) {
        super.setupAnim(zombie, p_102002_, p_102003_, p_102004_, p_102005_, p_102006_);
        float f = Mth.sin(this.attackTime * (float)Math.PI);
        float f1 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float)Math.PI);
        this.rightArm.zRot = 0.0F;
        this.rightSleeve.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.leftSleeve.zRot = 0.0F;
        this.rightArm.yRot = -(0.1F - f * 0.6F);
        this.rightSleeve.yRot = -(0.1F - f * 0.6F);
        this.leftArm.yRot = 0.1F - f * 0.6F;
        this.leftSleeve.yRot = 0.1F - f * 0.6F;
        float f2 = -(float)Math.PI / (this.isAggressive(zombie) ? 1.5F : 2.25F);
        this.rightArm.xRot = f2;
        this.rightSleeve.xRot = f2;
        this.leftArm.xRot = f2;
        this.leftSleeve.xRot = f2;
        this.rightArm.xRot += f * 1.2F - f1 * 0.4F;
        this.rightSleeve.xRot += f * 1.2F - f1 * 0.4F;
        this.leftArm.xRot += f * 1.2F - f1 * 0.4F;
        this.leftSleeve.xRot += f * 1.2F - f1 * 0.4F;
        AnimationUtils.bobModelPart(this.rightArm, p_102004_, 1.0F);
        AnimationUtils.bobModelPart(this.rightSleeve, p_102004_, 1.0F);
        AnimationUtils.bobModelPart(this.leftArm, p_102004_, -1.0F);
        AnimationUtils.bobModelPart(this.leftSleeve, p_102004_, -1.0F);
    }

    public boolean isAggressive(T zombie) {
        return zombie.isAggressive();
    }

    public static LayerDefinition createBodyLayer(boolean slim) {
        MeshDefinition meshDefinition = createMesh(new CubeDeformation(0.0F), slim);
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public static MeshDefinition createMesh(CubeDeformation deformation, boolean slim) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        float f = 0.25F;
        if (slim) {
            partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, deformation), PartPose.offset(5.0F, 2.5F, 0.0F));
            partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, deformation), PartPose.offset(-5.0F, 2.5F, 0.0F));
            partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, deformation.extend(f)), PartPose.offset(5.0F, 2.5F, 0.0F));
            partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, deformation.extend(f)), PartPose.offset(-5.0F, 2.5F, 0.0F));
        } else {
            partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation), PartPose.offset(5.0F, 2.0F, 0.0F));
            partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(f)), PartPose.offset(5.0F, 2.0F, 0.0F));
            partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(f)), PartPose.offset(-5.0F, 2.0F, 0.0F));
        }

        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation), PartPose.offset(1.9F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(f)), PartPose.offset(1.9F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(f)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation.extend(f)), PartPose.ZERO);
        return meshdefinition;
    }

    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        ModelPart modelpart = this.getArm(arm);
        if (this.slim) {
            float f = 0.5F * (float)(arm == HumanoidArm.RIGHT ? 1 : -1);
            modelpart.x += f;
            modelpart.translateAndRotate(poseStack);
            modelpart.x -= f;
        } else {
            modelpart.translateAndRotate(poseStack);
        }
    }
}
