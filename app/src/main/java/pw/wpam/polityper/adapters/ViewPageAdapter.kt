package pw.wpam.polityper.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import pw.wpam.polityper.fragments.TeamFormFragment

//class ViewPageAdapter(supportFragmentManager: FragmentManager): FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

class ViewPageAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

//    override fun getItem(position: Int): Fragment {
//        return mFragmentList[position]
//    }
//
//    override fun getCount(): Int {
//        return mFragmentList.size
//    }
//
//    fun getPageTitle(position: Int): CharSequence? {
//        return mFragmentTitleList[position]
//    }

    fun addFragment(fragment: Fragment, title: String){
       mFragmentList.add(fragment)
       mFragmentTitleList.add(title)
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun update(data: ArrayList<String>) {
        val textListFragment = this.mFragmentList[0] as TeamFormFragment
        textListFragment.updateDataset(data)
    }

}