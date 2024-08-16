package application;

public class Currency {
    private String name;
    private String code;
   

    public Currency(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

  

    @Override
    public String toString() {
        return name + " (" + code + ")";
    }
}