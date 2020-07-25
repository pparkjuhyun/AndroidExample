package ppjh.mvvm.koin.di

import org.koin.dsl.module.module
import ppjh.mvvm.koin.component.HelloPresenter
import ppjh.mvvm.koin.component.HelloRepository
import ppjh.mvvm.koin.component.HelloRepositoryImpl

val helloModule = module {
    single<HelloRepositoryImpl> { HelloRepositoryImpl() }
    factory { HelloPresenter(get()) }
}
