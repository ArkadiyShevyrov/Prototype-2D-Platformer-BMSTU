package gamestates.playingstates;

import gamestates.GameState;

public enum PlayState {

    PLAYING, PAUSED, GAME_OVER, LVL_COMPLETED;

    public static PlayState state = PLAYING;
}
