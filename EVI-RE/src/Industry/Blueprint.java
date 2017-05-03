package Industry;

import Handler.SQL_StaticDataExport;
import util.ItemWrapper;
import util.SkillWrapper;

import java.util.LinkedList;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;


public class Blueprint {
int ID;
int groupID;
String Name;
File Image;
boolean Public;
BigDecimal ME_Level= new BigDecimal(0);
BigDecimal TE_Level= new BigDecimal(0);
protected static SQL_StaticDataExport DATA = new SQL_StaticDataExport();

private final IndustryActivity[] Activities = new IndustryActivity[9];

public enum MaterialEfficiency{
    ME_01("1"),
    ME_02("2"),
    ME_03("3"),
    ME_04("4"),
    ME_05("5"),
    ME_06("6"),
    ME_07("7"),
    ME_08("8"),
    ME_09("9"),
    ME_10("10");
    public final BigDecimal ME;
    MaterialEfficiency(String V){
        this.ME= new BigDecimal(V);
    }
}

public enum TimeEfficiency{
    TE_02("2"),
    TE_04("4"),
    TE_06("6"),
    TE_08("8"),
    TE_10("10"),
    TE_12("12"),
    TE_14("14"),
    TE_16("16"),
    TE_18("18"),
    TE_20("20");
    public final BigDecimal TE;
    TimeEfficiency(String V){
        this.TE=new BigDecimal(V);
    }
}

public Blueprint(String typeName){
if (!typeName.endsWith("Blueprint")){typeName=typeName.trim()+" Blueprint";}
build(DATA.typeName_to_typeID(typeName));
}
protected Blueprint(){ // IndustryStatus.FakeBlueprint
    
}

/**
* Builds Blueprint from Blueprint TypeID
* @param type_ID
*/
public Blueprint(int type_ID){
build(type_ID);
}

// Called from Constructor 
private void build(int id){
ID=id;
groupID=DATA.getGroupID(ID);
Name=DATA.typeID_to_typeName(ID);
Image=new File("http://image.eveonline.com/Type/"+ID+"_64.png");

Public=DATA.is_published(ID);
LinkedList<String> tAct= DATA.getIndustryActivities(ID);

for (int i=0;i<=tAct.size()-1;i++){
Activities[Integer.parseInt(tAct.get(i))]=new IndustryActivity( IndustryActivity.Activity.values()[Integer.parseInt(tAct.get(i))],ID,i );
}  
}
// Constructor END




public void set_ME_Multiplier(BigDecimal newME){
ME_Level=new BigDecimal(100).subtract(newME).divide(new BigDecimal(100));
}

public void set_TE_Multiplier(BigDecimal newTE){ // LEVEL not Reduction in %
BigDecimal NTE=newTE.multiply(new BigDecimal(2));
TE_Level=new BigDecimal(100).subtract(NTE).divide(new BigDecimal(100));
}

/**
* gets Materials needed to Perform Actitvity. 0 TypeID / 1 Qt
* @param Act
* @param Quantity
* @return
*/
public LinkedList<ItemWrapper> getMaterials(int Act,int Quantity){
LinkedList<ItemWrapper> R=new LinkedList<>();

for (int i=0;i<=Activities[Act].Matlist.size()-1;i++){
//int t=Activities[Act].Matlist.get(i)[0]; // gets TypeID
int t=Activities[Act].Matlist.get(i).typeID;

BigInteger temp=Activities[Act].Matlist.get(i).Quantity;
BigDecimal Mat= new BigDecimal(temp).multiply(new BigDecimal(Quantity)).multiply(ME_Level).setScale(0, RoundingMode.CEILING);

R.add(new ItemWrapper(t,Mat.toBigInteger()));
}
   
return R;
}

public LinkedList<ItemWrapper> getProduct(int Act){
LinkedList<ItemWrapper> R= new LinkedList<>();
for (int i=0;i<=Activities[Act].Products.size()-1;i++){
    //R.add(Activities[Act].Products.get(i)[0]);
    R.add(new ItemWrapper(Activities[Act].Products.get(i).typeID,Activities[Act].Products.get(i).Quantity));
}
return R;
}

public Integer[] getSkills(Industry.IndustryActivity.Activity Act){
LinkedList<SkillWrapper> S = Activities[Act.ActivityID].SkillList;
Integer[] R= new Integer[S.size()];
S.toArray(R);
return R;
}

public int getTime(Industry.IndustryActivity.Activity Act){
    return Activities[Act.ActivityID].Time;
}

}    
    
