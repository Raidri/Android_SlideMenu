Android_SlideMenu
=================

##Requirements
- Android 2.3 (Gingerbread) and above

##Introduction
The slidemenu works like the facebook or google plus slide menu. the content layout slides out and the menu slides in from which direction u want (TOP / BOTTOM / LEFT / RIGHT).

##Example
The SlideMenu library is really easy to use. First we generate our Layout

For Example:

	<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity" >
    <!-- navigation menu -->
    <FrameLayout
        android:id="@+id/slidemenu"
        android:background="@android:color/black"
        android:layout_width="250dp"
        android:layout_height="fill_parent"
        android:layout_alignParentLeft="true">
    </FrameLayout>
    <!-- content -->
    <RelativeLayout 
        android:id="@+id/content"
        android:background="@android:color/white"
        android:clickable="true"
        android:onClick="hideSideMenu"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent">
    </RelativeLayout>


The next step is to define the layouts in our SlideMenu class

	public class MainActivity extends Activity {

	private SlideMenu menu;
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		img = (ImageView) this.findViewById(R.id.img);

		FrameLayout slideMenu = (FrameLayout) findViewById(R.id.slidemenu);

		RelativeLayout content = (RelativeLayout) findViewById(R.id.content);

		try {
			menu = new SlideMenu(slideMenu, content);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showSlideMenu(View v) {
		try {
			// setDuration and setSlideOrientation are optional. The default values are
			// duration = 100 and slideOrientation from LEFT
			menu.setDuration(1000);
			menu.setSlideOrientation(SlideMenu.SLIDE_LEFT);
			menu.slideIn();
		} catch (Exception e) {

		}
	}

	public void hideSlideMenu(View v) {

		try {
			menu.slideOut();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

Now bind the showSlideMenu and hideSlideMenu method to a button or an other view.

If you should slide in from top or bottom then you must set ur FrameLayout to Bottom or Top.

That's all !
