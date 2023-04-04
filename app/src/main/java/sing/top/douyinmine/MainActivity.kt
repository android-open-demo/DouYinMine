package sing.top.douyinmine

import android.animation.ArgbEvaluator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import sing.top.douyinmine.widget.TitleLayout
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.NestedScrollView.OnScrollChangeListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import sing.top.douyinmine.widget.ScaleScrollView
import sing.top.douyinmine.widget.FullViewPager
import sing.top.douyinmine.model.TabItemModel
import sing.top.douyinmine.fragment.TabFragment
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var colorPrimary = 0

    private lateinit var statusView: View
    private lateinit var tab1: TabLayout
    private lateinit var tab2: TabLayout
    private lateinit var titleLayout: TitleLayout

    private var evaluator: ArgbEvaluator? = null

    private val statusBarHeight: Int
        get() {
            val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resId > 0) {
                return resources.getDimensionPixelSize(resId)
            }
            return 0
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        colorPrimary = ContextCompat.getColor(this, R.color.colorPrimary)
        initView()
    }

    private fun initView() {
        //设置状态栏和导航栏
        statusView = findViewById(R.id.statusView)
        val lp = LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, statusBarHeight)
        statusView.layoutParams = lp

        statusView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.navigationBarColor = colorPrimary
        window.statusBarColor = Color.parseColor("#00000000")

        val banner = findViewById<AppCompatImageView>(R.id.banner)
        val scrollView = findViewById<ScaleScrollView>(R.id.scrollView)

        scrollView.setTargetView(banner)
        scrollView.setOnScrollChangeListener(onScrollChangeListener)

        tab1 = findViewById(R.id.tab1)
        tab2 = findViewById(R.id.tab2)
        titleLayout = findViewById(R.id.title_layout)
        val viewPager = findViewById<FullViewPager>(R.id.viewpager)

        val list = getTabs()
        val fragmentManager = supportFragmentManager
        viewPager.adapter = object : FragmentPagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getCount() = list.size

            override fun getItem(position: Int): Fragment {
                val model = list[position]
                return fragmentManager.fragmentFactory.instantiate(this@MainActivity.classLoader, model.clazz)
            }

            override fun getPageTitle(position: Int) = list[position].title
        }

        tab1.setupWithViewPager(viewPager)
        tab2.setupWithViewPager(viewPager)
    }

    private fun getTabs(): List<TabItemModel>{
        val tabs: MutableList<TabItemModel> = ArrayList()
        tabs.add(TabItemModel("作品30", TabFragment::class.java.name))
        tabs.add(TabItemModel("动态30", TabFragment::class.java.name))
        tabs.add(TabItemModel("喜欢30", TabFragment::class.java.name))
        return tabs
    }

    private val onScrollChangeListener = OnScrollChangeListener { v, _, _, _, _ ->
        if (::tab1.isInitialized && ::tab2.isInitialized && ::titleLayout.isInitialized && ::statusView.isInitialized) {
            val distance = tab1.top - titleLayout.height - statusView.height
            var ratio = v.scaleY * 1f / distance
            if (distance <= v.scrollY) {
                ratio = 1f
                if (tab2.visibility != View.VISIBLE) {
                    tab2.visibility = View.VISIBLE
                    statusView.setBackgroundColor(colorPrimary)
                }
            } else {
                if (tab2.visibility == View.VISIBLE) {
                    tab2.visibility = View.INVISIBLE
                    statusView.setBackgroundColor(Color.TRANSPARENT)
                }
            }
            if (null == evaluator) {
                evaluator = ArgbEvaluator()
            }
            titleLayout.setBackgroundColor(
                evaluator!!.evaluate(ratio, Color.TRANSPARENT, colorPrimary) as Int
            )
            titleLayout.setTitleColor(
                evaluator!!.evaluate(ratio, Color.TRANSPARENT, Color.WHITE) as Int
            )
        }
    }
}