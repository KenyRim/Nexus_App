package com.example.nexusapp.fragments;

import android.os.Build
import android.os.Bundle;
import android.transition.Fade
import android.transition.TransitionInflater
import android.view.LayoutInflater;
import android.view.View
import android.view.ViewGroup;
import androidx.fragment.app.Fragment
import com.example.nexusapp.R

abstract class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val fade = Fade()
            fade.excludeTarget(android.R.id.statusBarBackground, true)
            fade.excludeTarget(android.R.id.navigationBarBackground, true)
            sharedElementEnterTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.simple_fragment_transition)

            sharedElementReturnTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.simple_fragment_transition)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanseState: Bundle?
    ): View? {
        return initFragment(inflater, parent, savedInstanseState)
    }

    abstract fun initFragment(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
}