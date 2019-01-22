package com.github.moribund.net.packets.data;

import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.Value;

import java.io.Serializable;

@Value
public class PlayerData implements Serializable {
    private static final long serialVersionUID = 5508611406619988854L;

    private int playerId;
    private float x;
    private float y;
    private float rotation;
    private int hitpoints;
    private ObjectList<Integer> inventoryItems;
}
