package net.smackplays.smacksutil.platform;

import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.platform.services.*;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IModConfig CONFIG = load_1(IModConfig.class);
    public static final IVeinMiner VEIN_MINER = load(IVeinMiner.class);
    public static final IKeyHandler KEY_HANDLER = load_1(IKeyHandler.class);
    public static final IClientPacketSender C2S_PACKET_SENDER = load_1(IClientPacketSender.class);
    public static final IServerPacketSender S2C_PACKET_SENDER = load(IServerPacketSender.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
    public static <T> T load_1(Class<T> clazz) {
        if (PLATFORM.isClient()){
            return load(clazz);
        }
        return null;
    }

}