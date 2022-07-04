package br.edu.ifsuldeminas.tarefas2.domain;

import java.io.Serializable;

public class Task implements Serializable {

    private Integer id;
    private String description;
    private boolean active;
    private Category category;

    public Task(Integer id, String description){
        this.id = id;
        this.description = description;
        active = true;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = this.category;
    }

    @Override
    public String toString(){
        return String.format("%s\n%s", description,
                Boolean.toString(active));
    }
}
