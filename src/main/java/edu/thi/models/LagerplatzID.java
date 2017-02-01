package edu.thi.models;

import java.io.Serializable;

public class LagerplatzID implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private String processId;
    private Long lagerplatzId;
    
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
