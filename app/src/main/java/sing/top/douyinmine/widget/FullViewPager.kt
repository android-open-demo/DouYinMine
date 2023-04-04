package sing.top.douyinmine.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import android.view.View.MeasureSpec

/**
 * 解决Scrollview嵌套ViewPager显示不全
 */
class FullViewPager : ViewPager {
    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (null != child) {
                child.measure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                )
                val measuredHeight = child.measuredHeight
                if (measuredHeight > height) {
                    height = measuredHeight
                }
            }
        }
        val h = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, h)
    }

}