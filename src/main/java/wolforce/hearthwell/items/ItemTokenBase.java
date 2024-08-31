package wolforce.hearthwell.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.TokenNames;
import wolforce.hearthwell.client.events.EventsForge;

public class ItemTokenBase extends Item {

	public ItemTokenBase(Properties props) {
		super(props);
	}

	private static final char[] chars = { 'A', 'E', 'I', 'O', 'U', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '⚔', '☠', '☀', '☁', '☽',
			'♀', '♂', '♠', '♣', '♥', '♦', '♪', '♫', '♬', '⚠', '⚡', '⛏', '❄', '❌', '❤', 'ᘔ', 'Ɛ', '⏺', '⚓', '⛨', };

	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
		Player player = context.getPlayer();

//		if (!player.isShiftKeyDown())
//			return InteractionResult.PASS;

		Level world = context.getLevel();
		BlockPos blockpos = context.getClickedPos();

		int n = chars[world.getRandom().nextInt(chars.length)];

		if (world.isClientSide) {
			EventsForge.setRenderLetter(blockpos, n);
		} else {
			int match = TokenNames.addFromPlayer(player.getStringUUID(), (char) n);
			if (match >= 0) {
				player.setItemInHand(context.getHand(), new ItemStack(HearthWell.getTokenItem(match)));
			}
		}
		return InteractionResult.SUCCESS;
	}

}
