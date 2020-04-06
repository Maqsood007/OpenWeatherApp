package com.test.nyt_most_viewed.di

/**
 * @author Muhammad Maqsood.
 */


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maqsood007.weatherforecast.ui.cities.CitiesViewModel
import com.maqsood007.weatherforecast.ui.forcasts.WeatherForecastViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        viewModels[modelClass]?.get() as T
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WeatherForecastViewModel::class)
    internal abstract fun mostViewedArticlesViewModel(viewModel: WeatherForecastViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CitiesViewModel::class)
    internal abstract fun CitiesViewModel(viewModel: CitiesViewModel): ViewModel


}