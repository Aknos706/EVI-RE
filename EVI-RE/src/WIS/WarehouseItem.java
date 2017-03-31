package WIS;

import java.math.BigDecimal;

public class WarehouseItem {

public int typeID;
//public String typeName;
public BigDecimal Value;
public int amount;

public WarehouseItem(int tid, BigDecimal v,int m){
    this.Value=v;
    this.typeID=tid;
    this.amount=m;
}
    
}
