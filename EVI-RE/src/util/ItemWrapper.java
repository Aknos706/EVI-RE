package util;

public class ItemWrapper extends Wrapper_Base{
    public int Quantity=0;
    public ItemWrapper(int id,int qt){
        super(id);
        Quantity=qt;
    }
    public ItemWrapper(int id){
        super(id);
        Quantity=1;
    }
    public void addQuantity(int Qt) {
        Quantity+=Qt; // old + new
    }
}