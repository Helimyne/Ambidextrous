package mod.ambidextrous.setup;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Helimyne
 * @date 1/6/2022
 * @time 12:52 AM
 */
public class RClickEvent {
//    static InputConstants.Key keyUseR = InputConstants.getKey("keyUse");
//    public static void tryRClick() {
//
//        KeyMapping.click(keyUseR);
//
//    }
    private final Minecraft minecraft;
    private final Method rightClickMouseMethod;

    public RClickEvent(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.rightClickMouseMethod = ObfuscationReflectionHelper.findMethod(minecraft.getClass(), "m_91277_");
    }


    public void tryRClick(){
        if (!isWorldInFocus()){
            return;
        }
        tryRightClick();
    }

    private boolean isWorldInFocus(){
        if (minecraft == null){
            return false;
        }
        if (minecraft.player == null){
            return false;
        }
        //according torchkey noinspection ReduntantIfStatement
        if (minecraft.screen != null){
            return false;
        }
        return true;
    }

    private void tryRightClick(){
        try {
            rightClickMouseMethod.invoke(minecraft);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

}
