package week_11;

public class RubrikObject {
    String name;
    double time;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public RubrikObject(int id,String name, double time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }
}
