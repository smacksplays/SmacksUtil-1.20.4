package net.smackplays.smacksutil.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BackpackUpgradeItem extends Item {
    private final float stackSizeMultiplier;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    protected static final UUID BASE_MULTIPLIER_UUDI = UUID.fromString("CB3F55D3-645C-4F38-B497-9C13A33DB5CF");
    public BackpackUpgradeItem(Properties props, int multiplier) {
        super(props);
        this.stackSizeMultiplier = multiplier;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> map = ImmutableMultimap.builder();
        map.put(Services.PLATFORM.getBackpackUpgradeMultiplierAttribute(),
                new AttributeModifier(BASE_MULTIPLIER_UUDI, "Backpack modifier", this.stackSizeMultiplier, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = map.build();
    }
    public BackpackUpgradeItem(int multiplier){
        super(new Item.Properties().stacksTo(1));
        this.stackSizeMultiplier = multiplier;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> map = ImmutableMultimap.builder();
        map.put(Services.PLATFORM.getBackpackUpgradeMultiplierAttribute(),
                new AttributeModifier(BASE_MULTIPLIER_UUDI, "Backpack modifier", this.stackSizeMultiplier, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = map.build();
    }
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }
}
