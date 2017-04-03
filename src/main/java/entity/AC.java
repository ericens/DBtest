package entity;

/**
 * Created by ericens on 2017/3/25.
 */
public class AC {
    int id;
    String name;
    double money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AC{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    public void setMoney(double money) {
        this.money = money;
    }


}
