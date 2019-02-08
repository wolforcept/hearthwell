package wolforce.items.tools;

import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;

public class MyDagger extends MySword {

	private static final UUID DAGGER_DAMAGE_DROP = UUID.fromString("AC2A9706-2D2C-4579-AC27-C82374E83640");

	public MyDagger(String name, ToolMaterial mat, Item repairIngot, double attackSpeed) {
		super(name, mat, repairIngot, attackSpeed);
	}

	/**
	 * An explanation on operation: ModifiableAttributeInstance#computeValue There
	 * are three valid operations: 0, 1, and 2. They are executed in order.
	 * Operation 0 adds the given modifier to the base value of the attribute.
	 * Operation 1 adds [modifier * new base value] to the final value. Operation 2
	 * multiplies the final value by (1.0 + modifier). The IAttribute has the
	 * ability to clamp the final modified value.
	 * 
	 * For example, if we had an attribute with a base of 1.0, applying an
	 * AttributeModifier(name, 1, 0) would result in a value of 2.0 (1 + 1).
	 * Additionally applying an AttributeModifier(name, 1.5, 1) would result in a
	 * value of 5.0 (2.0 + 1.5 * 2.0). Further applying an AttributeModifier(name,
	 * 0.75, 2) would result in 8.75 (5.0 * 1.75).
	 */
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
					new AttributeModifier(DAGGER_DAMAGE_DROP, "Im a dagger", -1.0, 0));
		}
		return multimap;
	}
}
