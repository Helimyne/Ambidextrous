package mod.ambidextrous.setup;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.glfw.GLFW;

/**
 * @author Helimyne
 * @date 1/5/2022
 * @time 8:41 PM
 */
public class KeyBindingEventHandler {

    private final KeyMapping keyBind;
    private final Runnable callback;

    public KeyBindingEventHandler(final KeyMapping keyBind, final Runnable callback){
        this.keyBind = keyBind;
        this.callback = callback;
        ClientRegistry.registerKeyBinding(keyBind);
        MinecraftForge.EVENT_BUS.addListener(this::onKeyInputEvent);
        MinecraftForge.EVENT_BUS.addListener(this::onMouseInputEvent);
    }

    private void onKeyInputEvent(final InputEvent.KeyInputEvent event){
        if (event.getAction() == GLFW.GLFW_PRESS && keyBind.matches(event.getKey(), event.getScanCode())){
            callback.run();
        }
    }

    private void onMouseInputEvent(final InputEvent.MouseInputEvent event){
        if (event.getAction() == GLFW.GLFW_PRESS && keyBind.matchesMouse(event.getButton())){
            callback.run();
        }
    }

}
