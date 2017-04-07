package util;

public class SkillWrapper extends TypeWrapper{
    public int skillLevel=0;
    public SkillWrapper(int id,int lvl){
        super(id);
        skillLevel=lvl;
    }
}