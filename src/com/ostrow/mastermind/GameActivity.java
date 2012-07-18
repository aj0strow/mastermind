package com.ostrow.mastermind;

import java.util.Arrays;

import com.ostrow.mastermind.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.ostrow.mastermind.backend.GameRecord;
import com.ostrow.mastermind.backend.GuessAvg;
import com.ostrow.mastermind.backend.Info;
import com.ostrow.mastermind.backend.Result;
import com.ostrow.mastermind.gameui.ButtonHolderLayout;
import com.ostrow.mastermind.gameui.ColorRadioGroup;
import com.ostrow.mastermind.gameui.CreateButton;
import com.ostrow.mastermind.gameui.EnterGuessButton;
import com.ostrow.mastermind.gameui.GameRowLayout;
import com.ostrow.mastermind.gameui.PegsTextView;
import com.ostrow.mastermind.gameui.WinTextView;

public class GameActivity extends Activity {
	
	/*
	 * Information determined by the difficulty chosen.
	 * Static methods of Difficulty.java set values.
	 */
	private int holes;
	private String prefix;
	private Integer[] colorIds;
	private Integer[] pegIds;
		
	// For saving persistent data and settings
	private SharedPreferences settings;
	 
	private GuessAvg guessAvg;
	private GameRecord gameRecord;
	private int[] answer, guess;
	private int guesses = 0;
	
	// For making the color-select panel
	private RadioGroup radio;
	private int selection = -1;
	
	// For making the game board
	private LinearLayout gameArea;
	
	// To handle saving. 
	private boolean save;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * Request no title on the window, 
         * and set the content view to the game. 
         */
        setContentView(R.layout.game);
        
		settings = getSharedPreferences(getResources().getString(R.string.shared_preferences), 0);

        /*
         * Each intent starting a game should pass:
         * - Whether or not it is a new game.
         * - Prefix string for the settings.
         * - Amount of holes.
         * - Amount of colors.
         */
        Bundle extras = getIntent().getExtras();
        boolean newGame = extras.getBoolean("newGame");
        prefix = extras.getString("prefix");
        holes = extras.getInt("holes");
        int colors = extras.getInt("colors");
        colorIds = Info.newColorIdsArray(colors);
        pegIds = Info.newPegIdsArray(colors);
        
        /*
         * Order steps: 
         * 
         * 1. Color Selector at bottom created
         * 2. Set the guess average (if its a new game, make a new one.)
         * 3. Recover information if it is a repeat game.  
         * 4. If it is a new game, recover the previous game state
         * 5. Add a new row to continue the game
         */
                
        // Step #1
        LinearLayout colorPanel = (LinearLayout) findViewById(R.id.color_selector);
        createRadioButtons();
        colorPanel.addView(radio, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                
		// Step #2
		establishGuessAvg();
		
		// Step #3
		if(newGame) {
			gameRecord = new GameRecord(holes);
			answer = randomAnswer();
			gameRecord.append(answer);
		} else {
			String key = prefix + Info.GAME_RECORD_SUFFIX;
			String record = settings.getString(key, "");
			gameRecord = new GameRecord(holes, record);
			gameRecord.refresh();
			answer = gameRecord.readArray();
		}
				
		// Step #4
        gameArea = (LinearLayout) findViewById(R.id.game_area);
        if(!newGame) {
        	while(gameRecord.hasNext()) {
        		int[] data = gameRecord.readArray();
        		generateRow(data);
        	}
        }
		
		// Step #5
		createRow();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		if(save) {
			SharedPreferences.Editor editor = settings.edit();
			String key = prefix + Info.GAME_RECORD_SUFFIX;
			editor.putString(key, gameRecord.getRecord());
			editor.commit();
		}
	}

