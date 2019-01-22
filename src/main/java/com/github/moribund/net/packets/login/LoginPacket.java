package com.github.moribund.net.packets.login;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.net.packets.IncomingPacket;
import org.jooq.DSLContext;

public final class LoginPacket implements IncomingPacket {
    private String username;
    private String password;

    private LoginPacket() { }

    @Override
    public void process(Connection connection) {
        /*val dslContext = DSL.using(MoribundServer.getInstance().getDataSource(), SQLDialect.POSTGRES_10);
        try {
            val playersRecord = dslContext.selectFrom(Players.PLAYERS)
                    .where(Players.PLAYERS.USERNAME.equal(username))
                    .fetchSingle();

            if (BCrypt.checkpw(password, playersRecord.getPasswordHash())) {
                connection.sendTCP(new LoginResponsePacket(LoginResponse.SUCCESS));
            } else {
                connection.sendTCP(new LoginResponsePacket(LoginResponse.INCORRECT_PASSWORD));
            }
        } catch (NoDataFoundException e) {
            createAccount(dslContext);
            connection.sendTCP(new LoginResponsePacket(LoginResponse.NEW_ACCOUNT));
        }*/
        connection.sendTCP(new LoginResponsePacket(LoginResponse.SUCCESS));
    }

    private void createAccount(DSLContext dslContext) {
        /*val playersTable = Players.PLAYERS;
        val passwordHash = BCrypt.hashpw(password, BCrypt.gensalt(11));
        dslContext.insertInto(playersTable, playersTable.USERNAME, playersTable.PASSWORD_HASH)
                .values(username, passwordHash)
                .execute();*/
    }
}
