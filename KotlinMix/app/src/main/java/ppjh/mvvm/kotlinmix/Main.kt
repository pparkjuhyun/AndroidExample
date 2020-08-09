package ppjh.mvvm.kotlinmix

fun main() {
    val str = "Hello"
    println(str.myLength())

    val item = Item("newItem")  //java class 사용가능
    println(item.toString())
}

//Kotlin 확장함수
//final로 선언된 클래스 (ex) String)에 추가 기능 확장 가능
fun String.myLength(): Int {
    return this.length
}