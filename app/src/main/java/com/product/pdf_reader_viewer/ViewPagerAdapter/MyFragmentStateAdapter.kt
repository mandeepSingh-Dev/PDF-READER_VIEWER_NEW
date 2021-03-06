package com.product.pdf_reader_viewer.ViewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.product.pdf_reader_viewer.fragments.*

class MyFragmentStateAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {

        if(position==0)
        {
            return Recent_list_Fragment()

        }
        else if(position==1)
        {
            return BookMarks_list_Fragment_()
        }
        else if(position==2)
        {
            return pdf_list_Fragment()
        }
        else{
            return PdfTools_Fragment()
        }

    }
}