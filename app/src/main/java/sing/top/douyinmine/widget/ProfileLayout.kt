package sing.top.douyinmine.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import kotlin.jvm.JvmOverloads
import android.widget.FrameLayout
import sing.top.douyinmine.R
import androidx.appcompat.widget.AppCompatTextView

class ProfileLayout: FrameLayout {

    constructor(context: Context) : this(context,null)
    constructor(context: Context,attrs: AttributeSet?) : this(context,attrs,-1)
    constructor(context: Context,attrs: AttributeSet? ,defStyleAttr: Int) : super(context,attrs,defStyleAttr) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.layout_profile, this)
        findViewById<View>(R.id.avatar).setOnClickListener { v: View? ->
            val tv = findViewById<AppCompatTextView>(R.id.content)
            tv.text =
                "阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德"
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }
}