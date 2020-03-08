package com.xinh.travel

import com.xinh.data.di.preferenceModule
import com.xinh.data.di.repositoryModule
import com.xinh.domain.di.useCaseModule
import com.xinh.location.di.locationModule
import com.xinh.presentation.di.presentationModule
import com.xinh.share.BaseApplication
import com.xinh.travel.di.*
import com.xinh.workermanager.di.workerManagerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module


class TravelApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TravelApplication)
            modules(allModules())
        }
    }

    private fun allModules(): List<Module> {
        return listOf(
            appModule,
            useCaseModule,
            managerModule,
            navigatorModule,
            repositoryModule,
            serviceModule,
            preferenceModule,
            networkModule,
            presentationModule,
            locationModule,
            workerManagerModule
        )
    }
}