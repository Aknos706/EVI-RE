/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Industry;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.TreeMap;

public class SystemCostIndex {
    
public BigDecimal[] CostIndicies = new BigDecimal[9];
public int SystemID=0;
public static TreeMap<Integer,SystemCostIndex> Indicies = new TreeMap<>();


public SystemCostIndex(HashMap<Integer,HashMap> Map,int S_ID){
    int MapSize = Map.keySet().size();
    for(int i=0;i<=MapSize-1;i++){
        HashMap M  = Map.get(i);
        int ActID =Integer.valueOf(String.valueOf(M.get("activityID")));
        BigDecimal Index = new BigDecimal((double)M.get("costIndex"));
        CostIndicies[ActID] = Index;
    }
    this.SystemID=S_ID;
   this.insert();
}
private void insert(){
    Indicies.put(this.SystemID, this);
}
public static TreeMap getMap(){
    return SystemCostIndex.Indicies;
}

public static TreeMap SortedByActivity(Industry.IndustryActivity.Activity Act){
    TreeMap<BigDecimal,Integer> Sorted = new TreeMap<>();
    Integer[] I=new Integer[Indicies.keySet().size()];
    Indicies.keySet().toArray(I);
    for (Integer INT:I){
        Sorted.put(Indicies.get(INT).CostIndicies[Act.ActivityID],Indicies.get(INT).SystemID);
    }
    return Sorted;
}
    
    
    
    
    
    
    
    
    
    
    
    
public class CostIndex{
CostIndex(int A_ID,BigDecimal I){
    
    
}
}
    
    
}
