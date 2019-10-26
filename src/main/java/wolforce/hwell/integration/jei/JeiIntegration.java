package wolforce.hwell.integration.jei;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import wolforce.hwell.Main;
import wolforce.hwell.blocks.BlockCore;
import wolforce.mechanics.api.integration.MakeJeiIntegration;

public class JeiIntegration {

	public MakeJeiIntegration jei = new MakeJeiIntegration();

	public void register() {
		for (BlockCore core : Main.cores.values())
			jei.add(new JeiCatCoring(core));

		jei.add(new JeiCatBoxing());
		jei.add(new JeiCatCharging());
		jei.add(new JeiCatCrushing());
		jei.add(new JeiCatFreezing());
		jei.add(new JeiCatGrinding());
		jei.add(new JeiCatMutating());
		jei.add(new JeiCatPortal());
		jei.add(new JeiCatPulling());
		jei.add(new JeiCatPullingFiltered());
		jei.add(new JeiCatSeparating());
		jei.add(new JeiCatTubing());
		jei.add(new JeiCatGrafting());

		//

		// INFOS

		jei.addInfo(Main.crystal_nether, "Obtained by throwing a crystal into the nether portal.");
		jei.addInfo(new ItemStack(Main.cores.get("core_anima")), "Obtained by growing an Inert Seed.",
				"You must stay within 4 blocks of it.");
		jei.addInfo(new ItemStack(Main.inert_seed),
				"If you stay within 4 blocks of it, it will slowly grow and become an Anima Core.");
		jei.addInfo(new ItemStack(Main.cores.get("core_heat")),
				"Obtained by right clicking a Heat Block with a flint and steel.", "Just be careful...");
		jei.addInfo(new ItemStack(Main.empty_rod), "Hold right click to use.");
		jei.addInfo(new ItemStack(Main.branch), "Drop from Breaking Trees on Grit Vases.");

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("patchouli:book", "hwell:book_of_the_well");
		ItemStack book = new ItemStack(Item.getByNameOrId("patchouli:guide_book"), 1, 0, nbt);
		book.setTagInfo("patchouli:book", new NBTTagString("hwell:book_of_the_well"));
		jei.addInfo(book, "To get this item you need two wooden pressure plates, one on each hand. "
				+ "Then say an HONEST HEARTFELT prayer to the gods containing, but not only, the words \"poor\", \"please\", \"send\", \"book\", \"gods\". "
				+ "Quickly after that, while looking directly up, smash the pressure plates together with right click!");
	}

}
