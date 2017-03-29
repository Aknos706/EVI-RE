package Handler;

import java.util.LinkedList;

public class SQL_StaticDataExport_Map extends SQL_Handler{
    
public SQL_StaticDataExport_Map(){
    super("root",DB.Default_SDE,"");
}


public float getSolarSystemSecurity(int SystemID){
    return Float.valueOf(super.SingleQuery("SELECT * FROM `mapsolarsystems` WHERE solarSystemID="+SystemID, "security"));
}

public String solarSystemID_to_solarSystemName(int SystemID){
    return super.SingleQuery("SELECT * FROM `mapsolarsystems` WHERE solarSystemID="+SystemID, "solarSystemName");
}
public int solarSystemName_to_solarSystemID(String SolarSystem_Name){
    return Integer.valueOf(super.SingleQuery("SELECT * FROM `mapsolarsystems` WHERE solarSystemName='"+SolarSystem_Name+"'", "solarSystemID"));
}

public String constellationID_to_constellationName(int constellationID){
    return super.SingleQuery("SELECT * FROM `mapconstellations` WHERE constellationID="+constellationID, "constellationName");
}
public int constellationName_to_constellationID(String constellation_Name){
    return Integer.valueOf(super.SingleQuery("SELECT * FROM `mapconstellations` WHERE constellationName='"+constellation_Name+"'", "constellationID"));
}

public String regionID_to_regionName(int regionID){
    return super.SingleQuery("SELECT * FROM `mapregions` WHERE regionID="+regionID, "regionName");
}
public int regionName_to_regionID(String region_Name){
    return Integer.valueOf(super.SingleQuery("SELECT * FROM `mapregions` WHERE regionName='"+region_Name+"'", "regionID"));
}


public String[] get_solarSystemCoordinates(int solarSystem_ID){
    String[] R = new String[3];
    R[0] = super.SingleQuery("SELECT * FROM `mapsolarsystems` WHERE solarSystemID="+solarSystem_ID, "x");
    R[1] = super.SingleQuery("SELECT * FROM `mapsolarsystems` WHERE solarSystemID="+solarSystem_ID, "y");
    R[2] = super.SingleQuery("SELECT * FROM `mapsolarsystems` WHERE solarSystemID="+solarSystem_ID, "z");
    return R;
}
public String[] get_constellationCoordinates(int constellation_ID){
    String[] R = new String[3];
    R[0] = super.SingleQuery("SELECT * FROM `mapconstellations` WHERE constellationID="+constellation_ID, "x");
    R[1] = super.SingleQuery("SELECT * FROM `mapconstellations` WHERE constellationID="+constellation_ID, "y");
    R[2] = super.SingleQuery("SELECT * FROM `mapconstellations` WHERE constellationID="+constellation_ID, "z");
    return R;
}
public String[] get_regionCoordinates(int region_ID){
    String[] R = new String[3];
    R[0] = super.SingleQuery("SELECT * FROM `mapregions` WHERE regionID="+region_ID, "x");
    R[1] = super.SingleQuery("SELECT * FROM `mapregions` WHERE regionID="+region_ID, "y");
    R[2] = super.SingleQuery("SELECT * FROM `mapregions` WHERE regionID="+region_ID, "z");
    return R;
}


public int[] get_ConntectionsFromRegion(int fromRegionID){
    LinkedList<String> Con = super.Query("SELECT * FROM `mapregionjumps` WHERE fromRegionID="+fromRegionID, "toRegionID");
    int[] R = new int[Con.size()];
    for(int i=0;i<=Con.size()-1;i++){
        R[i] = Integer.valueOf(Con.get(i));
    }
    return R;
}
public int[] get_ConntectionToRegion(int toRegionID){
    LinkedList<String> Con = super.Query("SELECT * FROM `mapregionjumps` WHERE toRegionID="+toRegionID, "fromRegionID");
    int[] R = new int[Con.size()];
    for(int i=0;i<=Con.size()-1;i++){
        R[i] = Integer.valueOf(Con.get(i));
    }
    return R;
}
public int[] get_ConntectionsFromConstellation(int fromConstellationID){
    LinkedList<String> Con = super.Query("SELECT * FROM `mapconstellationjumps` WHERE fromConstellationID="+fromConstellationID, "toConstellationID");
    int[] R = new int[Con.size()];
    for(int i=0;i<=Con.size()-1;i++){
        R[i] = Integer.valueOf(Con.get(i));
    }
    return R;
}
public int[] get_ConntectionToConstellation(int toConstellationID){
    LinkedList<String> Con = super.Query("SELECT * FROM `mapconstellationjumps` WHERE toConstellationID="+toConstellationID, "fromConstellationID");
    int[] R = new int[Con.size()];
    for(int i=0;i<=Con.size()-1;i++){
        R[i] = Integer.valueOf(Con.get(i));
    }
    return R;
}
public int[] get_ConntectionsFromSolarSystem(int fromSolarSystemID){
    LinkedList<String> Con = super.Query("SELECT * FROM `mapsolarsystemjumps` WHERE fromSolarSystemID="+fromSolarSystemID, "toSolarSystemID");
    int[] R = new int[Con.size()];
    for(int i=0;i<=Con.size()-1;i++){
        R[i] = Integer.valueOf(Con.get(i));
    }
    return R;
}
public int[] get_ConntectionToSolarSystem(int toSolarSystemID){
    LinkedList<String> Con = super.Query("SELECT * FROM `mapsolarsystemjumps` WHERE toSolarSystemID="+toSolarSystemID, "fromSolarSystemID");
    int[] R = new int[Con.size()];
    for(int i=0;i<=Con.size()-1;i++){
        R[i] = Integer.valueOf(Con.get(i));
    }
    return R;
}


public int getConstellation_System(int solarSystemID){
    return Integer.valueOf(super.SingleQuery("SELECT * FROM `mapsolarsystems` WHERE solarSystemID="+solarSystemID, "constellationID"));
}
public int getRegion_System(int solarSystemID){
    return Integer.valueOf(super.SingleQuery("SELECT * FROM `mapsolarsystems` WHERE solarSystemID="+solarSystemID, "regionID"));
}
public int[] getSystem_Constellation(int constellationID){
    LinkedList<String> Systems = super.Query("SELECT * FROM `mapsolarsystems` WHERE constellationID="+constellationID, "solarSystemID");
    int[] R = new int[Systems.size()];
    for (int i=0;i<=Systems.size()-1;i++){
        R[i] = Integer.valueOf(Systems.get(i));
    }
    return R;
}
public int getRegion_Constellation(int constellationID){
    return Integer.valueOf(super.SingleQuery("SELECT * FROM `mapconstellations` WHERE constellationID="+constellationID, "regionID"));
}
public int[] getSystem_Region(int RegionID){
    LinkedList<String> Systems = super.Query("SELECT * FROM `mapsolarsystems` WHERE regionID="+RegionID, "solarSystemID");
    int[] R = new int[Systems.size()];
    for (int i=0;i<=Systems.size()-1;i++){
        R[i] = Integer.valueOf(Systems.get(i));
    }
    return R;
}
public int[] getConstellations_Region(int RegionID){
    LinkedList<String> Systems = super.Query("SELECT * FROM `mapconstellations` WHERE regionID="+RegionID, "constellationID");
    int[] R = new int[Systems.size()];
    for (int i=0;i<=Systems.size()-1;i++){
        R[i] = Integer.valueOf(Systems.get(i));
    }
    return R;
}

public int[] getAllRegions_KSpace(){
    LinkedList<String> Systems = super.Query("SELECT * FROM `mapregions` WHERE regionID<=11000001", "regionID");
    int[] R = new int[Systems.size()];
    for (int i=0;i<=Systems.size()-1;i++){
        R[i] = Integer.valueOf(Systems.get(i));
    }
    return R;
}




}
