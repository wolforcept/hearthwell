//package wolforce.hearthwell.data;
//
//import java.io.Serializable;
//
//import net.minecraft.item.ItemStack;
//import wolforce.hearthwell.HearthWell;
//import wolforce.hearthwell.util.Util;
//
//public class EnergyType implements Serializable {
//
//	private static final long serialVersionUID = HearthWell.VERSION.hashCode();
//
//	public final String name;
//	public final float r, g, b;
//	public final ItemValue[] items;
//
//	EnergyType(String name, double r, double g, double b, ItemValue... items) {
//		this.name = name;
//		this.r = (float) r;
//		this.g = (float) g;
//		this.b = (float) b;
//		this.items = items;
//	}
//
//	public static ItemValue IV(String item, int value) {
//		return new ItemValue(item, value);
//	}
//
//	public static class ItemValue implements Serializable {
//
//		private static final long serialVersionUID = HearthWell.VERSION.hashCode();
//
//		public final String item;
//		public final int value;
//
//		public ItemValue(String item, int value) {
//			this.item = item;
//			this.value = value;
//		}
//	}
//
//	public int getItemValue(ItemStack item2) {
//		for (ItemValue itemValue : items) {
//			ItemStack item1 = Util.parseStack(itemValue.item);
//			if (Util.equalExceptAmount(item1, item2))
//				return itemValue.value;
//		}
//		return 0;
//	}
//
//	public float getR() {
//		return (float) (r + Math.random() * .1);
//	}
//
//	public float getG() {
//		return (float) (g + Math.random() * .1);
//	}
//
//	public float getB() {
//		return (float) (b + Math.random() * .1);
//	}
//
//	public short getId() {
//		return (short) name.toLowerCase().hashCode();
//	}
//}
