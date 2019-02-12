package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity //L'entità che verrà automaticamente associata alla tabella del database,
@Table(name = "cars")

class Car {

    //costanti che indicano lo stato di un veicolo

    final static int NEW = 0;       //nuovo
    final static  int USED =1;     //usato
    final static  int ZEROKM = 2;  //km0

    private int id;
    private String title;
    private Fuel fuel;
    private int price;
    private int state;
    private int mileage;

    //Per utilizzare il valore Date di firstreg, nei file json,
    //utilizziamo l'annotazione sotto espressa con il relativo pattern
    //che indica appunto il formato della data.
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date firstreg;

    //Abbiamo 3 costruttori, quello di default, quello per un veicolo nuvo ed infine
    //quello per un veicolo usato

   public Car(){

   }

   public Car(String title, String fuel, Integer price) {
        this.title = title;
        this.fuel = new Fuel(fuel);
        this.price = price;

        //Avendo a disposizione solo il title, fuel e price
        //sicuramente è un veicolo nuovo

        this.state = NEW;
    }

    public Car(String title, String fuel, Integer price, Integer mileage, Date firstreg) {
        this.title = title;
        this.fuel = new Fuel(fuel);
        this.price = price;

        //Ci sono i Km quindi è un usato
        this.mileage = mileage;
        this.firstreg = firstreg;
        this.state = USED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY ) //indica come incrementare id. Lasciamo fare al database
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFuel() {
        return fuel.getName();
    }

    public void setFuel(String fuel) {
       if (this.fuel == null){
           this.fuel = new Fuel(fuel);
       }else {
           this.fuel.setName(fuel);
       }
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Date getFirstreg() {
        return firstreg;
    }

    public void setFirstreg(Date firstreg) {
        this.firstreg = firstreg;
    }

}