	/*
	 * Step #1- createRadioButtons():
	 * 
	 * Establishes radio as a RadioGroup and populates it with buttons. 
	 * Sets a lot of LayoutParams to format the buttons correctly. 
	 * Sets an OnChangedCheckedListener to save the selected index to the selection private variable.
	 */
	private void createRadioButtons() {
	    // A listener is added to the radio group to detect which is selected. 
	    OnCheckedChangeListener crglistener = new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup rg, int rb) {
				selection = rg.indexOfChild ( (RadioButton)findViewById(rb) );
			}
		};
		radio = new ColorRadioGroup(this, colorIds, crglistener);
	}
	
	/*
	 * Step #2- establishGuessAvg()
	 * 
	 * Creates a GuessAvg object based on settings, 
	 * or a brand new one. 
	 */
	private void establishGuessAvg() {
		String key = prefix + Info.AVG_SUFFIX;
		String avg = settings.getString(key, "");
        if(avg.length() > 0)
        	guessAvg = new GuessAvg(avg);
        else 
        	guessAvg = new GuessAvg();
	}
	
	/*
	 * Step #3- randomAnswer()
	 * 
	 * Creates a random answer of the correct size.
	 */
	
	private int[] randomAnswer() {
		int[] ans = new int[holes];
		for(int i=0; i<holes; i++) {
			// Assign a random number from 0 to # where # is the number of holes -1.
			ans[i] = (int) ( Math.random() * colorIds.length ); 
		}
		return ans;
	}
	
	/*
	 * Step #4- generateRow()
	 * creates a row based on a previous guess.
	 * Mirrors the createRow() funtion with changes.
	 *
	*/
	protected void generateRow(int[] data) {		
		// Each Row will have a LinearLayout container
		final GameRowLayout gameRow = new GameRowLayout(this);
			
			// Each GameRow has a ButtonHolder layout. The Buttons are made in the constructor.
			final ButtonHolderLayout buttonHolder = new ButtonHolderLayout(this, data, pegIds);
			gameRow.addView(buttonHolder);
			
			guesses++;
			Result guessResult = new Result(answer, data);
			for(int i=0; i<holes; i++) {
				( (CreateButton) 
					( (ButtonHolderLayout) gameRow.getChildAt(0) ).getChildAt(i) 
				).setResult(guessResult.result[i]);  // See CreateButton class for method.
			}
						
			gameRow.addView(
				new PegsTextView(getApplicationContext(), guessResult.amountPerfect(), Color.rgb(253, 208, 23) )); // #fdd017
			gameRow.addView(
				new PegsTextView(getApplicationContext(), guessResult.amountClose(), Color.rgb(192, 192, 192) )); // #c0c0c0			
		
		gameArea.addView(gameRow, 0);
	}
	
	/*
	 * Step #5- createRow(), checkComplete(), check(), winCondition(), showAlertbox()
	 */
	
	protected void createRow() {
		guess = new int[holes];
		Arrays.fill(guess, -1);		// Sets every value to -1
		
		// Each Row will have a LinearLayout container
		final GameRowLayout gameRow = new GameRowLayout(this);
		
			// Each CreateButton needs a listener to change the bg image and save the selection. 
			OnClickListener listener = new OnClickListener() {
				public void onClick(View v) {
					int index = ((CreateButton)v).getIndex();
					if(selection < 0) {				// To avoid breaking if no color is chosen.
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.game_colorErrorMessage), Toast.LENGTH_SHORT).show();
					} else if(selection == guess[index] ) {
						( (CreateButton) v).setImageDrawable( (Drawable) getResources().getDrawable(R.drawable.peg_hole) );
						guess[index] = -1;
					} else {
						((CreateButton) v).setImageDrawable( (Drawable) getResources().getDrawable(pegIds[selection]) );
						guess[index] = selection;
					}
				}
			};
			
			// Each GameRow has a ButtonHolder layout. The Buttons are made in the constructor.
			final ButtonHolderLayout buttonHolder = new ButtonHolderLayout(this, holes, listener);
			gameRow.addView(buttonHolder);
			
			// A button is added to the right of the holes to submit the guess
			OnClickListener eglistener = new OnClickListener() {
				public void onClick(View v) {
					if( checkComplete() ) {
						gameRecord.append(guess);
						guesses++;
						buttonHolder.disableAll();
						gameRow.removeView(v);
						Result guessResult = new Result(answer, guess);
						for(int i=0; i<holes; i++) {
							( (CreateButton) 
									( (ButtonHolderLayout) gameRow.getChildAt(0) ).getChildAt(i) 
							).setResult(guessResult.result[i]);  // See CreateButton class for method.
						}
					// Decide win condition or another guess.
						if(guessResult.amountPerfect() == holes) {
							gameRow.addView(new WinTextView( getApplicationContext() ));
							winCondition();
						} else {
					        save = true;
							gameRow.addView(
									new PegsTextView(getApplicationContext(), guessResult.amountPerfect(), Color.rgb(253, 208, 23) )); // #fdd017
							gameRow.addView(
									new PegsTextView(getApplicationContext(), guessResult.amountClose(), Color.rgb(192, 192, 192) )); // #c0c0c0
							createRow();
						}
					} else {
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.game_guessErrorMessage), Toast.LENGTH_SHORT).show();
					}
				}
			};
			
			final EnterGuessButton enterGuess = new EnterGuessButton(this, eglistener);
			gameRow.addView(enterGuess);
			
		gameArea.addView(gameRow, 0);
	}
	
	/*
	 * Checks to see if the guess is completely filled in with user choices. 
	 */
	private boolean checkComplete() {
		for(int i=0; i<holes; i++) {
			if(guess[i] == -1)
				return false;
		}
		return true;
	}
	
	/*
	 * Saves the game and the avg guesses on victory. 
	 */
	private void winCondition() {
		guessAvg.addGame(guesses);
		SharedPreferences.Editor editor = settings.edit();
		String avgKey = prefix + Info.AVG_SUFFIX;
		String gameKey = prefix + Info.GAME_RECORD_SUFFIX;
		editor.putString(avgKey, guessAvg.toString());
		editor.putString(gameKey, "");
		editor.commit();
		save = false;
		showAlertbox();
	}
	
	/*
	 * Creates an alertbox that ends the activity or allows more looking at the game. 
	 */
	private void showAlertbox() {
		AlertDialog.Builder alert = new AlertDialog.Builder(GameActivity.this);

		alert.setTitle( getResources().getString(R.string.winDialog_title) );
		String message = getResources().getString(R.string.winDialog_messageStart) + " " + guesses + " " + getResources().getString(R.string.winDialog_messageEnd);
		message += "\n\n" + getResources().getString(R.string.winDialog_description);
		alert.setMessage(message);
		alert.setIcon(R.drawable.icon);
		alert.setPositiveButton( getResources().getString(R.string.winDialog_analysis), 
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// Loop through every ButtonHolderLayout and trigger background changes for the buttons.
					for(int i=0; i<gameArea.getChildCount(); i++) {
						( (ButtonHolderLayout) 
							( (GameRowLayout) gameArea.getChildAt(i) ).getChildAt(0) 
						).handleAnalysis();  // See ButtonHolderLayout class for the method.
					}
				}
			}
		);
		alert.setNegativeButton( getResources().getString(R.string.winDialog_mainMenu),
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					GameActivity.this.finish();
				}
		 	}
		);
		alert.show();
	}
	
}
