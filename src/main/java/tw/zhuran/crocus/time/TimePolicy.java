package tw.zhuran.crocus.time;

import tw.zhuran.crocus.domain.Force;
import tw.zhuran.crocus.domain.Game;

public interface TimePolicy {
    boolean timeout(Game game, Force force);
}
