package mb;

/**
 * 内部建造者
 * @author KCSTATION
 *
 */
public class Workspace {
	private int blockQt;
	private int workerQt;
	

	public static class Builder<T> {
		
		private int totalMoney;
		
		private int blockQt;
		
		public Builder(int totalMoney){
			this.totalMoney = totalMoney;
		}
		
		public Builder dipBlock(int blockQt1){
			this.blockQt = blockQt1;
			return this;
		}
		
		public T build(){
			return (T) new Workspace(this);
		}
		
	}
	
	private Workspace(Builder builder){
		this.blockQt = builder.blockQt;
	}
	
}
