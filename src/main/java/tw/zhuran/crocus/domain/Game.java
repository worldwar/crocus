package tw.zhuran.crocus.domain;

import tw.zhuran.crocus.time.NeverTimeoutPolicy;
import tw.zhuran.crocus.time.TimePolicy;

import java.time.Duration;
import java.util.Date;

public class Game {
    private static final TimePolicy DEFAULT_TIME_POLICY = new NeverTimeoutPolicy();

    private Board board;
    private GameState state;
    private GameResult result;
    private Force turn;
    private Date startDate;
    private Date turnStartDate;
    private Duration redUsedTime;
    private Duration blackUsedTime;
    private TimePolicy timePolicy;

    public Game() {
        this(null);
    }

    public Game(TimePolicy timePolicy) {
        board = new Board();
        state = GameState.NOT_STARTED;
        if (timePolicy != null) {
            this.timePolicy = timePolicy;
        } else {
            this.timePolicy = DEFAULT_TIME_POLICY;
        }
    }

    public void start() {
        if (state == GameState.NOT_STARTED) {
            board.reset();
            state = GameState.STARTED;
            turn = Force.RED;
            startDate = new Date();
            turnStartDate = startDate;
        }
    }

    public boolean play(Piece piece, Position position) {
        if (state != GameState.STARTED) return false;
        if (turn != piece.getForce()) return false;
        if (!position.legal()) return false;

        Action action = board.makeAction(piece, position);
        if (board.legal(action)) {
            board.apply(action);
            next();
            return true;
        } else {
            return false;
        }
    }

    private void next() {
        settle();
        if (!ended()) {
            turnNext();
        }
    }

    private void turnNext() {
        Date now = new Date();
        addUsedTime(turn, now);
        turn = turn.opposed();
        turnStartDate = now;
    }

    private void addUsedTime(Force turn, Date now) {

    }

    private boolean ended() {
        return state == GameState.ENDED;
    }

    private void settle() {
        boolean checkmated = board.checkmated(turn.opposed());
        if (checkmated) {
            win(turn);
        }
    }

    private void win(Force force) {
        if (force == Force.RED) {
            endGame(GameResult.RED_WIN);
        } else {
            endGame(GameResult.BLACK_WIN);
        }
    }

    private void endGame(GameResult result) {
        this.result = result;
        state = GameState.ENDED;
    }

    public void resign(Force force) {
        win(force.opposed());
    }

    public void draw() {
        endGame(GameResult.DRAW);
    }

    public void prinit() {
        System.out.println(board.print());
    }

    public GameState getState() {
        return state;
    }
}
