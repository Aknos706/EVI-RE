package Handler;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;



public abstract class Handler_Base {
    
public enum CacheTime{
    Second(1000),
    Minute(Second.milis*60),
    Hour(Minute.milis*60),
    Day(Hour.milis*24),
    Week(Day.milis*7);
    public long milis;
    CacheTime(long V){
        this.milis=V;   
    }
}
    
    
    
public Handler_Base(){
}

public static Timestamp getTimestamp(String TimeString,String TZ){
Timestamp TS=null;
try {
SimpleDateFormat SDF= new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
SDF.setTimeZone(TimeZone.getTimeZone(TZ));
TS= new Timestamp(SDF.parse(TimeString).getTime());
} catch (ParseException ex) {
    Logger.getLogger(Handler_Base.class.getName()).log(Level.SEVERE, null, ex);
}
return TS;
}
public static Timestamp getTimestamp(String TimeString){
return Handler_Base.getTimestamp(TimeString, "UTC");
}

public static LinkedList<String> getHashMapKeys_String(HashMap HM){
    LinkedList<String> R=new LinkedList<>();
    String[] K= new String[HM.keySet().size()];
    HM.keySet().toArray(K);
    for (int i=0;i<=K.length-1;i++){
        R.add(K[i]);
    }
    return R;
}

public static LinkedList<Integer> getHashMapKeys_Integer(HashMap HM){
    LinkedList<Integer> R=new LinkedList<>();
    Integer[] K= new Integer[HM.keySet().size()];
    HM.keySet().toArray(K);
    for (int i=0;i<=K.length-1;i++){
        R.add(K[i]);
    }
    return R;
}
public static Integer[] getHashMapKeys_Integer_Integer(HashMap HM){
    Integer[] K= new Integer[HM.keySet().size()];
    HM.keySet().toArray(K);
    return K;
}

public static boolean CacheOlderThan(CacheTime T,Timestamp Cache){
    Timestamp Now = new Timestamp(System.currentTimeMillis());
    Timestamp AdjustedCache = new Timestamp(Cache.getTime()+T.milis);
    return Now.after(AdjustedCache);
}

}
