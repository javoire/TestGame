/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testgame.game;

import java.util.Iterator;
import java.util.Vector;

import testgame.appstates.TargetInfo;
import testgame.inventory.Inventory;
import testgame.items.AbstractItem;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 *
 * @author jo_da12
 */
public class Hud extends AbstractAppState {
    
    private Node 			hudRoot;
    private AppSettings 	settings;
    private AssetManager 	assetManager;
    private ViewPort 		guiViewPort;
	private BitmapFont 		hudFont;
	private BitmapText		inventoryText;
	private BitmapText 		targetInfoText;
	private TargetInfo 		targetInfo;
	private Inventory 		inventory;
	private int 			fontSize;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
        assetManager 		= app.getAssetManager();
        guiViewPort 		= app.getGuiViewPort();
        inventory			= app.getStateManager().getState(Inventory.class);
        targetInfo			= app.getStateManager().getState(TargetInfo.class);
     	hudFont 			= assetManager.loadFont("Interface/Fonts/Default.fnt");
    	fontSize 			= hudFont.getCharSet().getRenderedSize();

    	targetInfoText = new BitmapText(hudFont, false);
    	targetInfoText.setSize(fontSize);
    	inventoryText = new BitmapText(hudFont, false);
    	inventoryText.setSize(fontSize);
        inventoryText.setLocalTranslation(
        		settings.getWidth()-90, 
        		settings.getHeight(), 
        		0);
    	
        hudRoot.attachChild(targetInfoText);
    	hudRoot.attachChild(inventoryText);
    }
    
    public Hud (Node guiNode, BitmapFont guiFont,  AppSettings settings, FlyByCamera flyCam) {
        this.hudRoot 	= guiNode;
        this.settings 	= settings;
        this.hudFont 	= guiFont;
    }
    
    @Override
    public void update(float tpf) {
    	displayTargetInfo();
    	displayInventory();
    }
    
    private void displayInventory() {
    	Vector<String> items = inventory.getItemList();
    	Iterator<String> i = items.iterator();
    	String inventoryString = "";
    	while(i.hasNext()) {
    		inventoryString += i.next() + "\n";
    	}
    	inventoryText.setText(inventoryString);
	}

    public void initCrosshair() {
        BitmapText ch = new BitmapText(hudFont, false);
        ch.setSize(fontSize*4/3);
        ch.setText("+"); // crosshairs
		ch.setLocalTranslation(settings.getWidth() / 2
				- hudFont.getCharSet().getRenderedSize() / 3 * 2,
				settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        hudRoot.attachChild(ch);
    }
    
    /**
     * Checks if targets exists, then retrieves info from TargetInfo and displays
     * on a gui node
     */
    public void displayTargetInfo() {
    	if(targetInfo.hasTarget()) { 
    		targetInfoText.setText(targetInfo.getString());
	    	targetInfoText.setLocalTranslation( // center under crosshair
	    			settings.getWidth() / 2 - targetInfoText.getLineWidth() / 2,
	    			settings.getHeight() / 2 - targetInfoText.getLineHeight()*2, 0);
    	} else 
    		targetInfoText.setText(" "); // it bugs if it's just ""
   }
    
    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }
}
