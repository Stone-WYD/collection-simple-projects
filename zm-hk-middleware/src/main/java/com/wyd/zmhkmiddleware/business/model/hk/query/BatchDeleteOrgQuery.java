
package com.wyd.zmhkmiddleware.business.model.hk.query;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("net.hexar.json2pojo")
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
