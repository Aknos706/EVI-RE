package util;

import Handler.SQL_StaticDataExport;

abstract class Wrapper_Base {
    public String Name="";
    public int typeID=0;
    protected static final SQL_StaticDataExport DATA  = new SQL_StaticDataExport();
    protected Wrapper_Base(int I){  
        this.typeID=I;
        this.Name=DATA.typeID_to_typeName(I);
    }
}
