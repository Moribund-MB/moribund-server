package com.github.moribund.net.packets.login;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.jooq.public_.tables.Players;
import com.github.moribund.net.packets.IncomingPacket;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.NoDataFoundException;
import org.jooq.impl.DSL;
import org.mindrot.jbcrypt.BCrypt;

public final class LoginPacket implements IncomingPacket {
    private String username;
    private String password;

    private LoginPacket() { }

    @Override
    public void process(Connection connection) {
        if (MoribundServer.getInstance().getUsernameMap().containsValue(username)) {
            connection.sendTCP(new LoginResponsePacket(LoginResponse.ALREADY_LOGGED_IN));
            return;
        }
        val dslContext = DSL.using(MoribundServer.getInstance().getDataSource(), SQLDialect.POSTGRES_10);
        try {
            val playersRecord = dslContext.selectFrom(Players.PLAYERS)
                    .where(Players.PLAYERS.USERNAME.equal(username))
                    .fetchSingle();

            if (BCrypt.checkpw(password, playersRecord.getPasswordHash())) {
                connection.sendTCP(new LoginResponsePacket(LoginResponse.SUCCESS));
                MoribundServer.getInstance().getUsernameMap().put(connection.getID(), username);
            } else {
                connection.sendTCP(new LoginResponsePacket(LoginResponse.INCORRECT_PASSWORD));
            }
        } catch (NoDataFoundException e) {
            createAccount(dslContext);
            connection.sendTCP(new LoginResponsePacket(LoginResponse.NEW_ACCOUNT));
            MoribundServer.getInstance().getUsernameMap().put(connection.getID(), username);
        }
    }

    private void createAccount(DSLContext dslContext) {
        val playersTable = Players.PLAYERS;
        val passwordHash = BCrypt.hashpw(password, BCrypt.gensalt(11));
        dslContext.insertInto(playersTable, playersTable.USERNAME, playersTable.PASSWORD_HASH)
                .values(username, passwordHash)
                .execute();
    }
}
