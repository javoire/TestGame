package testgame.appstates;

import testgame.controls.HarvestingControl;
import testgame.inventory.Inventory;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.scene.Node;

public class HarvestingAppState extends AbstractAppState implements
		ActionListener {

	private TargetInfo targetInfo;
	private int harvestAmount;
	private Inventory inventory;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		app 			= (SimpleApplication) app; // cast to a more specific class
		targetInfo 		= app.getStateManager().getState(TargetInfo.class);
		inventory 		= app.getStateManager().getState(Inventory.class);
		harvestAmount = 5; // denna ska r�knas ut baserat p� typ/kvalit� av resource, kvalite av verktyg etc
	}

	@Override
	public void update(float tpf) {

	}

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {

	}

    /**
     * �r vi tillr n�ra?
     * klicka h�ger -> f� resources
     */
	public void tryHarvest() {
		Node harvestNode = targetInfo.getNode();
		HarvestingControl harvestControl = harvestNode.getControl(HarvestingControl.class);
		if (targetInfo.getIntDistance() < harvestControl.getHarvestableDistance()) {
			harvestControl.subtractFromAmount(harvestAmount);
			// l�gg till i inventory
		}
	}
}