package Industry;

import Handler.*;
import java.util.HashMap;
import java.util.LinkedList;

public class IndustryStatus {
    private LinkedList<Integer> Characters = new LinkedList<>();
    
    
public IndustryStatus(){
    
}





private final class FakeBlueprint extends Blueprint{
    
public FakeBlueprint(){
    
}


public Integer[] getSkills(int Act){
if(Act<=2){
    return this.getProductionSkills();
}else{
    return this.getScienceSkills();
}
}


public Integer[] getProductionSkills(){
    // 268
    return Blueprint.DATA.getGroupID_Member_Published(268);
}
public Integer[] getScienceSkills(){
    // 270
    return Blueprint.DATA.getGroupID_Member_Published(270);
}
    
}

}
