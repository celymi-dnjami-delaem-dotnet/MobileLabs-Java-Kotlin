package com.dmyaniuk.lb1;

public class Calories {
    private double height;
    private double weight;
    private double age;
    private boolean isMan;
    private String metabolismType;

    public Calories(boolean isMan, double height, double weight, double age, String metabolismType) {
        this.isMan = isMan;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.metabolismType = metabolismType;
    }

    public double getMetabolism() {
        if (this.isMan) {
            return this.getBasalMetabolismForMan() * this.getActiveMetabolism();
        } else {
            return this.getBasalMetabolismForWoman() * this.getActiveMetabolism();
        }
    }

    private double getBasalMetabolismForMan() {
        return 66.473 + (13.7516 * this.weight) + (5.0033 * this.height) - (6.755 * this.age);
    }

    private double getBasalMetabolismForWoman() {
        return 655.0955 + (9.5634 * this.weight) + (1.8496 * this.height) - (4.6756 * this.age);
    }

    private double getActiveMetabolism() {
        switch (this.metabolismType) {
            case ActiveMetabolismTypes.NoActivityPerson:
                return ActiveMetabolismTypes.NoActivityPersonIndex;
            case ActiveMetabolismTypes.LowActivityPerson:
                return ActiveMetabolismTypes.LowActivityPersonIndex;
            case ActiveMetabolismTypes.AverageActivityPerson:
                return ActiveMetabolismTypes.AverageActivityPersonIndex;
            case ActiveMetabolismTypes.ActivePerson:
                return ActiveMetabolismTypes.ActivePersonIndex;
            case ActiveMetabolismTypes.Sportsman:
                return ActiveMetabolismTypes.SportsmanIndex;
            default:
                return 0;
        }
    }
}
