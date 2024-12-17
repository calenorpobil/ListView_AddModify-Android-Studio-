package com.merlita.listview_addmodify.classes;

public class Titular {
    String title;
    double nota;

    public Titular(String title, String subtitle) {
        setTitle(title);
        setNota(subtitle);
    }

    public boolean getRojo(){
        try{
            if(nota<5){
                return true;
            }
        }catch(NumberFormatException ex){

        }
        return (false);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = Double.parseDouble(nota);
    }
}
