package io.github.lee.core.vm.err

class ViewModelException(val code: Int, val msg: String) : Throwable(msg) {

}