package net.rose.rip_and_tear.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EntityPose;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.rose.rip_and_tear.common.components.entity.StatueComponent;
import net.rose.rip_and_tear.common.entity.mob.StatueEntity;
import net.rose.rip_and_tear.common.entity.mob.StatueEntitySkinType;
import net.rose.rip_and_tear.common.init.ModEntityComponents;
import net.rose.rip_and_tear.common.util.Mathf;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class StatueConfigurationScreen extends Screen {
    private final StatueEntity statueEntity;

    public StatueConfigurationScreen(
            StatueEntity statueEntity
    ) {
        super(Text.literal("Statue Configuration"));
        this.statueEntity = statueEntity;
    }

    private float roundValue(float value) {
        return Math.round(value * 10) / 10F;
    }

    private float valueAsRotation(double value) {
        return roundValue((float) value * 360F);
    }

    @FunctionalInterface
    private static interface SliderValueSetter {
        void set(StatueComponent component, float value);
    }

    private StatueComponent getStatueComponent() {
        return ModEntityComponents.STATUE.maybeGet(statueEntity).orElse(null);
    }

    private SimpleSliderWidget createSliderWithValue(
            int index,
            String sliderName,
            float minimum, float maximum,
            Function<StatueComponent, Float> setStartingValue,
            SliderValueSetter onValueChanged
    ) {
        final int WIDTH = 100;
        final int HEIGHT = 20;
        final int GAP = 4;

        final int screenCenterX = width / 2;
        final int screenCenterY = height / 2;
        final int offset = index * HEIGHT + (index - 1) * GAP;

        return SimpleSliderWidget.builder(Text.literal(sliderName))
                .size(WIDTH, HEIGHT)
                // Remember: vertically, + makes it go down and - makes it go up.
                .position(screenCenterX - WIDTH / 2, screenCenterY - HEIGHT / 2 + offset)

                .startingValue(() -> {
                    var initialValue = setStartingValue.apply(getStatueComponent());
                    // Such a bad way of doing it ;3;
                    if (minimum == 0F) initialValue = initialValue / maximum;
                    else initialValue = (initialValue / maximum + 1) / 2F;
                    return (double) initialValue;
                })

                .onValueChanged(value -> {
                    // Get the global statue component on the entity if it has it.
                    ModEntityComponents.STATUE.maybeGet(statueEntity).ifPresent(statueComponent -> {
                        // If it has the component, calculate the value interpolated between the max and min params.
                        var effectiveValue = MathHelper.lerp(value, minimum, maximum);
                        var roundedEffectiveValue = roundValue((float) effectiveValue);
                        // Send that to the onValueChanged event functional interface declared in the def.
                        onValueChanged.set(statueComponent, roundedEffectiveValue);

                        // Send the new info to the server so it can be saved.
                        ClientPlayNetworking.send(statueComponent.getPayload());
                    });
                })

                .updateMessage(simpleSliderWidget -> {
                    // Get the value between the maximum and minimum params.
                    var effectiveValue = MathHelper.lerp(simpleSliderWidget.getValue(), minimum, maximum);
                    // Round it to have a floating number with a 1 decimal accuracy.
                    var roundedEffectiveValue = roundValue((float) effectiveValue);
                    // Display it as the message of the slider.
                    simpleSliderWidget.setMessage(Text.literal(sliderName + ": " + roundedEffectiveValue));
                })

                .build();
    }

    @Override
    protected void applyBlur() {
    }

    @Override
    protected void init() {
        super.init();

        addDrawableChild(createSliderWithValue(
                -4, "Entity Pose", 0, EntityPose.values().length - 1,
                component -> (float) component.getForcedPose().getIndex(),
                (component, forcedPose) -> {
                    var roundedIndex = (int)Math.clamp(forcedPose, 0, EntityPose.values().length - 1);
                    var pose = EntityPose.INDEX_TO_VALUE.apply(roundedIndex);
                    component.setForcedPose(pose);
                }
        ));
        addDrawableChild(createSliderWithValue(
                -3, "Skin Type", 0, StatueEntitySkinType.values().length - 1,
                component -> (float) component.getTextureIndex(),
                (component, skin) -> {
                    var roundedIndex = (int)Math.clamp(skin, 0, StatueEntitySkinType.values().length - 1);
                    component.setTextureIndex(roundedIndex);
                }
        ));
        addDrawableChild(createSliderWithValue(
                -2, "Head Yaw", -180F, 180F,
                StatueComponent::getForcedHeadYaw, StatueComponent::setForcedHeadYaw
        ));
        addDrawableChild(createSliderWithValue(
                -1, "Body Yaw", 0, 360F,
                StatueComponent::getForcedBodyYaw, StatueComponent::setForcedBodyYaw
        ));
        addDrawableChild(createSliderWithValue(
                0, "Pitch", -180F, 180F,
                StatueComponent::getForcedPitch, StatueComponent::setForcedPitch
        ));
        addDrawableChild(createSliderWithValue(
                1, "Limb Animation", 0, 1,
                StatueComponent::getForcedLimbSwingAnimationProgress,
                StatueComponent::setForcedLimbSwingAnimationProgress
        ));
        addDrawableChild(createSliderWithValue(
                2, "Limb Amplitude", 0, 1,
                StatueComponent::getForcedLimbSwingAmplitude,
                StatueComponent::setForcedLimbSwingAmplitude
        ));
    }
}
