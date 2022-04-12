package tech.tennoji.igncodefoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val fragments = ArrayList<Fragment>()
        val titles = ArrayList<String>()
        fragments.add(ArticleFragment.newInstance(5, 30))
        fragments.add(VideoFragment.newInstance(5, 30))
        titles.add(resources.getString(R.string.tab_title_articles))
        titles.add(resources.getString(R.string.tab_title_videos))
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }
        }
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}