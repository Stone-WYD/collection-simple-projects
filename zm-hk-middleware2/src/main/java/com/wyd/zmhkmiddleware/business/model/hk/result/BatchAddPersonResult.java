
package com.wyd.zmhkmiddleware.business.model.hk.result;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.wyd.zmhkmiddleware.business.model.hk.result.concrete.PersonAddFailure;
import com.wyd.zmhkmiddleware.business.model.hk.result.concrete.PersonAddSuccess;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BatchAddPersonResult {

    @Expose
    private List<PersonAddFailure> failures;
    @Expose
    private List<PersonAddSuccess> successes;

    public List<PersonAddFailure> getFailures() {
        return failures;
    }

    public void setFailures(List<PersonAddFailure> failures) {
        this.failures = failures;
    }

    public List<PersonAddSuccess> getSuccesses() {
        return successes;
    }

    public void setSuccesses(List<PersonAddSuccess> successes) {
        this.successes = successes;
    }

}
