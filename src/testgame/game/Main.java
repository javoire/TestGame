package testgame.game;
 
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.FlyByCamera;
import com.jme3.math.ColorRGBA;
import com.jme3.niftygui.NiftyJmeDisplay;
//import com.jme3.scene.plugins.blender.BlenderLoader;
import com.jme3.system.AppSettings;

import de.lessvoid.nifty.Nifty;

import java.util.logging.Level;
 
public class Main extends SimpleApplication {
 
    private World           world;
    private Game            game;
    private BasicGui        basicGui;
    private Player          player;
    private BulletAppState  bulletAppState;
    private NiftyJmeDisplay gui;
    
    public static void main(String[] args) {        
        java.util.logging.Logger.getLogger("").setLevel(Level.WARNING);
        
        Main app = new Main();
        AppSettings appSettings = new AppSettings(true);
        
        appSettings.setTitle("JDs fantastic game of awesome adventures...");
        
        app.setShowSettings(false); // splash screen
        app.setSettings(appSettings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
//        app.setDisplayFps(true); // nullpointer exception
//        app.setDisplayStatView(true); // stats on screen

        world           = new World(rootNode);
        game            = new Game();
        basicGui        = new BasicGui(guiNode, guiFont, settings, flyCam);
        gui				= new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        player          = new Player();
        bulletAppState  = new BulletAppState();

		assetManager.registerLocator("./assets/", FileLocator.class.getName()); // kommentera bort denna vid build!!!
        
        // attach all statemanagers
        stateManager.attach(bulletAppState);
        stateManager.attach(world);
        stateManager.attach(game);
        stateManager.attach(basicGui);
        stateManager.attach(player);
        
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));

        flyCam.setMoveSpeed(30);
        
//        loadStartMenu();
        
//        this.loadStatsView();
//        this.loadFPSText();
//        this.setDisplayFps(true);
//        this.setDisplayStatView(false);
    }
    
   public void loadStartMenu() {
   		/** Create a new NiftyGUI object */
		Nifty nifty = gui.getNifty();
		/** Read your XML and initialize your custom ScreenController */
		//nifty.fromXml("Interface/screen.xml", "start");
		nifty.fromXml("Interface/screen.xml", "start", new Gui(null));
		guiViewPort.addProcessor(gui);
		
		// disable the fly cam
		flyCam.setDragToRotate(true);
       //gui.loadStartMenu();
   }
    
  @Override
    public void simpleUpdate(float tpf) {
	  
        game.startGame(); // cannot be called in init

//        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
    }

}