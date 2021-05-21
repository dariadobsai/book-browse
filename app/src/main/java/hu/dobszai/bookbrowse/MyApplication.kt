package hu.dobszai.bookbrowse

import android.app.Application
import hu.dobszai.bookbrowse.viewmodels.DetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val myModule = module {

            viewModel {
                DetailViewModel(get())
            }
        }
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(myModule))
        }

        // Set debugging with Timber
        Timber.plant(Timber.DebugTree())
    }
}