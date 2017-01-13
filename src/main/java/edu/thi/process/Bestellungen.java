package edu.thi.process;

import java.io.Serializable;

public class Bestellungen implements Serializable {
   private static final long serialVersionUID = 1L;
   

   private Long bestellID;
   private String name;
   private Integer anzahl;

   
   public Long getBestellID() {
       return bestellID;
   }
   public void setBestellID(Long bestellID) {
       this.bestellID = bestellID;
   }
   public String getName() {
       return name;
   }
   
   public void setName(String name) {
       this.name = name;
   }
   public int getAnzahl() {
       return anzahl;
   }
   public void setAnzahl(Integer anzahl) {
       this.anzahl = anzahl;
   }
}

