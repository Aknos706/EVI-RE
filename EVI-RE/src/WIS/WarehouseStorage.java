package WIS;

import java.util.HashMap;
import java.math.BigDecimal;

public class WarehouseStorage {
    HashMap<Integer, WarehouseItem> StorageMap; // TypeID -> WarehouseItem
    
    public WarehouseStorage(){
        this.StorageMap = new HashMap<>();
    }
    
    public void addToStorage(int tid,int q,BigDecimal v){
        if(this.StorageMap.containsKey(tid)){
            WarehouseItem temp = this.StorageMap.get(tid);
            temp.addQuantity(q, v);
            this.StorageMap.replace(tid, temp);
        }else{
            this.StorageMap.put(tid,new WarehouseItem(tid,q,v));
        }
    }
    
    public void addToStorage(String typeName,int q,BigDecimal v){
        
    }
    
    //remove from storage returns nothing
    //take from storage returns warehouseitem
    
}
