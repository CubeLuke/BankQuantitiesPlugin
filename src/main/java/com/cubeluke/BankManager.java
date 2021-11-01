package com.cubeluke;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.runelite.api.Item;
import net.runelite.client.config.ConfigManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class BankManager
{
    private final ConfigManager configManager;
    private final Gson gson;

    private static final String CONFIG_GROUP = "bankquantities";
    private static final String BANK_ITEMS_KEY = "itemsinbank";

    private List<Item> itemsInBank;

    @Inject
    public BankManager(ConfigManager configManager, Gson gson)
    {
        this.configManager = configManager;
        this.gson = gson;
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

    public void saveBankConfig()
    {
        final String profileKey = configManager.getRSProfileKey();
        if (profileKey == null)
        {
            return;
        }
        configManager.setRSProfileConfiguration(CONFIG_GROUP, BANK_ITEMS_KEY, gson.toJson(itemsInBank));
    }

    public void loadBankConfig()
    {
        ArrayList<Item> itemsFromConfig = gson.fromJson(
                configManager.getRSProfileConfiguration(CONFIG_GROUP, BANK_ITEMS_KEY),
                new TypeToken<List<Item>>(){}.getType());
        if (itemsFromConfig != null) {
            itemsInBank = itemsFromConfig;
        } else {
            itemsInBank = new ArrayList<>();
        }
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
