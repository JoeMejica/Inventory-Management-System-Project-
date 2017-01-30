package Model;

public class Class {
	private Aisle[] aisles=new Aisle[2];
	private String ClassLetter;
	private boolean[] aislesFull=new boolean[2]; 
	public Class(String ClassLetter){
		this.ClassLetter=ClassLetter;
		for(int i = 0;i<aislesFull.length;i++){
			aislesFull[i]=false;
		}
	}
	public boolean allAislesFull(){
		if(!ClassLetter.equals("O")){
			for(int i=0;i<aislesFull.length;i++){
				if (!aislesFull[i])
					return false;
			}
				return true;
		}
		else{
			return aislesFull[0];
		}
	}
//	public void addItem(Item newItem){
//		if(!ClassLetter.equals("O")){
//			char aisleLetter=newItem.getBarcode().getAisleLetter();
//			if((aisleLetter-'a')%2==0){
//				aisles[0].addItem(newItem);
//				aislesFull[0]=aisles[0].allSectionsFull();
//			}
//			else{
//				aisles[1].addItem(newItem);
//				aislesFull[1]=aisles[1].allSectionsFull();
//			}
//		}
//		else{
//			aisles[0].addItem(newItem);
//			aislesFull[0]=aisles[0].allSectionsFull();
//		}
//	}
	
	public Item findItem(Barcode barcode){
		Item returnItem;
		if(!ClassLetter.equals("O")){
			char aisleLetter=barcode.getAisleLetter();
			if((aisleLetter-'a')%2==0){
				returnItem = aisles[0].findItem(barcode);
			}
			else{
				returnItem = aisles[1].findItem(barcode);
			}
		}
		else{
			returnItem=aisles[0].findItem(barcode);
		}
		return returnItem;
	}
	public Item removeItem(Barcode barcode){
		Item returnItem=null;
		if(!ClassLetter.equals("O")){
			char aisleLetter=barcode.getAisleLetter();
			if((aisleLetter-'a')%2==0){
				returnItem = aisles[0].removeItem(barcode);
				if(aislesFull[0])
					aislesFull[0]=false;
			}
			else{
				returnItem = aisles[1].removeItem(barcode);
				if(aislesFull[1])
					aislesFull[1]=false;
			}
		}
		else{
			returnItem=aisles[0].removeItem(barcode);
			if(aislesFull[0])
				aislesFull[0]=false;
		}
		return returnItem;	
		}
}
