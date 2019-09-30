package fr.cdreyfus.airqualitysensorapp.core.modules

import fr.cdreyfus.airqualitysensorapp.ui.data.DataViewModel
import fr.cdreyfus.airqualitysensorapp.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelsModule = module {
    viewModel {
        LoginViewModel(adafruitDataSource = get())
    }

    viewModel {
        DataViewModel(adafruitDataSource = get())
    }
}