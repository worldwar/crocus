package tw.zhuran.crocus;

import tw.zhuran.crocus.domain.Force;
import tw.zhuran.crocus.domain.Kind;
import tw.zhuran.crocus.domain.Piece;
import tw.zhuran.crocus.plan.*;
import tw.zhuran.crocus.rule.*;
import tw.zhuran.crocus.rule.action.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Boards {

    static EnumMap<Kind, ActionRule> rules = new EnumMap<Kind, ActionRule>(Kind.class);

    static EnumMap<Kind, Plan> plans = new EnumMap<Kind, Plan>(Kind.class);

    static String[] RED_SYMBOLS = new String[]{"將", "車", "馬", "炮", "象", "士", "兵"};

    static String[] BLACK_SYMBOLS = new String[]{"帥", "車", "馬", "炮", "相", "士", "卒"};

    static String EMPTY_SYMBOLS = "·";

    static {
        rules.put(Kind.KING, new KingActionRule());
        rules.put(Kind.ROOK, new RookActionRule());
        rules.put(Kind.KNIGHT, new KnightActionRule());
        rules.put(Kind.BISHOP, new BishopActionRule());
        rules.put(Kind.GUARD, new GuardActionRule());
        rules.put(Kind.GUN, new GunActionRule());
        rules.put(Kind.PAWN, new PawnActionRule());

        plans.put(Kind.KING, new KingPlan());
        plans.put(Kind.ROOK, new RookPlan());
        plans.put(Kind.KNIGHT, new KnightPlan());
        plans.put(Kind.BISHOP, new BishopPlan());
        plans.put(Kind.GUARD, new GuardPlan());
        plans.put(Kind.GUN, new GunPlan());
        plans.put(Kind.PAWN, new PawnPlan());
    }

    public static ActionRule rule(Kind kind) {
        return rules.get(kind);
    }

    public static Plan plan(Kind kind) {
        return plans.get(kind);
    }

    public static List<ActionRule> rulesFor(Piece piece) {
        List<ActionRule> rules = new ArrayList<>();
        rules.add(new ExistenceRule());
        rules.add(new PositionChangeRule());
        rules.add(new TargetForceFormationRule());
        rules.add(rule(piece.getKind()));
        rules.add(new CheckedActionRule());
        rules.add(new KingFaceRule());
        return rules;
    }

    public static String symbol(Piece p) {
        if (p == null) {
            return EMPTY_SYMBOLS;
        }
        Kind kind = p.getKind();
        if (p.getForce() == Force.RED) {
            return RED_SYMBOLS[kind.ordinal()];
        } else {
            return BLACK_SYMBOLS[kind.ordinal()];
        }
    }
}
