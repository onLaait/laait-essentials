package io.github.onlaait.essentials;

public interface CustomEntity {

    int laaitessentials_getTicksTeleport();

    void laaitessentials_setTicksTeleport(int value);

    default float laaitessentials_getPercentTeleport() {
        return laaitessentials_getTicksTeleport() / 10f;
    }

}
