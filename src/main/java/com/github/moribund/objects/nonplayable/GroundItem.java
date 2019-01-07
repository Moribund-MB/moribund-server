package com.github.moribund.objects.nonplayable;

import com.github.moribund.objects.attributes.Locatable;
import lombok.Value;

@Value
public class GroundItem implements Locatable {
    private final GroundItemType itemType;
    private final float x;
    private final float y;

    @Override
    public float getRotation() {
        return 0;
    }
}
