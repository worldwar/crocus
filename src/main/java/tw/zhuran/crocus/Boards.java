package tw.zhuran.crocus;

import tw.zhuran.crocus.domain.Kind;
import tw.zhuran.crocus.rule.action.*;

import java.util.EnumMap;

public class Boards {

    static EnumMap<Kind, ActionRule> rules = new EnumMap<Kind, ActionRule>(Kind.class);

    static {
        rules.put(Kind.KING, new KingActionRule());
        rules.put(Kind.ROOK, new RookActionRule());
        rules.put(Kind.KNIGHT, new KnightActionRule());
        rules.put(Kind.BISHOP, new BishopActionRule());
        rules.put(Kind.GUARD, new GuardActionRule());
        rules.put(Kind.GUN, new GunActionRule());
        rules.put(Kind.PAWN, new PawnActionRule());
    }

    public static ActionRule rule(Kind kind) {
        return rules.get(kind);
    }

}
