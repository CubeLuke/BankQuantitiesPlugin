package com.cubeluke.BankQuantitiesPlugin;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BankQuantitiesPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BankQuantitiesPlugin.class);
		RuneLite.main(args);
	}
}