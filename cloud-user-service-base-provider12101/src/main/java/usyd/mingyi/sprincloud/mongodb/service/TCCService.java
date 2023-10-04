package usyd.mingyi.sprincloud.mongodb.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface TCCService {
    @TwoPhaseBusinessAction(name = "addCommentToLovedComments",commitMethod = "confirmAdd",rollbackMethod = "cancelAdd")
    void addCommentToLovedComments(@BusinessActionContextParameter(paramName = "userId")Long userId,
                @BusinessActionContextParameter(paramName = "commentId")Long commentId);
    boolean confirmAdd(BusinessActionContext context);
    boolean cancelAdd(BusinessActionContext context);


    @TwoPhaseBusinessAction(name = "removeCommentFromLovedComments",commitMethod = "confirmRemove",rollbackMethod = "cancelRemove")
    void removeCommentFromLovedComments(@BusinessActionContextParameter(paramName = "userId")Long userId,
                                   @BusinessActionContextParameter(paramName = "commentId")Long commentId);
    boolean confirmRemove(BusinessActionContext context);
    boolean cancelRemove(BusinessActionContext context);
}
