package com.speshkitty.giantsfoundryitemvalues;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ClientTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.slf4j.Logger;

import java.awt.*;
import java.awt.Point;

@Slf4j
@PluginDescriptor(
	name = "Giant's Foundry Gear Values"
)
public class ItemBarValuesPlugin extends Plugin
{
	private Rectangle giantsFoundryArea = new Rectangle(3354, 11478, 24, 25);

	@Inject
	private Client client;

	@Inject
	private ItemBarValuesConfig config;

	@Inject
	private ItemBarValuesOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
		log.info("Giant Foundry Bar Value loaded!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		//log.info("Example stopped!");
	}

	protected Logger getLogger(){
		return log;
	}

	protected boolean ShouldDrawInfo(){
		if(TileIsInFoundry(client.getLocalPlayer().getWorldLocation()) && config.showInGF()) {
			return true;
		}
		return config.showEverywhere();
	}

	private boolean TileIsInFoundry(WorldPoint tile) {
		return giantsFoundryArea.contains(new Point(tile.getX(), tile.getY()));
	}

 	private WorldPoint lastPoint = null;
	@Subscribe
	public void onClientTick(ClientTick clientTick){
		WorldPoint current = client.getLocalPlayer().getWorldLocation();

		if(lastPoint == null || !lastPoint.equals(current)){
			lastPoint = current;
			//client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "You are in " + lastPoint, null);
		}
	}


	@Provides
	ItemBarValuesConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ItemBarValuesConfig.class);
	}
}
