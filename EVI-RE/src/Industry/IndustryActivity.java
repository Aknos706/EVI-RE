package Industry;

import Handler.SQL_StaticDataExport;
import util.ItemWrapper;
import util.SkillWrapper;
import java.util.LinkedList;

public class IndustryActivity{

Activity                    Act;
int                         BlueprintTypeID;
int                         Time;           // Time to finish Activity (Time in Seconds)
LinkedList<ItemWrapper>     Matlist     =   new LinkedList<>();
LinkedList<SkillWrapper>    SkillList   =   new LinkedList<>();
LinkedList<ItemWrapper>     Products    =   new LinkedList<>();

private static final SQL_StaticDataExport DATA = new SQL_StaticDataExport();

public IndustryActivity(Activity ActID,int BP_ID, int debug){
    this.Act = ActID;
    BlueprintTypeID=BP_ID;
    Time=DATA.getIndustryActivityTime(BlueprintTypeID, Act.ActivityID);
    // -- -- -- Materials

    LinkedList<String> Types        = DATA.getIndustryActivity_Materials_Types(BlueprintTypeID, Act.ActivityID);
    LinkedList<String> Quantities   = DATA.getIndustryActivity_Materials_Quantity(BlueprintTypeID, Act.ActivityID);

    for (int i=0;i<=Types.size()-1;i++){
        Matlist.add(new ItemWrapper(Integer.parseInt(Types.get(i)),Integer.parseInt(Quantities.get(i))));    
    }

// -- -- -- Products

    Types       = DATA.getIndustryActivity_Products_Types(BlueprintTypeID, Act.ActivityID);
    Quantities  = DATA.getIndustryActivity_Products_Quantity(BlueprintTypeID, Act.ActivityID);

    for (int i=0;i<=Types.size()-1;i++){
        Products.add(new ItemWrapper(Integer.parseInt(Types.get(i)),Integer.parseInt(Quantities.get(i))));    
    }

// -- -- -- Skills

    Types       = DATA.getIndustryActivity_Skills_Types(BlueprintTypeID, Act.ActivityID);
    Quantities  = DATA.getIndustryActivity_Skills_Level(BlueprintTypeID, Act.ActivityID);

    for (int i=0;i<=Types.size()-1;i++){
        SkillList.add(new SkillWrapper(Integer.parseInt(Types.get(i)),Integer.parseInt(Quantities.get(i))));    
    }

}
    
public enum Activity{
    None(0,"No Activity"),
    Manufacturing(1,"Manufacturing"),
    Technology_Research(2,"Technology Research"),
    TE_Research(3,"TE_Research"),
    ME_Research(4,"ME_Research"),
    Copying(5,"Copying"),
    Duplicating(6,"Duplicating"),
    ReverseEngineering(7,"ReverseEngineering"),
    Invention(8,"Invention");
    public final int ActivityID;
    public final String ActivityName;
    private Activity(int ActID, String ActName){
        this.ActivityID=ActID;
        this.ActivityName=ActName;
    }
}

}