
package com.wyd.zmhkmiddleware.business.model.hk.result;

import java.util.List;
 
import com.google.gson.annotations.Expose;
import com.wyd.zmhkmiddleware.business.model.hk.result.concrete.OrgAddFailure;
import com.wyd.zmhkmiddleware.business.model.hk.result.concrete.OrgAddSuccess;

  
@SuppressWarnings("unused")
public class BatchAddOrgResult {

    @Expose
    private List<OrgAddFailure> failures;
    @Expose
    private List<OrgAddSuccess> successes;

    public List<OrgAddFailure> getFailures() {
        return failures;
    }

    public void setFailures(List<OrgAddFailure> failures) {
        this.failures = failures;
    }

    public List<OrgAddSuccess> getSuccesses() {
        return successes;
    }

    public void setSuccesses(List<OrgAddSuccess> successes) {
        this.successes = successes;
    }

}
