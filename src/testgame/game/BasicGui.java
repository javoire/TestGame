/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testgame.game;

import testgame.inventory.Inventory;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 *
 * @author jo_da12
 */
public class BasicGui extends AbstractAppState {
    
    private Node guiNode;
    private AppSettings settings;
    private AssetManager assetManager;
    private InputManager inputManager;
    private AudioRenderer audioRenderer;
    private ViewPort guiViewPort;
    private BitmapFont guiFont;
    private FlyByCamera flyCam;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
        this.assetManager 	= app.getAssetManager();
        this.inputManager 	= app.getInputManager();
        this.audioRenderer 	= app.getAudioRenderer();
        this.guiViewPort 	= app.getGuiViewPort();
    }
    
    public BasicGui (Node guiNode, BitmapFont guiFont,  AppSettings settings, FlyByCamera flyCam) {
        this.guiNode 	= guiNode;
        this.settings 	= settings;
        this.guiFont 	= guiFont;
        this.flyCam 	= flyCam;
    }
    
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }
    
    public void initGui() {
        initCrosshair();
    }
    
    public void initCrosshair() {
          /** A centred plus sign to help the player aim. */
//        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 4/3);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
          settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
          settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }
    
    public void initInventory(Inventory inventory) {
//    	Vector<String> items = inventory.getItemList();
    }
    
    
    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }
}
