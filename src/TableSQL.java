import java.lang.Object.*;
import java.net.InetAddress;

public class TableSQL {

    //attributs
    private String nameAttr;
    private String typeAttr;
    private String optionAttr;
    private String valueAttr;

    // constructeurs
    public TableSQL(String name, String type) {
        this.nameAttr = name;
        this.typeAttr = type;
        this.optionAttr = "";
        this.valueAttr = "";
    }

    public void Message(String name, String type, String option) {
        this.nameAttr = name;
        this.typeAttr = type;
        this.optionAttr = option;
        this.valueAttr = "";
    }
    
    public void Message(String name, String type, String option, String value) {
        this.nameAttr = name;
        this.typeAttr = type;
        this.optionAttr = option;
        this.valueAttr = value;
    }

    //getters
    public String getName() {return this.nameAttr;}
    public String getType() {return this.typeAttr;}
    public String getOption() {return this.optionAttr;}
    public String getValue() {return this.valueAttr;}

    //setter
    public void setName(String name) { this.nameAttr = name; }
    public void setType(String type) { this.typeAttr = type; }
    public void setOption(String option) { this.optionAttr = option; }
    public void setValue(String value) { this.valueAttr = value; }
    
}
