package util;

public class ItemWrapper extends TypeWrapper{
    public int Quantity=0;
    public ItemWrapper(int id,int qt){
        super(id);
        Quantity=qt;
    }
}