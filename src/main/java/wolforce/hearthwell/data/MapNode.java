package wolforce.hearthwell.data;

import wolforce.hearthwell.HearthWell;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MapNode implements Serializable {

	private static final long serialVersionUID = HearthWell.VERSION.hashCode();

	public byte x, y;
	public int time;
	public String name, icon_stack;
	public String short_description, full_description;
	public String[] recipes_ids, parent_ids, required_items;

	public transient LinkedList<RecipePart> requiredItems;

	MapNode(byte x, byte y, String name, int time, String stack, String description, String fullDescription, String[] recipes_ids, String[] parent_ids,
			String[] required_items) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.time = time;
		this.icon_stack = stack;
		this.short_description = description;
		this.full_description = fullDescription;
		this.recipes_ids = recipes_ids;
		this.parent_ids = parent_ids;
		this.required_items = required_items;
	}

//	public LinkedList<MapNode> getChildren() {
//		LinkedList<MapNode> locations = new LinkedList<>();
//		for (String id : children)
//			locations.add(MapData.DATA.getNode(id));
//		return locations;
//	}

	public void init() {
		requiredItems = new LinkedList<>();
		for (String s : required_items)
			requiredItems.add(new RecipePart(s));
	}

	public short hash() {
		return hash(x, y);
	}

	public static short hash(byte[] xy) {
		return hash(xy[0], xy[1]);
	}

	public static short hash(byte x, byte y) {
		return (short) ((x << 8) | (y & 0xFF));
	}

	public List<MapNode> getParents(MapData data) {
		return Arrays.stream(parent_ids).map(id -> data.getNode(id)).filter(node -> node != null).collect(Collectors.toList());
	}

}