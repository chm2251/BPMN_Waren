package edu.thi.process;

import java.io.Serializable;

public class Bestellungen implements Serializable {
   private static final long serialVersionUID = 1L;
   

   private Long bestellID;
   private String name;
   private Integer anzahl;
   private String processId;
   private Long lagerplatzId;

   
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
   
    public String getProcessId() {
        return processId;
    }
    
    public void setProcessId(String processId) {
        this.processId = processId;
    }
    
    public Long getLagerplatzId() {
        return lagerplatzId;
    }
    
    public void setLagerplatzId(Long lagerplatzId) {
        this.lagerplatzId = lagerplatzId;
    }
}

