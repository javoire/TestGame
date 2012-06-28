package testgame.appstates;

import testgame.game.BasicGui;
import testgame.game.Player;
import testgame.game.World;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Ray;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class PlayerTargetingAppState extends AbstractAppState implements
		ActionListener {

	private SimpleApplication app;
	private Player player;
	private Camera cam;
	private Node trees;
	private World world;
	private BasicGui gui;
	private HarvestingAppState harvester;
	private TargetInfo targetInfo;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		this.app 		= (SimpleApplication) app; // cast to a more specific class
		this.player		= app.getStateManager().getState(Player.class);
		this.cam 		= app.getCamera();
		this.world	 	= app.getStateManager().getState(World.class);
		this.gui 		= app.getStateManager().getState(BasicGui.class);
		harvester 		= app.getStateManager().getState(HarvestingAppState.class);
		targetInfo		= app.getStateManager().getState(TargetInfo.class);
	}
	
	public CollisionResults findTarget() {
		CollisionResults results = new CollisionResults();
		Ray ray = new Ray(cam.getLocation(), cam.getDirection());
		trees.collideWith(ray, results); // checks all children to trees
		if(results.size() == 0)
			return null;
		if(results.getClosestCollision().getDistance() < 60) // closer than 5m
			return results;
		else
			return null;
	}
	
	@Override
	public void update(float tpf) {
		this.trees = world.getTrees();
		if(trees != null) {
			CollisionResults results = findTarget();
			if(results != null && results.size() > 0) {
				getTargetInfo(results);
				gui.displayTargetInfo(targetInfo);
//				if(isTargetHarvestable()) // TODO underr�tta harvester att man kan harvesta
//					targetInfo.isHarvestable();
			} else {
				gui.clearTargetInfo();
			}
		}
	}

	public boolean isTargetHarvestable() {
		if(!targetInfo.hasTarget()) { return false; }
		if(targetInfo.isHarvestable()) { return true; }
		return false;
	}
	
	private TargetInfo getTargetInfo(CollisionResults results) {
		if(results != null) {
			if(results.size() > 0) {
				targetInfo.setResults((results.getClosestCollision()));
				return targetInfo;
			}
		} 
		return null;
	}

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {

	}
}