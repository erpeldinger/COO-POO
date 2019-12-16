import java.lang.Object.*;
import java.net.InetAddress;

public class TableSQL {

    //attributs
    private String nameAttr;
    private String typeAttr;
    private String optionAttr;

    // constructeurs
    public TableSQL(String name, String type) {
        this.nameAttr = name;
        this.typeAttr = type;
        this.optionAttr = "";
    }

    public void Message(String name, String type, String option) {
        this.nameAttr = name;
        this.typeAttr = type;
        this.optionAttr = option;
    }
    
    //getters
    public String getName() {return this.nameAttr;}
    public String getType() {return this.typeAttr;}
    public String getOption() {return this.optionAttr;}

    //setter
    public void setName(String name) { this.nameAttr = name; }
    public void setType(String type) { this.typeAttr = type; }
    public void setOption(String option) { this.optionAttr = option; }
    
}
