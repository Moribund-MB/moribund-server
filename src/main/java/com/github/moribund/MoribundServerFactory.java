package com.github.moribund;

import com.github.moribund.entity.PlayableCharacter;
import com.github.moribund.net.NetworkBootstrapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.val;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * The factory that produces the {@link MoribundServer} and all of its dependencies.
 */
class MoribundServerFactory {

    /**
     * Creates the {@link MoribundServer} and all of its dependencies.
     * @return The newly made {@link MoribundServer}.
     */
    MoribundServer createServer() {
        val playersMap = createPlayersMap();
        val networkBootstrapper = createNetworkBootstrapper();
        val scheduler = createScheduler();
        val dataSource = createHikariDataSource();
        return new MoribundServer(playersMap, networkBootstrapper, scheduler, dataSource);
    }

    private HikariDataSource createHikariDataSource() {
        val config = new HikariConfig();

        config.setJdbcUrl("jdbc:postgresql://localhost:5432/moribund");
        config.setUsername("moribund-user");
        config.setPassword("moribund-pass");
        config.setAutoCommit(true);
        config.setMaximumPoolSize(32);

        return new HikariDataSource(config);
    }

    /**
     * Creates a {@link Scheduler} using a {@link StdSchedulerFactory}.
     * @return The newly created scheduler.
     */
    private Scheduler createScheduler() {
        try {
            return new StdSchedulerFactory().getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates an empty map of all the players in the game.
     * @return The newly made empty map of players.
     */
    private Int2ObjectMap<PlayableCharacter> createPlayersMap() {
        return new Int2ObjectOpenHashMap<>();
    }

    /**
     * Creates a network bootstrapper.
     * @return The newly made network bootstrapper.
     */
    private NetworkBootstrapper createNetworkBootstrapper() {
        return new NetworkBootstrapper();
    }
}
