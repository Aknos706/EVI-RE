package util;

import Handler.SQL_StaticDataExport;

public abstract class Wrapper_Base {
    
    public String typeName="";
    public int typeID=0;
    protected static final SQL_StaticDataExport DATA  = new SQL_StaticDataExport();
    
    protected Wrapper_Base(int I){  
        this.typeID=I;
        this.typeName=DATA.typeID_to_typeName(I);
    }
    
    protected Wrapper_Base(String S){
        this.typeName=S;
        this.typeID=DATA.typeName_to_typeID(typeName);
    }
}
