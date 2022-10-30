package xaidee.zombiefriends;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xaidee.zombiefriends.client.ZombieVariantRenderer;


@Mod(ZombieFriends.MOD_ID)
public class ZombieFriends {

    public static final String MOD_ID = "zombiefriends";

    public ZombieFriends() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(ZombieFriends::onClientSetup);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            //noinspection unchecked cast
            ZombieVariantRenderer.OLD_ZOMBIE_RENDER_FACTORY = (EntityRendererProvider<Zombie>)EntityRenderers.PROVIDERS.get(EntityType.ZOMBIE);
            EntityRenderers.register(EntityType.ZOMBIE, ZombieVariantRenderer::new);
        });
    }
}
