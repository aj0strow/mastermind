package com.ostrow.mastermind;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.ostrow.mastermind.R;
import com.ostrow.mastermind.backend.GameType;
import com.ostrow.mastermind.backend.GameTypesManager;
import com.ostrow.mastermind.backend.GuessAvg;
import com.ostrow.mastermind.backend.Info;
import com.ostrow.mastermind.dialog.NewTypeDialog;
import com.ostrow.mastermind.mainmenuui.ArrowButton;
import com.ostrow.mastermind.mainmenuui.ArrowsLayout;
import com.ostrow.mastermind.mainmenuui.AvgTextView;
import com.ostrow.mastermind.mainmenuui.ContainerLayout;
import com.ostrow.mastermind.mainmenuui.GroupingLayout;
import com.ostrow.mastermind.mainmenuui.NewGameButton;
import com.ostrow.mastermind.mainmenuui.ResumeButton;

/**
 * @author ajostrow
 *
 * The Main menu for Mastermind.
 * 
 * It takes the stored info, and either creates the default menu
 * or dynamically creates a menu based saved gametypes. 
 */
public class MainMenuActivity extends Activity {
		
	private SharedPreferences settings;
	SharedPreferences.Editor editor;
	// If it is the first time, the default menu needs to be loaded into 
	// the saved info. 
	private boolean firstTimeEver;
	
	// See com.ostrow.mastermind.backend.GameTypesManager
	private GameTypesManager manager;
	
	// To serve ads above the menu.
	private AdView adView;	 
	private LinearLayout mainMenu;
	// To handle enabling/disabling ResumeButtons in-game without restarting the avtivity. 
	private Map<String, ResumeButton> resumeButtons = new HashMap<String, ResumeButton>();
	// To handle reseting the AvgTextView after a game has been completed to reflect the
	// new average with the most recent score. 
	private Map<String, AvgTextView> avgTextViews = new HashMap<String, AvgTextView>();
	
