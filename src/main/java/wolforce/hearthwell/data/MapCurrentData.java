
//	private CurrLoc getOrCreate(byte x, byte y) {
//		CurrLoc temp = new CurrLoc(x, y);
//		if (locations.containsKey(temp))
//			return locations.get(temp);
//		locations.put(temp, temp);
//		return temp;
//	}

//	public boolean isOn(byte x, byte y) {
//		return getOrCreate(x, y).isOn;
//	}

//	public boolean isUnlocked(byte x, byte y) {
//		return getEnergy(x, y) == 127;
//	}

//	public byte getEnergy(byte x, byte y) {
//		if (x == 0 && y == 0)
//			return 127;
//		return getOrCreate(x, y).energy;
//	}

//	public void addEnergy(byte x, byte y, byte energy) {
//		CurrLoc loc = getOrCreate(x, y);
//		if (loc.energy + energy >= 127) {
//			loc.energy = 127;
//			for (RecipeHearthWell recipe : MapData.DATA.getLocation(x, y).recipes)
//				unlockRecipe(recipe);
//		} else
//			loc.energy += energy;
//		System.out.println(loc.energy);
//	}

//	private static class CurrLoc implements Serializable {
//
//		private static final long serialVersionUID = HearthWell.VERSION.hashCode();
//
//		public final byte x, y;
//
////		public byte energy = 0;
//		public boolean isOn = false;
//
//		public CurrLoc(byte x, byte y) {
//			this.x = x;
//			this.y = y;
//		}
//
//		@Override
//		public boolean equals(Object obj) {
//			if (obj != null && obj instanceof CurrLoc)
//				return ((CurrLoc) obj).x == x && ((CurrLoc) obj).y == y;
//			return false;
//		}
//
//		@Override
//		public int hashCode() {
//			return (x + "" + y).hashCode();
//		}
//	}

//
//
//

//	public byte[] toByteArray() {
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		ObjectOutputStream out = null;
//		try {
//			out = new ObjectOutputStream(stream);
//			out.writeObject(this);
//			out.flush();
//			byte[] ret = stream.toByteArray();
//			System.out.println(ret.length);
//			return ret;
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {/*@--*/try { if(stream!=null)stream.close(); } catch (IOException ex) {}}//@++
//		return new byte[] {};
//	}

//	public static MapCurrentData fromByteArray(byte[] buffer) {
//		if (buffer != null && buffer.length != 0) {
//			ObjectInput in = null;
//			try {
//				in = new ObjectInputStream(new ByteArrayInputStream(buffer));
//				return (MapCurrentData) in.readObject();
//			} catch (IOException | ClassNotFoundException e) {
//				e.printStackTrace();
//			} finally {/*@--*/try { if(in!=null)in.close(); } catch (IOException ex) {}}//@++
//		}
//		return new MapCurrentData();
//	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((locations == null) ? 0 : locations.hashCode());
//		result = prime * result + ((unlockedRecipes == null) ? 0 : unlockedRecipes.hashCode());
//		return result;
//	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		MapCurrentData other = (MapCurrentData) obj;
//		if (locations == null) {
//			if (other.locations != null)
//				return false;
//		} else if (!locations.equals(other.locations))
//			return false;
//		if (unlockedRecipes == null) {
//			if (other.unlockedRecipes != null)
//				return false;
//		} else if (!unlockedRecipes.equals(other.unlockedRecipes))
//			return false;
//		return true;
//	}

//
//
//

//}
