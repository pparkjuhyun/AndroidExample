package ppjh.mvvm.koin.component

class HelloRepositoryImpl : HelloRepository {
    override fun hello(): String {
        return "hello koin"
    }
}