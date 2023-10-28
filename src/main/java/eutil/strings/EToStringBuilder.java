package eutil.strings;

public class EToStringBuilder extends EStringBuilder {
    
    public EToStringBuilder(Object theObject) {
        super();
        
        String objStr = theObject.getClass().getSimpleName();
        println(objStr, "@", Integer.toHexString(hashCode()), " {");
        incrementTabCount();
    }
    
    public EToStringBuilder a(String name, boolean v) { println(name, ": ", v); return this; }
    public EToStringBuilder a(String name, char v) { println(name, ": ", v); return this; }
    public EToStringBuilder a(String name, byte v) { println(name, ": ", v); return this; }
    public EToStringBuilder a(String name, short v) { println(name, ": ", v); return this; }
    public EToStringBuilder a(String name, int v) { println(name, ": ", v); return this; }
    public EToStringBuilder a(String name, long v) { println(name, ": ", v); return this; }
    public EToStringBuilder a(String name, float v) { println(name, ": ", v); return this; }
    public EToStringBuilder a(String name, double v) { println(name, ": ", v); return this; }
    public EToStringBuilder a(String name, Object v) { println(name, ": ", v); return this; }
    
    public EToStringBuilder a(String name, boolean[] v) { println(name, ": ", EStringUtil.toString(v)); return this; }
    public EToStringBuilder a(String name, char[] v) { println(name, ": ", EStringUtil.toString(v)); return this; }
    public EToStringBuilder a(String name, byte[] v) { println(name, ": ", EStringUtil.toString(v)); return this; }
    public EToStringBuilder a(String name, short[] v) { println(name, ": ", EStringUtil.toString(v)); return this; }
    public EToStringBuilder a(String name, int[] v) { println(name, ": ", EStringUtil.toString(v)); return this; }
    public EToStringBuilder a(String name, long[] v) { println(name, ": ", EStringUtil.toString(v)); return this; }
    public EToStringBuilder a(String name, float[] v) { println(name, ": ", EStringUtil.toString(v)); return this; }
    public EToStringBuilder a(String name, double[] v) { println(name, ": ", EStringUtil.toString(v)); return this; }
    public EToStringBuilder a(String name, Object[] v) { println(name, ": ", EStringUtil.toString(v)); return this; }
    
    @Override
    public String toString() {
        decrementTabCount();
        println("}");
        return super.toString();
    }
    
}
