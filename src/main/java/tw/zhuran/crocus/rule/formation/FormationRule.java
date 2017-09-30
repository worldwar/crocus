package tw.zhuran.crocus.rule.formation;

import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Formation;

public interface FormationRule {
    boolean legal(Formation formation, Action action);
}
