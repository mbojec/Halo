package com.mbojec.halo.dagger

import com.mbojec.halo.ui.ForecastFragment
import com.mbojec.halo.ui.ListFragment
import com.mbojec.halo.ui.MainFragment
import com.mbojec.halo.ui.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeListFragment(): ListFragment

    @ContributesAndroidInjector
    abstract fun contributeForecastFragment(): ForecastFragment

}