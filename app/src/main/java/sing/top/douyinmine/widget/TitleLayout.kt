package sing.top.douyinmine.widget

import android.content.Context
import android.util.AttributeSet
import kotlin.jvm.JvmOverloads
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.AppCompatImageView
import sing.top.douyinmine.R
import androidx.annotation.ColorInt

class TitleLayout: RelativeLayout  {
    private var title: AppCompatTextView? = null
    private var finish: AppCompatImageView? = null
    private var menu: AppCompatImageView? = null

    constructor(context: Context) : this(context,null)
    constructor(context: Context,attrs: AttributeSet?) : this(context,attrs,-1)
    constructor(context: Context,attrs: AttributeSet? ,defStyleAttr: Int) : super(context,attrs,defStyleAttr) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.layout_title, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        title = findViewById(R.id.title)
        finish = findViewById(R.id.finish)
        menu = findViewById(R.id.menu)
    }

    fun setTitle(text: CharSequence?) {
        if (null != title) {
            title!!.text = text
        }
    }

    fun setTitleColor(@ColorInt color: Int) {
        if (null != title) {
            title!!.setTextColor(color)
        }
    }
}