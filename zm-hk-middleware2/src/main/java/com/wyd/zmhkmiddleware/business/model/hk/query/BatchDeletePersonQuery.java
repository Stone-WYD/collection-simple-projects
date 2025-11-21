
package com.wyd.zmhkmiddleware.business.model.hk.query;

import java.util.List;
 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class BatchDeletePersonQuery {

    @Expose
    private List<String> personIds;

    public List<String> getPersonIds() {
        return personIds;
    }

    public void setPersonIds(List<String> personIds) {
        this.personIds = personIds;
    }

}
