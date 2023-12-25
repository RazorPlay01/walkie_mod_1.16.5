/*
package net.razorplay.walkiemod.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class ToggleImageButton extends ImageButton {
    public void setState(boolean state) {
        this.state = state;
    }
    protected boolean state;

    public ToggleImageButton(int x, int y, ResourceLocation texture, Button.IPressable pressedAction, boolean state) {
        super(x, y, texture, pressedAction);
        this.state = state;
    }

    @Override
    protected void renderWidget(MatrixStack context, int mouseX, int mouseY) {
        if (state) {
            context.drawTexture(texture, getX() + 2, getY() + 2, 16, 0, 16, 16, 32, 32);
        } else {
            context.drawTexture(texture, getX() + 2, getY() + 2, 0, 0, 16, 16, 32, 32);
        }

    }
}
*/
