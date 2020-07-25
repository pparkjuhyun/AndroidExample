package ppjh.mvvm.koin.component

class HelloPresenter(private val repo: HelloRepositoryImpl) {
    fun sayHello() = "${repo.hello()} from $this"
}