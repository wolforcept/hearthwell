package wolforce.hearthwell.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import wolforce.hearthwell.entities.EntityHearthWell;

import java.util.function.Supplier;

public class MessageToggle {

	private int entityId;
	private byte x, y;

	public MessageToggle(Entity entity, byte x, byte y) {
		this(entity.getId(), x, y);
	}

	public MessageToggle(int entityId, byte x, byte y) {
		this.entityId = entityId;
		this.x = x;
		this.y = y;
	}

	public void encode(FriendlyByteBuf buff) {
		buff.writeInt(entityId);
		buff.writeByte(x);
		buff.writeByte(y);
	}

	public static MessageToggle decode(FriendlyByteBuf buff) {
		return new MessageToggle(buff.readInt(), buff.readByte(), buff.readByte());
	}

	public void onMessage(final Supplier<NetworkEvent.Context> ctx) {
		Player player = ctx.get().getSender();
		Entity entity = player.level().getEntity(entityId);
		if (entity instanceof EntityHearthWell) {
			EntityHearthWell hw = ((EntityHearthWell) entity);
			hw.setResearchPoint(x, y);
		}
		ctx.get().setPacketHandled(true);
	}

}