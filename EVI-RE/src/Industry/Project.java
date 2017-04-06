package Industry;

import Handler.*;
import util.ItemWrapper;
import java.util.LinkedList;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Project {
Blueprint BP= null;  // Single BP per Project. Multi BP Projects -> ProjectGroup
int Quantity=0;
String ProjectDesc = "";
ItemWrapper[] Product=null;

BigDecimal EstimatedBuildPrice=new BigDecimal(0);
private static final SQL_StaticDataExport   Data        = new SQL_StaticDataExport();
private static       boolean                Verbose=false;


LinkedList<Project> SubProjects = new LinkedList();                             // For i.e. T2 Materials / T1 Modules / Cap Parts / etc
LinkedList<ItemWrapper> Materials = new LinkedList<>();                         // Materials for THIS Stage
LinkedList<Integer> TypeIDs = new LinkedList<>();                               // Type IDs for Marketstat / Price Estimate

public Project(String BPName,int Qt,Industry.IndustryActivity.Activity Activity,Blueprint.MaterialEfficiency ME,Blueprint.TimeEfficiency TE){
    Quantity=Qt;
    BP = new Blueprint(BPName);
    if(Activity.Invention.equals(Activity) && !BPName.endsWith(" I")){
        try {
            throw this.NoInvention(BP);
        } catch (Exception ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    this.ProjectDesc = Activity.name()+" "+BPName+" x "+Qt;
    BP.set_ME_Multiplier(ME.ME);
    BP.set_TE_Multiplier(TE.TE);
    Product = new ItemWrapper[BP.getProduct(Activity.ActivityID).size()];
    for(int i=0;i<=BP.getProduct(Activity.ActivityID).size()-1;i++){
        Product[i] = new ItemWrapper(BP.getProduct(Activity.ActivityID).get(i).typeID,1);
    }
    Materials=getMaterials(Activity.ActivityID,Qt);
    addSubProjects(Activity);
}
public Project(){
    
}

public void setVerbose(boolean B){
    Project.Verbose=B;
}

/*
public void calc_EstimatedPrice(XML_EveCentralAPI.Market_Request_Method Method,XML_EveCentralAPI.Market Market){
BigDecimal BrokerFee = (new BigDecimal("100").add(EveCentral.getBrokerFees(Market))).divide(new BigDecimal("100"));
HashMap<Integer,ItemWrapper> ProjectMaterials = this.getProjectMaterialIDs();
Integer[] keys = new Integer[ProjectMaterials.keySet().size()];
ProjectMaterials.keySet().toArray(keys);
EveCentral.updatePrices(keys);

BigDecimal Total = new BigDecimal(0);
BigDecimal MatPrice;
BigDecimal MaterialQt;

for (int i=0;i<=ProjectMaterials.size()-1;i++){
    MaterialQt = new BigDecimal(ProjectMaterials.get(keys[i]).Quantity);
    MatPrice = EveCentral.getMarketStatPrice(ProjectMaterials.get(keys[i]).typeID, Method.getMethod());
    BigDecimal temp = MatPrice.multiply(MaterialQt);
    if(Project.Verbose){
        System.out.println(MaterialQt.intValue()+" x "+Data.typeID_to_typeName(ProjectMaterials.get(keys[i]).typeID)+" cost "+temp.floatValue()+" @"+MatPrice.floatValue()+" each");
    }
    Total=Total.add(temp.multiply(BrokerFee));
}
EstimatedBuildPrice=Total;
}
*/

private int getTime(IndustryActivity.Activity AC){
    BigDecimal SkillTimeMulti = new BigDecimal("1");
    Integer BP_Time=this.BP.getTime(AC);
    return SkillTimeMulti.multiply(new BigDecimal(BP_Time)).intValue();
}


private HashMap getProjectMaterialIDs(){
    HashMap R = getMaterials();
    return R;
}

private HashMap<Integer,ItemWrapper> getMaterials(){
    HashMap<Integer,ItemWrapper> R = new HashMap<>();
    for (int i=0;i<=Materials.size()-1;i++){
        R.put(Materials.get(i).typeID,Materials.get(i));
    }
    if(!SubProjects.isEmpty()){
        for (int i=0;i<=SubProjects.size()-1;i++){
            combineMaps(R,SubProjects.get(i).getMaterials());
        }
    }
    return R;
}

private HashMap<Integer,ItemWrapper> combineMaps(HashMap<Integer,ItemWrapper> Map1,HashMap<Integer,ItemWrapper> Map2){
    LinkedList<Integer> KeysMap2 = Handler.Handler_Base.getHashMapKeys_Integer(Map2);
    for (int i=0;i<=KeysMap2.size()-1;i++){
        if(Map1.containsKey(KeysMap2.get(i))){
            ItemWrapper Temp = Map1.get(KeysMap2.get(i));
            Temp.Quantity = Temp.Quantity + Map2.get(KeysMap2.get(i)).Quantity;
            Map1.put(Temp.typeID, Temp);
        }else{
            Map1.put(KeysMap2.get(i),Map2.get(KeysMap2.get(i)));
        }
    }
    return Map1;
}

private LinkedList<ItemWrapper> getMaterials(int Act, int Q){ // THIS Blueprint, ignoring Subprojects
return BP.getMaterials(Act,Q);
}

private void addSubProjects(Industry.IndustryActivity.Activity Activity){
    LinkedList<ItemWrapper> tMat = new LinkedList<>();
    for (int i=0;i<=Materials.size()-1;i++){
        if (Data.Blueprint_exists(Data.typeID_to_typeName(Materials.get(i).typeID))){
            SubProjects.add(new Project(Data.typeID_to_typeName(Materials.get(i).typeID),(int)Materials.get(i).Quantity,Activity,Blueprint.MaterialEfficiency.ME_10,Blueprint.TimeEfficiency.TE_20));  // TODO Check for ME/TE for known Blueprints
        }else{
            tMat.add(Materials.get(i));
        }
    }
    Materials=tMat;
}

private Exception NoInvention(Blueprint BP){
    System.err.println("Cant Invent from "+BP.Name);
    System.exit(1);
    return null;
}

public enum Invention{
    Invention(0,2,4,new BigDecimal("1")),
    Accelerant(1,2,10,new BigDecimal("1.2")),
    Attainment(4,-1,4,new BigDecimal("1.8")),
    Augmentation(9,-2,2,new BigDecimal("0.6")),
    Parity(3,1,-2,new BigDecimal("1.5")),
    Process(0,3,6,new BigDecimal("1.1")),
    Symmetry(2,1,8,new BigDecimal("1")),
    OptimizedAttainment(2,1,-2,new BigDecimal("1.9")),
    OptimizedAugmentation(7,2,0,new BigDecimal("0.9"));
    public final int AdditionalRuns;
    public final int ME_Modifier;
    public final int TE_Modifier;
    public final BigDecimal ChanceMod;
    Invention(int run,int me,int te, BigDecimal Chance){
        this.AdditionalRuns=run;
        this.ME_Modifier=me;
        this.TE_Modifier=te;
        this.ChanceMod=Chance;
    }
}

}
