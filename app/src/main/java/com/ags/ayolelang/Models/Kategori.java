package com.ags.ayolelang.Models;

public class Kategori {
    private int id_category,id_parent, id_sub_parent,priority;
    private String name;

    public Kategori() {
    }

    public Kategori(int id_category, int id_parent, int id_sub_parent, int priority, String name) {
        this.id_category = id_category;
        this.id_parent = id_parent;
        this.id_sub_parent = id_sub_parent;
        this.priority = priority;
        this.name = name;
    }


    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getId_parent() {
        return id_parent;
    }

    public void setId_parent(int id_parent) {
        this.id_parent = id_parent;
    }

    public int getId_sub_parent() {
        return id_sub_parent;
    }

    public void setId_sub_parent(int id_sub_parent) {
        this.id_sub_parent = id_sub_parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
