package tw.zhuran.crocus.rule.action;

import tw.zhuran.crocus.domain.Action;
import tw.zhuran.crocus.domain.Board;

public interface ActionRule {
    boolean legal(Board board, Action action);
}
