package com.ostrow.mastermind.backend;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.ImageView;

public class AutoResizeTextView extends ImageView {

	String mText = "";
	Integer color;
	int targetSize;
	Paint mTextPaint;
	
	float textScale;
	float textSize;

	int mViewWidth;
	int mViewHeight;
	int mTextBaseline;
	
	public AutoResizeTextView(Context context, Integer color, int targetSize) {
		super(context);
		this.color = color;
		this.targetSize = targetSize;
		init();
	}

	private void init() {
		mTextPaint = new Paint();
		mTextPaint.setColor(color);
		mTextPaint.setTypeface(Typeface.SERIF);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		mTextPaint.setTextSize(textSize);
		mTextPaint.setTextScaleX(textScale);
		mTextPaint.setAntiAlias(true);
	}
	
	public void setText(String text) {
		if(!text.equals(mText)) {
			mText = text;
			// first determine font point size
			adjustTextSize();
			// then determine width scaling depending on the new font point size
			adjustTextScale();
		}
		init();
	}

	/**
	* set the scale of the text Paint objects so that the
	* text will draw and take up the full screen width
	*/
	void adjustTextScale() {
		mTextPaint.setTextScaleX(1.0f); // Start with 1.0 (no scaling)
		Rect bounds = new Rect();
		// get the hypothetical bounding rect
		mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);
		// width and height are the size between the bounds.
		int w = bounds.right - bounds.left;
		int text_h = bounds.bottom-bounds.top;
		
		mTextBaseline = bounds.bottom + ( (mViewHeight-text_h) / 2 );
	
		// determine how much to scale the width to fit the view, doesn't stretch just shrinks
		float xscale = ((float) (mViewWidth - getPaddingLeft() - getPaddingRight() )) / w;
		if(xscale > 1.0) xscale = 1.0f; // Dont make it wider.
		textScale = xscale;
		mTextPaint.setTextScaleX(xscale);
	}

	/**
	* determine the proper text size to use to fill the full height
	*/
	void adjustTextSize() {
		mTextPaint.setTextScaleX(1.0f);
		mTextPaint.setTextSize(100);
		Rect bounds = new Rect();
		// get the hypothetical bounding rect as of now
		mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);
		
		int h = bounds.bottom - bounds.top;
		
		float target = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, targetSize, getResources().getDisplayMetrics() );

		// determine textSize scaling for desired height, and set it into the paint
		float size = ( (target/h) * 100f );
		textSize = size;
		mTextPaint.setTextSize(size);
	}

	/**
	* When the view size is changed, recalculate the paint settings
	* to have the text on the fill the view area (just in case)
	*/
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	
		// save view size
		mViewWidth = w;
		mViewHeight = h;
	
		// first determine font point size
		adjustTextSize();
		// then determine width scaling depending on the new font point size
		adjustTextScale();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// let the ImageButton paint background as normal
		super.onDraw(canvas);
	
		// draw the text:
		// - position is centered on width
		// - baseline is calculated to be positioned from the bottom of the view (see above)
		canvas.drawText(mText, mViewWidth/2, mViewHeight-mTextBaseline, mTextPaint);
	}

    
}
