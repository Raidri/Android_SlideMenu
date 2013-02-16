package de.pg.slidemenu;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * a basic slideIn Menu which can slide in from left / right / top / bottom
 * 
 * @author Pascal Geldmacher
 * 
 */
public class SlideMenu implements OnTouchListener {

	public static final int SLIDE_LEFT = 0, SLIDE_RIGHT = 1, SLIDE_TOP = 2,
			SLIDE_BOTTOM = 3;

	private  int MOVE_FACTOR = 20;

	private boolean STATE_INVISIBLE = true;

	private TranslateAnimation animateOutContent, animateOutMenu;
	private TranslateAnimation animateInContent, animateInMenu;

	private TranslateAnimation moveAnimation;

	private View slideInView;
	private View slideOutView;

	private long duration = 200;

	private int previousPosition, position = 0;
	private float previousTouchRegion = 0.0f;

	private int slideOrientation = SLIDE_LEFT;

	private RelativeLayout.LayoutParams params = null;

	public SlideMenu() {
	}

	public SlideMenu(View slideIn, View slideOut) throws Exception {
		if (slideIn == null || slideOut == null) {
			throw new Exception("The slideIn and slideOut view must be set");
		}
		this.slideInView = slideIn;
		this.slideOutView = slideOut;

		this.init();
	}

	public SlideMenu(View slideIn, View slideOut, long duration)
			throws Exception {
		if (slideIn == null || slideOut == null) {
			throw new Exception("The slideIn and slideOut view must be set");
		}

		this.slideInView = slideIn;
		this.slideOutView = slideOut;

		this.slideOutView.setOnTouchListener(this);

		this.duration = duration;

		this.init();
	}

	public SlideMenu(View slideIn, View slideOut, int slideOrientation)
			throws Exception {
		if (slideIn == null || slideOut == null) {
			throw new Exception("The slideIn and slideOut view must be set");
		}

		this.slideInView = slideIn;
		this.slideOutView = slideOut;
		this.slideOrientation = slideOrientation;

		this.init();
	}

	public SlideMenu(View slideIn, View slideOut, long duration,
			int slideOrientation) throws Exception {
		if (slideIn == null || slideOut == null) {
			throw new Exception("The slideIn and slideOut view must be set");
		}

		this.slideInView = slideIn;
		this.slideOutView = slideOut;
		this.duration = duration;
		this.slideOrientation = slideOrientation;

		this.init();
	}

	private void init() {
		this.createAnimation();
		this.slideOutView.setOnTouchListener(this);

		params = (LayoutParams) slideOutView.getLayoutParams();
		LayoutParams slideInParams = (LayoutParams) slideInView
				.getLayoutParams();
	}
	
