//package wolforce.hearthwell.net;
//
//import java.util.function.Supplier;
//
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.network.PacketBuffer;
//import net.minecraftforge.fml.network.NetworkEvent;
//import wolforce.hearthwell.data.MapCurrentData;
//import wolforce.hearthwell.entities.EntityHearthWell;
//
//public class MessageEnergy {
//
//	private int entityId;
//	private byte x, y, energy;
//
//	public MessageEnergy(Entity entity, byte x, byte y, byte energy) {
//		this(entity.getEntityId(), x, y, energy);
//	}
//
//	public MessageEnergy(int entityId, byte x, byte y, byte energy) {
//		this.entityId = entityId;
//		this.x = x;
//		this.y = y;
//		this.energy = energy;
//	}
//
//	public void encode(PacketBuffer buff) {
//		buff.writeInt(entityId);
//		buff.writeByte(x);
//		buff.writeByte(y);
//		buff.writeByte(energy);
//	}
//
//	public static MessageEnergy decode(PacketBuffer buff) {
//		return new MessageEnergy(buff.readInt(), buff.readByte(), buff.readByte(), buff.readByte());
//	}
//
//	public void onMessage(final Supplier<NetworkEvent.Context> ctx) {
//		PlayerEntity player = ctx.get().getSender();
//		Entity entity = player.world.getEntityByID(entityId);
//		if (entity instanceof EntityHearthWell) {
//			EntityHearthWell hw = ((EntityHearthWell) entity);
//			MapCurrentData currData = hw.getCurrentData();
//			currData.addEnergy(x, y, energy);
//			hw.setCurrentData(currData);
//		}
//		ctx.get().setPacketHandled(true);
//	}
//
//}