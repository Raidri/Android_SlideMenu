package de.pg.slidemenu;

import android.view.View;
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
public class SlideMenu {

	public static final int SLIDE_LEFT = 0, SLIDE_RIGHT = 1, SLIDE_TOP = 2,
			SLIDE_BOTTOM = 3;

	private boolean STATE_INVISIBLE = true;

	private TranslateAnimation animateOutContent, animateOutMenu;
	private TranslateAnimation animateInContent, animateInMenu;

	private View slideInView;
	private View slideOutView;

	private long duration = 100;

	private int slideOrientation = SLIDE_LEFT;

	public SlideMenu() {

	}

	public SlideMenu(View slideIn, View slideOut) throws Exception {

		if (slideIn == null || slideOut == null) {
			throw new Exception("The slideIn and slideOut view must be set");
		}
		this.slideInView = slideIn;

		this.slideOutView = slideOut;
		this.createAnimation();
	}

	public SlideMenu(View slideIn, View slideOut, long duration)
			throws Exception {
		if (slideIn == null || slideOut == null) {
			throw new Exception("The slideIn and slideOut view must be set");
		}

		this.slideInView = slideIn;
		this.slideOutView = slideOut;
		this.duration = duration;
	}

	public SlideMenu(View slideIn, View slideOut, int slideOrientation)
			throws Exception {
		if (slideIn == null || slideOut == null) {
			throw new Exception("The slideIn and slideOut view must be set");
		}

		this.slideInView = slideIn;
		this.slideOutView = slideOut;
		this.slideOrientation = slideOrientation;
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

			int moveRight = slideInView.getRight();

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

						}
					});

			animateInContent
					.setAnimationListener(new Animation.AnimationListener() {

						public void onAnimationStart(Animation animation) {

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

							STATE_INVISIBLE = true;

						}
					});
		} else if (slideOrientation == SLIDE_RIGHT) {
			/** SLIDE IN ANIMATION **/

			int moveLeft = slideInView.getWidth();

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

						}
					});

			animateInContent
					.setAnimationListener(new Animation.AnimationListener() {

						public void onAnimationStart(Animation animation) {

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

							STATE_INVISIBLE = true;

						}
					});

		} else if (slideOrientation == SLIDE_TOP) {
			/** SLIDE IN ANIMATION **/
			int moveTop = slideInView.getHeight();

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

						}
					});

			animateInContent
					.setAnimationListener(new Animation.AnimationListener() {

						public void onAnimationStart(Animation animation) {

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

							STATE_INVISIBLE = true;

						}
					});
		} else if (slideOrientation == SLIDE_BOTTOM) {
			/** SLIDE IN ANIMATION **/
			int moveBottom = slideInView.getHeight();

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

						}
					});

			animateInContent
					.setAnimationListener(new Animation.AnimationListener() {

						public void onAnimationStart(Animation animation) {

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

							STATE_INVISIBLE = true;

						}
					});
		}
	}

}
