package wolforce;

public class HwellMessage {
	// implements IMessage {
	//
	// public static final SimpleNetworkWrapper INSTANCE =
	// NetworkRegistry.INSTANCE.newSimpleChannel("hwell");
	//
	// // A default constructor is always required
	// public HwellMessage() {
	// }
	//
	// private int toSend;
	//
	// public HwellMessage(int toSend) {
	// this.toSend = toSend;
	// }
	//
	// @Override
	// public void toBytes(ByteBuf buf) {
	// // Writes the int into the buf
	// buf.writeInt(toSend);
	// }
	//
	// @Override
	// public void fromBytes(ByteBuf buf) {
	// // Reads the int back from the buf. Note that if you have multiple values,
	// you
	// // must read in the same order you wrote.
	// toSend = buf.readInt();
	// }
	//
	// // The params of the IMessageHandler are <REQ, REPLY>
	// // This means that the first param is the packet you are receiving, and the
	// // second is the packet you are returning.
	// // The returned packet can be used as a "response" from a sent packet.
	// public class HwellMessageHandler implements IMessageHandler<HwellMessage,
	// IMessage> {
	// // Do note that the default constructor is required, but implicitly defined
	// in
	// // this case
	//
	// @Override
	// public IMessage onMessage(HwellMessage message, MessageContext ctx) {
	// // This is the player the packet was sent to the server from
	// EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
	// // The value that was sent
	// int amount = message.toSend;
	//
	// // Execute the action on the main server thread by adding it as a scheduled
	// task
	// serverPlayer.getServerWorld().spawnParticle(particleType, xCoord, yCoord,
	// zCoord, numberOfParticles, xOffset, yOffset, zOffset, particleSpeed,
	// particleArguments);
	//
	// // serverPlayer.getServerWorld().addScheduledTask(() -> {
	// // serverPlayer.inventory.addItemStackToInventory(new
	// ItemStack(Items.DIAMOND,
	// // amount));
	// // });
	// // No response packet
	// return null;
	// }
	//
	// }
}
