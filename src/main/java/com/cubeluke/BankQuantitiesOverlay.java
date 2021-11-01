package com.cubeluke;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.QuantityFormatter;

import java.awt.*;

public class BankQuantitiesOverlay extends Overlay {

    @Inject
    private Client client;

    @Inject
    private BankManager bankManager;

    @Inject
    private TooltipManager tooltipManager;

    @Override
    public Dimension render(Graphics2D graphics) {
        final MenuEntry[] menu = client.getMenuEntries();
        final int menuSize = menu.length;

        if (menuSize <= 0)
        {
            return null;
        }

        final MenuEntry entry = menu[menuSize - 1];
        final int group = WidgetInfo.TO_GROUP(entry.getParam1());
        final int child = WidgetInfo.TO_CHILD(entry.getParam1());
        final Widget widget = client.getWidget(group, child);

        if (widget == null
                || !(group == WidgetInfo.INVENTORY.getGroupId()
                || group == WidgetInfo.EQUIPMENT.getGroupId()
                || group == WidgetInfo.EQUIPMENT_INVENTORY_ITEMS_CONTAINER.getGroupId()
        ))
        {
            return null;
        }
        int itemId = entry.getIdentifier();

        int quantity = bankManager.getItemQuantityCount(itemId);
        if (quantity > 0)
        {
            setupTooltip(quantity);
        }
        return null;
    }

    private void setupTooltip(int quantity) {
        final StringBuilder tooltipText = new StringBuilder();
        tooltipText.append("Bank: " + QuantityFormatter.quantityToRSDecimalStack(quantity));
        final String tooltip = tooltipText.toString();
        tooltipManager.add(new Tooltip(tooltip));
    }
}
