package wolforce.hearthwell.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.recipes.*;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class MapData implements Serializable {

	public static MapData DATA;
	public transient int minX = 0, minY = 0, maxX = 0, maxY = 0;

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final String CONFIG_FILE = "hearthwell_mapdata.json";
	private static final long serialVersionUID = HearthWell.VERSION.hashCode();
	private transient HashMap<Short, MapNode> nodesByPosition;

	public final HashMap<String, MapNode> nodes = new HashMap<>();
//	private HashMap<Short, EnergyType> energyTypes;

//	private transient HashMap<RecipeType, List<RecipeHearthWell>> recipesByType;

	public final LinkedList<RecipeFlare> recipes_flare = new LinkedList<>();
	public final LinkedList<RecipeInfluence> recipes_influence = new LinkedList<>();
	public final LinkedList<RecipeTransformation> recipes_transformation = new LinkedList<>();
	public final LinkedList<RecipeHandItem> recipes_handitem = new LinkedList<>();
	public final LinkedList<RecipeBurstSeed> recipes_burst = new LinkedList<>();
	public final LinkedList<RecipeCrushing> recipes_crushing = new LinkedList<>();

	public final LinkedList<LinkedList<? extends RecipeHearthWell>> allRecipes = new LinkedList<>();

	public MapData() {
		allRecipes.add(recipes_flare);
		allRecipes.add(recipes_influence);
		allRecipes.add(recipes_transformation);
		allRecipes.add(recipes_handitem);
		allRecipes.add(recipes_burst);
		allRecipes.add(recipes_crushing);
	}

	private MapData createBaseNode() {
		boolean pureFlareExists = false;
		for (RecipeFlare recipe : recipes_flare)
			pureFlareExists = pureFlareExists || recipe.recipeId.equals("pure_flare");

		String[] recipes = pureFlareExists ? array("pure_flare") : array();
//		String[] recipes = recipes_flare.stream().filter(r -> r.recipeId.equals("pure_flare")).count() > 0 ? array("pure_flare") : array();

		addNode("hearthwell", 0, 0, "The Hearth Well", 0, "hearthwell:crystal_diversity", //
				"The Hearth Well is an infinite source of energy given you by the gods.", //
				"The Hearth Well is an infinite source of energy given you by the gods./n"
						+ "Throw offerings near the Hearth Well to gain blessing and/n"
						+ "favour from the gods in the form of small flares./n"
						+ "Flares can be made stronger by adding extra Myst Dust to the recipe./n"
						+ "You can manipulate these flares with a entity_flare torch./n"
						+ "Some items and blocks will react to the presence of flares in particular ways.", //
				recipes, array(), array(), false);
		return this;
	}

	private MapData createDefaults() {

		recipes_influence.add(new RecipeInfluence("recipe_myst_grass", "minecraft:dirt|minecraft:grass_block",
				"hearthwell:myst_grass"));
		recipes_influence.add(new RecipeInfluence("recipe_petrified_wood", "#minecraft:logs", "hearthwell:petrified_wood"));
		recipes_influence.add(new RecipeInfluence("recipe_crystal_ore", "#minecraft:base_stone_overworld", "hearthwell:crystal_ore"));
		recipes_influence.add(new RecipeInfluence("recipe_crystal_ore_black", "minecraft:blackstone", "hearthwell:crystal_ore_black"));

		recipes_flare.add(new RecipeFlare("pure_flare", "Pure Flare", "AA55FF", "hearthwell:myst_dust,hearthwell:crystal"));

		recipes_handitem.add(new RecipeHandItem("recipe_burst_seed", "pure_flare", "hearthwell:inert_seed",
				"hearthwell:burst_seed"));
		recipes_handitem.add(new RecipeHandItem("recipe_mystic_ingot", "pure_flare", "minecraft:iron_ingot",
				"hearthwell:mystic_ingot"));

//	dirt gravel sand red_sand clay
//	cobblestone andesite diorite granite calcite basalt blackstone mossy_cobblestone tuff  deepslate
//	coarse_dirt grass_block mycelium 
//	obsidian crying_obsidian dripstone_block pointed_dripstone 
//	ice blue_ice packed_ice powder_snow snow 
//	water lava
//	coal_ore copper_ore gold_ore iron_ore lapis_lazuli_ore redstone_ore diamond_ore emerald_ore
//	deepslate_ore_variations
//	netherrack soul_soil nylium soul_sand glowstone magma_block nether_quartz_ore nether_gold_ore
//	end_stone gilded_blackstone
//	ancient_debris budding_amethyst

//	saplings
//	carrots beetroots pumpkin melon potatoes cocoa
//	sugar_cane cactus large_flowers grass tall_grass 
//	bamboo_shoot lily_pad sweet_berry_bush moss_block
//	azalea flowering_azalea hanging_roots big_dripleaf small_dripleaf dripleaf_stem spore_blossom
//	cave_vines dead_bush fern    
//	fungi glow lichen mushrooms blocks stem nether sprouts   
//	kelp sea_pickle coral coral_blocks coral_fans seagrass tall_seagrass
//	bee_nest cobweb dragon_egg turtle_egg

//	roots nether_wart warped_nether_wart_block shroomlight twisting_vines weeping_vines

//	chorus_plant chorus_flower

		recipes_burst.add(new RecipeBurstSeed("burstseed_dirt", "minecraft:dirt"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_gravel", "minecraft:gravel"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_sand", "minecraft:sand"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_redsand", "minecraft:red_sand"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_clay", "minecraft:clay"));

		recipes_burst.add(new RecipeBurstSeed("burstseed_stone", "minecraft:stone"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_cobblestone", "minecraft:cobblestone"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_andesite", "minecraft:andesite"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_diorite", "minecraft:diorite"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_granite", "minecraft:granite"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_calcite", "minecraft:calcite"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_basalt", "minecraft:basalt"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_blackstone", "minecraft:blackstone"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_mossy_cobblestone", "minecraft:mossy_cobblestone"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_tuff", "minecraft:tuff"));
		recipes_burst.add(new RecipeBurstSeed("burstseed_deepslate", "minecraft:deepslate"));

		recipes_burst.add(new RecipeBurstSeed("burstseed_quartz", "minecraft:quartz_block"));

		recipes_flare.add(new RecipeFlare("flare_growth", "Flare of Growth", "33FF77", "#minecraft:saplings,"));
		recipes_flare.add(new RecipeFlare("flare_rarity", "Flare of Rarity", "0033FF",
				"hearthwell:myst_dust,#minecraft:saplings"));
		recipes_flare.add(new RecipeFlare("flare_reaction", "Flare of Reaction", "FF3300",
				"hearthwell:myst_dust,#minecraft:saplings"));

		// examples
		recipes_transformation.add(new RecipeTransformation("recipe_fertile_soil", "flare_life", "minecraft:dirt",
				"hearthwell:fertile_soil"));
		recipes_transformation.add(new RecipeTransformation("recipe_myst_grass_2", "pure_flare",
				"minecraft:dirt|minecraft:grass_block", "hearthwell:myst_grass"));
		recipes_transformation.add(new RecipeTransformation("recipe_crystal_ore_black_2", "pure_flare",
				"minecraft:blackstone", "hearthwell:crystal_ore_black"));
		recipes_transformation.add(new RecipeTransformation("recipe_crystal_ore_2", "pure_flare",
				"#minecraft:base_stone_overworld", "hearthwell:crystal_ore"));
		recipes_transformation.add(new RecipeTransformation("recipe_petrified_wood_2", "pure_flare", "#minecraft:logs",
				"hearthwell:petrified_wood"));
//		recipes_handitem.add(new RecipeHandItem("example_recipe_handitem", "example_flare", "minecraft:diamond", "minecraft:coal"));

		recipes_crushing.add(new RecipeCrushing("recipe_", "hearthwell:fertile_soil", "minecraft:dirt"));
//		recipes_crushing.add(new RecipeCrushing("recipe_", "hearthwell:fertile_soil", "minecraft:dirt"));

		addNode("magical_plants", -2, -1, "Magical Plants", 60, "hearthwell:myst_grass", //
				"The Hearth Well can give life and magic.", //
				"Nearby grass absorbs the mysterious energies and changes colour.", //
				array("recipe_myst_grass", "recipe_myst_grass_2"), // recipes
				array("hearthwell"), array(), false);

		addNode("petrification", 2, -1, "Petrification", 60, "hearthwell:petrified_wood", //
				"The Hearth Well can also take away life and magic.", //
				"Nearby wood petrifies and becomes harder and brittle.", //
				array("recipe_petrified_wood", "recipe_petrified_wood_2"), // recipes
				array("hearthwell"), array(), false);

		addNode("crystal_formation", 0, 2, "Crystal Formation", 60, "hearthwell:crystal", //
				"Nearby stone begins to transform into some sort of Mysterious Crystal.", //
				"Stone near the Hearth Well is influenced by it./nIt absorbs the mysterious energies and has a chance/nto transform into crystal ore.", //
				array("recipe_crystal_ore", "recipe_crystal_ore_black", "recipe_crystal_ore_2",
						"recipe_crystal_ore_black_2"), // recipes
				array("hearthwell"), array(), false);

		//

		addNode("on_reactions", 2, 1, "On Reactions", 60, "flare_reaction", //
				"", //
				"", //
				array("flare_reaction"), // recipes
				array(), array(), false);

		addNode("on_rarity", -2, 1, "On Rarity", 60, "hearthwell:mystic_ingot", //
				"", //
				"", //
				array("flare_rarity"), // recipes
				array(), array(), false);

		addNode("on_growth", 0, -2, "On Growth", 60, "hearthwell:mystic_ingot", //
				"", //
				"", //
				array("flare_growth"), // recipes
				array(), array(), false);

		//

//		addNode("material_infusion", 2, 3, "Material Infusion", 120, "hearthwell:mystic_ingot", //
//				"More mysterious stuff.", //
//				"Holding an Iron Ingot in the presence of a Pure Flare will attract it,/nand the two will fuse, creating a new, strong, mystical alloy.", //
//				array("recipe_mystic_ingot"), // recipes
//				array("crystal_formation"), array("hearthwell:crystal"), false);

		addNode("rapid_growth", 0, -4, "Rapid Growth", 120, "hearthwell:fertile_soil", //
				"Create a new better soil for the growth of plant life.", //
				"Make a Cyan Flare from Cyan Dye, then use it to transform/ncoal into diamonds and coal blocks into diamond blocks.", //
				array("flare_life", "recipe_fertile_soil"), // recipes
				array("magical_plants", "petrification"), array("#minecraft:saplings|#minecraft:seeds"), false);

		addNode("material_multiplication", -2, 1, "Material Multiplication", 120, "hearthwell:burst_seed", //
				"When you need more of the same.", //
				"Burst seeds may be fed certain materials which will grow and multiply./nBut be careful, for they are unstable.", //
				array("recipe_burst_seed"), // recipes
				array("magical_plants", "crystal_formation"), array("hearthwell:crystal"), false);

		//

//		addNode("material_infusion", 2, 1, "Material Infusion", 600, "hearthwell:mystic_ingot", //
//				"Create a new better soil for the growth of plant life.", //
//				"Make a Cyan Flare from Cyan Dye, then use it to transform/ncoal into diamonds and coal blocks into diamond blocks.", //
//				array("flare_life", "recipe_fertile_soil"), // recipes
//				array("crystal_formation", "petrification"), array("minecraft:cobblestone", "minecraft:stone", "minecraft:blackstone"), false);

		// Layer2

//		addNode("material_infusion", 4, 1, "Material Infusion", 600, "hearthwell:mystic_ingot", //
//				"Create a new better soil for the growth of plant life.", //
//				"Make a Cyan Flare from Cyan Dye, then use it to transform/ncoal into diamonds and coal blocks into diamond blocks.", //
//				array("flare_life", "recipe_fertile_soil"), // recipes
//				array("material_infusion"), array("minecraft:cobblestone", "minecraft:stone", "minecraft:blackstone"), false);

		return this;
	}

	public MapData init() {

		mapNodesByPosition();

		for (MapNode node : nodes.values()) {
			node.init();
			for (String recipeId : node.recipes_ids) {
				RecipeHearthWell recipe = getRecipeById(recipeId);
				if (recipe == null)
					new HearthWellException(recipeId + " is missing!").printStackTrace();
				else
					recipe.init(node);
			}
		}

		MapNode hwNode = getHwNode();

		for (LinkedList<? extends RecipeHearthWell> recipes : allRecipes)
			for (RecipeHearthWell recipe : recipes)
				if (recipe.mapNode == null)
					recipe.init(hwNode);

		minX = 0;
		minY = 0;
		maxX = 0;
		maxY = 0;

		for (MapNode node : nodes.values()) {
			if (node.x < minX)
				minX = node.x;
			if (node.y < minY)
				minY = node.y;

			if (node.x > maxX)
				maxX = node.x;
			if (node.y > maxY)
				maxY = node.y;
		}
		minX = Math.abs(minX);
		minY = Math.abs(minY);

		return this;
	}

	public RecipeHearthWell getRecipeById(String recipeId) {
		for (RecipeInfluence recipe : recipes_influence)
			if (recipe.recipeId.equals(recipeId))
				return recipe;
		for (RecipeFlare recipe : recipes_flare)
			if (recipe.recipeId.equals(recipeId))
				return recipe;
		for (RecipeTransformation recipe : recipes_transformation)
			if (recipe.recipeId.equals(recipeId))
				return recipe;
		for (RecipeHandItem recipe : recipes_handitem)
			if (recipe.recipeId.equals(recipeId))
				return recipe;
		for (RecipeBurstSeed recipe : recipes_burst)
			if (recipe.recipeId.equals(recipeId))
				return recipe;
		for (RecipeCrushing recipe : recipes_crushing)
			if (recipe.recipeId.equals(recipeId))
				return recipe;
		return null;
	}

	private void mapNodesByPosition() {
		nodesByPosition = new HashMap<>();
		for (MapNode node : nodes.values()) {
			short nodeXY = MapNode.hash(node.x, node.y);
			if (nodesByPosition.containsKey(nodeXY))
				new HearthWellException(
						"Location collision for nodes: " + node.name + " and " + nodesByPosition.get(nodeXY).name)
						.printStackTrace();
			nodesByPosition.put(nodeXY, node);
			for (String parentId : node.parent_ids) {
				MapNode parent = getNode(parentId);
				if (parent == null)
					new HearthWellException("Invalid parent of node " + node.name + ": " + parentId).printStackTrace();
			}
		}
	}

	public void addNode(String id, int x, int y, String name, int time, String stack, String description,
			String fullDescription, String[] recipes, String[] connections, String[] required_items, boolean write) {
		nodes.put(id, new MapNode((byte) x, (byte) y, name, time, stack, description, fullDescription, recipes,
				connections, required_items));
		if (write) {
			init();
			writeData(this);
		}
	}

	public void removeNode(byte x, byte y, boolean write) {

		for (Iterator<String> iterator = nodes.keySet().iterator(); iterator.hasNext();) {
			String nodeId = (String) iterator.next();
			MapNode node = nodes.get(nodeId);
			if (node.x == x && node.y == y && (node.x != 0 || node.y != 0))
				iterator.remove();
		}

		if (write) {
			init();
			writeData(this);
		}
	}

	public void changeNodeId(MapNode node, String id) {
		removeNode(node.x, node.y, false);
		nodes.put(id, node);
		init();
		writeData(this);
	}

	public MapNode getHwNode() {
		return getNode("hearthwell");
	}

	public MapNode getNode(String id) {
		return nodes.get(id);
	}

	public MapNode getNode(byte x, byte y) {
		return nodesByPosition.get(MapNode.hash(x, y));
	}

	public MapNode getNode(byte[] rp) {
		return getNode(rp[0], rp[1]);
	}

	public String getNodeId(MapNode node) {
		for (String nodeId : nodes.keySet()) {
			if (nodes.get(nodeId) == node)
				return nodeId;
		}
		return null;
	}

	public Collection<MapNode> getAll() {
		return nodes.values();
	}

	public boolean nodeExists(short nodeXY) {
		for (MapNode node : nodes.values()) {
			if (nodeXY == MapNode.hash(node.x, node.y))
				return true;
		}
		return false;
	}

	@SafeVarargs
	public final static <T> T[] array(T... recipes) {
		return recipes;
	}

	//
	//
	//

	public static void loadData() {
		DATA = readData();
		DATA.createBaseNode();
		DATA.init();
		if (DATA == null)
			throw new RuntimeException("Could not load Hearth Well data successfully from config " + CONFIG_FILE);
	}

	public static MapData readData() {
		BufferedReader reader = null;
		File file = new File("config", CONFIG_FILE);
		if (!file.exists()) {
			return writeDefaultData();
		}
		try {
			reader = new BufferedReader(new FileReader(file));
			return ((MapData) gson.fromJson(reader, MapData.class)) //
					.createBaseNode() //
					.init(); //
			// @f--
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (Exception e) {
				}
		} // @f++
		return null;
	}

	public static MapData writeDefaultData() {
		return writeData( //
				new MapData() //
						.createBaseNode() //
						.createDefaults() //
						.init() //
		);
	}

	public static MapData writeData(MapData data) {
		BufferedWriter writer = null;
		try {
			// TODO
//			writer = new BufferedWriter(new FileWriter(new File("config", CONFIG_FILE)));
//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			writer.write(gson.toJson(data));
			return data;
			// @f--
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (Exception e) {
				}
		} // @f++
		return null;
	}

	public static class HearthWellException extends Exception {
		private static final long serialVersionUID = HearthWell.VERSION.hashCode();

		public HearthWellException(String message) {
			super(message);
		}
	}

}
