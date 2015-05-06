package com.chat.ui;

import java.util.List;
import java.util.Map;

import com.chat.misc.Configure;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Scroller;
import android.widget.SimpleAdapter;

/**
 * 仿Launcher中的WorkSapce，可以左右滑动切换屏幕的�? * 
 * @author Yao.GUET blog: http://blog.csdn.net/Yao_GUET date: 2011-05-04
 */
public class ScrollLayout extends ViewGroup {

	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;

	private int mCurScreen;
	private int mDefaultScreen = 0;

	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;

	private static final int SNAP_VELOCITY = 600;

	private int mTouchState = TOUCH_STATE_REST;
	private int mTouchSlop;
	private float mLastMotionX;
//	private float mLastMotionY;

	private PageListener pageListener;

	private OnItemSelecteListener onItemSelecteListener = null;
	
	public ScrollLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScroller = new Scroller(context);

		mCurScreen = mDefaultScreen;
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;
		final int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				final int childWidth = childView.getMeasuredWidth();
				childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!");
		}

		/**
		 * wrap_content 传进去的是AT_MOST 固定数�?或fill_parent 传入的模式是EXACTLY
		 */
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("ScrollLayout only can run at EXACTLY mode!");
		}

		// The children are given the same width and height as the scrollLayout
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		scrollTo(mCurScreen * width, 0);
	}

	/**
	 * According to the position of current layout scroll to the destination
	 * page.
	 */
	public void snapToDestination() {
		final int screenWidth = getWidth();
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		snapToScreen(destScreen ,true);
	}

	/**
	 * 
	 * @param whichScreen 移动的页�?	 * @param isRun是否可以移动
	 */
	public void snapToScreen(int whichScreen,boolean isRun) {
		// get the valid layout page
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		if (getScrollX() != (whichScreen * getWidth())) {
			
			final int delta = whichScreen * getWidth() - getScrollX();
			int V = 0;
			if(isRun)
				V = Math.abs(delta) * 2;
			else V = 0;
			mScroller.startScroll(getScrollX(), 0, delta, 0, V);
			mCurScreen = whichScreen;
			if(mCurScreen>Configure.curentPage){
				Configure.curentPage = whichScreen;
				if(pageListener != null)
					pageListener.page(Configure.curentPage);
			}else if(mCurScreen<Configure.curentPage){
				Configure.curentPage = whichScreen;
				if(pageListener != null)
					pageListener.page(Configure.curentPage);
			}
			invalidate(); // Redraw the layout
		}
	}

	public void setToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		mCurScreen = whichScreen;
		scrollTo(whichScreen * getWidth(), 0);
	}

	/**
	 * 获得当前页码
	 */
	public int getCurScreen() {
		return mCurScreen;
	}

	/**
	 * 当滑动后的当前页�?	 */ 
	public int getPage() {
		return Configure.curentPage;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);

		final int action = event.getAction();
		final float x = event.getX();
	//	final float y = event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaX = (int) (mLastMotionX - x);
			mLastMotionX = x;
			scrollBy(deltaX, 0);
			System.out.println("sds"+deltaX);
			break;
		case MotionEvent.ACTION_UP:
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			int velocityX = (int) velocityTracker.getXVelocity();

			if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
				// Fling enough to move left
				snapToScreen(mCurScreen - 1,true);
				System.out.println("mCurScreen"+mCurScreen);
			} else if (velocityX < -SNAP_VELOCITY && mCurScreen < getChildCount() - 1) {
				// Fling enough to move right
				snapToScreen(mCurScreen + 1,true);
			} else {
				snapToDestination();
			}
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			mTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(Configure.isMove)	return false;//拦截分发给子控件
		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();
	//	final float y = ev.getY();

		switch (action) {
		case MotionEvent.ACTION_MOVE:
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			if (xDiff > mTouchSlop) {
				mTouchState = TOUCH_STATE_SCROLLING;
			}
			break;
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
	//	mLastMotionY = y;
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
			break;

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return mTouchState != TOUCH_STATE_REST;
	}

	
	public void setDataToView(List<List<Map<String,Object>>> data,
													int resource,String from[] ,int to[],int numColumns){
		
		for(int i = 0 ; i < data.size() ; i ++){
			setDataToView(data.get(i), resource, from, to,i,numColumns);
		}
		
	}
	
	public void setDataToView(List<Map<String,Object>>data,
				int resource,String from[] ,int to[],final int i,int numColumns){
		SimpleAdapter adapter = new SimpleAdapter(getContext(), data, resource, from, to);
		GridView gridView = new GridView(getContext());
		gridView.setAdapter(adapter);
		LinearLayout linear = new LinearLayout(this.getContext());
		gridView.setNumColumns(numColumns);
		gridView.setHorizontalSpacing(15);
		gridView.setVerticalSpacing(15);
		linear.addView(gridView, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		addView(linear, i);
		System.out.println("初始化了Griview");
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(onItemSelecteListener != null)
					onItemSelecteListener.onItemSelected(parent, i, position);
			}
		});
	}
	
	public void setPageListener(PageListener pageListener) {
		this.pageListener = pageListener;
	}

	public void setOnItemSelecteListener(OnItemSelecteListener onItemSelecteListener){
		this.onItemSelecteListener = onItemSelecteListener;
	}
	
	public interface PageListener {
		void page(int page);
	}
	
	/**
	 * 监听点击事件
	 * @author YJN
	 *
	 */
	public interface OnItemSelecteListener{
		
		public void onItemSelected( AdapterView<?> parent,int groupPosition,int chilldPosition);
		
	}
	
}