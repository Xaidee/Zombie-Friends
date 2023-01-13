package xaidee.zombiefriends;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xaidee.zombiefriends.client.ZombieVariantRenderer;

@Mod(ZombieFriends.MOD_ID)
@Mod.EventBusSubscriber(modid = ZombieFriends.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ZombieFriends {

    public static final String MOD_ID = "zombiefriends";
    public static final Logger LOGGER = LogManager.getLogger();

    public ZombieFriends() {
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityType.ZOMBIE, ZombieVariantRenderer::new);
    }
}