	private void resetStates(){
		position = 0;
		previousPosition = 0;
		MOVE_FACTOR = 20;
		previousTouchRegion = 0;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getDuration() {
		return this.duration;
	}

	public View getSlideInView() {
		return slideInView;
	}

	public void setSlideInView(View slideInView) {
		this.slideInView = slideInView;
	}

	public View getSlideOutView() {
		return slideOutView;
	}

	public void setSlideOutView(View slideOutView) {
		this.slideOutView = slideOutView;
	}

	/**
	 * Set the orientation from where the view comes in DEFAULT IS FROM LEFT
	 * 
	 * @param slideOrientation
	 */
	public void setSlideOrientation(int slideOrientation) {
		this.slideOrientation = slideOrientation;
		this.createAnimation();
	}

	public void slideIn() throws Exception {
		if (!STATE_INVISIBLE) {
			return;
		}
		if (slideInView == null || slideOutView == null) {
			throw new Exception("slideIn or slideOut view is null");
		}

		if (slideInView.getVisibility() == View.INVISIBLE
				|| slideInView.getVisibility() == View.GONE) {
			slideInView.setVisibility(View.VISIBLE);
		}
		slideInView.startAnimation(animateInMenu);
		slideOutView.startAnimation(animateOutContent);

	}

	public void slideOut() throws Exception {
		if (slideInView == null || slideOutView == null) {
			throw new Exception("slideIn or slideOut view is null");
		}

		if (!STATE_INVISIBLE) {
			slideOutView.startAnimation(animateInContent);
			slideInView.startAnimation(animateOutMenu);
		}
	}

	private void createAnimation() {
		if (slideOrientation == SLIDE_LEFT) {
			/** SLIDE IN ANIMATION **/

			int moveRight = slideInView.getRight() / 2;

			animateInMenu = new TranslateAnimation(-moveRight, 0, 0, 0);
			animateInMenu.setDuration(duration);

			animateOutContent = new TranslateAnimation(0, moveRight, 0, 0);
			animateOutContent.setDuration(duration);

			/** SLIDE OUT ANIMATION **/
			animateOutMenu = new TranslateAnimation(0, -moveRight, 0, 0);
			animateOutMenu.setDuration(duration);

			animateInContent = new TranslateAnimation(0, -moveRight, 0, 0);
			animateInContent.setDuration(duration);

			animateOutContent
					.setAnimationListener(new Animation.AnimationListener() {

						public void onAnimationStart(Animation animation) {
							slideOutView.setEnabled(false);
						}

						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						public void onAnimationEnd(Animation animation) {
							slideOutView.clearAnimation();

							LayoutParams params = (RelativeLayout.LayoutParams) slideOutView
									.getLayoutParams();
							params.setMargins(slideInView.getRight(),
									params.topMargin, -slideInView.getRight(),
									params.bottomMargin);
							slideOutView.setLayoutParams(params);

							STATE_INVISIBLE = false;
							slideOutView.setEnabled(true);
						}
					});

			animateInContent
					.setAnimationListener(new Animation.AnimationListener() {

						public void onAnimationStart(Animation animation) {
							slideOutView.setEnabled(false);
						}

						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						public void onAnimationEnd(Animation animation) {
							slideOutView.clearAnimation();
							LayoutParams params = (RelativeLayout.LayoutParams) slideOutView
									.getLayoutParams();
							params.setMargins(0, params.topMargin, 0,
									params.bottomMargin);
							slideOutView.setLayoutParams(params);
							resetStates();
							STATE_INVISIBLE = true;
							slideOutView.setEnabled(true);

						}
					});
		} else if (slideOrientation == SLIDE_RIGHT) {
			/** SLIDE IN ANIMATION **/

			int moveLeft = slideInView.getWidth() / 2;

			animateInMenu = new TranslateAnimation(
					(slideInView.getRight() - moveLeft), 0, 0, 0);
			animateInMenu.setDuration(duration);

			animateOutContent = new TranslateAnimation(0, -moveLeft, 0, 0);
			animateOutContent.setDuration(duration);

			/** SLIDE OUT ANIMATION **/
			animateOutMenu = new TranslateAnimation(0, moveLeft, 0, 0);
			animateOutMenu.setDuration(duration);

			animateInContent = new TranslateAnimation(0, moveLeft, 0, 0);
			animateInContent.setDuration(duration);

			animateOutContent
					.setAnimationListener(new Animation.AnimationListener() {

						public void onAnimationStart(Animation animation) {
							slideOutView.setEnabled(false);
						}

						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						public void onAnimationEnd(Animation animation) {
							slideOutView.clearAnimation();

							LayoutParams params = (RelativeLayout.LayoutParams) slideOutView
									.getLayoutParams();
							params.setMargins(-slideInView.getWidth(),
									params.topMargin, slideInView.getWidth(),
									params.bottomMargin);
							slideOutView.setLayoutParams(params);

							STATE_INVISIBLE = false;
							slideOutView.setEnabled(true);

						}
					});

			animateInContent
					.setAnimationListener(new Animation.AnimationListener() {

						public void onAnimationStart(Animation animation) {
							slideOutView.setEnabled(false);
						}

						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						public void onAnimationEnd(Animation animation) {
							slideOutView.clearAnimation();
							LayoutParams params = (RelativeLayout.LayoutParams) slideOutView
									.getLayoutParams();
							params.setMargins(0, params.topMargin, 0,
									params.bottomMargin);
							slideOutView.setLayoutParams(params);
							resetStates();
							STATE_INVISIBLE = true;
							slideOutView.setEnabled(true);

						}
					});

		} else if (slideOrientation == SLIDE_TOP) {
			/** SLIDE IN ANIMATION **/
			int moveTop = slideInView.getHeight() / 2;

			animateInMenu = new TranslateAnimation(0, 0, -moveTop, 0);
			animateInMenu.setDuration(duration);

			animateOutContent = new TranslateAnimation(0, 0, 0, moveTop);
			animateOutContent.setDuration(duration);

			/** SLIDE OUT ANIMATION **/
			animateOutMenu = new TranslateAnimation(0, 0, 0, -moveTop);
			animateOutMenu.setDuration(duration);

			animateInContent = new TranslateAnimation(0, 0, 0, -moveTop);
			animateInContent.setDuration(duration);

			animateOutContent
					.setAnimationListener(new Animation.AnimationListener() {

						public void onAnimationStart(Animation animation) {
							slideOutView.setEnabled(false);
						}

						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						public void onAnimationEnd(Animation animation) {
							slideOutView.clearAnimation();

							LayoutParams params = (RelativeLayout.LayoutParams) slideOutView
									.getLayoutParams();
							params.setMargins(params.leftMargin,
									slideInView.getHeight(),
									params.rightMargin,
									-slideInView.getHeight());
							slideOutView.setLayoutParams(params);

							STATE_INVISIBLE = false;
							slideOutView.setEnabled(true);
						}
					});

			animateInContent
					.setAnimationListener(new Animation.AnimationListener() {

						public void onAnimationStart(Animation animation) {
							slideOutView.setEnabled(false);
						}

						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						public void onAnimationEnd(Animation animation) {

							// slideInView.clearAnimation();
							slideOutView.clearAnimation();
							LayoutParams params = (RelativeLayout.LayoutParams) slideOutView
									.getLayoutParams();
							params.setMargins(params.leftMargin, 0,
									params.rightMargin, 0);
							slideOutView.setLayoutParams(params);
							resetStates();
							STATE_INVISIBLE = true;
							slideOutView.setEnabled(true);

						}
					});
		} else if (slideOrientation == SLIDE_BOTTOM) {
			/** SLIDE IN ANIMATION **/
			int moveBottom = slideInView.getHeight() / 2;

			animateInMenu = new TranslateAnimation(0, 0,
					(slideInView.getBottom() - moveBottom), 0);
			animateInMenu.setDuration(duration);

			animateOutContent = new TranslateAnimation(0, 0, 0, -moveBottom);
			animateOutContent.setDuration(duration);

			/** SLIDE OUT ANIMATION **/
			animateOutMenu = new TranslateAnimation(0, 0, 0, moveBottom);
			animateOutMenu.setDuration(duration);

			animateInContent = new TranslateAnimation(0, 0, 0, moveBottom);
			animateInContent.setDuration(duration);

			animateOutContent
					.setAnimationListener(new Animation.AnimationListener() {

						public void onAnimationStart(Animation animation) {
							slideOutView.setEnabled(false);
						}

						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						public void onAnimationEnd(Animation animation) {
							slideOutView.clearAnimation();

							LayoutParams params = (RelativeLayout.LayoutParams) slideOutView
									.getLayoutParams();
							params.setMargins(params.leftMargin,
									-slideInView.getHeight(),
									params.rightMargin, slideInView.getHeight());
							slideOutView.setLayoutParams(params);

							STATE_INVISIBLE = false;
							slideOutView.setEnabled(true);

						}
					});

			animateInContent
					.setAnimationListener(new Animation.AnimationListener() {

						public void onAnimationStart(Animation animation) {
							slideOutView.setEnabled(false);
						}

						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						public void onAnimationEnd(Animation animation) {
							slideOutView.clearAnimation();
							LayoutParams params = (RelativeLayout.LayoutParams) slideOutView
									.getLayoutParams();
							params.setMargins(params.leftMargin, 0,
									params.rightMargin, 0);
							slideOutView.setLayoutParams(params);
							resetStates();
							STATE_INVISIBLE = true;
							slideOutView.setEnabled(true);
						}
					});
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		float rawX = event.getRawX();
		float rawY = event.getRawY();
		if (action == MotionEvent.ACTION_MOVE) {

			if (slideOrientation == SLIDE_LEFT) {
				
				if(previousTouchRegion > 0){
					MOVE_FACTOR = (int)(rawX - previousTouchRegion);
				}
				
				previousPosition = position;
			
				position += MOVE_FACTOR;
				
				Log.d("", "Position: "+params.leftMargin+" "+ slideInView.getWidth());
				if (position < 0) {
					position = 0;
				} else if (params.leftMargin > slideInView.getWidth()) {
					position = slideInView.getWidth();
				}
				previousTouchRegion = rawX;

				params.setMargins(position, 0, -position, 0);
				slideOutView.setLayoutParams(params);

			} else if (slideOrientation == SLIDE_RIGHT) {

			} else if (slideOrientation == SLIDE_TOP) {

			} else if (slideOrientation == SLIDE_BOTTOM) {

			}

			//slideOutView.startAnimation(moveAnimation);

		} else if (action == MotionEvent.ACTION_UP && position > 0) {
			
			if(rawX > position && !STATE_INVISIBLE){
				return false;
			}
			float visibleWidthScope = (slideOutView.getResources().getDisplayMetrics().densityDpi / 2.0f);

			if (slideOrientation == SLIDE_LEFT) {
				
				if (position >= visibleWidthScope) {
					moveAnimation = new TranslateAnimation(0,
							(slideInView.getWidth()-previousPosition), 0, 0);
					//moveAnimation.setFillAfter(true);
					moveAnimation.setDuration(duration);
					moveAnimation
							.setAnimationListener(new Animation.AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
							
								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationEnd(Animation animation) {
									slideOutView.clearAnimation();
									params.setMargins(slideInView.getWidth(), 0, -slideInView.getWidth(), 0);
									slideOutView.setLayoutParams(params);
									position = slideInView.getWidth();
									previousPosition = slideInView.getWidth();
									MOVE_FACTOR = 20;
									STATE_INVISIBLE = false;
								}
							});
				} else {
					Log.d("", "PreviousPosition: "+previousPosition);
					moveAnimation = new TranslateAnimation(previousPosition, -previousPosition , 0, 0);
					//moveAnimation.setFillAfter(true);
					moveAnimation.setDuration(duration);
					moveAnimation
							.setAnimationListener(new Animation.AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {

								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationEnd(Animation animation) {
									slideOutView.clearAnimation();
									params.setMargins(0, 0, 0, 0);
									slideOutView.setLayoutParams(params);
									resetStates();
									STATE_INVISIBLE = true;
								}
							});
				}

				slideOutView.startAnimation(moveAnimation);
				
				return true;

			} else if (slideOrientation == SLIDE_RIGHT) {

			} else if (slideOrientation == SLIDE_TOP) {

			} else if (slideOrientation == SLIDE_BOTTOM) {

			}

		}

		return false;
	}
	
}
