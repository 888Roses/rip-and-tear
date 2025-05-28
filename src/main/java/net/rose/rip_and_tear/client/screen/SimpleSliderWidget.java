package net.rose.rip_and_tear.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.rose.rip_and_tear.common.util.SoundUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleSliderWidget extends SliderWidget {
    private final Consumer<Double> onValueChanged;
    private final Consumer<SimpleSliderWidget> updateMessage;

    protected SimpleSliderWidget(
            int x, int y, int width, int height,
            Text text,
            double value,
            Consumer<Double> apply, Consumer<SimpleSliderWidget> update
    ) {
        super(x, y, width, height, text, value);
        this.onValueChanged = apply;
        this.updateMessage = update;

        updateMessage();
    }

    public static Builder builder(Text text) {
        return new Builder(text);
    }

    @Override
    protected void updateMessage() {
        updateMessage.accept(this);
    }

    private double lastValue;

    @Override
    protected void applyValue() {
        onValueChanged.accept(this.value);

        var dst = Math.abs(this.value - lastValue);
        if (dst > 0.05) {
            // We're on the client anyway
            var client = MinecraftClient.getInstance();
            client.world.playSoundClient(SoundEvents.BLOCK_NOTE_BLOCK_HAT.value(), SoundCategory.MASTER, 1, MathHelper.lerp((float)this.value, 0.5F, 2F));
            lastValue = this.value;
        }
    }

    public double getValue() {
        return value;
    }

    public static class Builder {
        private final Text text;
        private int x, y, width, height;
        private double startingValue;

        private Consumer<Double> apply;
        private Consumer<SimpleSliderWidget> update;

        protected Builder(Text text) {
            this.text = text;

            this.x = this.y = 0;
            this.width = 100;
            this.height = 20;
            this.startingValue = 0;
            this.apply = value -> {};
            this.update = widget -> {};
        }

        public Builder dimensions(int x, int y, int width, int height) {
            position(x, y);
            size(width, height);
            return this;
        }

        public Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder onValueChanged(Consumer<Double> onValueChanged) {
            this.apply = onValueChanged;
            return this;
        }

        public Builder updateMessage(Consumer<SimpleSliderWidget> updateMessage) {
            this.update = updateMessage;
            return this;
        }

        public Builder startingValue(double startingValue) {
            this.startingValue = startingValue;
            return this;
        }

        public Builder startingValue(Supplier<Double> startingValue) {
            return this.startingValue(startingValue.get());
        }

        public SimpleSliderWidget build() {
            return new SimpleSliderWidget(x, y, width, height, text, startingValue, apply, update);
        }
    }
}
