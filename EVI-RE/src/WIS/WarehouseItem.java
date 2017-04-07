package WIS;

import java.math.BigDecimal;

public class WarehouseItem extends util.ItemWrapper{

public BigDecimal perItemValue;
public BigDecimal totalItemValue;

public WarehouseItem(int tid,int q,BigDecimal v){
    super(tid,q);
    this.perItemValue=v;
    this.totalItemValue=v;
}

public void Info(){
    System.out.println("Total Quantity of the Stockpile is "+this.Quantity);
    System.out.println("Total Value of the Stockpile is "+this.totalItemValue);
    System.out.println("Value per Object now is "+this.perItemValue);
}

public void addQuantity(int q,BigDecimal v){
    this.Quantity = this.Quantity + q;
    BigDecimal valueToBeAdded = new BigDecimal(q).multiply(v);
    this.totalItemValue = this.totalItemValue.add(valueToBeAdded);
    this.perItemValue = this.totalItemValue.divide(new BigDecimal(this.Quantity));
    System.out.println("Added "+q+" of "+this.typeName+" to Stockpile");
}

public void subtractQuantity(int q) throws WIS.WarehouseExceptions.NotEnoughItems{
    if (q<=this.Quantity){
        this.Quantity = this.Quantity - q;
    }else{
        throw new WIS.WarehouseExceptions.NotEnoughItems();
    }
}
        

}
