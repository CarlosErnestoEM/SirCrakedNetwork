package com.minebone.gui.anvil;

import com.minebone.util.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class AnvilHandler {

    static final Class<?> CHAT_MESSAGE_CLASS = Utils.getNMSClass("ChatMessage");
    static final Class<?> ICRAFTING_CLASS = Utils.getNMSClass("ICrafting");
    static final Class<?> PACKET_PLAY_OUT_OPEN_WINDOW_CLASS = Utils.getNMSClass("PacketPlayOutOpenWindow");

    private static final Class<?> ANVIL_CONTAINER_CLASS = Utils.getNMSClass("ContainerAnvil");
    private static final Class<?> CONTAINER_CLASS = Utils.getNMSClass("Container");
    private static final Class<?> ENTITY_HUMAN_CLASS = Utils.getNMSClass("EntityHuman");
    private static final Class<?> BLOCK_POSITION_CLASS = Utils.getNMSClassOrNull("BlockPosition");
    private static final Class<?> ENTITY_CLASS = Utils.getNMSClass("Entity");

    private static Object BASE_BLOCK_POSITION;

    static {
        if (!Utils.SERVER_VERSION.contains("1_7")) {
            try {
                Constructor<?> constructor = BLOCK_POSITION_CLASS.getConstructor(int.class, int.class, int.class);
                BASE_BLOCK_POSITION = constructor.newInstance(0, 0, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Object getNewAnvilContainer(Object entityHuman) {
        Object anvilContainer = null;

        try {
            if (BLOCK_POSITION_CLASS == null) {
                anvilContainer = ANVIL_CONTAINER_CLASS.getConstructors()[0].newInstance(Utils.getFieldValue(ENTITY_HUMAN_CLASS, "inventory", entityHuman), Utils.getFieldValue(ENTITY_CLASS, "world", entityHuman), 0, 0, 0, entityHuman);
            } else {
                anvilContainer = ANVIL_CONTAINER_CLASS.getConstructors()[0].newInstance(Utils.getFieldValue(ENTITY_HUMAN_CLASS, "inventory", entityHuman), Utils.getFieldValue(ENTITY_CLASS, "world", entityHuman), BASE_BLOCK_POSITION, entityHuman);
            }

            Utils.setFieldValue(CONTAINER_CLASS, "checkReachable", anvilContainer, false);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return anvilContainer;
    }
}