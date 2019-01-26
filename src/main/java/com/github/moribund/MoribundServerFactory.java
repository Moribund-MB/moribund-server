package com.github.moribund;

import com.github.moribund.game.GameContainer;
import com.github.moribund.net.NetworkBootstrapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.extern.java.Log;
import lombok.val;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * The factory that produces the {@link MoribundServer} and all of its dependencies.
 */
@Log
class MoribundServerFactory {

    /**
     * Creates the {@link MoribundServer} and all of its dependencies.
     * @return The newly made {@link MoribundServer}.
     */
    MoribundServer createServer() {
        val playersMap = createGameContainer();
        val networkBootstrapper = createNetworkBootstrapper();
        val scheduler = createScheduler();
        val dataSource = createHikariDataSource();
        return new MoribundServer(playersMap, networkBootstrapper, scheduler, dataSource);
    }

    /**
     * Creates a {@link HikariDataSource} to connect to the PostgreSQL database.
     * @return The {@link HikariDataSource}.
     */
    private HikariDataSource createHikariDataSource() {
        val config = new HikariConfig();

        config.setJdbcUrl("jdbc:postgresql://localhost:5432/moribund");
        config.setUsername("moribund-user");
        config.setPassword("moribund-pass");
        config.setAutoCommit(true);
        config.setMaximumPoolSize(32);

        log.info("Initiated the PostgreSQL connection!");
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
     * Creates a container that stores games.
     * @return The container for games.
     */
    private GameContainer createGameContainer() {
        return new GameContainer(new Int2ObjectOpenHashMap<>());
    }

    /**
     * Creates a network bootstrapper.
     * @return The newly made network bootstrapper.
     */
    private NetworkBootstrapper createNetworkBootstrapper() {
        return new NetworkBootstrapper();
    }
}
