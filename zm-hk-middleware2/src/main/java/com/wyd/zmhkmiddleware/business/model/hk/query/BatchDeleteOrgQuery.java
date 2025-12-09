
package com.wyd.zmhkmiddleware.business.model.hk.query;

import java.util.List;
 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class BatchDeleteOrgQuery {

    @Expose
    private List<String> indexCodes;

    public List<String> getIndexCodes() {
        return indexCodes;
    }

    public void setIndexCodes(List<String> indexCodes) {
        this.indexCodes = indexCodes;
    }

}