	private boolean doResume;
	// To avoid repeated button colors if possible. 
	private boolean[] buttonImage = new boolean[7]; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main_menu);
        
        // Look up the ui
        adView = (AdView) findViewById(R.id.adView);
        mainMenu = (LinearLayout) findViewById(R.id.main_menu_parent);
        
        // Get shared preferences and get information on gameTypes.
        settings = getSharedPreferences(getResources().getString(R.string.shared_preferences), 0); 
    			// SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        
        firstTimeEver = settings.getBoolean("first_time_ever", true);
        if(firstTimeEver) {
        	manager = new GameTypesManager();
        	populateInitialGameTypes();
        } else {
        	String previousManager = settings.getString("game_types_manager", "");
        	manager = new GameTypesManager(previousManager);
        }
                
        for(String prefix : manager.keys) {
        	createRow(prefix);
        }
        
        // All of the colors are available before any rows are generated. 
        Arrays.fill(buttonImage, false);
                
        Button createNewType = (Button) findViewById(R.id.createNewType);
        createNewType.setOnClickListener(
	        new OnClickListener() {
				public void onClick(View v) {
					
					Context context = MainMenuActivity.this;
				    Display display = ( (android.view.WindowManager) context.getSystemService(Context.WINDOW_SERVICE) ).getDefaultDisplay();      
				    
				    final NewTypeDialog dialog = new NewTypeDialog(context, display.getWidth());
				    
				    OnClickListener buttonListener = new OnClickListener() {
						public void onClick(View v) {
							// Get the data from the dialog. 
							int numHoles = dialog.holesGroup.getSelectedValue();
							int numColors = dialog.colorsGroup.getSelectedValue();
							String typeName = dialog.enterName.getText().toString();
							// Make sure the fields were filled out. 
							if(	numHoles > 0 && numColors > 0 && typeName.length() > 0) {
								// Check that the name is an appropriate size; if so, make a new gametype. 
								if(typeName.length() <= 25) {
									GameType newType = new GameType(typeName, numHoles, numColors, manager);
							    	setGameType(newType);
							    	dialog.dismiss();
							    	resetAllArrows();
							    	setResumeButtons();
								}
								else {
									dialog.enterName.setText("");
									Toast.makeText(dialog.getContext(), getResources().getString(R.string.createDialog_errorTooLong), Toast.LENGTH_LONG).show();
								}
							} else {
								Toast.makeText(dialog.getContext(), getResources().getString(R.string.createDialog_errorMessage), Toast.LENGTH_LONG).show();
							}
						}
					};
					
					dialog.setSubmitOnClickListener(buttonListener);
				}
	        }
	    );
                
        doResume = false;
    	resetAllArrows();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	// Should be done the first time as well.
    	setAverages();
    	setResumeButtons();
    	
    	if(doResume) {
    		AdRequest ar = new AdRequest();
    		ar.addTestDevice(AdRequest.TEST_EMULATOR);
    		adView.loadAd(ar);
    		adView.bringToFront();
    	}
    }
    
    protected void onStop() {
    	super.onStop();
    	
    	editor.putString("game_types_manager", manager.toString());
    	editor.commit();
    	
    	doResume = true;
    }
    
    private void populateInitialGameTypes() {    	
    	// 3 default types which correspond to the Disney, Original and Extreme commercial
    	// versions of the physical kid's board game. 
    	GameType beginner = new GameType( getResources().getString(R.string.easy) , 3, 5, manager);
    	GameType classic = new GameType( getResources().getString(R.string.medium)  , 4, 6, manager);
    	GameType difficult = new GameType( getResources().getString(R.string.hard) , 5, 8, manager);
    	// Make the default game types persistent. 
    	editor.putString(beginner.getPrefix() + Info.GAMETYPE_SUFFIX, beginner.toString());
    	editor.putString(classic.getPrefix() + Info.GAMETYPE_SUFFIX, classic.toString());
    	editor.putString(difficult.getPrefix() + Info.GAMETYPE_SUFFIX, difficult.toString());
    	// Make sure the default games are never added again. 
    	editor.putBoolean("first_time_ever", false);
    	editor.putString("game_types_manager", manager.toString());
    	
    	editor.commit();
    }
    
    /*
     * Takes a unique Id AKA prefix and generates a new menu row. 
     */
    private void createRow(final String prefix) {
    	
    	// The GameType holds all the information for the game. 
    	final GameType gameType = getGameType(prefix);
 
    	// $container holds the entire section of menu for the gametype comprising of
    	// a NewGameButton, a GroupingLayout and an ArrowsLayout. 
    	final ContainerLayout container = new ContainerLayout(this);
    	
    	// The NewGameButton needs an OnClickListener to start the game, and
    	// an OnLongClickListener for the options. 
    	OnClickListener newGameListener = new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
				intent.putExtra( "newGame", true );
				intent.putExtra("prefix", prefix);
				intent.putExtra( "holes", gameType.getHoles() );
				intent.putExtra( "colors", gameType.getColors() );
				startActivity(intent);
			}
    	};
    	
    	OnLongClickListener gameOptionsListener = new OnLongClickListener() {
			
    		// Opens up a dialog for reseting the average or deleting the type. 
			public boolean onLongClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(MainMenuActivity.this);

				alert.setTitle( gameType.getName() + " " + getResources().getString(R.string.optionsDialog_title) );
				alert.setMessage( getResources().getString(R.string.optionsDialog_description) );
				
				// Positive button = reset the average. 
				alert.setPositiveButton( getResources().getString(R.string.optionsDialog_avgReset), 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							resetAverage( gameType.getPrefix() );
							setAverages();
						}
					}
				);
				
				// Neutral button = delete the game type. 
				alert.setNeutralButton( getResources().getString(R.string.optionsDialog_delete), 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						 /* To remove a gametype, the key/ID needs to be removed by the manager, 
						  * deleted from persistent data, removed from the hashes, and 
						  * removed from the UI. */
							manager.removeKey( gameType.getPrefix() );
							removeGameType(gameType);
							resumeButtons.remove( gameType.getPrefix() );
							avgTextViews.remove( gameType.getPrefix() );
							mainMenu.removeView(container);
							// Save the current state of GmaeTypesManager manager to persistent data. 
					    	editor.putString("game_types_manager", manager.toString());
					    	editor.commit();
					    	// Make sure the right arrows are enabled/disabled. 
					    	resetAllArrows();
						}
					}
				);
				
				// Negative Button dismisses the dialog. 
				alert.setNegativeButton( getResources().getString(R.string.optionsDialog_cancel), null);
				
				alert.show();
				return true;
			}
    	};
    	
    	// Randomly choose an unchosen color for the pressed button. 
    	int buttonImageIndex = randomButtonColor();
    	    	
    	NewGameButton newGameButton = new NewGameButton(this, gameType.getName(), 
    			newGameListener, gameOptionsListener, buttonImageIndex);
    	
    	// The groupingLayout holds the AvgTextView and the ResumeButton. 
    	GroupingLayout groupingLayout = new GroupingLayout(this);
    	
    	AvgTextView avgTextView = new AvgTextView(this, getAverage(prefix) );
    	// Add the AvgTextView to the hash to update it in-game. 
    	avgTextViews.put(prefix, avgTextView);
    	
    	OnClickListener resumeGameListener = new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
				intent.putExtra( "newGame", false );
				intent.putExtra("prefix", prefix);
				intent.putExtra( "holes", gameType.getHoles() );
				intent.putExtra( "colors", gameType.getColors() );
				startActivity(intent);
			}
    	};
    	
    	ResumeButton resumeButton = new ResumeButton(this, resumeGameListener, buttonImageIndex);
    	// Add the ResumeButton to the hash to update it in-game. 
    	resumeButtons.put(prefix, resumeButton);
    	
    	groupingLayout.addView(avgTextView);
    	groupingLayout.addView(resumeButton);
    	
    	// The arrowsLayout holds the up and down ArrowButtons. 
    	final ArrowsLayout arrowsLayout = new ArrowsLayout(this);
    	
    	OnClickListener upButtonListener = new OnClickListener() {
			public void onClick(View v) {
				int index = mainMenu.indexOfChild(container);
				if(index != 0) {
					mainMenu.removeView(container);
					mainMenu.addView(container, index-1);
					manager.moveKeyUp(prefix);
					resetAllArrows();
				}
			}
    	};
    	
    	ArrowButton upButton = new ArrowButton(this, R.drawable.arrow_up_selector, upButtonListener);
    	
    	OnClickListener downButtonListener = new OnClickListener() {
			public void onClick(View v) {
				int index = mainMenu.indexOfChild(container);
				if(index != mainMenu.getChildCount()-1) {
					mainMenu.removeView(container);
					mainMenu.addView(container, index+1);
					manager.moveKeyDown(prefix);
					resetAllArrows();
				} 
			}
    	};
    	
    	ArrowButton downButton = new ArrowButton(this, R.drawable.arrow_down_selector, downButtonListener);

    	arrowsLayout.addView(upButton);
    	arrowsLayout.addView(downButton);
    	
    	// Put the elements in the container. 
    	container.addView(newGameButton);
    	container.addView(groupingLayout);
    	container.addArrowsLayout(arrowsLayout);
    	
    	mainMenu.addView(container);
    }
    
    private String getAverage(String prefix) {
    	String avg = settings.getString(prefix + Info.AVG_SUFFIX, "");
    	if(avg.length() == 0) {
    		return getResources().getString(R.string.avg_na);
    	} else {
    		DecimalFormat df = new DecimalFormat( "#########0.00");
    		String val = df.format(  new GuessAvg(avg).getAvg()  );
    		return getResources().getString(R.string.avg) + " = " + val;
    	}
    }
    
    // Loop through the AvgTextView hash and set the values. 
    private void setAverages() {
    	for(String prefix : manager.keys) {
        	String text = getAverage(prefix);
        	avgTextViews.get(prefix).setText(text);
    	}
    }
    
    // Delete the data for the average of a gametype. 
    private void resetAverage(String prefix) {
    	editor.putString(prefix + Info.AVG_SUFFIX, "");
    	editor.commit();
    }
    
    private String getGameRecord(String prefix) {
    	return settings.getString(prefix + Info.GAME_RECORD_SUFFIX, "");
    }
    
    // Loop through the ResumeButtons hash and determine if it should 
    // be enabled/disabled; i.e. if gamerecord is not empty. 
    private void setResumeButtons() {
    	for(String prefix : manager.keys) {
    		String record = getGameRecord(prefix);
    		resumeButtons.get(prefix).setEnabled( record.length() != 0 );
    	}
    }
    
    // Take a new GameType object and persist it, generate a new UI row. 
    private void setGameType(GameType gameType) {
    	editor.putString(gameType.getPrefix() + Info.GAMETYPE_SUFFIX, gameType.toString());
    	editor.commit();
    	createRow(gameType.getPrefix());
    }
    
    // Remove the data of a GameType forever. 
    private void removeGameType(GameType gameType) {
    	String prefix = gameType.getPrefix();
    	editor.remove(prefix + Info.GAMETYPE_SUFFIX);
    	editor.remove(prefix + Info.AVG_SUFFIX);
    	editor.remove(prefix + Info.GAME_RECORD_SUFFIX);
    	editor.commit();
    }
    
    // Return a GameType object from the String representation saved. 
	private GameType getGameType(String prefix) {
    	String history = settings.getString(prefix + Info.GAMETYPE_SUFFIX, "");
    	if(history.length() == 0) {
    		throw new NoSuchElementException();
    	}
    	return new GameType(history);
    }
	
	private void disableArrows() {
		( (ContainerLayout) mainMenu.getChildAt( 0 )).disableUpArrow();
		( (ContainerLayout) mainMenu.getChildAt( mainMenu.getChildCount()-1 )).disableDownArrow();
	}
	
	private void resetAllArrows() {
		for(int i=0; i<mainMenu.getChildCount(); i++) {
			( (ContainerLayout) mainMenu.getChildAt( i )).enableArrows();
		}
		disableArrows();
	}

	// Choose a random index that hasn't been taken yet for the button color.  
	private int randomButtonColor() {
		int available = countAvailable(buttonImage);
		if(available == 0) {
			Arrays.fill(buttonImage, false);
			available = buttonImage.length;
		}
		int[] availableIndeces = new int[available];
		int index = 0;
		for(int i=0; i<buttonImage.length; i++) {
			if(!buttonImage[i]) {
				availableIndeces[index] = i;
				index++;
			}
		}
		index = (int)(Math.random() * availableIndeces.length);
		return availableIndeces[index];
	}
	
	private int countAvailable(boolean[] array) {
		int total = 0;
		for(boolean b : array) {
			if(!b) total++;
		}
		return total;
	}
    
    
}