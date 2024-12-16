package com.lhk.kkojbackendjudgeservice.judge;

import com.lhk.kkojbackendjudgeservice.judge.strategy.DefaultJudgeStrategyImpl;
import com.lhk.kkojbackendjudgeservice.judge.strategy.JavaJudgeStrategyImpl;
import com.lhk.kkojbackendjudgeservice.judge.strategy.JudgeContext;
import com.lhk.kkojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.lhk.kkojbackendmodel.model.codesandbox.model.JudgeInfo;
import com.lhk.kkojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题策略管理
 */
@Service
public class JudgeManager {
    /**
     * 区分不同的判题策略，执行判题
     * @param judgeContext
     * @return
     */
    public JudgeInfo judge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategyImpl();
        if (language.equals("java")){
            judgeStrategy = new JavaJudgeStrategyImpl();
        }
        return judgeStrategy.judge(judgeContext);
    }
}
