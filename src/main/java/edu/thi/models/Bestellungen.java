package edu.thi.models;

import java.io.Serializable;

public class Bestellungen implements Serializable {
   private static final long serialVersionUID = 1L;
   

   private Long bestellID;
   private String name;
   private Integer anzahl;
   private String processId;
   private Integer priority;
  

   
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
    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
}

