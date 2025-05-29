package net.rose.rip_and_tear.client.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.rose.rip_and_tear.common.payload.C2S.SetMobPickUpLootPayload;
import net.rose.rip_and_tear.common.components.entity.StatueComponent;
import net.rose.rip_and_tear.common.entity.mob.StatueEntitySkinType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.rose.rip_and_tear.common.init.ModEntityComponents;
import net.rose.rip_and_tear.common.entity.mob.StatueEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttribute;
import com.google.common.collect.ImmutableMultimap;
import net.rose.rip_and_tear.common.util.TextUtils;
import net.minecraft.registry.entry.RegistryEntry;
import net.rose.rip_and_tear.common.RipAndTear;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.EntityPose;
import net.fabricmc.api.Environment;
import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.minecraft.text.Text;

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

    @FunctionalInterface
    private interface SliderValueSetter {
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
        return createSliderWithValue(
                index, sliderName, minimum, maximum,
                simpleSliderWidget -> {
                    // Get the value between the maximum and minimum params.
                    var effectiveValue = MathHelper.lerp(simpleSliderWidget.getValue(), minimum, maximum);
                    // Round it to have a floating number with a 1 decimal accuracy.
                    var roundedEffectiveValue = roundValue((float) effectiveValue);
                    // Display it as the message of the slider.
                    return Text.literal(sliderName + ": " + roundedEffectiveValue);
                },
                setStartingValue, onValueChanged
        );
    }

    private SimpleSliderWidget createSliderWithValue(
            int index,
            String sliderName,
            float minimum, float maximum,
            Function<SimpleSliderWidget, Text> updateMessage,
            Function<StatueComponent, Float> setStartingValue,
            SliderValueSetter onValueChanged
    ) {
        final int WIDTH = 100;
        final int HEIGHT = 20;
        final int GAP = 4;
        final int HORIZONTAL_OFFSET = width / 4;

        final int screenCenterX = width / 2;
        final int screenCenterY = height / 2;
        final int offset = index * HEIGHT + (index - 1) * GAP;

        return SimpleSliderWidget.builder(Text.literal(sliderName))
                .size(WIDTH, HEIGHT)
                // Remember: vertically, + makes it go down and - makes it go up.
                .position(screenCenterX - WIDTH / 2 - HORIZONTAL_OFFSET, screenCenterY - HEIGHT / 2 + offset)

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

                .updateMessage(widget -> widget.setMessage(updateMessage.apply(widget)))
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
                widget -> {
                    final var max = EntityPose.values().length - 1;
                    var effectiveValue = MathHelper.lerp(widget.getValue(), 0, max);
                    var roundedEffectiveValue = roundValue((float) effectiveValue);
                    var roundedIndex = (int) Math.clamp(roundedEffectiveValue, 0, max);
                    return TextUtils.toLiteral(TextUtils.prettify(EntityPose.values()[roundedIndex].name()));
                },
                component -> (float) component.getForcedPose().getIndex(),
                (component, forcedPose) -> {
                    var roundedIndex = (int) Math.clamp(forcedPose, 0, EntityPose.values().length - 1);
                    var pose = EntityPose.INDEX_TO_VALUE.apply(roundedIndex);
                    component.setForcedPose(pose);
                }
        ));
        addDrawableChild(createSliderWithValue(
                -3, "Skin Type", 0, StatueEntitySkinType.values().length - 1,
                widget -> {
                    final var max = StatueEntitySkinType.values().length - 1;
                    var effectiveValue = MathHelper.lerp(widget.getValue(), 0, max);
                    var roundedEffectiveValue = roundValue((float) effectiveValue);
                    var roundedIndex = (int) Math.clamp(roundedEffectiveValue, 0, max);
                    return StatueEntitySkinType.values()[roundedIndex].getName();
                },
                component -> (float) component.getTextureIndex(),
                (component, skin) -> {
                    var roundedIndex = (int) Math.clamp(skin, 0, StatueEntitySkinType.values().length - 1);
                    component.setTextureIndex(roundedIndex);
                }
        ));
        addDrawableChild(createSliderWithValue(
                -2, "Head Yaw", -180F, 180F,
                StatueComponent::getForcedHeadYaw, StatueComponent::setForcedHeadYaw
        ));
        addDrawableChild(createSliderWithValue(
                -1, "Pitch", -180F, 180F,
                StatueComponent::getForcedPitch, StatueComponent::setForcedPitch
        ));
        addDrawableChild(createSliderWithValue(
                0, "Body Yaw", 0, 360F,
                StatueComponent::getForcedBodyYaw, StatueComponent::setForcedBodyYaw
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
        addDrawableChild(createSliderWithValue(
                3, "Can Hold Items", 0, 1,
                widget -> TextUtils.toLiteral("Can Hold Items: " + (widget.getValue() < 0.5F ? "False" : "True")),
                component -> component.getLivingEntity().canPickUpLoot() ? 1F : 0F,
                (component, value) -> {
                    if (component.getLivingEntity() instanceof MobEntity mobEntity) {
                        mobEntity.setCanPickUpLoot(value >= 0.5F);
                        ClientPlayNetworking.send(SetMobPickUpLootPayload.fromMobEntity(mobEntity));
                    }
                }
        ));
        addDrawableChild(createSliderWithValue(
                4, "Size", 0f, 3f,
                widget -> TextUtils.toLiteral("Size: " + (MathHelper.lerp((float) widget.getValue(), 0, 3F) >= 0 ? "+" : "") + roundValue(MathHelper.lerp((float) widget.getValue(), 0, 3F))),
                component -> {
                    var attributes = component.getLivingEntity().getAttributes();
                    if (attributes.hasModifierForAttribute(EntityAttributes.SCALE, RipAndTear.id("statue_scale"))) {
                        return (float) attributes.getModifierValue(
                                EntityAttributes.SCALE, RipAndTear.id("statue_scale")
                        ) / 3f;
                    }

                    return 0f;
                },
                (component, value) -> {
                    var attributes = component.getLivingEntity().getAttributes();
                    if (attributes.hasModifierForAttribute(
                            EntityAttributes.SCALE,
                            RipAndTear.id("statue_scale")
                    )) {
                        // TODO: Payload sync c2s
                        attributes.addTemporaryModifiers(
                                ImmutableMultimap.<RegistryEntry<EntityAttribute>, EntityAttributeModifier>builder()
                                        .put(EntityAttributes.SCALE,
                                                new EntityAttributeModifier(RipAndTear.id("statue_scale"),
                                                        value, EntityAttributeModifier.Operation.ADD_VALUE))
                                        .build()
                        );
                    }
                }
        ));
    }
}
