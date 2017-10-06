package tw.zhuran.crocus.time;

import tw.zhuran.crocus.domain.Force;
import tw.zhuran.crocus.domain.Game;

public class NeverTimeoutPolicy implements TimePolicy {

    @Override
    public boolean timeout(Game game, Force force) {
        return false;
    }
}
