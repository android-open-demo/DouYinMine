package sing.top.douyinmine.widget

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.widget.NestedScrollView

/**
 * 顶部下拉图片放大回弹效果
 */
class ScaleScrollView : NestedScrollView {
    /**
     * 需要放大的View
     */
    private var mTargetView: View? = null

    /**
     * 放大View的宽
     */
    private var mTargetViewWidth = 0

    /**
     * 放大View的高
     */
    private var mTargetViewHeight = 0

    /**
     * 上一次按下的位置
     */
    private var mLastPosition = 0f

    /**
     * 是否正在滑动
     */
    private var isScrolling = false

    /**
     * 滑到顶部是否需要回弹效果
     */
    private var isCanOverScroll = false

    /**
     * 放大系数
     */
    private var mScaleRatio = 1.2f

    /**
     * 恢复原样速度
     */
    private var mCallbackSpeed = 0.2f

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,-1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setTargetView(targetView: View?) {
        mTargetView = targetView
    }

    fun setScaleRatio(ratio: Float) {
        mScaleRatio = ratio
    }

    fun setCallbackSpeed(speed: Float) {
        mCallbackSpeed = speed
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        overScrollMode = OVER_SCROLL_ALWAYS
    }

    override fun fling(y: Int) {
        isCanOverScroll = y <= -6000
        super.fling(y)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (null != mTargetView) {
            if (mTargetViewWidth <= 0 || mTargetViewHeight <= 0) {
                mTargetViewWidth = mTargetView!!.measuredWidth
                mTargetViewHeight = mTargetView!!.measuredHeight
            }
            when (ev.action) {
                MotionEvent.ACTION_UP -> {
                    //手指移开，放大的View恢复原样
                    isScrolling = false
                    callbackView()
                }
                MotionEvent.ACTION_MOVE -> {
                    if (!isScrolling) {
                        if (scrollY == 0) {
                            mLastPosition = ev.y
                        } else {
                            return super.onTouchEvent(ev)
                        }
                    }
                    val value = (ev.y - mLastPosition) * mScaleRatio
                    if (value < 0) {
                        return super.onTouchEvent(ev)
                    }
                    isScrolling = true
                    updateTargetViewValue(value)
                    return true
                }
                else -> {}
            }
        }
        return super.onTouchEvent(ev)
    }

    /**
     * View恢复到最初状态动画
     */
    private fun callbackView() {
        val value = (mTargetView!!.measuredWidth - mTargetViewWidth).toFloat()
        val animator = ValueAnimator.ofFloat(value, 0f)
        animator.duration = (value * mCallbackSpeed).toLong()
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation -> updateTargetViewValue(animation.animatedValue as Float) }
        animator.start()
    }

    /**
     * 更新View的宽高属性值
     */
    private fun updateTargetViewValue(value: Float) {
        if (null == mTargetView) {
            return
        }
        if (mTargetViewWidth <= 0 || mTargetViewHeight <= 0) {
            return
        }
        val lp = mTargetView!!.layoutParams
        if (null != lp) {
            lp.width = (mTargetViewWidth + value).toInt()
            lp.height =
                (mTargetViewHeight * ((mTargetViewWidth + value) / mTargetViewWidth)).toInt()
            if (lp is MarginLayoutParams) {
                lp.setMargins(-(lp.width - mTargetViewWidth) / 2, 0, 0, 0)
            }
            mTargetView!!.layoutParams = lp
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        //滑动到顶部，速度很快，开启动画
        if (t == 0 && isCanOverScroll) {
            zoomAnimator()
        }
        super.onScrollChanged(l, t, oldl, oldt)
    }

    /**
     * 先放大后恢复动画
     */
    private fun zoomAnimator() {
        val value = mTargetViewWidth * mScaleRatio
        val enlarge = ValueAnimator.ofFloat(0f, value)
        enlarge.addUpdateListener { animation -> updateTargetViewValue(animation.animatedValue as Float) }
        val narrow = ValueAnimator.ofFloat(value, 0f)
        narrow.addUpdateListener { animation -> updateTargetViewValue(animation.animatedValue as Float) }
        val animationSet = AnimatorSet()
        animationSet.duration = (value * mCallbackSpeed).toLong()
        animationSet.playSequentially(enlarge, narrow)
        animationSet.interpolator = DecelerateInterpolator()
        animationSet.start()
    }
}