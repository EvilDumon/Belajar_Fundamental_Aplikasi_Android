package com.example.myflexiblefragment

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class CategoryFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Category Fragment"

        val btnDetailCategory: Button = view.findViewById(R.id.btn_detail_category)
        btnDetailCategory.setOnClickListener(this)
    }



    override fun onClick(v: View) {
        if (v.id == R.id.btn_detail_category) {
            val mDetailcategoryFragment = LifestyleFragment()

            val mBundle = Bundle()
            mBundle.putString(LifestyleFragment.EXTRA_NAME, "Lifestyle")
            val descriptions = "Kategori ini akan berisi produk-produk lifestyle"

            mDetailcategoryFragment.apply {
                arguments = mBundle
                description = descriptions
            }
            parentFragmentManager
                ?.beginTransaction()
                ?.apply {
                    replace(
                        R.id.frame_container,
                        mDetailcategoryFragment,
                        LifestyleFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                    commit()
                }
        }
    }

}