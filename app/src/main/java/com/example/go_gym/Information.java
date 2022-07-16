package com.example.go_gym;

public class Information {
    int id,age,height,weight;
    String name;
    public Information(int i,int a,int h, int w, String n){
        id = i;
        age = a;
        height = h;
        weight = w;
        name = n;
    }
    public int getId(){return id;}

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }
}
