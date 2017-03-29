package Handler;

import java.util.LinkedList;
import java.util.HashMap;
import java.math.BigDecimal;



public class SQL_StaticDataExport extends SQL_Handler{

private HashMap<String,HashMap<String,String>> ConqStations=null;
    
public SQL_StaticDataExport(){
    super(DB.Default_SDE);
}

public void setConqStations(HashMap<String,HashMap<String,String>> Stations){
    this.ConqStations=Stations;
}

public String typeID_to_typeName(int typeID){ // Returns first(ONLY) typeName matching the typeID
String R="No typeName for typeID "+typeID;    
LinkedList<String> tR = super.Query("Select * FROM `invtypes` WHERE typeID="+typeID,"typeName");
if(!tR.isEmpty()){R=tR.getFirst();}
return R;    
}
public String typeID_to_typeName(String string_typeID){ // Returns first(ONLY) typeName matching the typeID
int typeID=Integer.valueOf(string_typeID);
String R="No typeName for typeID "+typeID;    
LinkedList<String> tR = super.Query("Select * FROM `invtypes` WHERE typeID="+typeID,"typeName");
if(!tR.isEmpty()){R=tR.getFirst();}
return R;    
}

public int typeName_to_typeID(String typeName){
int R=-1;
LinkedList<String> tLL=super.Query("Select * FROM `invtypes` WHERE typeName='"+typeName+"'","typeID");
if (!tLL.isEmpty()){R=(int)Integer.parseInt(tLL.getFirst());}
return R;
}

public int getGroupID(int typeID){
    return Integer.valueOf(super.SingleQuery("SELECT * FROM `invtypes` WHERE typeID="+typeID, "groupID"));
}
public String getGroupName(int groupID){
    return super.SingleQuery("SELECT * FROM `invgroups` WHERE groupID="+groupID, "groupName");
}
public int getCategoryID(int groupID){
    return Integer.valueOf(super.SingleQuery("SELECT * FROM `invgroups` WHERE groupID="+groupID, "categoryID"));
}    
public String getCategoryName(int CategoryID){
    return super.SingleQuery("SELECT * FROM `invcategories` WHERE CategoryID="+CategoryID, "categoryName");
}

public Integer[] getGroupID_Member_Published(int GroupID){
    // 268 + 270 Prod & Research
    LinkedList<String> Members = super.Query("SELECT * FROM `invtypes` WHERE groupID='"+GroupID+"' AND published='1'", "typeID");
    Integer[] R = new Integer[Members.size()];
    for (int i=0;i<=Members.size()-1;i++){
        R[i]=Integer.valueOf(Members.get(i));
    }
    return R;
}
public Integer[] getCategoryMember(int CategoryID){
    LinkedList<String> Members = super.Query("SELECT * FROM `invgroups` WHERE categoryID='"+CategoryID+"'", "groupID");
    Integer[] R = new Integer[Members.size()];
    for (int i=0;i<=Members.size()-1;i++){
        R[i]=Integer.valueOf(Members.get(i));
    }
    return R;
}

public String stationID_to_stationName(int stationID){ // Returns first(ONLY) typeName matching the typeID
String R="No stationName for stationID "+stationID;    
LinkedList<String> tR = super.Query("SELECT * FROM `stastations` WHERE stationID="+stationID,"stationName");
if(!tR.isEmpty()){R=tR.getFirst();}
return R;    
}
public String stationID_to_stationName(String string_stationID){ // Returns first(ONLY) typeName matching the typeID
    int stationID=Integer.valueOf(string_stationID);
    String R="No stationName for stationID "+stationID;
    LinkedList<String> tR = super.Query("SELECT * FROM `stastations` WHERE stationID="+stationID,"stationName");
    if(!tR.isEmpty()){
        R=tR.getFirst();
    }
    return R;    
}
public String stationMap_to_stationName(HashMap<String,String> LHM){
    String R="No stationName for stationID ";
    if(LHM.get("typeID").equalsIgnoreCase("27")){ // Station Office
        
    }else if(LHM.get("typeID").equalsIgnoreCase("62")){ // BUY / SELL / DELIVERIES
        
    }
    
    return R;
}





public String officeID_to_StationID(int locationID){
    if(locationID<=60014861 && locationID>=60014928){
        return this.stationID_to_stationName(locationID);
    }else if(locationID>=66000000 && locationID<67000000){
        return this.stationID_to_stationName(locationID-6000001);
    }else if(locationID>=67000000 && locationID<68000000){
        return ConqStations.get(String.valueOf(locationID-6000000)).get("stationName");
    }else if(locationID < 60000000 ){
        return super.SingleQuery("SELECT * FROM `mapsolarsystems` WHERE solarSystemID="+locationID, "solarSystemName");
    }else{
        return null;
    }
}

public String InventoryFlag_to_FlagName(int flag){
    return super.Query("SELECT * FROM `invflags` WHERE flagID="+flag, "flagText").getFirst();
}


public boolean is_published(int typeID){
boolean R=false;
LinkedList<String> tLL=super.Query("Select * FROM `invtypes` WHERE typeID='"+typeID+"'","published");
if (!tLL.isEmpty()){
    if(tLL.getFirst().equalsIgnoreCase("1")){R=true;}
}
return R;
}

public boolean Blueprint_exists(String typeName){
    if (!typeName.endsWith(" Blueprint")){
        typeName=typeName+" Blueprint";
    }
    return Blueprint_exists(typeName_to_typeID(typeName));
}

public boolean Blueprint_exists(int typeID){
    boolean R=false;
    if (!super.Query("SELECT * FROM `industryactivityproducts` WHERE typeID="+typeID,"typeID").isEmpty()){
        R=true;
    }
    return R;
}

public BigDecimal Type_Baseprice(int typeID){
    return new BigDecimal(super.SingleQuery("SELECT * FROM `invtypes` WHERE typeID="+typeID, "basePrice"));
}

public LinkedList<String> getIndustryActivities(int ID){
    return super.Query("SELECT * FROM `industryactivity` WHERE typeID="+ID, "activityID");
}

public int getIndustryActivityTime(int ID,int ActID){
    return Integer.valueOf(super.SingleQuery("SELECT * FROM `industryactivity` WHERE typeID="+ID+" AND activityID="+ActID, "time"));
}

public LinkedList<String> getIndustryActivity_Materials_Types(int ID,int ActID){
    return super.Query("SELECT * FROM `industryactivitymaterials` WHERE typeID="+ID+" AND activityID="+ActID, "materialTypeID");
}
public LinkedList<String> getIndustryActivity_Materials_Quantity(int ID,int ActID){
    return super.Query("SELECT * FROM `industryactivitymaterials` WHERE typeID="+ID+" AND activityID="+ActID, "quantity");
}

public LinkedList<String> getIndustryActivity_Products_Types(int ID,int ActID){
    return super.Query("SELECT * FROM `industryactivityproducts` WHERE typeID="+ID+" AND activityID="+ActID,"productTypeID");
}
public LinkedList<String> getIndustryActivity_Products_Quantity(int ID,int ActID){
    return super.Query("SELECT * FROM `industryactivityproducts` WHERE typeID="+ID+" AND activityID="+ActID,"quantity");
}

public LinkedList<String> getIndustryActivity_Skills_Types(int ID,int ActID){
    return super.Query("SELECT * FROM `industryactivityskills` WHERE typeID="+ID+" AND activityID="+ActID, "skillID");
}
public LinkedList<String> getIndustryActivity_Skills_Level(int ID,int ActID){
    return super.Query("SELECT * FROM `industryactivityskills` WHERE typeID="+ID+" AND activityID="+ActID, "level");
}

}