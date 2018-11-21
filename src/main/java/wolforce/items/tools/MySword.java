package wolforce.items.tools;

import net.minecraft.item.ItemSword;

public class MySword extends ItemSword {

	private int attackDamage;

	public  MySword(String name, int maxUses, int attackDamage) {
		super(ToolMaterial.DIAMOND);
		this.attackDamage = attackDamage;
		setRegistryName(name);
		setUnlocalizedName(name);
		setMaxDamage(maxUses);
		setMaxStackSize(1);
	}

	@Override
	public float getAttackDamage() {
		return attackDamage;
	}
}
