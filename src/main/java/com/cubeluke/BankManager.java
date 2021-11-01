package com.cubeluke;

import com.google.inject.Singleton;
import net.runelite.api.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class BankManager
{
    private List<Item> itemsInBank;

    public BankManager()
    {
        this.itemsInBank = new ArrayList<>();
    }

    public void updateBank(Item[] items)
    {
        itemsInBank = Arrays.asList(items);
    }

    public List<Item> getItemsInBank() {
        return itemsInBank;
    }

    public int getItemQuantityCount(Item item)
    {
        return getItemQuantityCount(item.getId());
    }

    /**
     * Gets the quantity count for the provided item within the bank.
     * @param itemId
     * @return The quantity. If the item isn't in the bank it will return 0.
     */
    public int getItemQuantityCount(int itemId)
    {
        Optional<Item> result = itemsInBank.stream().filter((i -> i.getId() == itemId)).findFirst();
        if (result.isPresent())
        {
            return result.get().getQuantity();
        } else
        {
            return 0;
        }
    }
}
