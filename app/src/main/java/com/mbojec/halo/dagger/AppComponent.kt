package com.mbojec.halo.dagger

import android.app.Application
import com.mbojec.halo.HaloApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    MainActivityModule::class,
    FragmentModule::class,
    ViewModelModule::class])

interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(haloApplication: HaloApplication)

}