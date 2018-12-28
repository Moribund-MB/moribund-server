package com.github.moribund;

/**
 * The runner that starts the {@link MoribundServer}.
 */
class MoribundServerRunner {
    /**
     * The main method of the program where the game starts from.
     * @param args The program arguments provided.
     */
    public static void main(String[] args) {
        MoribundServer.getInstance().start();
    }
}
