package net.razorplay.walkiemod.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.common.Mod;
import net.razorplay.walkiemod.WalkieMod;
import net.razorplay.walkiemod.item.custom.WalkieTalkieItem;
import net.razorplay.walkiemod.network.ModMessages;
import net.razorplay.walkiemod.network.packet.c2s.ActivateButtonC2SPacket;
import net.razorplay.walkiemod.network.packet.c2s.ChannelC2SPacket;
import net.razorplay.walkiemod.network.packet.c2s.MuteButtonC2SPacket;

import java.util.function.Consumer;

public class WalkieTalkieScreen extends Screen {
    private final int xSize = 195;
    private final int ySize = 76;
    private static final ResourceLocation TEXTURE = new ResourceLocation(WalkieMod.MOD_ID, "textures/gui/gui_walkietalkie.png");
    private static final ITextComponent TITLE = new TranslationTextComponent("");
    private static final ResourceLocation MICROPHONE = new ResourceLocation("voicechat", "textures/icons/microphone_button.png");
    private static ITextComponent SET_CHANNEL = new TranslationTextComponent("screen.walkietalkie.set");
    private ITextComponent ACTIVATE_MICRO;
    private ITextComponent ACTIVATE_WALKIE;
    private Button muteMicro;
    private Button activateWalkie;
    private Button setChannel;
    private TextFieldWidget channelSelector;
    private final ItemStack stack;
    protected int guiLeft;
    protected int guiTop;
    private String CHANNEL;


    public WalkieTalkieScreen(ItemStack stack) {
        super(TITLE);
        this.stack = stack;

        Minecraft.getInstance().displayGuiScreen(this);
    }

    @Override
    protected void init() {
        super.init();
        CHANNEL = new String(String.valueOf(stack.getTag().getInt(WalkieTalkieItem.NBT_KEY_CANAL)));
        ACTIVATE_WALKIE = new TranslationTextComponent("screen.walkietalkie.is_active").appendString(": " + stack.getTag().getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE));
        ACTIVATE_MICRO = new TranslationTextComponent("screen.walkietalkie.is_muted").appendString(": " + stack.getTag().getBoolean(WalkieTalkieItem.NBT_KEY_MUTE));

        this.guiLeft = (this.width - xSize) / 2;
        this.guiTop = (this.height - ySize) / 2;

        this.channelSelector = new TextFieldWidget(this.font, this.guiLeft + 7, this.guiTop + 6, this.xSize - 35, 20, new StringTextComponent(""));
        this.channelSelector.setText(CHANNEL);
        //Definir maximo de caracteres
        this.channelSelector.setMaxStringLength(8);
        //Permitir solo numeros
        this.channelSelector.setResponder(new Consumer<String>() {
            @Override
            public void accept(String input) {
                // Validar la entrada para permitir solo números
                if (!input.matches("^\\d*$")) {
                    String numericInput = input.replaceAll("\\D", "");  // Eliminar todos los caracteres que no sean números
                    channelSelector.setText(numericInput);  // Actualizar el texto del TextFieldWidget
                }
            }
        });
        //Agregar el boton a la screen
        this.addButton(this.channelSelector);

        this.setChannel = new Button(this.guiLeft + 170, this.guiTop + 6, 20, 20, SET_CHANNEL, (button) -> {
            this.setChannel();
        });
        this.addButton(this.setChannel);

        this.muteMicro = new Button(this.guiLeft + 6, this.guiTop + this.ySize - 47, this.xSize - 12, 20, ACTIVATE_MICRO, (button) -> {
            this.muteMicro();
        });
        this.addButton(this.muteMicro);

        this.activateWalkie = new Button(this.guiLeft + 6, this.guiTop + this.ySize - 27, this.xSize - 12, 20, ACTIVATE_WALKIE, (button) -> {
            this.activateWalkie();
        });
        this.addButton(this.activateWalkie);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, guiTop + 7, 4210752);

    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);
        super.renderBackground(matrixStack);
    }

    private void activateWalkie() {
        ModMessages.sendToServer(new ActivateButtonC2SPacket());
        this.closeScreen();
    }

    private void setChannel() {
        ModMessages.sendToServer(new ChannelC2SPacket(channelSelector.getText()));
    }

    private void muteMicro() {
        ModMessages.sendToServer(new MuteButtonC2SPacket());
        this.closeScreen();
    }
}
