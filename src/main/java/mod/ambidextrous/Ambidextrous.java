package mod.ambidextrous;

import com.mojang.blaze3d.platform.InputConstants;
import mod.ambidextrous.setup.KeyBindingEventHandler;
import mod.ambidextrous.setup.RClickEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod(Ambidextrous.MODID)
public class Ambidextrous {

    public static final String MODID = "ambidextrous";

    public final KeyMapping keyBind = new KeyMapping(
            "key.ambidextrous.off_hand",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_4,
            KeyMapping.CATEGORY_GAMEPLAY
    );

    public Ambidextrous(){

        //setup for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetupEvent);
        MinecraftForge.EVENT_BUS.addListener(Ambidextrous::myClickInputEvent);
    }

    @OnlyIn(Dist.CLIENT)
    private void onClientSetupEvent(final FMLClientSetupEvent event){
        final RClickEvent rClickEvent = new RClickEvent(Minecraft.getInstance());
        new KeyBindingEventHandler(keyBind,  rClickEvent::tryRClick);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void myClickInputEvent(InputEvent.ClickInputEvent event){
        KeyMapping keyUsed = event.getKeyMapping();
        if (event.isUseItem()) {
            if (event.getHand() == InteractionHand.OFF_HAND) {
                event.setCanceled(true);
                event.setSwingHand(false);
            }
        }
    }
}
